<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>

<script>

    $(function () {
        var s = $('input[name=lookup_param]').val()
        var t = $('input[name=lookup_modelId]').val();
        $.bindChecked('#'+s+t, function (data) {
            $("#j_input_"+s+"_"+t+"").val(function(){
                if(data.length > 0){
                    var arr =[];
                    $.each(data,function(k,v){
                        arr.push(v.content)
                    });
                    return arr.join(',')
                }
            });
            $(this).dialog('closeCurrent')
        });
    })

</script>

<style>
    .dialogContent {
        background-color: white
    }
</style>
<div id="${lookup_param}${lookup_modelId}">
<div class="bjui-pageHeader" style=";background-color: white;margin: 0;padding: 0;border: none">
    <div class="dialog_search">
    <form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/app/${modelName}/lookup" method="post" style="margin: 0;padding: 0">
        <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
        <input type="hidden" name="pageSize" value="${query.pageSize }"/>
        <input type="hidden" name="orderField" value="${query.orderField }"/>
        <input type="hidden" name="orderDirection" value="${query.orderDirection}">
        <input type="hidden" name="chk_style" value="${chk_style}">
        <input type="hidden" name="lookup_param" value="${lookup_param}">
        <input type="hidden" name="lookup_modelId" value="${lookup_modelId}">
        <c:forEach items="${hiddenFields}" var="item">
            <input type="hidden" name="${item.key}" value="${item.value }">
        </c:forEach>
        <c:forEach items="${fields}" var="item">
            <c:if test="${item.form_view_type eq 'hidden' and not empty item.list_search_op}">
                <input type="hidden" name="record.${item.field_name}" value="${record[item.field_name] }">
            </c:if>
        </c:forEach>

        <div class="bjui-searchBar">
            <%--动态生成查询条件 start --%>
                <c:set var="queryCount" value="0"></c:set>

                <c:forEach items="${searchFields }" var="f">

                    <c:if test="${!empty(f.list_search_op) and item.form_view_type ne 'hidden' }">
                        <c:set var="queryCount" value="${queryCount + 1 }"></c:set>

                            <label>${f.field_alias }：</label>
                            <c:if test="${not empty f.list_format and fn:containsIgnoreCase(f.type, 'date')}">
                                <fmt:formatDate value="${fvalue }" pattern="${f.list_format }" var="fvalue" />
                            </c:if>
                            <input type="text"
                                   value="${requestScope[f.field_name] }" name="${f.field_name }"
                                   data-width="${f.form_search_width  }"
                                   size="12"
                                   data-ds="${ctx }${f.form_data_source }"
                                   data-toggle="${f.form_view_type }"
                                   data-chk-style="${f.chk-style }"
                                   data-live-search='${f.form_is_search }'
                                   data-select-empty-text="-全部-"
                                   <c:if test="${not empty f.list_format }">data-pattern="${f.list_format }"</c:if>
                            >&nbsp;


                    </c:if>
                </c:forEach>

                <c:if test="${queryCount gt 0 }">
                    <button type="submit" class="ess-red ess_btn" style="margin-left: 5px">查询</button>
                </c:if>

                &nbsp;
                <button type="button" class="deep-blue ess_btn fr get_back">带回</button>
        </div>
                <div class="ess_line_98 dialog_display" >
                    <div class="radioOrcheckbox-wrap">
                        ${chk_lookup}
                    </div>
                </div>
        <c:if test="${chk_style}">
                <input type="hidden" name="chk_lookup" value="${chk_lookup}"
                       style="float: right;width: 400px;margin-right: 10px;display: none">
        </c:if>

    </form>
    </div>
</div>
<div class="bjui-pageContent tableContent white ess-pageContent"
     style="width:100%;border: none;margin: 0;padding: 0;;top: 100px;background-color: white">
    <div class="ess_line_98">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th style="background: #DBF1ED; color: #03B9A0;" width="40">序号</th>
            <c:forEach items="${fields }" var="f">
                <c:if test="${f.lookup_list_show == 'true' }">
                    <th title="${f.field_alias }" style="background: #DBF1ED; color: #03B9A0;" width="${f.list_width }"
                            <c:if test="${f.list_order eq '1' }"> data-order-field="${f.field_name }" </c:if> >${f.field_alias }</th>
                </c:if>
            </c:forEach>
            <th width="120px" style="background: #DBF1ED; color: #03B9A0;">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list }" var="r" varStatus="s">
            <tr>
                <td>${s.count}</td>
                <c:forEach items="${fields }" var="f">
                    <c:if test="${f.lookup_list_show =='true' }">
                        <kval:val model="${r }" field="${f }"></kval:val>
                    </c:if>
                </c:forEach>

                <td align="center">
                    <div class="radioOrcheckbox-wrap checkinfo_wrap">
                        <c:if test="${chk_style}">
                                    <span class="input-wrap" >
                                        <input type="checkbox"
                                               id="${r.id}"
                                               name="result"
                                               content="<c:forEach items="${fields }" var="f" varStatus="s"><c:if test="${f.lookup_name =='true' }">${r[f.field_name]}</c:if></c:forEach>"
                                               ><span
                                            class="checkbox-bg"></span>
                                         <ul style="display: none" class="parame">
                                             <li key="id">${r.id}</li>
                                            <c:forEach items="${fields }" var="f" varStatus="s">
                                                <c:if test="${f.lookup_is_back eq 'true' }">
                                                <li key="${f.field_name}">${r[f.field_name]}</li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </span>
                        </c:if>

                        <c:if test="${!chk_style}">
                                    <span class="input-wrap">
                                        <input type="radio"
                                               id="${r.id}"
                                               name="result"
                                               content="<c:forEach items="${fields }" var="f" varStatus="s"><c:if test="${f.lookup_name =='true' }">${r[f.field_name]}</c:if></c:forEach>"
                                        ><span
                                            class="radio-bg"></span>
                                     <ul style="display: none" class="parame">
                                           <li key="id">${r.id}</li>
                                          <c:forEach items="${fields }" var="f" varStatus="s">
                                              <c:if test="${f.lookup_is_back eq 'true' }">
                                                  <li key="${f.field_name}">${r[f.field_name]}</li>
                                              </c:if>
                                          </c:forEach>
                                     </ul>
                                    </span>
                        </c:if>
                    </div>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
</div>
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker" data-toggle-change="changepagesize">
                <option value="30">30</option>
                <option value="60">60</option>
                <option value="120">120</option>
                <option value="150">150</option>
                <option value="500">500</option>
                <option value="1000">1000</option>
                <option value="2000">2000</option>
            </select>
        </div>
        <span>&nbsp;条，共 ${page.totalRow } 条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination" data-total="${page.totalRow }"
         data-page-size="${page.pageSize }" data-page-current="${page.pageNumber }" data-page-num="15"></div>
</div>
</div>

<script>
    $.setDialogH();
</script>
