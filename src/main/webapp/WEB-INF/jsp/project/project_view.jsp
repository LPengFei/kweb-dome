<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/16
  Time: 18:44
  To change this template use File | Settings | File Templates.

  详情页面入口
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<style>
    \  .tile-body .half_ a {
        display: inline-block;
        float: left;
        padding: 5px 9px;
        background-color: #15cab5;
        color: white;
        border-radius: 3px;
        margin-right: 10px;
        cursor: pointer
    }

    .tile-body .half_ span {
        display: inline-block;
        float: left;
        cursor: pointer;
        line-height: 21px;
        width: 80%
    }

    \  .plM_search .out_line select, .plM_search .out_line input {
        height: 27px;
        border: 1px solid #e2e7e8;
        border-radius: 3px
    }

    .bjui-upload > .queue > .item {
        position: relative;
        display: inline-block;
        margin-top: 5px;
        margin-right: 10px;
        padding: 10px;
        width: 200px;
        background: #f3f8fc;
        -webkit-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);
        -moz-box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);
        box-shadow: 0 3px 7px rgba(0, 0, 0, 0.5);
    }

    .bjui-upload > .queue > .item > .progress {
        display: inline-block;
        margin: 5px 0;
        width: 180px;
        height: 3px;
        background-color: #e5e5e5;
        vertical-align: middle;
        padding: 0;
        overflow: hidden;
    }

    .bjui-upload > .queue > .item > .preview {
        padding: 4px;
        width: 180px;
        height: 124px;
        border: 1px #CCC solid;
        border-radius: 3px;
        text-align: center;
    }

    .bjui-upload > .queue > .item > .preview > .img {
        display: block;
        width: 170px;
        height: 114px;
        overflow: hidden;
    }

</style>
<script type="text/javascript">
    //查看大图
    $.fancyboxInit('.fancy_arr');
    $(function () {
        //变更日志
        $(".switch-button .off").click(function () {
            $(this).hide().siblings().show();
            $(".change-log-window").animate({"right": "-258px"}, 300);
        });
        $(".switch-button .on").click(function () {
            $(this).hide().siblings().show();
            $(".change-log-window").animate({"right": "0px"}, 500)
        });
    });

</script>
<div class="plan-verify-wrap">


    <div class="section verify">
        <div class="tile-header">
            <h4>项目详情</h4>
        </div>
        <form>
            <div class="tile-body ess-form" style="padding: 0px;">
                <ul>
                    <c:forEach items="${fields }" var="f">
                        <c:if test="${f.form_view_type ne 'hidden'}">

                            <c:set var="validate_type" value="${f.form_validate_type }"></c:set>
                            <c:if test="${f.form_required eq 'true' }">
                                <c:set var="validate_type" value="${validate_type } required"/>
                            </c:if>

                            <li class="input_${f.form_view_type }"
                                style="${f.form_style == "clear:left" ? "clear:left":fn:contains(f.form_style,'710px')?'width:750px;':''}">
                                <label for="j_input_${f.field_name }">
                                        ${f.field_alias }
                                </label>
                                <c:if test="${f.form_view_type!='kindeditor' && f.form_view_type != 'file' }">
                                    <c:set var="fvalue" value="${record[f.field_name] }"/>
                                    <%-- 格式化日期--%>
                                    <c:if test="${not empty f.list_format and fn:containsIgnoreCase(f.type, 'date')}">
                                        <fmt:formatDate value="${fvalue }" pattern="${f.list_format }" var="fvalue"/>
                                    </c:if>

                                    <input type="text" name="record.${f.field_name }"
                                           id="j_input_${f.field_name }_${f.mid}"
                                           style="${f.form_style }" class="muted"
                                           value="${fvalue}"
                                           placeholder="请输入${f.field_alias }"
                                           data-ds="${ctx }${f.form_data_source }"
                                           data-toggle="${f.form_view_type }"
                                           readonly="readonly"
                                    />


                                </c:if>
                                <c:if test="${f.form_view_type eq 'kindeditor' }">
						        	<textarea data-toggle="kindeditor" name="record.${f.field_name }"
                                              id="j_input_${f.field_name }" readonly="readonly"
                                              data-upload-json="${ctx}/BJUI/plugins/kindeditor_4.1.10/jsp/upload_json.jsp"
                                              data-fill-desc-after-upload-image="false">
								${record[f.field_name] }
								${f.settings}
							</textarea>
                                </c:if>

                                <c:if test="${f.form_view_type eq 'file' }">
                                    <div id="doc_span_pic" data-toggle="upload"
                                         data-uploader="${ctx }/kconf/file/uploadfile?folder=pictures"
                                         style="position:relative; display:block; width:960px;background:#f3f8fc;overflow: auto;">
                                        <div class="queue" style="display:block;min-height:88px;">
                                            <c:if test="${!empty(record[f.field_name]) }">
                                                <c:set value="${ fn:split(record[f.field_name], ',') }" var="files"/>
                                                <c:forEach items="${ files }" var="file">
                                                    <div class="item" style="margin-right:6px;">
                                                        <div class="info">
                                                            <span class="up_filename"><a
                                                                    href="${ctx }/kconf/file/downfile?type=pictures&filename=${file}"
                                                                    onclick="attachement_ajax_filedownload(this); return false;">${file}</a></span>
                                                        </div>
                                                        <div class="progress">
                                                            <div class="bar" style="width: 100%;"></div>
                                                        </div>
                                                        <span class="percent"><a
                                                                href="${ctx }/kconf/file/downfile?type=pictures&filename=${file}">点击查看文件详细信息</a></span>
                                                    </div>
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>


                            </li>
                        </c:if>
                    </c:forEach>

                </ul>

            </div>
        </form>
        <span class="clearfix"></span>

    </div>


    <div class="col-md-12 honors_section">
        <h2 class="subtitle">发布历史信息</h2>
        <c:if test="${empty deployList}">
            <p class="no_data">暂无发布历史数据</p>
        </c:if>

        <c:if test="${not empty deployList}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>版本</th>
                    <th>发布时间</th>
                    <th>发布人员</th>
                    <th>测试人员</th>
                    <th>验收人员</th>
                    <th>备注</th>
                    <th>相关附件</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${deployList}" var="deploy">
                    <tr>
                        <td width="80">V${deploy.version}</td>
                        <td width="180"><fmt:formatDate value="${deploy.start_time}" pattern="yyyy-MM-dd HH:mm"/>
                            至
                            <fmt:formatDate value="${deploy.end_time}" pattern="yyyy-MM-dd HH:mm"/>
                        </td>
                        <td width="80">${deploy.deploy_person}</td>
                        <td width="80">${deploy.test_person}</td>
                        <td width="80">${deploy.validation_person}</td>
                        <td width="80">${deploy.remark}</td>
                        <td width="80">
                            <c:if test="${!empty deploy.attachment }">
                                <c:set value="${ fn:split(deploy.attachment, ',') }" var="files"/>
                                <c:forEach items="${ files }" var="file">
                                    【<span class="percent" style="margin-top: 20px"><a href="${ctx }/kconf/file/downfile?type=pictures&filename=${file}">${file}</a></span>】
                                </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>

            </table>
        </c:if>
    </div>

    <div class="col-md-12 honors_section">
        <h2 class="subtitle">培训历史信息</h2>
        <c:if test="${ empty trainingList}">
            <p class="no_data">暂无培训历史数据</p>
        </c:if>
        <c:if test="${not empty trainingList}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>培训时间</th>
                    <th>培训地点</th>
                    <th>培训人员</th>
                    <th>甲方联系人及联系方式</th>
                    <th>培训内容</th>
                    <th>备注</th>
                    <th>相关附件</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${trainingList}" var="training">
                    <tr>
                        <td><fmt:formatDate value="${training.start_time}" pattern="yyyy-MM-dd HH:mm"/>
                            至
                            <fmt:formatDate value="${training.end_time}" pattern="yyyy-MM-dd HH:mm"/>
                        </td>
                        <td width="150">${training.place}</td>
                        <td width="80">${training.person}</td>
                        <td width="80">${training.party_a}</td>
                        <td>${training.content}</td>
                        <td width="150">${training.remark}</td>
                        <td width="150">
                            <c:if test="${!empty training.attachment }">
                                <c:set value="${ fn:split(training.attachment, ',') }" var="files"/>
                                <c:forEach items="${ files }" var="file">
                                    【<span class="percent" style="margin-top: 20px"><a href="${ctx }/kconf/file/downfile?type=pictures&filename=${file}">${file}</a></span>】
                                </c:forEach>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>

            </table>
        </c:if>
    </div>


</div>


<script type="text/javascript">
    initForm();
</script>