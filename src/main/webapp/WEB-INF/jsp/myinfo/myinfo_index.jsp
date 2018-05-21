<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<div class="bjui-pageContent">
    <div style="margin-bottom: 8px">
        <ul>
            <li>
                <a href="${ctx}/kconf/user/changePwd" data-toggle="dialog" style="font-size: 18px;padding-left: 8px;">修改密码</a>
            </li>
        </ul>
    </div>
    <div class="ess-form">
        <form action="${ctx}/kconf/user/save" data-toggle="validate" data-alertmsg="false">
            <input type="hidden" name="record.${pkName}" value="${record[pkName] }">
            <ul>
                <c:forEach items="${fields }" var="f">
                    <c:set var="validate_type" value="${f.form_validate_type }"></c:set>
                    <c:if test="${f.form_required}">
                        <c:set var="validate_type" value="${validate_type } required"/>
                    </c:if>

                    <li class="input_${f.form_view_type }">
                        <label>${f.field_alias }</label>
                        <input type="text" name="record.${f.field_name }" id="j_input_${f.field_name }"
                               value="${record[f.field_name] }"
                               placeholder="请输入${f.field_alias }"
                               data-rule="${validate_type }"
                               data-ds="${ctx }${f.form_data_source }"
                               data-toggle="${f.form_view_type }"
                               data-chk-style="${f.form_chk_style }"
                               data-live-search='${f.form_live_search }'
                               data-url='${f.lookup_url }'
                               <c:if test="${f.form_readonly eq '1' }">readonly</c:if>
                               <c:if test="${f.form_multi_select }">multiple="multiple"</c:if>
                        />
                    </li>
                </c:forEach>
            </ul>

            <div class="clearfix"></div>
            <!-- 操作按钮 -->
            <div style="text-align:right; padding-top: 20px;">
                <button type="submit" class="btn" style="background: #14CAB4; color: white;">保存</button>
                <button type="button" class="btn-close" style="background: red; color: white;">取消</button>
            </div>
        </form>

    </div>
</div>


<c:choose>
    <c:when test="${not empty jsFile}">
        <script src="${ctx }${jsFile}"></script>
    </c:when>
    <c:otherwise>
        <script type="text/javascript">
            initForm();
        </script>
    </c:otherwise>
</c:choose>
