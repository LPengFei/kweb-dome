package com.cnksi.kconf.controller;

import com.cnksi.app.dataverifyhandler.UserVerifyHandler;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.controller.vo.UserQuery;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.User;
import com.cnksi.kconf.service.UserService;
import com.cnksi.kcore.exception.KException;
import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.cnksi.kcore.utils.MD5Util;
import com.cnksi.kcore.utils.PinyinUtil;
import com.cnksi.sec.RSAUtils;
import com.cnksi.sec.SecUserService;
import com.cnksi.utils.IConstans;
import com.cnksi.utils.IDataSource;
import com.jfinal.aop.Before;
import com.jfinal.core.Injector;
import com.jfinal.ext.plugin.annotation.KRequiresPermissions;
import com.jfinal.ext.plugin.shiro.ShiroMethod;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.interfaces.RSAPrivateKey;
import java.sql.SQLException;
import java.util.*;

public class UserController extends KController {
	public UserController() {
		super(User.class);
	}

    public void index() {

		User user = getSessionAttr(KConfig.SESSION_USER_KEY);
		Long deptId = getSessionAttr("user_dept_id");
		removeSessionAttr("user_dept_id");
		if (null != deptId) {
			setAttr("user_dept_id", deptId);
		}

		List<Record> record = Department.me.selectChidrenByParentId(user.getLong("dept_id"));
		if (record != null && record.size() > 0)
			record.get(0).set("open", true);
		setAttr("ztreedata", JsonKit.toJson(record));
		keepPara();
		render("user_index.jsp");
	}

	public void user() {
		super.doIndex();
		Department department = getUserDept();
		Long deptid = getParaToLong("deptid");

		if (deptid == null) {
			deptid = Long.valueOf(department.getLong("id"));
		}
		String ids = Department.me.getDeptIds(deptid);

		UserQuery queryParam = Injector.injectBean(UserQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			queryParam.setDeptid(null);
				if(null != queryParam.getUname()) {
					queryParam.addFilter("(u.uname like '%" + queryParam.getUname() + "%' or u.uname_pinyin like '%" + queryParam.getUname() + "%')");
					queryParam.setUname(null);

			}
			queryParam.addFilter(" u.dept_id in (" + ids + ")");
			setAttr("page", User.me.paginate(queryParam));
		}

		setAttr("isAdminUser", isAdminUser());

		render("user.jsp");
	}

	/**
	 * 解除登录锁定
	 */
	@KRequiresPermissions(name = "解锁人员")
	public void unlock(){
		User user = new User().setPkVal(getPara());
		boolean result = SecUserService.me.unlock(user);

		if (result == true){
			bjuiAjax(200);
		}else{
			bjuiAjax(300);
		}

//		String name = new User().setPkVal(getPara()).findById().getName();
//		Logb.me.saveLogb(this, name + " 用户解除锁定", "用户解锁");
	}

	public void edit() {
		super.doEdit();
		Long deptId = getParaToLong("deptid");
		String idValue = getPara("id");
		User record = null;
		if (idValue != null) {
			record = User.me.findById(idValue);
		} else {
			record = getModel(User.class, "", true);
		}
		record.set("dept_id", deptId == null ? record.get("dept_id") : deptId);
		setAttr("record", record);
		render("user_form.jsp");
	}

    public void save() {
        // 校验上传的是否是图片
        // List<String> allowType = Arrays.asList("image/bmp","image/png","image/gif","image/jpg","image/jpeg","image/pjpeg");

        User record = getModel(User.class, "record");
		record.set(IConstans.UNAME,record.getStr(IConstans.UNAME).replaceAll(" ",""));

		if(StrKit.notBlank(record.getStr(IConstans.IDCARD))){
			String str = record.getStr(IConstans.IDCARD);
			try {
				int count = Integer.parseInt(str.substring(str.length() - 2, str.length() - 1));
				if (count % 2 == 0) {
					record.set(IConstans.SEX, IDataSource.SEX_WOMAN);
				} else {
					record.set(IConstans.SEX, IDataSource.SEX_MAN);
				}
			} catch (Exception e) {
				bjuiAjax(300, "身份证格式不正确！", false, "");
			}
		}
        setSessionAttr("user_dept_id", record.getLong("dept_id"));
        record.set("uname_pinyin", PinyinUtil.getPinYinHeadChar(record.get("uname")).toLowerCase());
        //兼容三种人资质小写字母的输入
  		String qualification_principal = record.getStr("qualification_principal");
  		if (StringUtils.isNotBlank(qualification_principal)) {
  			record.set("qualification_principal", qualification_principal.toUpperCase());
  		}
  		String qualification_gzpqfr = record.getStr("qualification_gzpqfr");
  		if (StringUtils.isNotBlank(qualification_gzpqfr)) {
  			record.set("qualification_gzpqfr", qualification_gzpqfr.toUpperCase());
  		}
  		String qualification_gzxkr = record.getStr("qualification_gzxkr");
  		if (StringUtils.isNotBlank(qualification_gzxkr)) {
  			record.set("qualification_gzxkr", qualification_gzxkr.toUpperCase());
  		}
        if (record.getLong("id") != null) {
            // 判断电话号码和账户是不是重复，电话号码因为可以为空，所以只能在电话号码不为空的情况下做重复校验
            User user = User.me.findById(record.getLong("id"));
            User existUser = User.me.findByPropertity(new String[]{"uaccount", "enabled"}, new Object[]{record.getStr("uaccount"), "0"}, Logical.AND);
			String idcard = record.getStr("idcard");
			User idcardUser = User.me.findByPropertity(new String[] { "idcard", "enabled" },
					new Object[] { idcard, "0" }, BaseModel.Logical.AND);

            if (existUser != null && !existUser.getStr("uaccount").equals(user.getStr("uaccount"))) {
                bjuiAjax(300, "用户账号(" + record.getStr("uaccount") + ")已在系统中存在！");
                return;
            } else if (null != record.get("tel")) {
                existUser = User.me.findByPropertity(new String[]{"tel", "enabled"}, new Object[]{record.getStr("tel"), "0"}, Logical.AND);
                if (existUser != null && !existUser.getStr("tel").equals(user.getStr("tel"))) {
                    bjuiAjax(300, "用户电话号码(" + record.getStr("tel") + ")已在系统中存在！");
                    return;
                }
            }else if (idcardUser != null && !idcardUser.getStr("idcard").equals(user.getStr("idcard"))) {
				bjuiAjax(300, "身份证号码(" +idcard + ")已在系统中存在！");
				return;
			}
            record.update();
            if(getLoginUserId().equals(record.getLong(IConstans.ID))){
            	removeSessionAttr(KConfig.SESSION_USER_KEY);
				Subject subject = SecurityUtils.getSubject();
				Session session = subject.getSession(false);
				session.setTimeout(-1000 * 60 * 30); // 设定超时时间
				session.setAttribute(KConfig.SESSION_USER_KEY, record);
			}


            bjuiAjax(200, "更新成功", true);

        } else {
            String uaccount = PinyinUtil.getAccountPinyin(record.getStr("uname"));
            String idcard = record.getStr("idcard");
            if (idcard != null && idcard.length() >=4) {
                uaccount += idcard.substring(idcard.length() - 4, idcard.length());
            }
            record.set("uaccount", uaccount);
            // 判断电话号码和账户是不是重复，电话号码因为可以为空，所以只能在电话号码不为空的情况下做重复校验
            User existUser = User.me.findByPropertity(new String[]{"uaccount", "enabled"}, new Object[]{record.getStr("uaccount"), "0"}, Logical.AND);

			User idcardUser = User.me.findByPropertity(new String[] { "idcard", "enabled" },
					new Object[] { idcard, "0" }, BaseModel.Logical.AND);

            if (existUser != null) {
                bjuiAjax(300, "用户账号(" + record.getStr("uaccount") + ")已在系统中存在！");
                return;
            } else if (null != record.get("tel")) {
                existUser = User.me.findByPropertity(new String[]{"tel", "enabled"}, new Object[]{record.getStr("tel"), "0"}, Logical.AND);
                if (existUser != null) {
                    bjuiAjax(300, "用户电话号码(" + record.getStr("tel") + ")已在系统中存在！");
                    return;
                }
            }else if (idcardUser != null) {
				bjuiAjax(300, "身份证号码(" +idcard + ")已在系统中存在！");
				return;
			}
            record.set(record.getPkName(), -100);
            record.set("upwd", MD5Util.getMd5(uaccount + "*"));
            record.set("id", -100);
            record.save();

            bjuiAjax(200, "保存成功，初始化密码为：" + uaccount + "*", true);

        }

    }

	public void delete() {
		User record = User.me.findById(getPara("id"));
		if (record != null) {
			setSessionAttr("user_dept_id", record.get("dept_id"));
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		}

	}

	/**
	 * 重置密码
	 */
	public void resetPwd() {
		User record = User.me.findById(getPara("id"));
		if (record != null) {
			record.set("upwd", MD5Util.getMd5(record.get("uaccount") + "*"));
			if (record.update()) {
				bjuiAjax(200, "操作成功，密码重置为初始密码：" + record.get("uaccount") + "*");
			} else {
				bjuiAjax(300, "操作失败,内部错误");
			}
		} else {
			bjuiAjax(300, "操作失败,用户不存在");
		}
	}

	/**
	 * 修改密码
	 */
	public void changePwd() {
		// 返回修改密码页面
		if (getRequest().getMethod().equalsIgnoreCase("GET")) {
			RSAUtils.genRsa(getRequest());
			render("user_change_pwd_form.jsp");
			return;
		}

		User record = getModel(User.class, "record");
		record.setPkVal(getLoginUserId());
		record.set("uaccount", getLoginUser().get("uaccount"));
		Objects.requireNonNull(record.get("upwd"));
		RSAPrivateKey privateKey = getSessionAttr(RSAUtils.SESSION_RSA_PRIVATE_KEY);
		String upwd = RSAUtils.decryptByPrivateKey(record.get("upwd"), privateKey);
		//验证密码md5完整性
//		String[] arr = upwd.split("-");
//		Preconditions.checkArgument(arr.length == 2 && arr[0].equals(MD5Util.getMd5(arr[1])), "数据被篡改");

		// 密码格式校验
		try {
			SecUserService.me.validPassword(upwd, record.getStr("uaccount"));
		} catch (IncorrectCredentialsException e) {
			bjuiAjax(300, e.getMessage());
			return;
		}

		Object uid = record.getPkVal();
		//新老密码不能一样
		User userInDb = User.me.findById(uid);
		upwd = SecUserService.me.encodePassword(upwd);
		if (Objects.equals(userInDb.get("upwd"), upwd)){
			bjuiAjax(300, "新旧密码不能相同");
			return;
		}

		// 更新新密码
		record.set("upwd", upwd);
		record.set("change_pwd_time", new Date());
		if (record.getPkVal() == null && ShiroMethod.user()) {
			record.setPkVal(getLoginUser().getPkVal());
		}
		if (record.update()) {
			bjuiAjax(200, "操作成功", true);
		} else {
			bjuiAjax(300);
		}
	}


	/**
	 * 部门是否存在用户
	 */
	public void isExistUsers() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String dept_id = getPara("dept_id");
		User user = User.me.findByPropertity(new String[] { "enabled", "dept_id" }, new Object[] { "0", dept_id }, Logical.AND);
		if (user == null) {
			resultMap.put("statusCode", "200");
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", "300");
			renderJson(resultMap);
		}
	}

	public void export() throws IOException {
		super.doIndex();
		Department department = getUserDept();
		Long deptid = getParaToLong("deptid");

		if (deptid == null) {
			deptid = Long.valueOf(department.getLong("id"));
		}
		String ids = Department.me.getDeptIds(deptid);

		UserQuery queryParam = Injector.injectBean(UserQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			String uname = queryParam.getUname();
			String uaccount = queryParam.getUaccount();

			if (StringUtils.isNotBlank(uaccount)) {
				uaccount = URLDecoder.decode(uaccount,"UTF-8");
				queryParam.setUaccount(null);
				queryParam.setUaccount(uaccount);
			}

			if(null !=uname) {
				uname = URLDecoder.decode(uname, "UTF-8");
				queryParam.addFilter("(u.uname like '%" + uname + "%' or u.uname_pinyin like '%" +uname + "%')");
				queryParam.setUname(null);

			}
			queryParam.setDeptid(null);
			queryParam.addFilter(" u.dept_id in (" + ids + ")");
			Page<User> p = User.me.paginate(queryParam);
			String xlsid = getPara("xlsid", "-1");
			super.export(xlsid, p.getList());
		}

	}

	@Override
	protected ExcelImportResult<Map<String, Object>> importxls(String xlsid, UploadFile xlsFile) {

		ExcelImportResult<Map<String, Object>> datas = super.importxls(xlsid, xlsFile);

		return datas;
	}

	public void json() {
		renderJson(UserService.service.comboboxData());
	}

	/**
	 * 查询对应用户的部门ID，如果不存在则创建相应部门
	 * @param dept_id
	 * @param newDepts
	 * @param sz_dept_name
	 * @param bz_name
	 */
	private Long getDeptIdAndNewDept(Long dept_id,List<Department> newDepts,String sz_dept_name,String bz_name){

	    Long user_dept_id = dept_id;
		//查询当前单位下面的所有部门
		String ids = Department.me.getChildLst(dept_id);

		//所在部门和班组的类型必须为科室或者是班组
		List<String> sz_types = new ArrayList<>(Arrays.asList(IDataSource.DEPT_TYPE_KESHI, IDataSource.DEPT_TYPE_BANZU));

		List<Department> sz_departs = new ArrayList<>();
		String bz_ids = "";
		Department sz_dept = new Department();
		if(StrKit.notBlank(sz_dept_name)) {
			//2.sz_dept_name所在部门
			sz_departs = Department.me.getDepartByDnameAndId(sz_dept_name, ids, sz_types);
			if (sz_departs.size() > 0) {
				//所在部门下面的所有部门ID
				bz_ids = Department.me.getChildLst(sz_departs.get(0).getLong(IConstans.ID));
				for (Department depart : sz_departs) {
					bz_ids = bz_ids + Department.me.getChildLst(depart.getLong(IConstans.ID));
				}

			}else{
				//如果所在部门不存在，则插入相应的所在部门，默认规定为科室
                user_dept_id = setKsDept(dept_id,newDepts,sz_dept_name,sz_dept);
			}
		}


		//班组数据
		List<Department> bz_departs=new ArrayList<>();
		if(StrKit.notBlank(bz_name)){
			//3.banzu所在班组
            if(StrKit.notBlank(bz_ids)) {
                bz_departs = Department.me.getDepartByDnameAndId(bz_name, bz_ids, sz_types);
            }
			//如果对应的班组存在，则用户的部门ID等于该班组ID
			if(bz_departs.size()>0){
                user_dept_id = bz_departs.get(0).get(IConstans.ID);
			}else {
				//判断相应班组是否已经插入，如果已经插入则找到对应的部门ID，如果没有插入则新增相应班组
                user_dept_id = 	setbzDept(dept_id,newDepts, sz_departs, bz_name,sz_dept);
			}
		}else{
			//如果班组对应字段内容为空，则对应的用户部门ID为相应的所在部门ID
            if(sz_dept.getLong(IConstans.ID)!=null){
                user_dept_id = sz_dept.getLong(IConstans.ID);
            }else if(sz_departs.size()>0){

                user_dept_id = sz_departs.get(0).getLong(IConstans.ID);
            }
		}
		return  user_dept_id;
	}

	/**
	 * 插入科室数据（所在部门）
	 * @param dept_id
	 * @param newDepts
	 * @param sz_dept_name
	 */
	private Long setKsDept(Long dept_id,List<Department> newDepts,String sz_dept_name,Department sz_dept){
	    Long ks_dept_id =dept_id;
        boolean flag = false;
		for (Department newDept: newDepts){
		   if(dept_id.equals(newDept.getLong(IConstans.PID)) && sz_dept_name.equals(newDept.getName())){
               flag = true;
               ks_dept_id = newDept.getLong(IConstans.ID);
               sz_dept._setAttrs(newDept);
               break;
		   }
		}
		if(!flag){
			Department	dept = new Department();
			dept.set(IConstans.ID,-100);
			dept.set(IConstans.TYPE,IDataSource.DEPT_TYPE_KESHI);
			dept.set(IConstans.PID,dept_id);
			dept.set(IConstans.DNAME,sz_dept_name);
			dept.set(IConstans.SHORT_NAME,sz_dept_name);
			dept.set(IConstans.PINYIN,PinyinUtil.getPinYinHeadChar(sz_dept_name).toLowerCase());
			dept.set(IConstans.JCDWID,dept_id);

			dept.save();
            ks_dept_id = dept.getLong(IConstans.ID);
			sz_dept._setAttrs(dept);
			newDepts.add(dept);
		}
		return   ks_dept_id;
	}

	/**
	 * 插入班组数据
	 * @param dept_id
	 * @param newDepts
	 * @param sz_departs
	 * @param bz_name
	 */
	private Long setbzDept(Long dept_id,List<Department> newDepts,List<Department> sz_departs,String bz_name,Department sz_dept){
	    Long bz_dept_id =dept_id;
		 boolean flag = false;
		 //判断该班组部门是否已经插入，则找到相应的部门ID
         for (Department newDept: newDepts){
         	//所在部门是否已经插入
         	if(sz_dept.getLong(IConstans.ID)==null){
				for (Department sdept : sz_departs) {
					if (sdept.getLong(IConstans.ID).equals(newDept.getLong(IConstans.PID)) && bz_name.equals(newDept.getName())) {
						flag = true;
                        bz_dept_id = newDept.getLong(IConstans.ID);
						break;
					}
				}
				//如果已经插入则找出相应的所在部门
			}else if(sz_dept.getLong(IConstans.ID).equals(newDept.getLong(IConstans.PID)) && bz_name.equals(newDept.getName())){
				flag = true;
                bz_dept_id = newDept.getLong(IConstans.ID);
				break;
			}
		 }
		 if(!flag){
			 Department	dept = new Department();
			 dept.set(IConstans.ID,-100);
			 dept.set(IConstans.TYPE,IDataSource.DEPT_TYPE_BANZU);
			 dept.set(IConstans.DNAME,bz_name);
			 dept.set(IConstans.SHORT_NAME,bz_name);
			 dept.set(IConstans.PINYIN,PinyinUtil.getPinYinHeadChar(bz_name).toLowerCase());

			 if(sz_dept.getLong(IConstans.ID)!=null){
				 dept.set(IConstans.PID,sz_dept.get(IConstans.ID));
				 dept.set(IConstans.JCDWID,sz_dept.getJCDWID());
			 }else if(sz_departs.size()>0){
				 dept.set(IConstans.PID,sz_departs.get(0).getLong(IConstans.ID));
				 if(sz_departs.get(0).getJCDWID()!=null){
					 dept.set(IConstans.JCDWID,sz_departs.get(0).getJCDWID());
				 }
			 }else{
				 dept.set(IConstans.PID,dept_id);
				 dept.set(IConstans.JCDWID,dept_id);
			 }

			 dept.save();
             bz_dept_id = dept.getLong(IConstans.ID);
			 newDepts.add(dept);
		 }
		 return bz_dept_id;

	}

	/**
	 * 用户导入
	 */
	public void importxlsed() {
		String errorFile = "",msg="";

		try {
			UserVerifyHandler verifyHandler = new UserVerifyHandler();
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile(), null, verifyHandler);
			List<Department> departs = new ArrayList<>();
			Map<String,User> mapUser = new HashMap<>();
			if (!result.isVerfiyFail()) {
				Db.tx(new IAtom(){
					@Override
					public boolean run() throws SQLException {
						 String msgThrow = "";
						for (Map<String, Object> map : result.getList()) {
							//1.确定当前用户所在部门所在单位（基层，车间，本部）
							List<String> type = new ArrayList<>(Arrays.asList(IDataSource.DEPT_TYPE_BENBU, IDataSource.DEPT_TYPE_JICENG, IDataSource.DEPT_TYPE_CHEJIAN));

							//规定单位(dept_id)为基层单位，车间，本部
							Department jc_dept = Department.me.getDepartByDname(map.get(IConstans.DEPT_NAME).toString().trim(), type);


							Long dept_id = jc_dept.getLong(IConstans.ID);

							String sz_dept_name = "";
							String bz_name = "";
							if (map.get("sz_dept_name") != null) {
								sz_dept_name = map.get("sz_dept_name").toString();
							}

							if (map.get("banzu") != null) {
								bz_name = map.get("banzu").toString();
							}
							//获取用户部门
							synchronized (departs) {
								dept_id = getDeptIdAndNewDept(dept_id, departs, sz_dept_name, bz_name);
							}
							User user = new User();
							user.put(map);
							//获取用户名
							String uname = user.getName();
							uname = uname.replaceAll(" ", "");
							user.set(IConstans.UNAME, uname);
							user.set(IConstans.DEPT_ID,dept_id);
							//身份证号
							String idCard = user.get(IConstans.IDCARD).toString().trim();
							user.set(IConstans.IDCARD, idCard);
							//拼音
							user.set("uname_pinyin", PinyinUtil.getPinYinHeadChar(uname).toLowerCase());

							//账户
							String account = PinyinUtil.getAccountPinyin(uname.toLowerCase()) + idCard.substring(idCard.length() - 4, idCard.length());


							//查询账号是否重复
							User exitUser = User.me.findByUsername(account);
							if (exitUser != null) {
								msgThrow = "用户名：" + uname + "，身份号为：" + idCard + ",生成的账号：" + account + "在系统已经存在，请联系系统管理人员进行创建！";
								break;
							}
							if(mapUser.get(account)!=null){
								msgThrow ="用户名：" + uname + "，身份号为：" + idCard + ",生成的账号：" + account + "与前面生成的账号重复，请联系系统管理人员进行创建！";
								break;
							}
							if(StrKit.notBlank(user.getStr(IConstans.TEL)) && mapUser.get(user.getStr(IConstans.TEL))!=null){
								msgThrow = "电话号码："+user.getStr(IConstans.TEL)+"重复";
								break;
							}
							user.set("uaccount", account);
							user.set("upwd", MD5Util.getMd5(user.getStr("uaccount") + "*"));
							user.save();
							mapUser.put(user.getStr(IConstans.TEL),user);
							mapUser.put(user.getStr("uaccount"),user);
						}
                       if(StrKit.notBlank(msgThrow)){
							throw new KException(msgThrow);
					   }else{

						   return true;
					   }

					}
				} );

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
		} catch (Exception e){
			e.printStackTrace();
			msg = e.getMessage();
		}
        Map<String, Object> resultMap = bjuiAjaxBackMap(StrKit.notBlank(msg) ? 300 : 200, msg, false);
        if(Integer.parseInt(resultMap.get("statusCode").toString())==200){
            resultMap.put("message","导入数据成功！");
        }
		resultMap.put("errorFile", errorFile);

		renderJson(resultMap);

	}

	/**
	 * 工作负责人员显示
	 */
	public void findQualification() {
		super.doIndex();
		keepPara();
		Department department = getUserDept();
		Long deptid = getParaToLong("deptid");

		if (deptid == null) {
			deptid = Long.valueOf(department.getLong("id"));
		}
		String ids = Department.me.getDeptIds(deptid);
		UserQuery queryParam = Injector.injectBean(UserQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			if (null != queryParam.getUaccount() || null != queryParam.getUname()) {
				if(null != queryParam.getUname()) {
					queryParam.addFilter("(u.uname like '%" + queryParam.getUname() + "%' or u.uname_pinyin like '%" + queryParam.getUname() + "%')");
					queryParam.setUname(null);
				}
			} else {
				queryParam.setDeptid(null);
				queryParam.addFilter(" u.dept_id in (" + ids + ")");
			}
			Page<User> page = User.me.paginate(queryParam);
			setAttr("page", page);
		}
		setAttr("methodName","findQualification");
		keepPara();
		render("/WEB-INF/jsp/qualification/user_list.jsp");
	}

	public void findQualificationls() {
		super.doIndex();
		keepPara();
		Department department = getUserDept();
		Long deptid = getParaToLong("dept_id");

		if (deptid == null) {
			deptid = Long.valueOf(department.getLong("id"));
		}
		String ids = Department.me.getDeptIds(deptid);
		UserQuery queryParam = Injector.injectBean(UserQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			queryParam.addFilter("  u.jd_user_type LIKE '%gzfzry%'");
			if (null != queryParam.getUaccount() || null != queryParam.getUname()) {
				if(null != queryParam.getUname()) {
					queryParam.addFilter("(u.uname like '%" + queryParam.getUname() + "%' or u.uname_pinyin like '%" + queryParam.getUname() + "%')");
					queryParam.setUname(null);
				}
			} else {
				queryParam.setDeptid(null);
				queryParam.addFilter(" u.dept_id in (" + ids + ")");
			}
			Page<User> page = User.me.paginate(queryParam);
			setAttr("page", page);
		}
		setAttr("methodName","findQualificationls");
		keepPara();
		render("/WEB-INF/jsp/qualification/user_list.jsp");
	}

	public void lookup(){
		super.doIndex();
		Department department = getUserDept();
		Long deptid = getParaToLong("deptid");

		if (deptid == null) {
			deptid = Long.valueOf(department.getLong("id"));
		}
		String ids = Department.me.getDeptIds(deptid);
		UserQuery queryParam = Injector.injectBean(UserQuery.class, null, getRequest(), false);
		if (queryParam != null) {
			if (null != queryParam.getUaccount() || null != queryParam.getUname()) {
				if(null != queryParam.getUname()) {
					queryParam.addFilter("(u.uname like '%" + queryParam.getUname() + "%' or u.uname_pinyin like '%" + queryParam.getUname() + "%')");
					queryParam.setUname(null);
				}
			} else {
				queryParam.setDeptid(null);
				queryParam.addFilter(" u.dept_id in (" + ids + ")");
			}
			Page<User> page = User.me.paginate(queryParam);
			page.getList().forEach(user -> {
				  if(user.getLong(IConstans.DEPT_ID)==null && user.getLong(IConstans.COMPANY_ID)!=null){
				  	//CompayInfo  compayInfo = CompayInfo.me.findById(user.getLong(IConstans.COMPANY_ID));
				   	user.set(IConstans.DEPT_ID,user.getLong(IConstans.COMPANY_ID));
                   // user.set(IConstans.DNAME,compayInfo==null?"null":compayInfo.getStr(IConstans.COMPAY_NAME));
				  }else if(user.getLong(IConstans.DEPT_ID)==null && user.getLong(IConstans.COMPANY_ID)==null){
					  user.set(IConstans.DEPT_ID,"null");
					  user.set(IConstans.DNAME,"null");
				  }
			});
			setAttr("page", page);
		}
		keepPara();
		setAttr("methodName","lookup");
		render("/WEB-INF/jsp/qualification/user_list.jsp");
	}

	/**
	 * 用户列表
	 */
	public void userlist(){
	/*	super.doIndex();
		Department department = getUserDept();
		Long deptid = getParaToLong("deptid");

		if (deptid == null) {
			deptid = Long.valueOf(department.getLong("id"));
		}
		String ids = Department.me.getDeptIds(deptid);

		UserQuery queryParam = (UserQuery) super.doIndex(UserQuery.class);
		if (queryParam != null) {
			queryParam.addFilter("u.qualification like '%工作负责人%'");
			if (null != queryParam.getUaccount() || null != queryParam.getUname()) {
				setAttr("page", User.me.paginate(queryParam));
			} else {
				queryParam.setDeptid(null);
				queryParam.addFilter(" u.dept_id in (" + ids + ")");
				setAttr("page", User.me.paginate(queryParam));
			}
		}
		keepPara();
		render("/WEB-INF/jsp/qualification/user_list.jsp");*/
	}
	
	
	/**
	 * 弹出导入三种人资质的jsp
	 */
	public void _importqualificationxls() {
		keepPara();
		setAttr("modelName", modelName);
		setAttr("appid", KConfig.appid);
		render("user_import.jsp");
	}
	/**
	 * 导入三种人资质
	 */
	@Before(Tx.class)
	public void importqualificationxls() {
		String errorFile = "", msg = "";
		int count = 0;
		try {
			UserVerifyHandler verifyHandler = new UserVerifyHandler();
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile(), null, verifyHandler);
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					String departmentname = String.valueOf(map.get("departmentname")).trim();
					String currdeptname = String.valueOf(map.get("currdeptname")).trim();
					String username = String.valueOf(map.get("username")).trim();
					username = username.replaceAll(" ", "");
					//System.err.println("username--------"+username);
					//进行非空判断
					if (departmentname!=null && StringUtils.isNotBlank(username)) {
						//获取到用户
						//record = User.me.findByPropertity(new String[] { "dept_id","uname", "enabled" }, new Object[] { longid,username,"0" }, Logical.AND);
						List<Record> recordList = Db.find("SELECT u.id FROM k_user u , k_department d, k_department jcdw WHERE u.dept_id = d.id AND d.jcdwid = jcdw.id AND u.enabled='0' AND jcdw.dname = ? and uname = ?", departmentname,username);
						if (recordList.size()==0) {
							recordList = Db.find("SELECT u.id FROM k_user u , k_department d, k_department jcdw WHERE u.dept_id = d.id AND d.pid = jcdw.id AND u.enabled='0' AND jcdw.dname = ? and uname = ?", departmentname,username);
							
							if (recordList.size()==0) {
								recordList = Db.find("SELECT u.id FROM k_user u , k_department d WHERE u.dept_id = d.id  AND u.enabled='0' AND d.dname = ? and uname = ?", currdeptname,username);
								
								if (recordList.size()==0) {
									recordList = Db.find("SELECT u.id FROM k_user u , k_department d WHERE u.dept_id = d.id  AND u.enabled='0' AND d.dname = ? and uname = ?", departmentname,username);
								}
							}
						}
						
						//System.err.println("recordList.size() ++++++++++ "+recordList.size());
						if (recordList.size()>0) {
							//System.err.println("recordList.size() -------- "+recordList.size());
							Record record = recordList.get(0);
							User user = new User();
							Long longid = record.getLong("id");
							user.set("id", longid);
							if (map.get("qualification_gzpqfr") != null) {
								user.set("qualification_gzpqfr", String.valueOf(map.get("qualification_gzpqfr")).trim());
							}else {
								user.set("qualification_gzpqfr", null);
							}
							if (map.get("qualification_principal") !=null) {
								user.set("qualification_principal", String.valueOf(map.get("qualification_principal")).trim());
							}else {
								user.set("qualification_principal", null);
							}
							if (map.get("qualification_gzxkr") !=null) {
								user.set("qualification_gzxkr", String.valueOf(map.get("qualification_gzxkr")).trim());
							}else {
								user.set("qualification_gzxkr", null);
								
							}
							user.update();
							count++;
						}else{
							Department department=null;
							if (map.get("currdeptname") !=null) {
								
								department = Department.me.findByPropertity(new String[]{"dname","enabled"}, new Object[]{currdeptname,"0"}, Logical.AND);
							}
							if (department==null) {
								department = Department.me.findByPropertity(new String[]{"dname","enabled"}, new Object[]{departmentname,"0"}, Logical.AND);
							}
							if (department!=null) {
								Long longid = department.getLong("id");
								User user = new User();
								if (map.get("qualification_gzpqfr") != null) {
									user.set("qualification_gzpqfr", String.valueOf(map.get("qualification_gzpqfr")).trim());
								}
								if (map.get("qualification_principal") !=null) {
									user.set("qualification_principal", String.valueOf(map.get("qualification_principal")).trim());
								}
								if (map.get("qualification_gzxkr") !=null) {
									user.set("qualification_gzxkr", String.valueOf(map.get("qualification_gzxkr")).trim());
								}
								String uaccount = PinyinUtil.getAccountPinyin(username.toLowerCase());
								if (map.get("idcard")!=null) {
									String idcard = String.valueOf(map.get("idcard")).trim();
									if (idcard != null && idcard.length() >=4) {
										uaccount += idcard.substring(idcard.length() - 4, idcard.length());
									}
								}
								user.set("uname",username);
								user.set("uaccount",uaccount);
								user.set("uname_pinyin", PinyinUtil.getPinYinHeadChar(username.toLowerCase()));
								user.set("dept_id", longid);
								user.set("upwd", MD5Util.getMd5(user.getStr("uaccount") + "*"));
								user.set(IConstans.ID,-100);
								user.save();
								count++;
							}
						}
					}
					
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
		msg = "导入总数据条数：           "+count;
		Map<String, Object> resultMap = bjuiAjaxBackMap(StrKit.notBlank(msg) ? 300 : 200, msg, false);
        if(Integer.parseInt(resultMap.get("statusCode").toString())==200){
            resultMap.put("message","导入数据成功！");
        }
		resultMap.put("errorFile", errorFile);
		renderJson(resultMap);
		
	}
	
}
