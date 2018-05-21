package com.cnksi.taglib;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.utils.KStrKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 从model中获取field配置的value
 * 
 * @author joe
 *
 */
@SuppressWarnings("rawtypes")
public class KValueTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(KValueTag.class);

	public static final String cacheName = "kValueTagDatas";

	private Model model;

	private KModelField field;

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {

		JspWriter out = this.pageContext.getOut();

		try {

			Objects.requireNonNull(model);
			Objects.requireNonNull(field);

			String fieldName = field.getStr("field_name");
			Object val = model.get(fieldName, ""); // method(datas.get(i), f);
			Objects.requireNonNull(val);

			String valStr = KStrKit.toStr(val);

			String outString = "<td data-val='" + valStr + "'>" + valStr + "</td>";

			// 如果列表配置里有sql配置，则直接根据sql获取字段值(如获取部门id对应的部门名称等)
			String listViewSql = field.getStr("list_sql");
			if (StrKit.notBlank(listViewSql)) {
				String fnameVal = fieldName + "#" + val;
				valStr = KDataCache.get(fnameVal);

				if (StrKit.isBlank(valStr)) {

					if (listViewSql.contains("{")) {
						listViewSql = patternDeal(listViewSql);
					}
					if (listViewSql.contains("?"))
						valStr = KStrKit.toStr(Db.queryColumn(listViewSql, val));
					else
						valStr = KStrKit.toStr(Db.queryColumn(listViewSql));

					// 缓存数据库中存在字段的数据
					// boolean contains = Stream.of(model._getAttrNames()).anyMatch(attrName -> fieldName.equals(attrName));
					// if(ArrayUtils.contains(model._getAttrNames(), fieldName))
					KDataCache.put(fnameVal, valStr);
				}

			} else {
				// //根据数据源配置获取id对应值
				// String dataSourceName = setting.getDataconfig().getString("dataSource");
				//
				// //读取数据源 查询条件所需参数
				// if (StrKit.notBlank(dataSourceName)) {
				// Record data = DataSourceManager.me.getOneData(dataSourceName, valStr);
				// if(data != null){
				// valStr = data.get("name");
				// }
				// }
			}

			// 日期、数字格式化
			String listFormatStr = field.getStr("list_format");
			if (StrKit.notBlank(field.getStr("list_format")) && val != null) {
				if (val instanceof Date) {
					SimpleDateFormat sd = new SimpleDateFormat(listFormatStr);
					valStr = sd.format((Date) val);
				} else if (val instanceof Number || KStrKit.isNumber(valStr)) {
					DecimalFormat decimalFormat = new DecimalFormat(listFormatStr);
					valStr = decimalFormat.format(Double.parseDouble(valStr));
				}
			}

			// 读取样式配置
			String cssClass = KStrKit.trimEmpty(field.getStr("list_class"));
			String style = KStrKit.trimEmpty(field.getStr("list_style"));
			if (StrKit.notBlank(style) && !style.endsWith(";"))
				style += ";";

			String width = KStrKit.trimEmpty(field.getStr("list_width"));
			if (StrKit.notBlank(width))
				style += "width: " + width.trim() + "px;";

			String align = KStrKit.trimEmpty(field.getStr("list_align"));
			if (StrKit.notBlank(align))
				style += "text-align: " + align + ";";

			String label = valStr;
			if (StrKit.notBlank(field.getStr("list_text"))) {
				label = field.getStr("list_text");
				label.replace("'", "\"");
				label = patternDeal(label);
			}
			String link_addr = field.getStr("link_addr");
			link_addr = patternDeal(link_addr);

			String link_attr = field.getStr("link_attr");
			link_attr = patternDeal(link_attr);

			String pageContextPath = pageContext.getServletContext().getContextPath();
			if (StrKit.notBlank(link_addr)) {

				String labelFormat = "<a href='%s' %s > %s</a>";
				if (!link_addr.startsWith("http://")) {
					link_addr = pageContextPath + link_addr;
				}
				label = String.format(labelFormat, link_addr, link_attr, label);
			}

			outString = String.format("<td style='%s' class='%s' title='%s' data-val='%s' >%s</td>", style, cssClass, valStr, valStr, label);

			out.print(outString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return super.doStartTag();
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setField(KModelField field) {
		this.field = field;
	}

	public KModelField getField() {
		return field;
	}

	/**
	 * 正则表达式处理
	 * 
	 * @param oriStr
	 * @return
	 */
	private String patternDeal(String oriStr) {
		if (oriStr == null) {
			return "";
		}
		oriStr = oriStr.replaceAll("\\{", "#").replaceAll("\\}", "#");
		Pattern p = Pattern.compile("#(.+?)#");
		Matcher m = p.matcher(oriStr);
		while (m.find()) {
			String dynic = m.group(0); // 查找动态参数#userid#
			String key = dynic.replaceAll("#", "");

			if (model.get(key) != null && !"null".equals(model.get(key))) {
				oriStr = oriStr.replaceAll(dynic, model.get(key).toString());
			} else {
				oriStr = oriStr.replaceAll(dynic, "");
			}
		}
		return oriStr;
	}
}
