package com.cnksi.utils;

/**
 * Created by zf on 2017/4/24.
 */

/**
 * 数据源
 */
public class IDataSource {


    /**
     * 停电归类（配网）
     */
    public static String BLACKOUT_TYPE_PW="配网";
    /**
     * 停电归类（主网）
     */
    public static String BLACKOUT_TYPE_ZW ="主网";

    /**
     * 停电类型（县调(停电)）
     */
    public static String BLACKOUT_TYPE_XD="县调(停电)";
    /**
     * 停电类型（地调(停电)）
     */
    public static String BLACKOUT_TYPE_DD ="地调(停电)";


    /**
     * 部门类型（本部）
     */
    public static String DEPT_TYPE_BENBU = "benbu";
    /**
     * 部门类型（基层单位）
     */
    public static String DEPT_TYPE_JICENG = "jiceng";

    /**
     * 部门类型（基层部门）
     */
    public static String DEPT_TYPE_JICENGBUMEN="jicengbumen";

    /**
     * 部门类型（车间）
     */
    public static String DEPT_TYPE_CHEJIAN="chejian";
    /**
     * 部门类型（车间部门）
     */
    public static String DEPT_TYPE_CHEJIANBUMEN="chejianbumen";

    /**
     * 部门类型（科室）
     */
    public static String DEPT_TYPE_KESHI="keshi";

    /**
     * 部门类型（班组）
     */
    public static String DEPT_TYPE_BANZU="banzu";

    /**
     * 线路
     */
    public static String LINEBDZ_XL="线路";

    /**
     * 变电站
     */
    public static String LINEBDZ_BDZ="变电站";

    /**
     *草稿状态（计划状态）
     */
    public static String PLAN_STATUS_DRAFT="draft";

    /**
     * 流转中状态（计划状态）
     */
    public static String PLAN_STATUS_VERIFYING="verifying";

    /**
     * 审核通过（计划状态）
     */
    public static String PLAN_STATUS_VERIFY="verify";

    /**
     * 被退回（计划状态）
     */
    public static String PLAN_STATUS_REJECT="reject";

    /**
     * 被撤回(计划状态)
     */
    public static String PLAN_STATUS_RECALL = "recall";

    /**
     * 取消月计划(计划状态)
     */
    public static String PLAN_STATUS_CANCEL= "cancel";

    /**
     * 月计划审核(审核类型)
     */
    public static String AUDIT_PLAN_MONTH = "audit_plan_month";

    /**
     * 周计划审核(审核类型)
     */
    public static String AUDIT_PLAN_WEEK = "audit_plan_week";

    /**
     * 日计划审核（审核类型）
     */
    public static String AUDIT_PLAN_DAY = "audit_plan_day";
    /**
     * 抢修计划审核（审核类型）
     */
    public static String AUDIT_PLAN_REPAIR = "audit_plan_repair";
    /**
     * 临时计划审核（审核类型）
     */
    public static String AUDIT_PLAN_NORMAL = "audit_plan_normal";
    /**
     * 周计划变更审核(审核类型)
     */
    public static String AUDIT_PLAN_WEEK_CHANGE = "audit_plan_week_change";

    /**
     * 抢修变更计划审核（审核类型）
     */
    public static String AUDIT_PLAN_REPAIR_CHANGE = "audit_plan_repair_change";
    /**
     * 临时变更计划审核（审核类型）
     */
    public static String AUDIT_PLAN_NORMAL_CHANGE = "audit_plan_normal_change";

    /**
     * 审核结果（通过）
     */
    public static String VERIFY_RESULT_APPROVAL = "approval";

    /**
     * 审核结果（拒绝）
     */
     public static String VERIFY_RESULT_REJECT = "reject";

    /**
     * 男(性别)
     */
    public static String SEX_MAN="男";

    /**
     * 女(性别)
     */
    public static String SEX_WOMAN="女";

    /**
     * 周计划(计划类型)
     */
    public static String ZHOU_PLAN_TYPE="zhou";

    /**
     * 抢修计划(计划类型)
     */
    public static String QIANGXIU_PLAN_TYPE="qiangxiu";

    /**
     * 临时计划(计划类型)
     */
    public static String LINSHI_PLAN_TYPE="linshi";

    /**
     * 周变更计划(计划变更类型)
     */
    public static String ZHOU_CHANGE_PLAN_TYPE="zhou_change";

    /**
     * 抢修变更计划(计划变更类型)
     */
    public static String QIANGXIU_CHANGE_PLAN_TYPE="qiangxiu_change";

    /**
     * 临时变更计划(计划变更类型)
     */
    public static String LINSHI_CHANGE_PLAN_TYPE="linshi_change";

    /**
     * 保存成功
     */
    public static String SAVE_SUCCESS = "保存成功!";

    /**
     * 保存失败
     */
    public static String SAVE_FAILED="保存失败!";

    public static String ONE = "1";

    public static String ZERO = "0";

    /**
     * 加入黑名单扣分分数
     */
    public static int BLACKLIST_SCORE = 12;

    /**
     * 无法获取到相应的数据
     */
    public static String NOT_DATA="无法获取到相应的数据";

    /**
     * 变更状态（取消）
     */
    public static String CHANGE_STATUS_DELETE = "delete";

    /**
     * 延期
     */
    public static String CHANGE_STATUS_DELAY = "delay";

    /**
     * 暂时停工
     */
    public static String CHANGE_STATUS_STOP_WORK="stop_work";

    /**
     * 提前完工
     */
    public static String CHANGE_STATUS_PRE_COMPLETE="pre_complete";

    /**
     * 正常完工
     */
    public static String CHANGE_STATUS_COMPLETE = "complete";

    /**
     * 正常状态
     */
    public static String CHANGE_STATUS_NORMAL = "normal";


    /**
     * 安监部(安质部)
     */
    public static String DEPT_ANJB__RELEVANT="安监";


    /**
     * 天府新区
     */
    public static String TFXQ_CTX="tfxq";
}
