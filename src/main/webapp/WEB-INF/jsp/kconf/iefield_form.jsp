<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageContent">
	<div class="ess-form">
		<form action="${ctx}/kconf/iexport/fieldSave"  data-toggle="validate" data-alertmsg="false">
			<input type="hidden" name="record.iefid" value="${record.iefid }">
			<input type="hidden" name="record.ieid" value="${record.ieid }">
			<c:if test="${iexport.ietype eq '导出' }">
				<span style="font-weight: bold;line-height:30px;">导出配置 </span>
				<hr/>
				<ul>
					<li>
						<label>字段名称</label>
						<input type="text" name="record.field_name" id="j_field_name" value="${record.field_name }" placeholder="请输入名称" class="form-control">
					</li>
					<li>
						<label>字段别名</label>
						<input type="text" name="record.field_alias" id="j_field_alias" value="${record.field_alias }" placeholder="请输入别名" class="form-control">
					</li>
					<li>
						<label>宽度</label>
						<input type="text" name="record.width" id="j_width" value="${ record.width }" placeholder="请输入宽度" class="form-control">
					</li>
					<li>
						<label>日期格式化</label>
						<input type="text" name="record.format" id="j_format" value="${record.format }" placeholder="如：yyyy-MM-dd" class="form-control">
					</li>
					<li>
						<label>字段顺序</label>
						<input type="text" name="record.sort" id="j_sort" value="${record.sort }" placeholder="请输入字段顺序" class="form-control">
					</li>
					<li>
						<label>类型</label>
						<select data-toggle="selectpicker" data-width="200" name="record.type"  data-size="10" data-val="${record.type }">
							<option value="1"  selected="selected" >StringType</option>
							<option value="10" <c:if test="${record.type == '10' }"> selected="selected" </c:if>>DoubleType</option>
							<option value="3" <c:if test="${record.type == '3' }"> selected="selected" </c:if>>ImageType</option>
						</select>
					</li>
					<li>
						<label>对齐方式</label>
						<select data-toggle="selectpicker" data-width="200" name="record.text_align"  data-size="10" data-val="${record.text_align }">
							<option value="1"  selected="selected" >left</option>
							<option value="2" <c:if test="${record.text_align == '2' }"> selected="selected" </c:if>>center</option>
							<option value="3" <c:if test="${record.text_align == '3' }"> selected="selected" </c:if>>right</option>
						</select>
					</li>
					<li style="width: 615px;">
						<label>数据获取</label>
						<input type="text" name="record.list_sql" style="width:100%;"  id="j_list_sql" value="${ record.list_sql}" placeholder="如: select dname from dept where deptid=?" class="form-control">
					</li>
				</ul>
			</c:if>


			<c:if test="${iexport.ietype eq '导入' }">
				<div class="clearfix"></div>
				<span style="font-weight: bold;line-height:40px;">导入配置 </span>
				<hr/>

				<ul>
					<li>
						<label>字段名称</label>
						<input type="text" name="record.field_name" id="j_field_name" value="${record.field_name }" placeholder="请输入名称" class="form-control">
					</li>
					<li>
						<label>字段别名</label>
						<input type="text" name="record.field_alias" id="j_field_alias" value="${record.field_alias }" placeholder="请输入别名" class="form-control">
					</li>
					<li>
						<label>必包含列？</label>
						<select data-toggle="selectpicker" data-width="200" name="record.required"  data-size="10" data-val="${record.required }">
							<option value="0"  selected="selected" >非必填</option>
							<option value="1" <c:if test="${record.required == '1' }"> selected="selected" </c:if>>必填</option>
						</select>
					</li>
					<li>
						<label>内容为空？</label>
						<select data-toggle="selectpicker" data-width="200" name="record.allow_blank"  data-size="10" data-val="${record.allow_blank }">
							<option value="0"  selected="selected" >允许空</option>
							<option value="1" <c:if test="${record.allow_blank == '1' }"> selected="selected" </c:if>>不允许空</option>
						</select>
					</li>
					<li>
						<label>日期格式</label>
						<input type="text" name="record.format" id="j_format" value="${record.format }" placeholder="如：yyyy-MM-dd" class="form-control">
					</li>
					<li>
						<label>字段顺序</label>
						<input type="text" name="record.sort" id="j_sort" value="${record.sort }" placeholder="请输入字段顺序" class="form-control">
					</li>
					<li style="width: 960px;">
						<label>数据获取</label>
						<input type="text" style="width:98%;" name="record.list_sql" id="j_list_sql" value="${ record.list_sql}" placeholder="如: select deptid from dept where dname=?" class="form-control">
					</li>
					<li style="margin-left: 40px;">
						<label>备注</label>
						<input type="text" name="record.remark" id="j_remark" value="${record.remark }" placeholder="请输入备注" class="form-control">
					</li>
					<li>
						<label>常量</label>
						<select data-toggle="selectpicker" data-width="200" name="record.data_lookup"  data-size="10">
							<option value="">---请选择---</option>
							<c:forEach items="${dsList }" var="ds">
								<option value="${ds.ltid }"  <c:if test="${record.data_lookup == ds.ltid }"> selected="selected" </c:if>>${ds.tname }</option>
							</c:forEach>
						</select>
					</li>
					<li style="width: 960px;">
						<label>附加数据选项</label>
						<input type="text" style="width:98%;" name="record.data_resources" id="j_data_resources_sql" value="${ record.data_resources}" placeholder="如果常量无法满足要求，请输入sql,如: select * from dept" class="form-control">
					</li>
				</ul>
			</c:if>
			<div class="clearfix"></div>
			<!-- 操作按钮 -->
			<div style="text-align:right; padding-top: 20px;">
				<button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
				<button type="button" class="btn-close" style="background: red; color: white;">取消</button>
			</div>

		</form>
	</div>
</div>
