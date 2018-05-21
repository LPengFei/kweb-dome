<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../taglib.jsp" %> 

<form action="${ctx }/kconf/field/save" data-toggle="validate" data-alertmsg="false">
	<div class="bjui-pageContent">
		<div class="ess-form">
			<input type="hidden" name="f.mid" value="${record.mid }">
			<input type="hidden" name="f.mfid" value="${record.mfid }">
			<input type="hidden" name="f.enabled" value="0">
			<fieldset>
				<legend style="display: block;">基础配置</legend> 
				<ul>
					<li>
						<label>字段名称</label>
						<input type="text" name="f.field_name" id="j_field_name" value="${record.field_name }"  placeholder="请输入字段名称: id">
					</li>
					<li>
						<label>字段别名</label>
						<input type="text" name="f.field_alias" id="j_field_alias" value="${record.field_alias }"  placeholder="请输入字段别名">
					</li>
					<li>
						<label>字段类型</label>
						<input type="text" name="f.type" id="j_type" value="${record.type }" placeholder="请输入字段类型">
					</li>
				 	 
				 	<%-- <li>
						<label>字段备注</label>
					 	<input type="text" name="f.remark" id="j_remark" value="${record.remark }" placeholder="请输入备注">
				 	</li> --%>
				</ul>
			</fieldset>
			
			 <fieldset>
			 	<legend style="display: block;">列表检索配置</legend>
			 	<ul>
					<li>
						<label>列表检索名称</label>
						<input type="text" name="f.list_search_label" placeholder="如：开始时间"   value="${record.list_search_label }" />
					</li>
					<li>
						<label>列表检索条件</label>
						<input type="text" name="f.list_search_op" placeholder="检索条件: =,>,<,like,!=,>=,<="   value="${record.list_search_op }" />
					</li>
					
					<li>
						<label>检索显示顺序</label>
						<input type="text" name="f.list_search_sort" value="${record.list_search_sort }" placeholder="10">
					</li>
					<li>
						<label>检索显示宽度</label>
						<input type="text" name="f.list_search_width" value="${record.list_search_width }" placeholder="60">
					</li>
					<li>
						<label>列表检索样式</label>
						<input type="text" name="f.list_search_style" placeholder="如：padding-left:10px;"  value="${record.list_search_style }" />
					</li>
					
					<li>
						<label>检索日期格式化</label>
						<input type="text" name="f.list_search_dateformat" placeholder="如：yyyy-MM-dd"  value="${record.list_search_dateformat }" />
					</li>
					
					<li>
						<label>检索扩展属性</label>
						<input type="text" name="f.list_search_extattr" placeholder="如：class='abc' id='abc' "  value="${record.list_search_extattr }" />
					</li>
					
				</ul>
			</fieldset>
			
			 <fieldset>
			 	<legend style="display: block;">列表配置</legend>
			 	<ul>
					<li>
						<label>列表显示</label>
						<select data-toggle="selectpicker" data-width="200" name="f.list_view"  data-size="10">
	                    	<option value="true" <c:if test="${record.list_view }"> selected="selected" </c:if>>显示</option>
							<option value="false" <c:if test="${not record.list_view }"> selected="selected" </c:if> >不显示</option>
	                    </select>
					</li>
					
					<li>
						<label>列表显示顺序</label>
						<input type="text" name="f.list_sort" value="${record.list_sort }" placeholder="10">
					</li>
					<li>
						<label>表头排序？</label>
						<select data-toggle="selectpicker" data-width="200" name="f.list_order"  data-size="10"> 
	                    	<option value="1" <c:if test="${record.list_order =='1' }"> selected="selected" </c:if>>允许排序</option>
	                    	<option value="0" <c:if test="${record.list_order == '0' }"> selected="selected" </c:if>>不允许排序</option>
	                    </select>
					</li>
					<li>
						<label>字段可编辑？</label>
						<select data-toggle="selectpicker" data-width="200" name="f.list_readonly"  data-size="10">
							<option value="0" <c:if test="${record.list_readonly =='0' }"> selected="selected" </c:if>>不允许编辑</option>
							<option value="1" <c:if test="${record.list_readonly == '1' }"> selected="selected" </c:if>>允许编辑</option>
						</select>
					</li>
					<li>
						<label>对齐方式</label>
		                <select data-toggle="selectpicker" data-width="200" name="f.list_align"  data-size="10">
	                    	<option value="left" selected="selected" >居左</option>
	                    	<option value="center" <c:if test="${record.list_align =='center' }"> selected="selected" </c:if>>居中</option>
	                    	<option value="right" <c:if test="${record.list_align == 'right' }"> selected="selected" </c:if>>居右</option>
	                    </select>
					</li>
					
					<li>
						<label>列表宽度(px)</label>
						<input type="text" name="f.list_width" placeholder="如：100" value="${record.list_width }" />
					</li>
					
					<li>
						<label>列表样式</label>
						<input type="text" name="f.list_style" placeholder="如：padding-left:10px;"  value="${record.list_style }" />
					</li>
					
					<li>
						<label>日期、数字格式化</label>
						<input type="text" name="f.list_format" placeholder="如：yyyy-MM-dd"  value="${record.list_format }" />
					</li>
					 
					<li>
						<label>列表关联SQL</label>
						<input type="text" name="f.list_sql" placeholder="如: select dname from dept where deptid=?" value="${record.list_sql }" />
					</li>
					<li>
						<label>显示文本</label>
						<input type="text" name="f.list_text" placeholder="如: 下载,图片" value="${record.list_text }" />
					</li>
					<li>
						<label>超级链接</label>
						<input type="text" name="f.link_addr" placeholder="如: /app/project?id={pid}" value="${record.link_addr }" />
					</li>
					<li>
						<label>链接属性</label>
						<input type="text" name="f.link_attr" placeholder="如:data-toggler=navtab data-id=proj-detail" value="${record.link_attr }" />
					</li>
					<li>
						<label>导入数据关联</label>
						<input type="text" name="f.import_data_sql" placeholder="如:select lkey from k_lookup where lvalue=?" value="${record.import_data_sql }" />
					</li>
					
				</ul>
			</fieldset>
			
			
			<fieldset>
				<legend style="display: block;">表单配置</legend> 
				<ul>
					 <li>
						<label>输入方式</label>
						<select data-toggle="selectpicker" data-width="200" name="f.form_view_type"  data-size="10">
							<option value="text" <c:if test="${record.form_view_type == 'text' }"> selected="selected" </c:if> >普通文本框-text</option>
							<option value="hidden" <c:if test="${record.form_view_type == 'hidden' }"> selected="selected" </c:if> >隐藏域-hidden</option>
							<option value="password" <c:if test="${record.form_view_type == 'password' }"> selected="selected" </c:if>>密码输入框-password</option>
							<option value="textarea" <c:if test="${record.form_view_type == 'textarea' }"> selected="selected" </c:if>>多行文本域-textarea</option>
							<option value="kindeditor" <c:if test="${record.form_view_type == 'kindeditor' }"> selected="selected" </c:if>>在线编辑器-kindeditor</option>
							<option value="datepicker" <c:if test="${record.form_view_type == 'datepicker' }"> selected="selected" </c:if>>日期选择器-datepicker</option>
							<option value="selectpicker" <c:if test="${record.form_view_type == 'selectpicker' }"> selected="selected" </c:if>>下拉选择框-selectpicker</option>
							<option value="selectztree" <c:if test="${record.form_view_type == 'selectztree' }"> selected="selected" </c:if>>树形下拉框-selectztree</option>
							<option value="lookup" <c:if test="${record.form_view_type == 'lookup' }"> selected="selected" </c:if>>查找带回-lookup</option>
							<option value="tags" <c:if test="${record.form_view_type == 'tags' }"> selected="selected" </c:if>>关键字检索-tags</option>
							<option value="file" <c:if test="${record.form_view_type == 'file' }"> selected="selected" </c:if>>文件上传</option>
						</select>
					 </li>
					 <li>
						<label>表单显示</label>
	                    <select data-toggle="selectpicker" data-width="200" name="f.form_view"  data-size="10">
	                    	<option value="true"  <c:if test="${record.form_view == 'true' }"> selected="selected" </c:if>>显示</option>
							<option value="false" <c:if test="${record.form_view == 'false' }"> selected="selected" </c:if>>不显示</option>
	                    </select>
					 </li>
					 <li>
						<label>表单排序</label>
						<input type="text" name="f.form_sort" value="${record.form_sort }" placeholder="10" />
					</li>
					 
					 <li>
						<label>是否只读</label>
	                     <select data-toggle="selectpicker" data-width="200" name="f.form_readonly"  data-size="10">
	                    	<option value="readonly"  <c:if test="${record.form_readonly  eq 'readonly' }"> selected="selected" </c:if>>只读字段</option>
							<option value="" <c:if test="${empty(record.form_readonly)}"> selected="selected" </c:if>>非只读字段</option>
	                    </select>
					 </li>

					 <li>
						<label>验证类型</label>
						<select data-toggle="selectpicker" data-width="200" name="f.form_validate_type"  data-size="10">
							<option value="">不验证</option> 
							<option value="digits" <c:if test="${record.form_validate_type == 'digits' }"> selected="selected" </c:if>>整数</option>
							<option value="number" <c:if test="${record.form_validate_type == 'number' }"> selected="selected" </c:if>>小数</option>
							<option value="date"  <c:if test="${record.form_validate_type == 'date' }"> selected="selected" </c:if>>日期</option>
							<option value="mobile"  <c:if test="${record.form_validate_type == 'mobile' }"> selected="selected" </c:if>>电话号码</option>
							<option value="ID_card"  <c:if test="${record.form_validate_type == 'ID_card' }"> selected="selected" </c:if>>身份证</option>
						</select>
					 </li>

					 <li>
						<label>是否必填</label>
					 	<input type="radio" name="f.form_required" id="j_custom_isrequired1" data-toggle="icheck" value="true"  data-label="是&nbsp;&nbsp;" <c:if test="${record.form_required == 'true' }"> checked="checked" </c:if>>
	                    <input type="radio" name="f.form_required" id="j_custom_isrequired2" data-toggle="icheck" value="false" data-label="否" <c:if test="${record.form_required == 'false' }"> checked="checked" </c:if>>
 					 </li>

					 <li>
						<label>长度验证</label>
					 	<input type="text" name="f.form_min_length" id="j_length_validate" placeholder="最小值" size="6" value="${record.form_min_length }" />
					 	-
						<input type="text" name="f.form_max_length" id="j_length_validate" placeholder="最大值" size="6" value="${record.form_max_length }" />
					 </li>

					 <li>
						<label>表单样式</label>
					 	<input type="text" name="f.form_style" value="${record.form_style }"  placeholder="请输入表单样式">
					 </li>
					 
					<li>
						<label>下拉选择检索</label>
						<select data-toggle="selectpicker" data-width="200" name="f.form_live_search"  data-size="10">
	                    	<option value="true"  <c:if test="${record.form_live_search}"> selected="selected" </c:if>>检索</option>
							<option value="false" <c:if test="${!record.form_live_search}"> selected="selected" </c:if>>不检索</option>
	                    </select>
					</li>
					<li>
						<label>是否多选</label>
						<select data-toggle="selectpicker" data-width="200" name="f.form_chk_style"  data-size="10">
	                    	<option value="true"  <c:if test="${record.form_chk_style }"> selected="selected" </c:if>>多选</option>
							<option value="false" <c:if test="${!record.form_chk_style }"> selected="selected" </c:if>>单选</option>
	                    </select>
					</li>
					<li>
						<label>数据源</label>
						<select data-toggle="selectpicker" data-width="200" name="f.form_data_source"  data-size="10">
							<option value="">---请选择---</option>
							<c:forEach items="${dsList }" var="ds">
	                    	<option value="${ds.dataurl }"  <c:if test="${record.form_data_source == ds.dataurl }"> selected="selected" </c:if>>${ds.dsname }</option>
							</c:forEach>
	                    </select>
					</li>
					<li>
						<label>设置默认值</label>
						<input type="text" name="f.settings" value="${record.settings eq 'null'?"":record.settings}"  placeholder="设置表单默认值">
					</li>
					<li>
						<label>填报说明</label>
						<input type="text" name="f.form_notes" value="${record.form_notes eq 'null'?"":record.form_notes}"  placeholder="设置填报说明">
					</li>
					<!-- 
					<li>
						<label>导入关联数据</label>
						<input type="text" name="f.import_data_sql" placeholder="请输入查找带回url,如/kconf/lookup"  value="${record.import_data_sql }" />
					</li> -->
					 
					<li>
						<label>data-url</label>
						<input type="text" name="f.data_url" value="${record.data_url }"  placeholder="data-url">
					</li>
					<li>
						<label>查找带回是否多选</label>
						<input type="radio" name="f.chk_style" data-toggle="icheck" value="true"  data-label="是&nbsp;&nbsp;" <c:if test="${record.chk_style == 'true' }"> checked="checked" </c:if>>
						<input type="radio" name="f.chk_style" data-toggle="icheck" value="false" data-label="否" <c:if test="${record.chk_style == 'false' }"> checked="checked" </c:if>>
					</li>
					<li>
						<label>data-group</label>
						<input type="text" name="f.data_group" value="${record.data_group }"  placeholder="data-group">
					</li>
					<li>
						<label>data-width</label>
						<input type="text" name="f.data_width" value="${record.data_width }"  placeholder="data-width">
					</li>
					<li>
						<label>data-height</label>
						<input type="text" name="f.data_height" value="${record.data_height }"  placeholder="data-height">
					</li>
					
					<li>
						<label>data-title</label>
						<input type="text" name="f.data_title" value="${record.data_title }"  placeholder="data-title">
					</li>
					<li>
						<label>查找带回列表显示</label>
						<select data-toggle="selectpicker" data-width="200" name="f.lookup_list_show"  data-size="10">
							<option value="true" <c:if test="${record.lookup_list_show }"> selected="selected" </c:if>>显示</option>
							<option value="false" <c:if test="${not record.lookup_list_show }"> selected="selected" </c:if> >不显示</option>
						</select>
					</li>
					<li>
						<label>查找是否带回</label>
						<input type="radio" name="f.lookup_is_back" data-toggle="icheck" value="true"  data-label="是&nbsp;&nbsp;" <c:if test="${record.lookup_is_back == 'true' }"> checked="checked" </c:if>>
						<input type="radio" name="f.lookup_is_back" data-toggle="icheck" value="false" data-label="否" <c:if test="${record.lookup_is_back == 'false' }"> checked="checked" </c:if>>
					</li>
					<li>
						<label>显示字段</label>
						<input type="radio" name="f.lookup_name" data-toggle="icheck" value="true"  data-label="是&nbsp;&nbsp;" <c:if test="${record.lookup_name == 'true' }"> checked="checked" </c:if>>
						<input type="radio" name="f.lookup_name" data-toggle="icheck" value="false" data-label="否" <c:if test="${record.lookup_name == 'false' }"> checked="checked" </c:if>>
					</li>
					
					<li>
						<label>文件上传大小限制(KB)</label>
						<input type="text" name="f.file_size_limit" value="${record.file_size_limit }"  placeholder="文件上传大小限制:10240000">
					</li>
					<li>
						<label>文件上传类型限制</label>
						<input type="text" name="f.file_type_exts" value="${record.file_type_exts }"  placeholder="多个用;分割,如： *.png;*.jpg">
					</li>
					<li>
						<label>是否允许选择多个文件</label>
						<input type="text" name="f.file_multi" value="${record.file_multi }"  placeholder="true / false">
					</li>
					<li>
						<label>是否允许自动上传</label>
						<input type="text" name="f.file_auto" value="${record.file_auto }"  placeholder="true / false">
					</li>
					<li>
						<label>是否重命名</label>
						<input type="text" name="f.file_rename" value="${record.file_rename }"  placeholder="true / false">
					</li>
					<li>
						<label>是否加锁</label>
						<select data-toggle="selectpicker" data-width="200" name="f.is_lock"  data-size="10">
							<option value="0"  <c:if test="${record.is_lock eq '0' }"> selected="selected" </c:if>>否</option>
							<option value="1" <c:if test="${!record.is_lock  eq '1'}"> selected="selected" </c:if>>是</option>
						</select>
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
// 	$(function(){
		$(".ess-form li", $.CurrentDialog).css("height","52px");

// 	});
</script>

 