package com.cnksi.yw.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.utils.DateUtil;
import com.cnksi.yw.model.*;
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

/**
 *
 */
@KRequiresPermissions(name = "运维日志管理", model = Ylog.class)
public class YlogController extends KController {

    public YlogController() {
        super(Ylog.class);
    }

    @KRequiresPermissions(name = "列表")
    public void index() {

        KWebQueryVO queryParam = super.doIndex();
        if (queryParam != null) {
            queryParam.addFilter("enabled = 0");
            queryDate(queryParam);
            setAttr("page", Ylog.me.paginate(queryParam));
        }

        render("/WEB-INF/jsp/ylog/ylog_list.jsp");
    }

    private void queryDate(KWebQueryVO queryParam) {

        String start_time = getPara(IConstans.START_TIME);
        String end_time = getPara(IConstans.END_TIME);

        if (StrKit.notBlank(start_time)) {
            setAttr(IConstans.START_TIME, start_time);
            removeCondition(queryParam, IConstans.START_TIME, start_time);
        }

        if (StrKit.notBlank(end_time)) {
            setAttr(IConstans.END_TIME, end_time);
            removeCondition(queryParam, IConstans.END_TIME, end_time);
        }

        if (StrKit.notBlank(start_time)) {
            queryParam.addFilter("start_time", ">=", start_time.concat(" 00:00:00"));
        }
        if (StrKit.notBlank(end_time)) {
            queryParam.addFilter("start_time", "<=", end_time.concat(" 23:59:59"));
        }

    }

    @KRequiresPermissions(name = "编辑")
    public void edit() {
        keepPara();
        super.doEdit();
        String tab = getPara(IConstans.TABID);
        String modelId = getPara(IConstans.MID);
        if (StrKit.notBlank(modelId)) {
            getKModel(modelId);
        }
        if (StrKit.notBlank(tab)) {
            tabid = tabid + "," + tab;
        }
        setAttr("tabid", tabid);
        String idValue = getPara("id", getPara());
        Ylog record = null;
        if (idValue != null) {
            record = Ylog.me.findById(idValue);
        } else {

            User loginUser = getSessionAttr(KConfig.SESSION_USER_KEY);
            record = new Ylog();
            record.set("user_id", loginUser.get("id"));
            record.set("start_time", new Date());

        }
        setAttr("record", record);

        render(formJsp);
    }


    @KRequiresPermissions(name = "保存")
    public void save() {
        //查询相应的tabid,如果传过来的tabid为空就读取模型中的tabid
        String tab = getPara(IConstans.TABID);
        String modelId = getPara(IConstans.MID);
        if (StrKit.notBlank(modelId)) {
            getKModel(modelId);
        }
        if (StrKit.notBlank(tab)) {
            tabid = tabid + "," + tab;
        }
        Ylog record = getModel(Ylog.class, "record");
        if (record.get("id") != null) {
            record.update();
        } else {
            record.set("create_time", new Date());
            record.set("create_time", new Date());
            record.set("creator_id", getLoginUserId());
            record.set("creator_name", getLoginUserName());
            User user = User.me.findById(record.getLong("user_id"));
            record.set("user_name", user.getStr("uname"));
            Project p = Project.me.findById(record.getLong("project_id"));
            record.set("city", p.getStr("city"));
            record.save();
        }

        bjuiAjax(200, true, tabid);
    }


    @KRequiresPermissions(name = "详情查看")
    public void view() {

        super.doEdit();

        String idValue = getPara(IConstans.ID, getPara());
        Ylog record = null;
        if (idValue != null) {
            record = Ylog.me.findById(idValue);
            setAttr(IConstans.RECORD, record);
            render(detailJsp);
        } else {
            bjuiAjax(300, IDataSource.NOT_DATA, false);
        }
    }


    @KRequiresPermissions(name = "删除")
    public void delete() {
        Ylog record = Ylog.me.findById(getPara("id"));
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<Ylog> p = null;
        if (queryParam != null) {
            queryParam.addFilter("enabled = 0");
            queryDate(queryParam);
            p = Ylog.me.paginate(queryParam);
        }
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    Ylog record = new Ylog();
                    record.put(map);
                    record.set("create_time", new Date());
                    record.set("creator_id", getLoginUserId());
                    record.set("creator_name", getLoginUserName());
                    User user = User.me.findById(record.getLong("user_id"));
                    record.set("user_name", user.getStr("uname"));
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

        if (Integer.parseInt(resultMap.get("statusCode").toString()) == 200)
            resultMap.put("message", "导入数据成功！");

        resultMap.put("errorFile", errorFile);

        renderJson(resultMap);

    }


    public void lookup() {
        keepPara();

        String kmodelid = getPara("modelId", getPara());
        KWebQueryVO queryParam;

        if (kmodelid != null) {
            KModel kModel = getKModel(kmodelid);
            queryParam = super.doIndex(kModel, null);
        } else
            queryParam = super.doIndex();

        if (queryParam != null)
            setAttr("page", Ylog.me.paginate(queryParam));

        setAttr("modelName", "ylog");
        render(lookupJsp);
    }

    public void report() {
        //统计默认开始时间和结束时间
        Date start_time = DateUtil.getWeekStartDate();
        Date end_time = DateUtil.getWeekEndDate();

        setAttr("start_time", start_time);
        setAttr("end_time", end_time);
        render("/WEB-INF/jsp/ylog/report.jsp");
    }

    public void ylogReport() {
        YlogVo ylogVo = new YlogVo();


        List<YlogVo.SeriesBean> seriesBeans = new ArrayList<>();

        String data_type = getPara("type");
        String start_time = getPara("start_time");
        String end_time = getPara("end_time");
        if ("week".equals(data_type)) {
            start_time = DateUtil.getWeekStart();
            end_time = DateUtil.getWeekEnd();
        } else if ("month".equals(data_type)) {
            start_time = DateUtil.getMonthStart();
            end_time = DateUtil.getMonthEnd();
        } else if ("year".equals(data_type)) {
            start_time = DateUtil.getYearStart();
            end_time = DateUtil.getYearEnd();
        }

        StringBuilder sql = new StringBuilder("select p.id,p.`name`,y.type,sum(y.used_time) as used_time  from y_ylog y left join y_project p on y.project_id = p.id where 1=1 ");
        if (StrKit.notBlank(end_time)) {
            sql.append(" and y.start_time <= '" + end_time.concat(" 23:59:59") + "'");
        }
        if (StrKit.notBlank(start_time)) {
            sql.append(" and y.start_time >= '" + start_time.concat(" 00:00:00") + "'");
        }
        sql.append(" group by y.project_id,y.type");
        List<Record> records = Db.find(sql.toString());

        List<Lookup> lookups = Lookup.me.findByTypeId("yw_log_type");
        List<Project> projects = Project.me.find("select * from y_project where enabled = 0 order by sort");

        for (int i = 0; i < lookups.size(); i++) {
            YlogVo.SeriesBean seriesBean = new YlogVo.SeriesBean();
            String lvalue = lookups.get(i).getValue();
            List<Double> data = new ArrayList<>();
            for (int j = 0; j < projects.size(); j++) {

                Long project_id = projects.get(j).getLong("id");
                Double used_time = 0.0;

                for (int k = 0; k < records.size(); k++) {

                    Long project_id_ = records.get(k).getLong("id");
                    String type = records.get(k).getStr("type");
                    if (lvalue.equals(type) && project_id.equals(project_id_)) {
                        used_time = records.get(k).getDouble("used_time");
                    }

                }
                data.add(used_time);


            }
            seriesBean.setData(data);
            seriesBean.setName(lvalue);
            seriesBeans.add(seriesBean);
        }
        List<String> projectNames = projects.stream().map(project -> project.getStr("name")).collect(Collectors.toList());

        ylogVo.setStatus("ok");
        ylogVo.setStart_time(start_time);
        ylogVo.setEnd_time(end_time);
        ylogVo.setProjectName(projectNames);
        ylogVo.setSeries(seriesBeans);
        renderJson(ylogVo);


    }

    public void ylogReportPie() {
        YlogVoPie yvp = new YlogVoPie();

        String data_type = getPara("type");
        String start_time = getPara("start_time");
        String end_time = getPara("end_time");

        if ("week".equals(data_type)) {
            start_time = DateUtil.getWeekStart();
            end_time = DateUtil.getWeekEnd();
        } else if ("month".equals(data_type)) {
            start_time = DateUtil.getMonthStart();
            end_time = DateUtil.getMonthEnd();
        } else if ("year".equals(data_type)) {
            start_time = DateUtil.getYearStart();
            end_time = DateUtil.getYearEnd();
        }

        List<YlogVoPie.SeriesBean> seriesBeans = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT sum(y.used_time) as used_time,y.type as type FROM `y_ylog` y where 1=1 ");
        if (StrKit.notBlank(end_time)) {
            sql.append(" and y.start_time <= '" + end_time.concat(" 23:59:59") + "'");
        }
        if (StrKit.notBlank(start_time)) {
            sql.append(" and y.start_time >= '" + start_time.concat(" 00:00:00") + "'");
        }
        sql.append(" group by y.type");
        List<Record> records = Db.find(sql.toString());
        List<Lookup> lookups = Lookup.me.findByTypeId("yw_log_type");

        for (Record record : records) {
            YlogVoPie.SeriesBean seriesBean = new YlogVoPie.SeriesBean();
            seriesBean.setValue(record.getDouble("used_time"));
            seriesBean.setName(record.getStr("type"));
            seriesBeans.add(seriesBean);
        }

        List<String> types = lookups.stream().map(lookup -> lookup.getStr("lvalue")).collect(Collectors.toList());

        if (records.size() > 0) {
            yvp.setStatus("ok");
        } else {
            yvp.setStatus("false");
        }

        yvp.setStart_time(start_time);
        yvp.setEnd_time(end_time);
        yvp.setType(types);
        yvp.setSeries(seriesBeans);
        renderJson(yvp);
    }


    public void ylogUpload() {

        String ids = getPara(IConstans.IDS);
        String modelId = getPara(IConstans.MID);
        //查询相应的tabid
        getKModel(modelId);


        YlogVoUp yvu = new  YlogVoUp();
        if (!StrKit.notBlank(ids)) {
            yvu.setStatus("error");
            renderJson(yvu);
        }else{
            List<Record> ylogs = Ylog.me.getYlogList(ids);
            List<YlogVoUp.DataBean> datas = new ArrayList<>();

            for(Record record : ylogs ){
                YlogVoUp.DataBean db  =  new  YlogVoUp.DataBean();
                db.setEmail(record.getStr("email"));
                db.setProject_name(record.getStr("project_name"));
                db.setJob_content(record.getStr("job_content" ));
                db.setJb_date(record.getDate("jb_date"));
                db.setJob_duration(record.getDouble("job_duration"));
                db.setType_name(record.getStr("type_name"));
                datas.add(db);

            }
            yvu.setStatus("ok");
            yvu.setData(datas);
            renderJson(yvu);
        }


    }

}
