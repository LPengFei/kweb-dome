package com.cnksi.kconf.controller;

import com.alibaba.fastjson.util.TypeUtils;
import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.excel.KExcelDataHandler;
import com.cnksi.kconf.excel.KVerifyHanlder;
import com.cnksi.kconf.model.*;
import com.cnksi.kcore.exception.KException;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.utils.KStrKit;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.taglib.KDataCache;
import com.cnksi.utils.IConstans;
import com.cnksi.utils.IDataSource;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.upload.UploadFile;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;
import org.jeecgframework.poi.excel.entity.params.ExcelImportEntity;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.jeecgframework.poi.exception.excel.ExcelImportException;
import org.jeecgframework.poi.util.PoiPublicUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KController extends Controller {
	protected static final String formJsp = "/WEB-INF/jsp/common/common_form.jsp";
	protected static final String listJsp = "/WEB-INF/jsp/common/common_list.jsp";
	protected static final String importJsp = "/WEB-INF/jsp/common/_import.jsp";
	protected static final String lookupJsp="/WEB-INF/jsp/common/common_lookup.jsp";
	protected static final String detailJsp="/WEB-INF/jsp/common/common_view.jsp";
	protected static final int statusCode200 = 200;
	protected static final int statusCode300 = 300;
	protected String modelName = "", tableName = "", tabid = "";
	protected Class<? extends Model<?>> modelClass = null;
	protected Table table = null;

	protected User loginUser = null;

	public KController(Class<? extends Model<?>> modelClass) {

		table = TableMapping.me().getTable(modelClass);
		tableName = table.getName();
		modelName = modelClass.getSimpleName().toLowerCase();

	}

	/**
	 * 移除查询条件
	 * @param queryParam
	 * @param field_name 字段名称
	 * @param content  字段对应的查询内容
	 * @return
	 */
	protected void removeCondition(KWebQueryVO queryParam,String field_name,Object content){
		Map<String, Object> map = queryParam.getFilter();
		String key = "";
		for(Entry<String, Object> entry: map.entrySet()){
			//根据字段名称和查询内容匹配
			if(entry.getValue()!=null) {
				if (entry.getKey().contains(field_name) && entry.getValue().toString().contains(content.toString())) {
					key = entry.getKey();
					break;
				}
			}else{
				if(entry.getKey().contains(field_name) ){
					key = entry.getKey();
					break;
				}
			}
		}
		map.remove(key);
		queryParam.setFilterMap(map);
	}

	protected void removeConditionMap(KWebQueryVO queryParam,Map<String,Object> map){
		for(Entry<String, Object> entry: map.entrySet()){
			removeCondition(queryParam,entry.getKey(),entry.getValue());
		}
	}

	/**
	 * 添加隐藏域到页面上
	 */
	protected KController addHiddenField(String name, String value) {
		Map<String, Object> hiddenFields = getAttr("hiddenFields");
		hiddenFields = Optional.ofNullable(hiddenFields).orElse(new HashMap<>());
		hiddenFields.put(name, value);
		setAttr("hiddenFields", hiddenFields);
		return this;
	}

	/**
	 * 添加多个隐藏域
	 *
	 * @param names
	 *            hidden name以逗号分隔的字符串
	 * @param values
	 *            hidden value
	 */
	protected void addHiddenField(String names, String... values) {
		String[] nameArr = names.split(",");
		if (nameArr.length != values.length) {
			throw new RuntimeException("names 和 values 长度不一致");
		}

		int i = 0;
		for (String name : nameArr) {
			addHiddenField(name, values[i++]);
		}

	}

	protected KModel getKModel(String kmodelid) {
		KModel kModel = null;
		if (Objects.nonNull(kmodelid))
			kModel = KModel.me.findById(kmodelid);
		else
			kModel = KModel.me.findByTableName(tableName);

		if(kModel!=null && StrKit.notBlank(kModel.getStr(IConstans.TABID)))
			tabid = kModel.get(IConstans.TABID);
		return kModel;
	}

	protected KModel getKModel() {
		return getKModel(getPara("kmodelId"));
	}

	/**
	 * 列表查询前的处理
	 *
	 * @return KWebQueryVO 返回类型
	 */
	protected KWebQueryVO doIndex() {
		return doIndex(getKModel(), null);
	}

	protected KWebQueryVO doIndex(Class<?> cls) {
		return doIndex(KModel.me.findByTableName(tableName), cls);
	}

	/**
	 * 处理请求参数，去掉参数_
	 *
	 * @param requstString
	 * @return
	 */
	private String subRequestStr(String requstString) {
		if (Objects.isNull(requstString))
			return "";

		String[] paras = requstString.split("&");
		// 去掉参数_
		requstString = Stream.of(paras).filter(s1 -> !s1.startsWith("_=")).reduce("", (s1, s2) -> s1 + "&" + s2).replaceFirst("&", "?");
		return requstString;
	}

	/**
	 * 编辑页面，查询数据库配置的字段信息
	 */
	protected void doEdit(KModel m) {
		if (m != null) {
			List<KModelField> fields = m.getFormViewField();

			m.put("fields", fields);
		}
		setAttr("model", m);
		setAttr("fields", m != null ? m.get("fields") : null);
		setAttr("pkName", table.getPrimaryKey()[0]);
		setAttr("modelName", modelName);
	}

	/**
	 * <pre>
	 * 1、处理Excel导入导出
	 * 2、获取数据库配置的动态参数
	 * </pre>
	 *
	 * @param m
	 * @param cls
	 *            {@link KWebQueryVO} 子类
	 * @return
	 */
	protected KWebQueryVO doIndex(KModel m, Class<?> cls) {
		// 导出Excel配置
		List<Iexport> exports = Iexport.me.findByModelId("导出", m.getPkVal());
		// 导入Excel配置
		List<Iexport> imports = Iexport.me.findByModelId("导入", m.getPkVal());

		KWebQueryVO queryParam = null;
		if (cls == null) {
			queryParam = new KWebQueryVO();
			queryParam.setTableName(tableName);
			queryParam.setPageSize(getParaToInt("pageSize", queryParam.getPageSize()));
			queryParam.setPageNumber(getParaToInt("pageNumber", queryParam.getPageNumber()));
			queryParam.setOrderField(getPara("orderField", queryParam.getOrderField()));
			queryParam.setOrderDirection(getPara("orderDirection", queryParam.getOrderDirection()));
			queryParam.setExport(getParaToBoolean("export",false));
		} else {
			queryParam = (KWebQueryVO) Injector.injectBean(cls, null, getRequest(), false);
		}
		List<KModelField> fields = new ArrayList<>();
		List<KModelField> searchFields =null;
		List<ModelLink> links = null;
		if (m != null) {

			loginUser = getSessionAttr(KConfig.SESSION_USER_KEY);
			// fields = m.getListViewField(loginUser);

			// 处理查询条件
			List<KModelField> allFields = m.getFieldsByRole(KStrKit.toStr(getLoginUser().get("role_id")));
			for (KModelField field : allFields) {
				/*if ("true".equals(field.get("list_view"))) {
				}*/
				fields.add(field);

				String fname = field.getStr("field_name");

				// 获取查询条件，并将之设置到queryVO
				String searchOp = field.get("list_search_op");
				if (StrKit.isBlank(searchOp))
					continue;

				String requstVal = getPara(fname);
				if(getRequest().getMethod().equals("POST")){
					if (StrKit.notBlank(searchOp, requstVal)) {
						if ("like".equals(searchOp.trim())) {
							requstVal = "%" + requstVal + "%";
						}
						queryParam.addFilter(fname, " " + searchOp + " ", requstVal);
					}
				}else{
					try {
						if(StringUtils.isNotBlank(requstVal)){
							requstVal = URLDecoder.decode(requstVal, "UTF-8");
						}
						if (StrKit.notBlank(searchOp, requstVal)) {
							if ("like".equals(searchOp.trim())) {
								requstVal = "%" + requstVal + "%";
							}
							queryParam.addFilter(fname, " " + searchOp + " ", requstVal);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}


//				if (StrKit.notBlank(requstVal)) {
//					if ("like".equals(searchOp.trim())) {
//						requstVal = "%" + requstVal + "%";
//					}
//					queryParam.addFilter(fname, " " + searchOp + " ", requstVal);
//				}
			}

			// 2017-1-17 14:55:11 modified by joey modellink加入排序
			links = ModelLink.me.findModelLinks(m.getPkVal(),getUserDept().getLong(IConstans.ID));


			searchFields = m.getListSearchViewField(loginUser);

		}

		setAttr("model", m);
		setAttr("fields", fields);
		setAttr("searchFields",searchFields);
		setAttr("imports", imports);
		setAttr("exports", exports);
		setAttr("query", queryParam);
		setAttr("modelName", modelName);
		setAttr("pkName", table.getPrimaryKey()[0]);
		setAttr("links", links);
		setAttr("subRequestStr", subRequestStr(getRequest().getQueryString()));

		keepPara();

		// 清除缓存(清理菜单缓存不能注销掉)
		KDataCache.clear();

		/*if (fields == null || fields.isEmpty()) {
			return null;
		} else {
		}*/
		return queryParam;
	}

	protected void doEdit() {
		doEdit(getKModel());
	}

	/**
	 * 下载导入错误的excel文件
	 */
	public void downerrfile() {
		String fileName = getPara("fname");
		String path = PoiPublicUtil.getWebRootPath("upload");
		File file = new File(path + "/" + fileName);
		if (file.exists()) {
			renderFile(file);
		} else {
			renderNull();
		}
	}

	/**
	 * 导出Excel模板
	 *
	 * @throws IOException
	 */
	public void exportpl() throws IOException {
		String xlsid = getPara("xlsid", "1");
		export(xlsid, null);
	}

	/**
	 * 导航到数据导入页面
	 */
	public void importxls() {
		keepPara();
		setAttr("modelName", modelName);
		setAttr("appid", KConfig.appid);
		render(importJsp);
	}

	/**
	 * 根据配置的模型导出数据
	 *
	 * @param xlsid
	 *            模型id
	 * @param data
	 * @throws IOException
	 *
	 * @throws IOException
	 */
	protected void export(Object xlsid, List<?> data, String title, Map<String, String> titleDynicParams) throws IOException {
		Iexport m = Iexport.me.findById(xlsid);
		if (m != null) {
			List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
			// List<Iefield> fileds = Iefield.me.findListByPropertity("ieid", m.get("ieid"));
			List<Iefield> fields = Iefield.me.find("select * from k_iefield where ieid = ? and enabled = 0 order by sort",m.get("ieid").toString());
			// 取消角色对导出字段的配置控制，原因：角色系统不完善；
			loginUser = getSessionAttr(KConfig.SESSION_USER_KEY);
			if(StrKit.notBlank(m.getStr("ietable"))){
				KModel model = KModel.me.findByTableName(m.getStr("ietable"));
				if (model == null) {
					throw new KException("未在KModel中查询到" + m.getStr("ietable") + "配置的字段信息");
				}
			}

			if (loginUser.getRole() == null) {
				throw new KException("当前登录用户未配置"
						+ "信息");
			}

			List<Record> validateDatas = new ArrayList<>();
			int index_num = 1;
			Map<String,Iefield> map = fields.stream().collect(Collectors.toMap(field->field.getStr("field_alias"), field->field));
			boolean hasIndexCell = false; //是否含有序号列
			for (Iefield f : fields) {
				// TODO 根据当前登录角色配置的字段信息导出
				// if (!hasExportField.contains(f.getStr("field_name"))) {
				// continue;
				// }
				ExcelExportEntity eee = new ExcelExportEntity(f.getStr("field_name"), f.getStr("field_alias"), f.getInt("width") == null ? 15 : f.getInt("width"));
				eee.setWrap(true);

				if (StringUtils.defaultIfBlank(eee.getAliasName(), "").equals("序号")) {
					hasIndexCell = true;
					index_num --;
				}

				if (StrKit.notBlank(f.getStr("format"))) {
					eee.setFormat(f.getStr("format"));
				}

				if (f.getInt("text_align") != null && 1 == f.getInt("text_align")) {
					eee.setTextAlign(CellStyle.ALIGN_LEFT);
				} else {
					eee.setTextAlign(CellStyle.ALIGN_CENTER);
				}


				eee.setKey(f.get("field_name"));

				/*if(null!=f.getInt("required")&&f.getInt("required")==1){
                    eee.setTextAlign();
				}*/


				eee.setHeight(20);

				eee.setType(f.getInt("type"));
				if (f.getInt("sort") != null)
					eee.setOrderNum(f.getInt("sort"));
				entity.add(eee);

				String names="";
				Record record_data = new Record();
				if(StrKit.notBlank(f.getStr("data_lookup"))){
					names = Db.queryStr("select group_concat(lkey) from k_lookup where ltid = ? and enabled = 0",f.getStr("data_lookup"));

				}

				if(StrKit.isBlank(names) &&StringUtils.isNotBlank(f.getStr("data_resources"))){
					List<Record> records = Db.find(f.getStr("data_resources"));
					if(records.size() > 0) {
						names = records.stream().map(record -> record.getStr("name")).collect(Collectors.joining(","));
					}
				}
				if(StrKit.notBlank(names)){
					record_data.set("dataArry", StringUtils.split(names.toString(), ","));
					record_data.set("index", index_num);
					validateDatas.add(record_data);
				}

				index_num++;
			}

			//添加序号列
			if (!hasIndexCell)
				entity.add(0, new ExcelExportEntity("sys_index", "序号", 10));

			// 处理Excel的Title
			if (title == null) {
				title = m.getStr("title");
			}

			title = dealDynicParams(title, titleDynicParams);

			ExportParams ep = new ExportParams(title, "sheet1");
			KExcelDataHandler handler = new KExcelDataHandler();
			handler.setFields(fields);
			ep.setDataHanlder(handler);

			if(titleDynicParams != null && titleDynicParams.get("titleheight") != null){
				ep.setTitleHeight(Short.parseShort(titleDynicParams.get("titleheight")));
			}else{
				ep.setTitleHeight((short)26);
			}
			if(titleDynicParams != null && titleDynicParams.get("secondtitleheight") != null){
				ep.setTitleHeight(Short.parseShort(titleDynicParams.get("secondtitleheight")));
			}else{
				ep.setTitleHeight((short)24);
			}

			ep.setStyle(ExcelExportStylerDefaultImpl.class);

			Workbook workbook = ExcelExportUtil.exportExcel(ep, entity, data);
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet != null) {
				// 对数据添加数据有效性验证
				if(validateDatas.size() > 0){
					for (Record validateData : validateDatas) {
						// 给指定列添加有效性数据参数从左到右依次为：需添加验证数据的sheet、有效性数据集合、开始行、结束行、开始列、结束列
						sheet.addValidationData(setDataValidation(sheet,validateData.get("dataArry"), 2, 500, validateData.get("index") , validateData.get("index")));
					}
				}

				//添加序号
				int index = 1, i = 0;
				for (Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext(); ) {
					Row row = iterator.next();

					row.forEach(cell -> {
						CellStyle cellStyle = cell.getCellStyle();
						Font font = workbook.createFont();
						font.setFontName("微软雅黑");
						font.setFontHeightInPoints((short) 10);
						cellStyle.setFont(font);
						cell.setCellStyle(cellStyle);

						if (cell.getRowIndex() == 1) {
							Iefield iefield = map.get(cell.getStringCellValue());
							if (null != iefield && iefield.getInt("allow_blank") == 1) {
								CellStyle style = 	new ExcelExportStylerDefaultImpl(workbook).getTitleStyle(HSSFColor.RED.index);
								Font fonts = workbook.createFont();
								fonts.setColor(HSSFColor.RED.index);
								font.setFontName("微软雅黑");
								font.setFontHeightInPoints((short) 10);
								style.setFont(fonts);
								cell.setCellStyle(style);
							}
						}
					});


					//跳过标题和表头
					if (++i <= 2 || row == null) continue;
					row.getCell(0).setCellValue(String.valueOf(index++));
				}
			}

			File xlsFile = new File(System.getProperty("java.io.tmpdir", PathKit.getWebRootPath()), title + ".xls");
			FileOutputStream fout = new FileOutputStream(xlsFile);

			workbook.write(fout);
			renderFile(xlsFile);
		} else {
			renderError(404);
		}
	}

	/**
	 * 适配导出Excel中 文档标题中有动态参数匹配功能
	 *
	 * @param xlsid=配置的iexport中ID
	 * @param data=Excel中Row数据
	 * @param =Excel自定义标题(为空则获取数据库中配置的title)
	 * @param =Excel自定义中的动态参数
	 * @throws IOException
	 */
	protected void export(Object xlsid, List<?> data) throws IOException {
		export(xlsid, data, null, null);
	}

	/**
	 * 根据模板ID，解析上传的Excel文件
	 *
	 * @param xlsid
	 * @param xlsFile
	 * @throws Exception
	 */
	protected ExcelImportResult<Map<String, Object>> importxls(String xlsid, UploadFile xlsFile) {
		return importxls(xlsid, xlsFile, null, null);
	}

	protected ExcelImportResult<Map<String, Object>> importxls(String xlsid, UploadFile xlsFile, Object dataHandler, Object verifyHandler) {
		if (xlsFile == null || !xlsFile.getFile().exists()) {
			throw new ExcelImportException("excel文件为空");
		}

		if (!xlsFile.getFileName().endsWith(".xls") && !xlsFile.getFileName().endsWith(".xlsx"))
			throw new ExcelImportException("请选择excel文件(xls, xlsx)");

		ExcelImportResult<Map<String, Object>> result = new ExcelImportResult<>();

		ImportParams params = new ImportParams();

		Iexport m = Iexport.me.findById(xlsid);
		if (m != null) {
			params.setTitleRows(1);
			params.setHeadRows(1);
			// 必填包含的列头
			List<String> requiredCol = new ArrayList<String>();
			List<Iefield> fields = Iefield.me.findByIeid(m.getLong("ieid").toString());
			for (Iefield f : fields) {
				String fieldAlias = f.getStr("field_alias");
				fieldAlias = StringUtils.replaceChars(fieldAlias, "（）","()");

				if (1 == f.getInt("required")) {
					requiredCol.add(fieldAlias);
				}
				ExcelImportEntity eie = new ExcelImportEntity();
				if (StrKit.notBlank(f.getStr("format"))) {
					eie.setFormat(f.getStr("format"));

				}
				if (1 == f.getInt("allow_blank")) {
					eie.setAllowBlank(false);
				}

				//转换中英文字符
				eie.setName(fieldAlias);
				eie.setKey(f.getStr("field_name"));
				eie.setType(f.getInt("type")); // 设定导入列的类型
				params.addExcelImportEntity(eie);
			}

			// params.setSaveUrl(""); // 服务器端临时文件保存的位置
			params.setNeedSave(true);
			params.setSaveUrl("upload");
			/**
			 * 导入时校验数据模板,是不是正确的Excel
			 */
			String[] importFields = new String[requiredCol.size()];
			requiredCol.toArray(importFields);
			params.setImportFields(importFields);

			if (dataHandler == null) {
				KExcelDataHandler _dataHandler = new KExcelDataHandler();
				_dataHandler.setFields(fields);
				params.setDataHanlder(_dataHandler);
			} else {
				if (dataHandler instanceof KExcelDataHandler) {
					KExcelDataHandler _dataHandler = (KExcelDataHandler) dataHandler;
					_dataHandler.setFields(fields);
					params.setDataHanlder(_dataHandler);
				}
			}
			// 数据校验
			if (verifyHandler == null) {
				KVerifyHanlder _verifyHandler = new KVerifyHanlder();
				_verifyHandler.setFields(fields);
				params.setVerifyHanlder(_verifyHandler);
			} else {
				if (verifyHandler instanceof KVerifyHanlder) {
					KVerifyHanlder _verifyHandler = (KVerifyHanlder) verifyHandler;
					_verifyHandler.setFields(fields);
					params.setVerifyHanlder(_verifyHandler);
				}
			}
			List<Map<String,Object>> lists = new ArrayList<>();
			String file_names = Db.queryStr("SELECT GROUP_CONCAT(field_name) FROM k_iefield WHERE ieid=? and enabled = 0 ",m.getLong("ieid"));
			result =ExcelImportUtil.importExcelVerify(xlsFile.getFile(), Map.class, params);
			for (Map<String,Object> mapReult:result.getList()){
				Map<String,Object> map = new HashMap<>();
				for(Map.Entry<String, Object> entry: mapReult.entrySet()){
					if(file_names.contains(entry.getKey())){
						map.put(entry.getKey(),entry.getValue());
					}
				}
				lists.add(map);

			}
			result.setList(lists);
		}
		return result;
	}

	/**
	 *
	 * @param flag true 保存成功 false 保存失败
	 * @param status 0 保存； 1 保存并继续
	 * @param tabid
	 */
	protected  void saveStatus(Boolean flag,String status,String tabid){
		if (flag && IDataSource.ONE.equals(status)) {
			//保存并继续
			bjuiAjax(200, IDataSource.SAVE_SUCCESS, false, tabid);
		} else if (flag && (IDataSource.ZERO.equals(status) || StrKit.isBlank(status))) {
			//保存成功
			bjuiAjax(200, IDataSource.SAVE_SUCCESS, true, tabid);
		} else {
			//保存失败
			bjuiAjax(300, IDataSource.SAVE_FAILED, false, "");
		}
	}
	/**
	 * <pre>
	 * statusCode=200,300
	 * msg=操作成功,300=操作失败
	 * closeCurrent = false
	 * tabid= {modelName}-list
	 * </pre>
	 *
	 * @return
	 */
	protected void bjuiAjax(int statusCode) {
		bjuiAjax(statusCode, statusCode == 200 ? "操作成功" : "操作失败");
	}

	/**
	 * <pre>
	 * statusCode=200,300
	 * msg={msg}
	 * closeCurrent = false
	 * tabid= {modelName}-list
	 * </pre>
	 *
	 * @return
	 */
	protected void bjuiAjax(int statusCode, String msg) {
		bjuiAjax(statusCode, msg, false);
	}

	protected void bjuiAjax(int statusCode, boolean closeCurrent) {
		bjuiAjax(statusCode, null, closeCurrent, modelName + "-list");
	}

	/**
	 * <pre>
	 * statusCode=200,300
	 * msg={msg}
	 * closeCurrent = {closeCurrent}
	 * tabid= {modelName}-list
	 * </pre>
	 *
	 * @return
	 */
	protected void bjuiAjax(int statusCode, String msg, boolean closeCurrent) {
		bjuiAjax(statusCode, msg, closeCurrent, modelName + "-list");
	}

	protected void bjuiAjax(int statusCode, boolean closeCurrent, String tabid) {
		bjuiAjax(statusCode, null, closeCurrent, tabid);
	}

	/**
	 * <pre>
	 * statusCode=200,300
	 * msg={msg}
	 * closeCurrent = {closeCurrent}
	 * tabid= {tabid}
	 * </pre>
	 *
	 * @return
	 */
	protected void bjuiAjax(int statusCode, String msg, boolean closeCurrent, String tabid) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("statusCode", statusCode);
		resultMap.put("message", msg);
		resultMap.put("closeCurrent", closeCurrent);
		resultMap.put("tabid", tabid);
		renderJson(resultMap);
	}

	/**
	 * 返回json map对象，可以向里面继续添加其他key-value
	 *
	 * @param statusCode
	 * @param msg
	 * @param closeCurrent
	 * @return
	 */
	protected Map<String, Object> bjuiAjaxBackMap(int statusCode, String msg, boolean closeCurrent) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("statusCode", statusCode);
		resultMap.put("message", msg);
		resultMap.put("closeCurrent", closeCurrent);
		resultMap.put("tabid", modelName + "-list");
		return resultMap;
	}

	/**
	 * 当前登录用户
	 *
	 * @return
	 */
	protected User getLoginUser() {
		return getSessionAttr(KConfig.SESSION_USER_KEY);
	}

	protected boolean isAdminUser(){
		return Objects.equals(getLoginUser().get("role_id") + "", "1");
	}

	/**
	 * 当前登录用户ID
	 *
	 * @return
	 */
	protected Long getLoginUserId() {

		User user = getLoginUser();

		return user == null ? -1L : user.get("id");
	}

	/**
	 * 当前登录用户ID
	 *
	 * @return
	 */
	protected String getLoginUserName() {

		User user = getLoginUser();

		return user == null ? "" : user.getName();
	}

	/**
	 * 当前登录用户的角色
	 *
	 * @return
	 */
	protected Role getUserRole() {
		Role role = getSessionAttr(KConfig.SESSION_ROLE_KEY);

		return role;
	}

	/**
	 * 当前登录用户的所属部门
	 *
	 * @return
	 */
	protected Department getUserDept() {
		Department department = getSessionAttr(KConfig.SESSION_DEPT_KEY);

		return department;
	}

	/**
	 * 处理系统级动态参数（月份、年份等），以及其他自定义参数
	 *
	 * @param title excel导出时标题中含动态参数，也可以用于其他带动态参数的字符串，如url
	 * @param dynicParams
	 * @return
	 */
	private String dealDynicParams(String title, Map<String, String> dynicParams) {
		if (title == null) {
			return "";
		}
		if (dynicParams == null) dynicParams = new HashMap<>();

		Pattern p = Pattern.compile("#(.+?)#");
		Matcher m = p.matcher(title);
		while (m.find()) {
			String dynic = m.group(0); // 查找动态参数#userid#
			String key = dynic.replaceAll("#", "");
			//可从session中读取动态参数
			String value = dynicParams.getOrDefault(key, TypeUtils.castToString(getSessionAttr(key)));

			title = title.replace(dynic, StringUtils.trimToEmpty(value));

			/*if (dynicParams.containsKey(key)) {
				title = title.replace(dynic, dynicParams.get(key));
			} else {
				System.err.println("Title: " + title + "\r\n 动态参数：" + key + " 未传值！！");
			}*/

		}
		return title;

	}

	/**
	 * 获取request参数，如果包含动态参数，将之替换为配置数据
	 * @return
	 */
	@Override
	public String getPara(String name) {
		return dealUrlDynicParams(super.getPara(name));
	}

	/**
	 * 处理url中动态请求参数
	 * @param paramVal
	 * @return
	 */
	private String dealUrlDynicParams(String paramVal){
		//处理#param#动态参数
		if (StrKit.notBlank(paramVal) && paramVal.contains("$")){
			paramVal = StringUtils.replace(paramVal, "$", "#");
			paramVal = dealDynicParams(paramVal, null);
		}
		return paramVal;
	}

	public Controller keepPara() {
		Map<String, String[]> map = getParaMap();
		for (Entry<String, String[]> e: map.entrySet()) {
			String[] values = e.getValue();

			//处理动态请求参数
			if (values != null){
				for (int i = 0; i < values.length; i++) {
					values[i] = dealUrlDynicParams(values[i]);
				}
				if (values.length == 1)
					setAttr(e.getKey(), values[0]);
				else
					setAttr(e.getKey(), values);
			}

		}
		return this;
	}

	/**
	 * 加上时间查询(特殊处理)
	 * @param queryParam
	 */
	public void queryCondition(KWebQueryVO queryParam){
		String start_time = getPara(IConstans.START_TIME);
		String end_time = getPara(IConstans.END_TIME);

		if(StrKit.notBlank(start_time)){

			setAttr(IConstans.START_TIME,start_time);
			removeCondition(queryParam,IConstans.START_TIME,start_time);
		}

		if(StrKit.notBlank(end_time)){
			setAttr(IConstans.END_TIME,end_time);
			removeCondition(queryParam,IConstans.END_TIME,end_time);
		}

		if(StrKit.notBlank(start_time)){
			queryParam.addFilter("end_time", ">=", start_time.concat(" 00:00:00"));
		}
		if(StrKit.notBlank(end_time)){
			queryParam.addFilter("start_time", "<=", end_time.concat(" 23:59:59"));
		}
	}



	/**
	 * 设置excel数据有效性
	 * @param sheet
	 * @param textList 有效性数据集合
	 * @param firstRow  开始行
	 * @param endRow   结束行
	 * @param firstCol  开始列
	 * @param endCol   结束列
	 * @return
	 */
	private static DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		// 设置行列范围
		// 设置数据有效性加载在哪个单元格上。
		// 四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList addressList = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);

		// 设置数据有效性对象
		DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
		DataValidation dataValidation = helper.createValidation(constraint, addressList);

		//处理Excel兼容性问题
		if(dataValidation instanceof XSSFDataValidation) {
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(true);
		}else {
			dataValidation.setSuppressDropDownArrow(false);
		}

		return dataValidation;
	}

	/**
	 * 根据时间倒序查询
	 * @param queryParam
	 */
	public void queryConditionsort(KWebQueryVO queryParam){
		queryParam.setOrderField("create_time");
		queryParam.setOrderDirection("desc");
	}

	public Map<String,Object> findRepeatDate(List<Map<String, Object>> mapList,Map<String,Object> map){
		Map<String,Object> objectMap = new HashMap<>();
		for (Map<String, Object> content : mapList) {
			Date start_time = (Date) content.get("start_time");
			Date toStart_time = (Date) map.get("end_time");

			Date end_time = (Date) content.get("end_time");
			Date toEnd_time = (Date) map.get("start_time");

			//判断是否在同一时间段内
			if (start_time.getTime() <= toStart_time.getTime() && end_time.getTime() >= toEnd_time.getTime()) {
				objectMap.putAll(map);
				break;
			}
		}
		return objectMap;
	}
}
