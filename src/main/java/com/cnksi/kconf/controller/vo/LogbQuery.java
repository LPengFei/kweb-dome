package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;

/**
 * 业务日志查询
 */
@KQuery(select = "SELECT * ", from = "FROM k_logb ", orderBy = " id desc")
public class LogbQuery extends KWebQueryVO {

}
