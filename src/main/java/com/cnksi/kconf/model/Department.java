package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.utils.IConstans;
import com.cnksi.utils.IDataSource;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 部门
 * 
 * @author joe
 *
 */
public class Department extends BaseModel<Department> {

	private static final long serialVersionUID = 1L;

	public static final Department me = new Department();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 查询所有的部门(部门树)
	 * 
	 * @return
	 */
	public List<Record> findAllDeptTree() {
		String sql = "select id as id, pid as pId, dname as name from " + tableName + " d  where enabled=0 order by sort";
		return Db.find(sql);
	}

	/**
	 * 查询所有的非班组部门(部门树)
	 *
	 * @return
	 */
	public List<Record> findAllDeptTreeNotBanzu() {
		String sql = "select id as id, pid as pId, dname as name from " + tableName + " d  where enabled=0  and (type <> 'banzu' or id = 1)  order by sort";
		return Db.find(sql);
	}

	/**
	 * 查询所有基层单位(type=1 本部 type=2基层单位 type=3班组 type=0 电业局)
	 * 
	 * @return
	 */
	public List<Record> findJcdwDept() {
		String sql = "select id as id, pid as pId, dname as name from " + tableName + " d  where enabled=0 and type in('jiceng','chejian') order by sort";
		return Db.find(sql);
	}

	/**
	 * 查询所有的部门
	 * 
	 * @return
	 */
	public List<Department> findAllDept() {
		String sql = "SELECT id, pid, dname,sort FROM " + tableName + " d where 1=1 and enabled = 0 ORDER BY sort";
		return find(sql);
	}

	/**
	 * 根据父级部门id查询所有子级部门
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Record> selectChidrenByParentId(Long parentId) {
		List<Record> records = new ArrayList<Record>();
		Department dept = Department.me.findById(parentId);
		Record root = new Record();
		root.set("id", dept.get("id"));
		root.set("name", dept.get("dname"));
		root.set("pId", dept.get("pid"));
		// 查询所有的单位信息
		List<Department> list = Department.me.findAllDept();
		List<Record> children = getChilds(parentId, list);
		if (children.size() > 0) {
			root.set("children", children);
		}
		records.add(root);
		return records;
	}

	/**
	 * 根据deptid查询下级所有单位
	 * 
	 * @param deptId
	 * @return
	 */
	private List<Record> getChilds(Long deptId, List<Department> allDepts) {
		List<Department> list = findByParentId(deptId, allDepts);
		List<Record> result = new ArrayList<Record>();

		for (Department dept : list) {
			Record tree = new Record();
			tree.set("id", dept.get("id"));
			tree.set("name", dept.get("dname"));
			tree.set("pId", dept.get("pid"));
			List<Record> children = getChilds((dept.getLong("id")), allDepts);
			if (children.size() > 0) {
				tree.set("children", children);
			}
			result.add(tree);
		}
		return result;
	}

	/**
	 * 递归查询部门
	 * @param dept
	 * @return
	 */
	public String findUpDepart(Department dept){
		if((StrKit.notNull(dept.getLong(IConstans.PID))&& dept.getLong(IConstans.PID)==1) || dept.getLong(IConstans.ID)==1){
			return StrKit.notBlank(dept.getStr("short_name"))?dept.getStr("short_name"):dept.getName();
		}else{
			Department record =  Department.me.findById(dept.getLong(IConstans.PID));
			String dname = StrKit.notBlank(dept.getStr("short_name"))?dept.getStr("short_name"):dept.getName();
			return dname+"-"+findUpDepart(record);
		}
	}



	/**
	 * 查询当前部门下的所有部门
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Department> selectDepartByParentId(Long parentId) {
		List<Department> records = new ArrayList<>();
		Department dept = Department.me.findById(parentId);
		records.add(dept);
		// 查询所有的单位信息
		List<Department> list = Department.me.findAllDept();
		List<Department> children = getChildDepart(parentId, list);
		if (children.size() > 0) {
			records.addAll(children);
		}
		return records;
	}

	/**
	 * 根据deptid查询下级所有单位
	 * 
	 * @param deptId
	 * @return
	 */
	private List<Department> getChildDepart(Long deptId, List<Department> allDepts) {
		List<Department> list = findByParentId(deptId, allDepts);
		List<Department> result = new ArrayList<>();
		result.addAll(list);
		for (Department dept : list) {
			List<Department> children = getChildDepart((dept.getLong("id")), allDepts);
			if (children.size() > 0) {
				result.addAll(children);
			}
		}
		return result;
	}

	public String getStringDepartId(List<Department> departs) {
		String ids = "";
		for (Department department : departs) {
			if ("".equals(ids)) {
				ids = department.getLong("id") + "";
			} else {
				ids = ids + "," + department.getLong("id");
			}
		}
		return ids;
	}

	public List<Department> findByParentId(Long deptId) {
		String sql = "select * from %s where pid= ? and enabled = 0 order by sort asc  ";
		return find(String.format(sql, tableName), deptId);
	}

	public List<Department> findByParentId(Long deptId, List<Department> allDepts) {
		List<Department> result = new ArrayList<Department>();
		for (Department dept : allDepts) {
			Long idAll = dept.getLong("pid");
			if (null == idAll || !idAll.equals(deptId)) {
				continue;
			} else {
				result.add(dept);
			}
		}
		return result;
	}

	/**
	 * 查询所有子部门ids，并且以逗号分隔,以字符串形式返回
	 * 
	 * @param deptId
	 * @return
	 */
	public String getDeptIds(Long deptId) {
		String deptidStr = getChildLst(deptId);
		deptidStr = deptidStr.replace("$,", "");

		return deptidStr;
	}

	/**
	 * 查询所有子部门ids，并且以逗号分隔,以list形式返回
	 * 
	 * @param deptId
	 * @return
	 */
	public List<String> getListDeptIds(Long deptId) {
		String deptidStr = getChildLst(deptId);
		deptidStr = deptidStr.replace("$,", "");
		List<String> deptIds = new ArrayList<String>();
		Collections.addAll(deptIds, deptidStr.split(","));
		return deptIds;
	}

	/**
	 * 根据id查询id下的所有子部门
	 * 
	 * @param id
	 *            部门id
	 * @return
	 */
	public String getChildLst(Long id) {
		String sql = String.format("select getChildLst(%s)", id);
		String str = Db.queryStr(sql);
		if (str.startsWith("$,")) {
			str = str.replace("$,", "");
		}
		return str;
	}

	public String getName() {
		return get("dname");
	}

	public String getType() {
		return getStr("type");
	}

	public Long getJCDWID() {
		return get("jcdwid") == null ? null : getLong("jcdwid");
	}

	/**
	 * 根据部门类型查询部门
	 * @param type
	 * @return
	 */
	public List<Department> getbyType(String type){
		StringBuffer buffer = new StringBuffer("select * from k_department d where  d.type = ? and d.enabled = 0 and pid is not null");
		return  find(buffer.toString(),type);
	}

	/**
	 * 根据部门ID查询基层单位
	 * @param id
	 * @return
	 */
	public Department getJcdw(Long id){
		StringBuffer buffer = new StringBuffer("select * from k_department where id in (select jcdwid from k_department where id = ?)");
	    return  findFirst(buffer.toString(),id);
	}

	public Department getDepartByDname(String dname,List<String> types){
        StringBuilder builder = new StringBuilder("select * from k_department where dname = ? and enabled = 0 ");
		setType(builder,types);
		return  findFirst(builder.toString(),dname);

	}


	public List<Department> getDepartByDnameAndId(String dname, String ids, List<String> types) {
		StringBuilder builder = new StringBuilder("select * from k_department where dname = ? and id in ("+ids+") and enabled  = 0");
		setType(builder,types);
		return  find(builder.toString(),dname);

	}

    private  void setType(StringBuilder builder,List<String> types){
		if(types.size()>0){
			builder.append(" and  type in ( '"+types.get(0)+"'");
			for(String type:types){

				builder.append(",'"+type+"'");
			}
			builder.append(" )");
		}
	}

	/**
	 * 某个单位是否属于本部或本部下辖单位
 	 */
	public boolean isBenbu() {
    	if (IDataSource.DEPT_TYPE_BENBU.equals(this.getStr(IConstans.TYPE))) {
    		return true;
		} else {
    		Department jcdw = Department.me.getJcdw(this.getLong("id"));
    		if (jcdw != null && IDataSource.DEPT_TYPE_BENBU.equals(jcdw.getStr(IConstans.TYPE))) {
    			return true;
			}
		}
		return false;
	}

	/**
	 * 根据归口部门查询
	 * id可以为空
	 * relevant不能为空
	 * @return
	 */
	public List<Department> findByRelevant(Long id,String relevant) {

		StringBuilder sb  =  new StringBuilder();
		sb.append("select * from k_department where enabled = 0 and relevant = ?");
		if(StrKit.notNull(id)){
			sb.append("  and id = '"+id+"'");

		}
		return find(sb.toString(),relevant);
	}
}
