
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
            <form id="cancel_form" action="${ctx}/kconf/model/exchange" method="post"  data-toggle="validate">
                <input type="hidden" name="mid" value="${mid}">
                <input type="hidden" name="tabid" value="${tabid}">

                <div class="modal-body" style="padding:5px 15px;">
                    <div class="shyj-tile">请选择模型A(将A模型字段覆盖到当前模型)：</div>
                    <select data-toggle="selectpicker" data-width="400" name="modelid"  data-size="10">
                        <c:forEach items="${models }" var="m">
                            <option value="${m.mid }">${m.mname}</option>
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