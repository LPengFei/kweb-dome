package com.cnksi.taglib;

import com.cnksi.kconf.model.Lookup;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 在页面中直接获取数据库 k_lookup表中配置的数据
 * 
 * @author joe
 *
 */
public class KLookupTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(KLookupTag.class);

	// lookup type
	private String type;

	// lookup key
	private String key;

	// default value
	private String defaultValue;

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

			Lookup lookup = Lookup.me.findByPropertity(new String[] { "ltid", "lkey", "enabled" }, new Object[] { type, key, "0" }, Logical.AND);

			if (lookup != null) {
				out.print(StringUtils.defaultIfEmpty(lookup.getStr("lvalue"), ""));
			} else {
				out.print(StringUtils.defaultIfEmpty(defaultValue, ""));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return super.doStartTag();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
