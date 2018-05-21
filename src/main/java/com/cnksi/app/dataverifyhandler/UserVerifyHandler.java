package com.cnksi.app.dataverifyhandler;

import java.util.*;

import com.cnksi.kconf.excel.KVerifyHanlder;
import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.jfinal.model.BaseModel.Logical;
import com.cnksi.utils.IConstans;
import com.cnksi.utils.IDataSource;
import com.jfinal.kit.StrKit;

/**
 * 导入项目时，Excel业务验证
 * 
 * @author joe
 *
 */
public class UserVerifyHandler extends KVerifyHanlder {

	Map<String, Department> deptMap = new HashMap<>();

	@Override
	protected boolean businessVerifyHandler(String colName, Object value, StringBuilder sb) {
		boolean success = true;


		// 导入数据时，部门名称应该唯一
		if (IConstans.DEPT_NAME.equals(colName)) {
			List<String> type =new ArrayList<>(Arrays.asList(IDataSource.DEPT_TYPE_BENBU,IDataSource.DEPT_TYPE_JICENG,IDataSource.DEPT_TYPE_CHEJIAN));

			//规定单位(dept_id)为基层单位，车间，本部
			Department department = Department.me.getDepartByDname(value.toString().trim(),type);

			if(department==null){
				sb.append(" 单位 " + value + " 在系统(本部,车间，基层单位)中不存在，请添加 " + value + " 单位");
				success = false;
			}
		}

		//校验身份证
		if (IConstans.IDCARD.equals(colName)) {

			if(StrKit.notNull(value)){
				String str = value.toString().trim();

				if (str.length()<4) {
					sb.append("身份证" + value + "格式不正确");
					success = false;
				}
			}else{
				sb.append("身份证不能为空");
				success = false;
			}
		}

		if(IConstans.TEL.equals(colName) && value!=null){
			User existUser = User.me.findByUsername(value.toString());
			if (existUser != null) {
				sb.append("电话号码"+value+"已在系统中存在！");
				success = false;
			}

		}
		if(IConstans.UNAME.equals(colName)){
			String uname = String.valueOf(value).replaceAll(" ", "");
			if(StrKit.isBlank(uname)){
				sb.append("用户名"+value+"不能为空！");
				success =false;
			}

		}


		return success;
	}

}
