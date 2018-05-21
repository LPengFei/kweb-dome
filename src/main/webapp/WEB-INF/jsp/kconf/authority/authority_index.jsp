<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../../taglib.jsp" %>
<style>
    .tableS_init{
        height: 303px; overflow-y: auto;
    }
</style>
<script type="text/javascript">
   /* if($('#add_user_dept_id').val()!=''){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var node = treeObj.getNodeByParam("id", $('#add_user_dept_id').val());
        treeObj.selectNode(node,false,false);
        do_open_layout(event, treeId, treeNode)
    }*/

	function do_authority_layout(event, treeId, treeNode) {
		var id = treeNode.id;
		if("undefined" == typeof id){
			id = "";
		}
 		$(event.target).bjuiajax('doLoad', {url:"${ctx }/kconf/linkauthority/deptplus?dept_id=" + id + "&parent_id=" + treeNode.pId+"&kid=${kid}&mid=${mid}", target:"#layout-authority-01"})
	    $(event.target).bjuiajax('doLoad', {url:"${ctx }/kconf/linkauthority/deptminus?dept_id=" + id+"&kid=${kid}&mid=${mid}", target:"#layout-authority-02"})

	    
	    event.preventDefault();
		
	}
	
	// 默认选中第一个节点 
	var loadUserCount = 0;
     $.CurrentNavtab.on('bjui.afterInitUI', function() {
	     //点击部门树第一个节点
	     if(loadUserCount == 0){
	    	 var zTree = $.fn.zTree.getZTreeObj("tree");//获取ztree对象
             var node;
             if($('#authority_dept_id').val()!=''){
                 node = zTree.getNodeByParam("id", $('#authority_dept_id').val());
             }
             else{
               var  nodes = zTree.getNodes();
               node = nodes[0];
             }
		     zTree.selectNode(node);//选择点
	     	 $("#"+node.tId).find(">a").click();
	     	 loadUserCount++;
	     }
	 })
	
	function ztree_returnjson_authority() {
     	return ${ztreedata};
    }

	
</script>
<style>
	.ztree li a.curSelectedNode {
	    background-color: #d9e7f2;
	    color: black;
	    border: 0px;
	    opacity: 1;
	}
</style>
<div class="bjui-pageContent white" >
    <input type="hidden" id="authority_dept_id" value="${authority_dept_id}">
    <div style="float:left; width:300px;height:100%;  overflow:auto;" class="fixedtableScroller">
        <a class="btn" style="display:none; background: #14CAB4; color: white; margin: auto auto 10px 20px" data-url="${ctx}/kconf/department/initJcdwid" data-toggle='doajax' data-confirm-msg='确定要整理部门数据吗么？'>整理部门数据</a>
<!--      border:1px solid #c3ced5; border-radius:5px; height:99%; margin-top:7px; -->
        <fieldset style="width:300px; min-height: 98%;overflow: hidden ">
        	<legend style="display: block;">部门树</legend>
            <ul id="tree" class="ztree" data-toggle="ztree" data-expand-all="false"   data-on-click="do_authority_layout"
                 data-max-add-level="6" data-options="{nodes:'ztree_returnjson_authority'}" data-setting="{callback: {beforeRename: setAuthorityRename}}">
            </ul>
        </fieldset>
    </div>
    <div  style="margin-left:210px;margin-right:10px; height:100%; overflow:hidden; padding-left: 10px; ">
         <div class="navtab-panel tabsPageContent" style="height:43%; overflow:hidden;">
             <fieldset style="height:100%;">
                 <legend style="display: block;">部门信息</legend>
                 <div id="layout-authority-01" style="height:99%;">
                 </div>
             </fieldset>
        </div>
        <div class="navtab-panel tabsPageContent" style="height:55%;  overflow:hidden;">
            <fieldset style="height:98%;margin-top:6px;">
                <div id="layout-authority-02" style="height:100%; overflow:hidden;">
                </div>
            </fieldset>
        </div>
    </div>
</div>
