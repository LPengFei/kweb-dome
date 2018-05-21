<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>

<form id="pagerForm" action="${ctx }/kconf/modellink/save" data-toggle="validate" data-alertmsg="false">
	<div class="bjui-pageContent">
		<div class="ess-form">
			<input type="hidden" name="r.id" value="${record.id }">
			<input type="hidden" name="r.mid" value="${record.mid }">
			<input type="hidden" name="r.enabled" value="0">
			<fieldset>
				<legend style="display: block;">模型链接配置</legend> 
				<ul>
					<li>
						<label>名称</label>
						<input type="text" name="r.label"  value="${record.label }" data-rule="required" placeholder="请输入链接名称">
					</li>
					<li>
						<label>模型</label>
						<input type="text"  value="${mname }" readonly="readonly">
					</li>

					<li style="margin-left: 30px;">
						<label>显示位置</label>
						<select data-toggle="selectpicker" name="r.position" value="${record.position}" data-width="210" id="j_input_position">
							<c:if test="${record.position eq 'nav' }">
								<option value="list">list</option>
								<option value="nav" selected="selected">nav</option>
								<option value="lot">lot</option>
							</c:if>
							<c:if test="${record.position eq 'list' or record.position == null}">
								<option value="list">list</option>
								<option value="nav">nav</option>
								<option value="lot">lot</option>
							</c:if>
							<c:if test="${record.position eq 'lot' }">
								<option value="list">list</option>
								<option value="nav">nav</option>
								<option value="lot" selected="selected">lot</option>
							</c:if>
						</select>

					</li>
					<li>
						<label>顺序</label>
						<input type="text"  name="r.ord" value="${record.ord }"  placeholder="顺序">
					</li>

					<li style="clear:left;width: 990px;">
						<label>地址</label>
						<input type="text" name="r.link"  value="${record.link }"
						 placeholder="例如：/app/project/edit/{proid}/{project_type}" style="width:100%;" />
					</li>


				 	<li style="clear:left;width: 990px;">
						<label>样式</label>
					 	<input type="text" name="r.style" value="${record.style }" placeholder="例如：color:green;" style="width:100%;" />
				 	</li>
				 	<br>
				 	<li style="clear:left;width: 990px;">
						<label>其他</label>
                        <textarea type="text" name="r.other" placeholder="请输入其他" style="width:100%;height: 65px;" >${record.other }</textarea>
				 	</li>

					<li style="clear:left;width: 990px;">
						<label>备注</label>
						<input type="text"  name="r.remark" value="${record.remark }"  style="width:100%;" placeholder="备注">
					</li>
				</ul>
			</fieldset>
			<!-- 操作按钮 -->
			<div style="text-align:right;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-close" style="background: red; color: white;">取消</button>
			</div>
			<div class="clearfix">
			</div>
		</div>
	</div>
</form>

<script type="text/javascript">

    initForm($('#pagerForm'));
</script>
 