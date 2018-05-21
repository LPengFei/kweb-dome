<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<style>
    .ess-form ul > li.input_kindeditor {
        width: 100%
    }

    .tags-menu {
        overflow: scroll;
        height: 100px;
        z-index: 100
    }

    #planmonth_form label.required:after {
        content: '  ';
        color: red;
        padding-top: 10px;
        font-size: 100%;
    }
    
    .ess_upload_css{
    	border:none;
    }
    
.bjui-upload > .queue > .item{ position:relative; display:inline-block; margin-top:5px; margin-right:10px; padding:10px; width:200px; background:#f3f8fc; -webkit-box-shadow:0 3px 7px rgba(0,0,0,0.5);-moz-box-shadow:0 3px 7px rgba(0,0,0,0.5);box-shadow:0 3px 7px rgba(0,0,0,0.5);}
.bjui-upload > .queue > .item > .progress{display:inline-block; margin:5px 0;width:180px; height:3px; background-color:#e5e5e5; vertical-align:middle; padding:0; overflow:hidden;}
.bjui-upload > .queue > .item > .preview{padding:4px; width:180px; height:124px; border:1px #CCC solid; border-radius:3px; text-align:center;}
.bjui-upload > .queue > .item > .preview > .img{display:block; width:170px; height:114px; overflow:hidden;}
    

</style>
<div class="bjui-pageContent" id="planmonth_form">
    <div class="ess-form">
        <form id="plan_month_form" action="${ctx}/${appid }/${modelName }/save" data-toggle="validate"
              data-alertmsg="false">
            <div id="hiden_input">
                <input type="hidden" name="tableid" value="${tableid}">
                <c:forEach items="${fields}" var="item">
                    <c:if test="${item.form_view_type eq 'hidden'}">
                        <input type="hidden" name="record.${item.field_name}" value="${record[item.field_name] }">
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
                            <c:if test="${not empty(f.form_notes)}"><span class="problem_c" content="${f.form_notes}">?</span></c:if>
                            <c:if test="${f.form_required eq 'true' }"><span class="import_start">  *</span></c:if>
                            </label>
                            <c:if test="${f.form_view_type!='kindeditor' && f.form_view_type != 'file' }">
                                <c:set var="fvalue" value="${record[f.field_name] }"/>
                                <%-- 格式化日期--%>
                                <c:if test="${not empty f.list_format and fn:containsIgnoreCase(f.type, 'date')}">
                                    <fmt:formatDate value="${fvalue }" pattern="${f.list_format }" var="fvalue"/>
                                </c:if>

                                <input type="text" name="record.${f.field_name }" id="j_input_${f.field_name }_${f.mid}"
                                       style="${f.form_style }" class="muted"
                                       value="${fvalue ==null?f.settings:fvalue}"
                                       placeholder="请输入${f.field_alias }"
                                       data-rule="${validate_type }"
                                       data-ds="${ctx }${f.form_data_source }"
                                       data-toggle="${f.form_view_type }"
                                       data-chk-style="${f.form_chk_style }"
                                       data-form-style="${f.form_style }"
                                       data-live-search='${f.form_live_search }'
                                       data-url='${ctx }${f.data_url }?chk_style=${f.chk_style}&lookup_param=${f.field_name }&lookup_modelId=${f.mid}'
                                       data-group="${f.data_group }"
                                       data-width="${f.data_width }"
                                       data-height="${f.data_height eq ""?'400':f.data_height}"
                                       data-title="${f.data_title }"
                                       <c:if test="${f.form_required eq 'false' }">data-select-empty-text="---请选择---"</c:if>
                                       <c:if test="${f.form_readonly eq 'readonly' }">readonly</c:if>
                                       <c:if test="${f.form_chk_stype }">multiple="multiple"</c:if>
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
			                     <div id="doc_span_pic" data-toggle="upload" data-uploader="${ctx }/kconf/file/uploadfile?folder=document&rename=${f.file_rename}"
			                            data-file-size-limit="${f.file_size_limit }"
			                            data-file-type-exts="${f.file_type_exts }"
			                            data-preview-img="false"
			                            data-multi="${f.file_multi }"
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
			                            data-icon="cloud-upload" style="position:relative; display:block; width:960px;background:#f3f8fc;overflow: auto;">
			                    		
			                    		<input type="hidden" name="record.${f.field_name }"  value="${record[f.field_name]}" id="attachement_${f.field_name }" />
			                            
			                           	<div class="queue" style="display:block;min-height:88px;">
			                            	<a href="javascript:void(0)" class="btn btn-default bjui-upload-select" data-icon="cloud-upload" style="margin-top: -10px;margin-right: -10px;width: 24px;height: 24px;padding-left: 4px;padding-top: 4px;float:right;"></a>
			                           		<c:if test="${!empty(record[f.field_name]) }">
			                           			<c:set value="${ fn:split(record[f.field_name], ',') }" var="files" />
			                           			<c:forEach items="${ files }" var="file">
				                           		<div class="item" style="margin-right:6px;">    
					                           		<div class="info">
					                           			<span class="up_filename"><a href="${ctx }/kconf/file/downfile?filename=${file}&type=document" onclick="attachement_ajax_filedownload(this); return false;" >${file}</a></span>
					                           			<span class="up_cancel" title="取消上传" data-file-name="${file }" data-file-id="attachement_${f.field_name }"><i class="fa fa-times-circle-o"></i></span>
					                           		</div>
					                           		<div class="progress">
					                           			<div class="bar" style="width: 100%;"></div>
					                           		</div>    
					                           		<span class="percent"><a href="${ctx }/kconf/file/downfile?filename=${file}&type=document">点击查看文件详细信息</a></span>
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
            <div class="clearfix"></div>
            <!-- 操作按钮 -->
            <div style="text-align:right; padding-top: 20px;width:960px;">
                <button type="button" class="btn-close" style="background: red; color: white;margin-left: 10px;">取消
                </button>
                <button type="submit" class="btn" style="background: #14CAB4; color: white;margin-left: 10px;">保存
                </button>
            </div>
        </form>
    </div>

    <%--<div class="pro_dialog_contain">--%>
    <%--检修作业的具体内容简要描述，特别是大型、交叉作业--%>
    <%--检修作业的具体内容简要描述，特别是大型、交叉作业--%>
    <%--检修作业的具体内容简要描述，特别是大型、交叉作业--%>
    <%--</div>--%>
</div>
<script type="text/javascript">
    initForm($('#common_form',$.CurrentNavtab));
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

            /* 点击保存并继续按钮的js */
            $(function () {
                /*
                 * 判断是否支持localstorage
                 */
                if (localStorage) {

                }

            });

        </script>

        <script>
            $(function () {
                $.problem_dia('.problem_c')
            })
        </script>


    </c:otherwise>
</c:choose>
