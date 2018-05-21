<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<style>
	.ess-form ul > li.input_kindeditor { width: 100% }

	.input_file{width: 1210px;}

</style>
<div class="white">
	<div class="ess-form">
		<form id="dept_form" action="${ctx}/kconf/department/save"  data-toggle="validate" data-alertmsg="false">
			<%--<input type="hidden" name="record.${pkName}" value="${record[pkName] }">--%>
			<%--输出隐藏域--%>
				<div id="hiden_input">
					<input type="hidden" name="tabid" value="${tabid}">
					<c:forEach items="${fields}" var="item">
						<c:if test="${item.form_view_type eq 'hidden'}">
							<input type="hidden" name="record.${item.field_name}" value="${empty(record[item.field_name])?item.settings:record[item.field_name]}">
						</c:if>
					</c:forEach>
				</div>
				<ul>
					<c:forEach items="${fields }" var="f">
						<c:if test="${f.form_view_type ne 'hidden'}">

							<c:set var="validate_type" value="${f.form_validate_type }"></c:set>
							<c:if test="${f.form_required eq 'true' }">
								<c:set var="validate_type" value="${validate_type } required"/>
							</c:if>

							<li class="input_${f.form_view_type }" style="${f.form_style == "clear:left" ? "clear:left":fn:contains(f.form_style,'710px')?'width:750px;':''}">
								<label <c:if test="${f.form_required eq 'true' }"> class="required"</c:if> for="j_input_${f.field_name }">
										${f.field_alias }
								</label>
								<c:if test="${f.form_view_type!='kindeditor' && f.form_view_type != 'file' }">
									<c:set var="fvalue" value="${record[f.field_name] }"/>
									<%-- 格式化日期--%>
									<c:if test="${not empty f.list_format and fn:containsIgnoreCase(f.type, 'date')}">
										<fmt:formatDate value="${fvalue }" pattern="${f.list_format }" var="fvalue"/>
									</c:if>

									<input type="text" name="record.${f.field_name }" id="j_input_${f.field_name }"
										   value="${fvalue}"
										   placeholder="请输入${f.field_alias }"
										   data-rule="${validate_type }"
										   data-ds="${ctx }${f.form_data_source }"
										   data-toggle="${f.form_view_type }"
										   data-chk-style="${f.form_chk_style }"
										   data-live-search='${f.form_live_search }'
										   data-url='${f.lookup_url }'
										   <c:if test="${f.form_required eq 'false' }">data-select-empty-text="---请选择---"</c:if>
										   <c:if test="${f.form_readonly eq 'true' }">readonly</c:if>
										   <c:if test="${f.form_chk_style }">multiple="multiple"</c:if>
										   <c:if test="${not empty f.list_format }">data-pattern="${f.list_format }"</c:if>
									/>

							</c:if>
							<c:if test="${f.form_view_type eq 'kindeditor' }">
							<textarea data-toggle="kindeditor" name="record.${f.field_name }"
									  id="j_input_${f.field_name }"
									  data-upload-json="${ctx}/BJUI/plugins/kindeditor_4.1.10/jsp/upload_json.jsp"
									  data-fill-desc-after-upload-image="false">
								${record[f.field_name] }
								${f.settings}
							</textarea>
							</c:if>

							<c:if test="${f.form_view_type eq 'file' }">
								<div id="doc_span_pic" data-toggle="upload" data-uploader="${ctx }/kconf/file/uploadfile?folder=assets&rename=${f.file_rename}"
									 data-file-size-limit="${f.file_size_limit }"
									 data-file-type-exts="${f.file_type_exts }"
									 data-preview-img="false"
									 data-multi="false"
									 data-auto="${f.file_auto }"
									 data-button-text=""
										<c:if test="${f.file_multi}">
											data-upload-num="false"
										</c:if>
										<c:if test="${!f.file_multi}">
											data-upload-num="true"
										</c:if>
									 data-inputid="attachement_${f.field_name }"
									 data-on-upload-success="attachement_upload_success"
									 data-icon="cloud-upload" style="position:relative; display:block; width:100%;background:#f3f8fc;overflow: auto;">

									<input type="hidden" name="record.${f.field_name }"  value="${record[f.field_name]}" id="attachement_${f.field_name }" />

									<div class="queue" style="display:block;min-height:88px;">
										<a href="javascript:void(0)" class="btn btn-default bjui-upload-select" data-icon="cloud-upload" style="margin-top: -10px;margin-right: -10px;width: 24px;height: 24px;padding-left: 4px;padding-top: 4px;float:right;"></a>
										<c:if test="${!empty(record[f.field_name]) }">
											<c:set value="${ fn:split(record[f.field_name], ',') }" var="files" />
											<c:forEach items="${ files }" var="file">
												<div class="item" style="margin-right:6px;">
													<div class="info">
														<span class="up_filename"><a href="${ctx }/kconf/file/downfile?type=assets&filename=${file}" onclick="attachement_ajax_filedownload(this); return false;" >${file}</a></span>
														<span class="up_cancel" title="取消上传" data-file-name="${file }" data-file-id="attachement_${f.field_name }"><i class="fa fa-times-circle-o"></i></span>
													</div>
													<div class="progress">
														<div class="bar" style="width: 100%;"></div>
													</div>
													<span class="percent"><a href="${ctx }/kconf/file/downfile?type=assets&filename=${file}">点击查看文件详细信息</a></span>
												</div>
											</c:forEach>
										</c:if>
									</div>
								</div>
							</c:if>


						</li>
					</c:if>
				</c:forEach>
				<div style="text-align:right; padding-top:5px;">
					<button type="submit" class="btn" style="background: #14CAB4; color: white;position: absolute;top: 0px;right: 20px;">保存修改</button>

				</div>
			</ul>
				<div class="clearfix"></div>

		</form>
	</div>

	<%--<div class="pro_dialog_contain">--%>
	<%--检修作业的具体内容简要描述，特别是大型、交叉作业--%>
	<%--检修作业的具体内容简要描述，特别是大型、交叉作业--%>
	<%--检修作业的具体内容简要描述，特别是大型、交叉作业--%>
	<%--</div>--%>
</div>
<script type="text/javascript">

    initForm($('#dept_form',$.CurrentNavtab));
    var input_range;
    $('.bjui-upload-select').on('click',function(){
        input_range = $(this).parent();
    })


    //Ajax方式下载附件文件
    function attachement_ajax_filedownload(a) {
        $.fileDownload($(a).attr('href'), {
            failCallback: function(responseHtml, url) {
                if (responseHtml.trim().startsWith('{')) responseHtml = responseHtml.toObj()
                $(a).bjuiajax('ajaxDone', responseHtml)
            }
        })
    }


</script>


<c:choose>
	<c:when test="${not empty jsFile}">
		<script src="${ctx }${jsFile}"></script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			$(function(){
                if($("input[name='record.id']").val()=='1'){
                    $("input[name='record.pid']").parent('li').hide();
                }else{
                    $("input[name='record.pid']").parent('li').show();
                }
			});

		</script>
	</c:otherwise>
</c:choose>