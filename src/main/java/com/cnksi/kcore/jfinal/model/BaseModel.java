package com.cnksi.kcore.jfinal.model;

import com.alibaba.fastjson.util.TypeUtils;
import com.cnksi.kcore.exception.KException;
import com.cnksi.kcore.utils.IdWorker;
import com.cnksi.kcore.utils.KStrKit;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.*;
import com.mysql.jdbc.ResultSetMetaData;
import com.sun.net.httpserver.Authenticator;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Stream;

/**
 * 基础Model
 * 
 * @author joe
 *
 * @param <M>
 */
public abstract class BaseModel<M extends BaseModel<M>> extends Model<M> {

	private static final long serialVersionUID = 1L;

	protected String tableName = "", pkName = ""; // 表名和主键名称

	public <T> T getPkVal() {
		return get(pkName);
	}

	@SuppressWarnings("unchecked")
	public BaseModel() {
		if (StrKit.isBlank(tableName)) {
			tableName = TableMapping.me().getTable(getCls()).getName();
		}
		if (StrKit.isBlank(pkName)) {
			pkName = TableMapping.me().getTable(getCls()).getPrimaryKey()[0];
		}
	}

	@SuppressWarnings("unchecked")
	public M setPkVal(Object value) {
		set(pkName, value);
		return (M) this;
	}

	public M findById() {
		Object id = getPkVal();
		return super.findById(id);
	}
	@Override
	public Long getLong(String attr) {
		return TypeUtils.castToLong(get(attr));
	}

	public String getStr(String attr){
		return TypeUtils.castToString(get(attr));
	}

	public Date getDate(String attr){
		return TypeUtils.castToDate(get(attr));
	}

    @Override
    public BigInteger getBigInteger(String attr) {
        return TypeUtils.castToBigInteger(get(attr));
    }

    /**
	 * 将字段的值转换为lookup对应的值
	 * 
	 * @param @param
	 *            fieldName
	 * @param @param
	 *            fieldVal
	 * @param @param
	 *            lookupMap 设定文件
	 */
	public void setOfLookup(String fieldName, Map<String, String> lookupMap) {
		this.set(fieldName, lookupMap.get(KStrKit.toStr(this.get(fieldName))));
	}

	/**
	 * 将Model的集合按一定的规则转换为Map 便于小数据量的快速反复查询
	 * 
	 * @param list
	 * @return
	 */
	public <T> Map<T, M> toMap(List<M> list) {
		return toMap(list, pkName);
	}

	public <T> Map<T, M> toMap(List<M> list, String keyName) {
		Map<T, M> map = new LinkedHashMap<T, M>();
		for (M model : list) {
			map.put(model.get(keyName), model);
		}
		return map;
	}

	/**
	 * 根据键-值name，将list转换为对应Map
	 * 
	 * @param @param
	 *            list
	 * @param @param
	 *            keyName
	 * @param @param
	 *            valueName
	 * @return Map<String,String>
	 */
	public <K, V> Map<K, V> toMap(List<M> list, String keyName, String valueName) {
		Map<K, V> map = new LinkedHashMap<K, V>();
		list.forEach(record -> map.put(record.get(keyName), record.get(valueName)));
		return map;
	}

	public <K, V> Map<K, V> toMapOfRecord(List<Record> list, String keyName, String valueName) {
		Map<K, V> map = new LinkedHashMap<K, V>();
		list.forEach(record -> map.put(record.get(keyName), record.get(valueName)));
		return map;
	}

	/**
	 * 拼接sql：select * from %s order by %s asc
	 */
	public String sql(String... sql) {
		return String.join(" ", sql);
	}

	/**
	 * 按主键降序查询所有数据
	 * 
	 * @return
	 */
	public List<M> findAll() {
		String sql = String.format("select * from %s  order by %s asc", tableName, pkName);
		return find(sql);
	}
	/**
	 * 按主键降序查询所有未删除数据
	 *
	 * @return
	 */
	public List<M> findExitAll() {
		String sql = String.format("select * from %s  where  enabled = 0 order by %s  asc", tableName, pkName);
		return find(sql);
	}

	/**
	 * 根据某个字段和字段值查询一个对象
	 *
	 * @param propertity
	 *            字段名称
	 * @param value
	 *            字段值
	 * @return
	 */
	public M findByPropertity(String propertity, Object value) {
		String sql = String.format("select * from %s where %s = ?   ", tableName, propertity);
		return findFirst(sql, value);
	}

	/**
	 * 根据某个字段和字段值查询多个对象
	 *
	 * @param propertity
	 *            字段名称
	 * @param value
	 *            字段值
	 * @return
	 */
	public List<M> findListByPropertity(String propertity, Object value) {
		String sql = String.format("select * from %s where %s = ?  ", tableName, propertity);
		return find(sql, value);
	}

	public M findByPropertity(String[] propertitys, Object[] value, Logical logic) {
		String sql = String.format("select * from %s where 1=1 ", tableName);
		for (String propertity : propertitys) {
			sql += logic.name() + " " + propertity + "  = ? ";
		}
		return findFirst(sql, value);
	}

	public M findByPropertity(Stream<String> propertitys, Stream<Object> value, Logical logic) {
		return findByPropertity(propertitys.toArray(String[]::new), value.toArray(), logic);
	}

	public List<M> findListByPropertitys(String[] propertitys, Object[] value, Logical logic) {
		String sql = String.format("select * from %s where 1=1 ", tableName);
		for (String propertity : propertitys) {
			sql += logic.name() + " " + propertity + "  = ? ";
		}
		return find(sql, value);
	}

	public List<M> findListByPropertitys(Stream<String> properties, Stream<Object> values, Logical logical){
		return findListByPropertitys(properties.toArray(String[]::new), values.toArray(), logical);
	}

	/**
	 * 解析注解处理的SQL语句
	 *
	 * @param entity
	 *            包含(Query,QueryParam注解的VO实例对象)
	 * @param paramValues
	 *            SQL中的参数值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> buildSql(Object entity, List<Object> paramValues) {
		Map<String, String> queryObject = new HashMap<>();
		String select = "", from = "", join = "", orderBy = "", groupBy = "";
		StringBuilder sqlBuffer = new StringBuilder(256);
		StringBuilder whereBuffer = new StringBuilder(256);
		try {
			KQuery tableAnnotation = entity.getClass().getAnnotation(KQuery.class);
			// 解析SQL
			if (tableAnnotation != null) {
				select = tableAnnotation.select();
				from = tableAnnotation.from();
				join = tableAnnotation.join();
				queryObject.put("select", select);
				queryObject.put("from", from);
				queryObject.put("join", join);
				sqlBuffer.append(select).append(" ").append(from).append(" ").append(join).append(" ").append(" where 1=1 ");
				orderBy = tableAnnotation.orderBy();
				groupBy = tableAnnotation.groupBy();
			} else {
				queryObject.put("select", "select * ");
				queryObject.put("from", "from ".concat(getTableName()));
				sqlBuffer.append(String.format("select * from %s where 1=1 ", getTableName()));
			}

			// 解析页面动态参数
			Field[] fields = entity.getClass().getDeclaredFields();
			if (fields != null && fields.length > 0) {
				// 构建(Bean)where查询条件
				for (Field f : fields) {
					KQueryParam meta = f.getAnnotation(KQueryParam.class);
					if (meta == null)
						continue;

					Method m = entity.getClass().getMethod("get" + KStrKit.toCamel(f.getName()));
					Object val = m.invoke(entity);
					if (val != null) {
						whereBuffer.append(" and ").append(meta.colName()).append(" ").append(meta.op()).append(" ").append("?");
						if ("like".equalsIgnoreCase(meta.op())) {
							val = "%" + val + "%";
						}
						paramValues.add(val);
					}
				}
			}

			// 从Entity中解析自定义查询条件
			Method[] filterMethods = entity.getClass().getMethods();
			for (Method m : filterMethods) {
				if ("getFilter".equals(m.getName())) {
					Map<String, Object> customerFilter = (Map<String, Object>) m.invoke(entity);
					if (customerFilter != null) {
						for (Map.Entry<String, Object> entry : customerFilter.entrySet()) {
							if (entry.getValue() != null) {
								whereBuffer.append(" and ").append(entry.getKey()).append("?");
								paramValues.add(entry.getValue());
							} else {
								whereBuffer.append(" and ").append(entry.getKey());
							}
						}
					}
				}

				if ("getOrderBy".equals(m.getName())) {
					String customerOrderBy = (String) m.invoke(entity);
					if (customerOrderBy != null && customerOrderBy.trim().length() > 0) {
						orderBy = customerOrderBy;
					}
				}
			}

			if (sqlBuffer.length() == 0) {
				throw new KException("TableName为空或sql注解未配置，无法构建查询SQL");
			}

			sqlBuffer.append(whereBuffer);
			queryObject.put("where", " WHERE 1=1 ".concat(whereBuffer.toString()));

			if (StrKit.notBlank(orderBy) && orderBy.trim().length() > 0) {
				String tmp = " order by ".concat(orderBy);
				sqlBuffer.append(tmp);
				queryObject.put("orderBy", tmp);
			}

			if (StrKit.notBlank(groupBy) && groupBy.trim().length() > 0) {
				String tmp = " group by ".concat(groupBy);
				sqlBuffer.append(tmp);
				queryObject.put("groupBy",tmp);
			}
			queryObject.put("sql", sqlBuffer.toString());
		} catch (Exception e) {
			throw new KException(e.getMessage(), e);
		}
		return queryObject;
	}

	/**
	 * 获取子类的Class
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected abstract Class getCls();

	public String getTableName() {
		return tableName;
	}

	public String getPkName() {
		return pkName;
	}

	/**
	 * 根据KWebQueryVO组装数据查询条件，查询数据，多用于页面分页查询
	 *
	 * @param queryParam{KWebQueryVO}
	 * @return
	 */
	public Page<M> paginate(KWebQueryVO queryParam) {
		LinkedList<Object> paramValues = new LinkedList<Object>();
		Page<M> page = null;
		try {
			Map<String, String> queryObject = buildSql(queryParam, paramValues);
			if (queryObject.get("sql").contains("?")) {
				page = paginate(queryParam.getPageNumber(), queryParam.getPageSize(),queryObject.get("select"),queryObject.get("sql").substring(queryObject.get("select").length()), paramValues.toArray());
			} else {
				page = paginate(queryParam.getPageNumber(), queryParam.getPageSize(), queryObject.get("select"), queryObject.get("sql").substring(queryObject.get("select").length()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return page;
	}

	@Override
	public boolean save() {
		setId();

		Table table = getTable();

		if (table.hasColumnLabel("enabled")) {
			setEnabled(true);
		}

		return super.save();
	}

	public enum Logical {
		AND, OR
	}


	public void setId(){
		if (getPkVal() == null) {
			setPkVal(IdWorker.getId());
		} else { // pk=-100 数据库自增

			if (getPkVal() instanceof Integer) {
				if (getInt(pkName) == -100)
					setPkVal(null);
			}

			if (getPkVal() instanceof Long) {
				if (getLong(pkName).intValue() == -100)
					setPkVal(null);
			}

		}
	}

	public String saveValidate() {

		setId();

		Table table = getTable();

		if (table.hasColumnLabel("enabled")) {
			setEnabled(true);
		}
		try{
			super.save();

		}catch (Exception e){
			Connection conn=null;
			PreparedStatement pst = null;
			ResultSet rs=null;
			try {
				Config config = DbKit.getConfig();
				DataSource d =config.getDataSource();
				conn = d.getConnection();
				 pst = conn.prepareStatement("select * from "+table.getName()+" where 1=2");
				  rs = pst.executeQuery();
				ResultSetMetaData rsd = (ResultSetMetaData) rs.getMetaData();
				for(int i = 1; i <= rsd.getColumnCount(); i++) {
					Object o = get(rsd.getColumnName(i));
					//校验长度
					if(StrKit.notNull(o) && o.toString().length()>rsd.getColumnDisplaySize(i)){
						return  rsd.getSchemaName(i)+"("+rsd.getColumnName(i)+")最大长度为"+rsd.getColumnDisplaySize(i);
					}

					System.out.print("java类型："+rsd.getColumnClassName(i));
					System.out.print("  数据库类型:"+rsd.getColumnTypeName(i));
					System.out.print("  字段名称:"+rsd.getColumnName(i));
					System.out.print("  字段长度:"+rsd.getColumnDisplaySize(i));
					System.out.print("  字段备注："+rsd.getSchemaName(i));
					System.out.println();
				}
			}catch (Exception e2) {
				return "数据库连接失败";
			}finally {
				try {
					if(rs!=null)
						rs.close();
					if(pst!=null)
						pst.close();
					if(conn!=null)
					conn.close();
				}catch (Exception e3){
                   e3.printStackTrace();
				}
			}

		}

		return "success";
	}

}
