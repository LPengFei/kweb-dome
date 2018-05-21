
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>

<style>
    .shyj { width: 100%; resize: vertical; }
    .shyj-tile { margin: 20px 0px 6px 0px; }
    ::-webkit-input-placeholder{ 
   	 	font-size: 11px; 
	}
</style>
<!-- Modal -->

<div class="bjui-pageContent" style="width:100%">
            <form id="cancel_form" action="${ctx}/kconf/iexport/exchange" method="post"  data-toggle="validate">
                <input type="hidden" name="ieid" value="${ieid}">
                <input type="hidden" name="tabid" value="${tabid}">

                <div class="modal-body" style="padding:5px 15px;">
                    <div class="shyj-tile">请选择导入模板A(将A模型字段覆盖到当前导出模板)：</div>
                    <select data-toggle="selectpicker" data-width="400" name="p_ieid"  data-size="10">
                        <c:forEach items="${iexports }" var="i">
                            <option value="${i.ieid }">${i.iename}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="modal-footer" style="display:none; padding: 0px; margin: 0px; border: none;">
                    <div class="form-plan-edit-group-button" style="padding-bottom: 20px; margin-top: 20px;">
                        <button type="submit" class="btn btn-info" id="submit_verify">确认</button>
                        <button type="button" class="btn-close"  style="background: red; color: white;" >取消</button>
                    </div>
                </div>
            </form>
		
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="submit" class="btn-default ">提交</button></li>
        <li><button type="button" class="btn-close">关闭</button></li>
    </ul>
</div>

<script type="text/javascript">

</script>