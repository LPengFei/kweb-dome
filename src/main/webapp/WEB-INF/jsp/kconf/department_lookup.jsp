<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<style>
    .dialogContent {
        background-color: white
    }

    /******** 自定义radio和checkbox **********/
    .radioOrcheckbox-wrap-dept  div.input-wrap-dept { position: relative; display: inline-block; margin-right: 15px;padding: 5px }
    .radioOrcheckbox-wrap-dept  span.radio-bg { display: inline-block; float: left; margin-top: -1px; width: 14px; height: 14px; background: url("${ctx}/images/icon_radio_uncheck.png") no-repeat center;}

    .radioOrcheckbox-wrap-dept  div.input-wrap-dept label { padding-left: 6px; margin-top: -2px; color: #d0d0d0; font-weight: normal; }
    .radioOrcheckbox-wrap-dept  input[type="radio"] { position: absolute; top: 0px; left: 2px; width: 14px; height: 14px; opacity: 0; }

    .radioOrcheckbox-wrap-dept  input[type="radio"]:checked + .radio-bg { background: url("${ctx}/images/icon_radio_checked.png") no-repeat center; }

    .radioOrcheckbox-wrap-dept  div.input-wrap label { padding-left: 6px; margin-top: -2px; color: #d0d0d0; font-weight: normal; }
    .radioOrcheckbox-wrap-dept  input[type="radio"]:checked + .radio-bg + label { color: #15cab5; }
</style>
<div style="height: 420px" id="${lookup_param}${lookup_modelId}">
            <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
                <thead>
                <tr>
                    <th style="background: #DBF1ED; color: #03B9A0;text-align: center" width="40">序号</th>
                    <th style="background: #DBF1ED; color: #03B9A0;text-align: center">上级部门</th>
                    <th style="background: #DBF1ED; color: #03B9A0;text-align: center">基层单位</th>
                    <th style="background: #DBF1ED; color: #03B9A0;text-align: center">部门名称</th>
                    <th width="120px" style="background: #DBF1ED; color: #03B9A0;">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${reportCounts }" var="r" varStatus="s">
                    <tr>
                        <td style="text-align: center">${s.count}</td>
                        <td>${r.dept_pid}</td>
                        <td>${r.dept_jcdwid}</td>
                        <td>${r.dname}</td>

                        <td align="center">
                            <div class="radioOrcheckbox-wrap-dept checkinfo_wrap">

                                    <div class="input-wrap-dept" onclick="getInputData(this)">
                                        <input type="radio" id="${r.id}" name="result" content="<c:forEach items="${fields }" var="f" varStatus="s"><c:if test="${f.lookup_name =='true' }">${r[f.field_name]}</c:if></c:forEach>">
                                        <span class="radio-bg"></span>
                                     <ul style="display: none" class="parame">
                                         <li class="pid">${r.dept_pid}</li>
                                         <li class="dname">${r.dname}</li>
                                         <li class="id">${r.id}</li>
                                         <li class="short_name">${r.short_name}</li>
                                         <li class="type">${r.type}</li>
                                         <li class="nature">${r.nature}</li>
                                         <li class="relevant">${r.relevant}</li>
                                     </ul>
                                        <script type="text/javascript">
                                           function getInputData(ele){
                                             var parames = ele.getElementsByClassName('parame')[0],
                                               sectionUl = document.getElementsByClassName('section_ul')[0],
                                               parames_obj = {}
                                             for(var i=0;i<parames.children.length;i++){
                                               var paraClassName = parames.children[i].getAttribute("class")
                                               for(var j=0;j< sectionUl.children.length;j++){
                                                 var type = sectionUl.children[j].getElementsByTagName('span')[0]
                                                 // 获得span name 去掉record 的 值
                                                 var typeName = type.getAttribute('name').replace(/record\./,"")
                                                 if(paraClassName == typeName){
                                                   type.innerHTML = parames.children[i].innerHTML;
                                                   break;
                                                 }
                                               }
                                                 if(paraClassName=='id'){
                                                     $('#dept_view_id').val(parames.children[i].innerHTML);
                                                 }
                                                 if(paraClassName == 'dname'){
                                                     $('#dept_view_dname').val(parames.children[i].innerHTML);
                                                 }

                                             }

                                           }

                                        </script>
                                    </div>

                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
</div>



<%--
<script>
    $.setDialogH();
</script>
--%>
