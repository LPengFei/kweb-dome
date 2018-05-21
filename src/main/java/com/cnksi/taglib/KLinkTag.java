package com.cnksi.taglib;

import com.cnksi.kconf.model.ModelLink;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("rawtypes")
public class KLinkTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(KLinkTag.class);

	private Model model;

	private ModelLink link;

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
			Objects.requireNonNull(link);

			boolean flag = true;
			String outString;
			String href="";
			Pattern p = Pattern.compile("#(.+?)#");
			if(StrKit.isBlank(link.getStr("link"))){
				flag = false;
				outString = "<a style=\"%s\"  %s >%s</a>";
			}else{
				outString = "<a href=\"%s\"  style=\"%s\"  %s >%s</a>";
				 href = link.getStr("link").replaceAll("\\{", "#").replaceAll("\\}", "#");
				Matcher m = p.matcher(href);
				while (m.find()) {
					String dynic = m.group(0); // 查找动态参数#userid#
					String key = dynic.replaceAll("#", "");

					if (model.get(key) != null)
						href = href.replaceAll(dynic, model.get(key).toString());

				}
			}

			String other = "";
			if(StrKit.notBlank(link.getStr("other"))){

				 other = link.getStr("other").replaceAll("\\{", "#").replaceAll("\\}", "#");
				Matcher mo = p.matcher(other);
				while (mo.find()) {
					String dynic = mo.group(0); // 查找动态参数#userid#
					String key = dynic.replaceAll("#", "");

					if (model.get(key) != null)
						other = other.replaceAll(dynic, model.get(key).toString());
				}
			}

			if(flag) {
			    //href以javascript开头，不是url，不添加contextPath前缀
			    if(!href.trim().startsWith("javascript")){
			        href = pageContext.getServletContext().getContextPath() + href;
                }
				outString = String.format(outString, href, link.getStr("style"), other, link.getStr("label"));
			}else{
				outString = String.format(outString,  link.getStr("style"), other, link.getStr("label"));

			}
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

	public ModelLink getLink() {
		return link;
	}

	public void setLink(ModelLink link) {
		this.link = link;
	}

}
