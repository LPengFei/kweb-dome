package com.cnksi.kconf.controller;

import java.io.File; 
import java.io.IOException;
import java.util.*;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.service.DepartmentService;
import com.cnksi.kcore.jfinal.model.KModel;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.cnksi.utils.IConstans;
import com.cnksi.utils.IDataSource;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.jfinal.ext.plugin.annotation.KRequiresPermissions;
import com.cnksi.kconf.controller.KController;
import com.cnksi.kconf.model.LinkAuthority;
/**
 * 
 */
 @KRequiresPermissions(name = "权限控制",model=LinkAuthority.class)
 public class LinkAuthorityController extends KController {

 	public LinkAuthorityController(){
		super(LinkAuthority.class);
	} 

	@KRequiresPermissions(name = "列表")
	public void index() {

        //User user = getSessionAttr(KConfig.SESSION_USER_KEY);
        Long deptId = getSessionAttr("user_dept_id");
        removeSessionAttr("authority_dept_id");
        if (null != deptId) {
            setAttr("authority_dept_id", deptId);
        }

        List<Record> record = Department.me.findAllDeptTree();
        if (record != null && record.size() > 0)
            record.get(0).set("open", true);
        setAttr("ztreedata", JsonKit.toJson(record));
        keepPara();
        render("authority_index.jsp");

		/*KWebQueryVO queryParam = super.doIndex();

		if (queryParam != null) setAttr("page", LinkAuthority.me.paginate(queryParam));

		render(listJsp);*/
	}

    /**
     * 查询所有权限
     */
	public void deptplus(){
		keepPara();
	    String mid = getPara(IConstans.MID);
	    Long kid = getParaToLong(IConstans.KID);
	    Long dept_id = getParaToLong(IConstans.DEPT_ID);
		removeSessionAttr("authority_dept_id");
		setSessionAttr("authority_dept_id",dept_id);
	    String ids = Department.me.getChildLst(dept_id);
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize",10);
	    Page<Department> page = Department.me.paginate(pageNumber,pageSize,
				"SELECT d.* ","FROM k_model_link_authority r LEFT JOIN k_department d ON d.id = r.`dept_id` WHERE r.mid = ? " +
						"AND r.enabled = 0 AND d.`enabled` =0 AND r.kid = ?  and r.dept_id in ("+ids+")",mid,kid);
		page.getList().forEach(department -> DepartmentService.service.setparam(department));
        setAttr("page", page);
        render("authority_plus.jsp");
    }


    /**
     * 查询剩余部门
     */
    public void deptminus(){
    	keepPara();
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize",10);
        String mid = getPara(IConstans.MID);
        Long kid = getParaToLong(IConstans.KID);
        Long dept_id = getParaToLong(IConstans.DEPT_ID);

        String type = getPara("type");
        String relevant = getPara("relevant");
        String is_zg_dept = getPara("is_zg_dept");
        StringBuilder sb = new StringBuilder();
        if(StrKit.notBlank(type)){
        	sb.append(" and d.type = '"+type+"'");
		}

		if(StrKit.notBlank(relevant)){
        	sb.append(" and d.relevant = '"+relevant+"'");
		}

		if(StrKit.notBlank(is_zg_dept)){
        	sb.append(" and d.relevant = '"+is_zg_dept+"'");
		}
        String ids = Department.me.getChildLst(dept_id);

        List<Department> depts = Department.me.find("select d.* from k_department d where d.enabled = 0 and d.id in ("+ids+")"+sb.toString());
        List<Department> departments = Department.me.find("SELECT d.* FROM k_model_link_authority r LEFT JOIN k_department d " +
                "ON d.id = r.`dept_id` WHERE r.mid = ? AND r.enabled = 0 AND d.`enabled` =0 AND r.kid = ?  and r.dept_id in ("+ids+")" +sb.toString(), mid,kid);
        depts.removeAll(departments);

		List<Department> records=null;
		if(depts.size()>0){
			records = depts.subList((pageNumber-1)*pageSize,pageNumber*pageSize>depts.size()?depts.size():pageNumber*pageSize);
			Page<Department> page = new Page<>(records,pageNumber,pageSize,depts.size()%pageSize>0?depts.size()/pageSize+1:depts.size()/pageSize,depts.size());

			page.getList().forEach(department -> DepartmentService.service.setparam(department));
			setAttr("page", page);
		}
        render("authority_minus.jsp");
    }

	@KRequiresPermissions(name = "编辑")
	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		LinkAuthority record = null;
		if (idValue != null) {
			record = LinkAuthority.me.findById(idValue);
		}else{
			record = new LinkAuthority();

		}
		setAttr("record", record);

		render(formJsp);
	}


	@KRequiresPermissions(name = "保存")
	public void save() {
    	String mid = getPara(IConstans.MID);
    	String kid = getPara(IConstans.KID);
    	String tabid = getPara(IConstans.TABID);
    	String depts =getPara(IConstans.IDS);
        LinkAuthority record = new LinkAuthority();
        record.set(IConstans.MID,mid);
        record.set(IConstans.KID,kid);
        record.set(IConstans.CREATE_TIME,new Date());
        record.set(IConstans.CREATOR,getLoginUserId());
        record.set(IConstans.CREATE_NAME,getLoginUserName());

        List<LinkAuthority> linkAuthorities = new ArrayList<>();
        String[] dept = depts.split(",");
        LinkAuthority linkAuthority;
        for(int i=0;i<dept.length;i++){
        	linkAuthority = new LinkAuthority();
			linkAuthority.put(record);
			linkAuthority.set(IConstans.DEPT_ID,Long.valueOf(dept[i]));
        	linkAuthorities.add(linkAuthority);
		}

        Db.batchSave(linkAuthorities,linkAuthorities.size());

		bjuiAjax(200, false,tabid);
	}



	@KRequiresPermissions(name = "详情查看")
	public void view() {

		super.doEdit();

		String idValue = getPara(IConstans.ID, getPara());
		LinkAuthority record = null;
		if (idValue != null) {
			record = LinkAuthority.me.findById(idValue);
			setAttr(IConstans.RECORD, record);
			render(detailJsp);
		}else{
			bjuiAjax(300, IDataSource.NOT_DATA, false);
		}
	}



	@KRequiresPermissions(name = "删除")
	public void delete() {
		String mid = getPara(IConstans.MID);
		String kid = getPara(IConstans.KID);
		String tabid = getPara(IConstans.TABID);
		String depts =getPara(IConstans.IDS);

		Db.update("update k_model_link_authority set enabled = 1 where mid = ? and  kid = ? and dept_id in ("+depts+")",mid,kid);

		bjuiAjax(200,false,tabid);
		/*LinkAuthority record = LinkAuthority.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
		} else {
			bjuiAjax(300);
		} */

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<LinkAuthority> p = LinkAuthority.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					LinkAuthority record = new LinkAuthority();
					record.put(map);
					record.save();
				}
			} else {
				msg = "导入错误：数据校验失败，请查看校验结果文件！";
				errorFile = result.getSaveFile().getName();
				File errorFolder = new File(PathKit.getWebRootPath(), "error");
				if (!errorFolder.isDirectory()) {
					errorFolder.mkdirs();
				}
				File moveFile = new File(PathKit.getWebRootPath() + File.separator + "error", result.getSaveFile().getName());
				result.getSaveFile().renameTo(moveFile);
			}
		} catch (ExcelImportException e) {
			e.printStackTrace();
			msg = "导入错误：" + e.getMessage();
		}
		Map<String, Object> resultMap = bjuiAjaxBackMap(StrKit.notBlank(msg) ? 300 : 200, msg, false);

		if(Integer.parseInt(resultMap.get("statusCode").toString())==200)
			resultMap.put("message","导入数据成功！");

		resultMap.put("errorFile", errorFile);

		renderJson(resultMap);

	}



	public void lookup() {
		keepPara();

		String kmodelid = getPara("modelId", getPara());
		KWebQueryVO queryParam;

		if(kmodelid!=null){
			KModel kModel = getKModel(kmodelid);
			queryParam = super.doIndex(kModel, null);
		}else
			queryParam = super.doIndex();

		if(queryParam!=null)
			setAttr("page", LinkAuthority.me.paginate(queryParam));

		setAttr("modelName","linkauthority");
		render(lookupJsp);
	}


}
