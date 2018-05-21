package com.cnksi.app.controller;

import com.cnksi.kconf.controller.KController;
import com.cnksi.kconf.model.User;
import com.jfinal.ext.plugin.annotation.KRequiresPermissions;

public class MyInfoController extends KController {

	public MyInfoController() {
		super(User.class);
	}

	@KRequiresPermissions(name = "信息编辑")
	public void index() {
		super.doEdit();
		User user = getLoginUser();
		user = user.findById();
		setAttr("record", user);
		render("myinfo_index.jsp");
	}
}
