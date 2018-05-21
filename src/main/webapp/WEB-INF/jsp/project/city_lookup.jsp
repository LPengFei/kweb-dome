<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>
<style>
    #province,#citys,.get_back{
        width:200px;
        height:50px;
        font-size: 24px;
        margin-right: 20px;
    }
    .get_back{
        color:#fff;
        margin-bottom:12px;
    }
</style>

<div style="width: 100%;height: 100%;line-height: 400px;">
    <!-- 暂无 港澳台 如需要可在city.json文件中添加-->
    <select id="province" name="province" onchange="provincechange(this.selectedIndex)" style="margin-left: 60px;">
        <option>请选择省份</option>
    </select>

    <select id="citys" name="city" onchange="doCityAndCountyRelation();">
        <option>请选择城市</option>
    </select>
    <button type="button" class="deep-blue get_back">确认</button>
</div>
<form id="pagerForm" data-toggle="ajaxsearch" action="${ctx}/app/${modelName}/lookup" method="post" style="margin: 0;padding: 0">
    <input type="hidden" name="pageNumber" value="${query.pageNumber }"/>
    <input type="hidden" name="pageSize" value="${query.pageSize }"/>
    <input type="hidden" name="orderField" value="${query.orderField }"/>
    <input type="hidden" name="orderDirection" value="${query.orderDirection}">
    <input type="hidden" name="chk_style" value="${chk_style}">
    <input type="hidden" name="lookup_param" value="${lookup_param}">
    <input type="hidden" name="lookup_modelId" value="${lookup_modelId}">
    <input type="hidden" name="chk_lookup" id="chk_lookup" value="${chk_lookup}">
    <button type="submit" id="submit" style="display: none;"></button>
</form>
<script>
    $(function() {
        //load city.json
        init();
        $(".get_back").click(function () {
          var p =  $("#province").val();
          var c =  $("#citys").val();
           // alert(p+"/"+c);
            $("#chk_lookup").attr("value",p+"/"+c);
            $("#submit").click();
            $(this).dialog('closeCurrent')
        });
    });
    function init(){
        var province = document.getElementById('province');
        //给选择框一个高度，可直接写进数据，不然要先创建dom元素option再录值
        $.getJSON("${ctx}/js/provinces.json",function(result){
            province.length=result.provinces.length;
            for(var i=0;i<result.provinces.length;i++){
                province.options[i].text=result.provinces[i];
                province.options[i].value=result.provinces[i];
            }
        });

    }

    function provincechange(n){
        var city = document.getElementById('citys');

        $.getJSON("${ctx}/js/citys.json",function(result){
            var citystemp=result.citys[n];
            city.length=citystemp.length;
            for(var i=0;i<citystemp.length;i++){
                city.options[i].text=citystemp[i];
                city.options[i].value=citystemp[i];
            }
            city.options[0].selected=true;
        });

    }
</script>

