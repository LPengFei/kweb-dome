package com.cnksi.kconf.controller;

import com.cnksi.kconf.controller.vo.MenuQuery;
import com.cnksi.kconf.model.Menu;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.utils.IConstans;
import com.jfinal.core.Injector;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单管理controller
 */
public class MenuController extends KController {

	public MenuController() {
		super(Menu.class);
	}

	/**
	 * 菜单模块配置
	 */
	public void module_index() {
		keepPara();

		MenuQuery queryParam = Injector.injectBean(MenuQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			queryParam.addFilter("type='module'");
			queryParam.setOrderField("ord");
			queryParam.setOrderDirection("asc");
			setAttr("page", Menu.me.paginate(queryParam));
		}
		render("menu_module.jsp");

	}

	/**
	 * 父级菜单列表
	 */
	public void parent_index() {
		keepPara();

		MenuQuery queryParam = Injector.injectBean(MenuQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			queryParam.addFilter(" type='parent'");
			queryParam.setOrderField("ord");
			queryParam.setOrderDirection("asc");
			setAttr("page", Menu.me.paginate(queryParam));
		}
		render("menu_parent.jsp");
	}

	/**
	 * 子级菜单列表
	 */
	public void child_index() {
		keepPara();

		MenuQuery queryParam = Injector.injectBean(MenuQuery.class, null, getRequest(), false);
		if(queryParam!=null) {
			queryParam.addFilter("pmenuid is not null");
			queryParam.addFilter("pmenuid != ''");
			queryParam.addFilter("type='child'");
			queryParam.setOrderField("ord");
			queryParam.setOrderDirection("asc");

			setAttr("page", Menu.me.paginate(queryParam));
		}
		render("menu_child.jsp");
	}

	/**
	 * 跳转编辑/新增页面
	 */
	public void edit() {
		keepPara();
		super.doEdit();

		String idValue = getPara("id", getPara());

		Menu record = null;
		if (idValue != null) {
			record = Menu.me.findById(idValue);
		} else {
			record = new Menu();
			record.set("pmenuid", getPara("pmenuid", ""));
			record.set("type", getPara("type", ""));
		}

		if ("child".equals(getPara("type"))) {
			// 查询父类菜单
			List<Menu> menus = Menu.me.findListByPropertitys(new String[] { "type", "enabled" },
					new Object[] { "parent", "0" }, Logical.AND);
			setAttr("pmenus", menus);
		} else if ("parent".equals(getPara("type"))) {
			// 查询父类菜单
			List<Menu> menus = Menu.me.findListByPropertitys(new String[] { "type", "enabled" },
					new Object[] { "module", "0" }, Logical.AND);
			setAttr("pmenus", menus);
		}
		//查询所有的模型
		List<KModel> models = KModel.me.find("select * from k_model where enabled = 0");
		setAttr("models",models);

		setAttr("record", record);

		render("menu_form.jsp");
	}

	/**
	 * 保存/修改
	 */
	public void save() {
		Menu record = getModel(Menu.class, "record");
		String tabid = getPara("tableid");

		if(StrKit.notBlank(record.getStr("murl"))){
			record.set("murl",record.getStr("murl").trim());
		}

		if (record.get("menuid") != null) {
			Menu _rec = Menu.me.findById(record.getStr("menuid"));
			if (_rec != null) {
				if (null == record.getStr("type")) {
					record.set("type", _rec.get("type"));
				}
				record.update();
			} else {
				record.save();
			}
		}

		String mid = getPara(IConstans.MID);
		if(StrKit.notBlank(mid)) {
			KModel kModel =getKModel(mid);
			kModel.set(IConstans.TABID, record.getStr("menuid") + "-list");

			kModel.update();
		}
		bjuiAjax(200, true, tabid);

	}

	/**
	 * 删除
	 */
	public void delete() {
		Menu record = Menu.me.findById(getPara("id"));
		if (record != null) {
			if (!StrKit.isBlank(record.getStr("pmenuid"))) {
				record.set("enabled", 1).update();
			} else {
				record.set("enabled", 1).update();
				Menu.me.delChildMenu(record.getStr("menuid")); // 删除子级菜单
			}
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}
	}

	public void export() throws IOException {
		List<Menu> menus = Menu.me.findExitAll();
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, menus);
	}


	/**
	 * 模板导入路径
	 */
	@Override
	public void importxls() {
		keepPara();
		setAttr("modelName", "menu");
		setAttr("appid", "kconf");
		render(importJsp);
	}

	public void importxlsed() {
		String errorFile = "", msg = "";
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					Menu record = new Menu();
					record._setAttrs(map);

					//查询相应得字段是否存在，如果不存在则创建
					Menu menu = Menu.me.findById(record.getStr("menuid"));

					if(null!=menu){
						record.update();
					}else {
						record.save();
					}
				}
			} else {
				msg = "导入错误：数据校验失败，请查看校验结果文件！";
				errorFile = result.getSaveFile().getName();
				File errorFolder = new File(PathKit.getWebRootPath(), "error");
				if (!errorFolder.isDirectory()) {
					errorFolder.mkdirs();
				}
				File moveFile = new File(PathKit.getWebRootPath() + File.separator + "error",
						result.getSaveFile().getName());
				result.getSaveFile().renameTo(moveFile);
			}
		} catch (ExcelImportException e) {
			e.printStackTrace();
			msg = "导入错误：" + e.getMessage();
		}
		Map<String, Object> resultMap = bjuiAjaxBackMap(StrKit.notBlank(msg) ? 300 : 200, msg, false);

		if (Integer.parseInt(resultMap.get("statusCode").toString()) == 200)
			resultMap.put("message", "导入数据成功！");

		resultMap.put("errorFile", errorFile);

		renderJson(resultMap);

	}


}
