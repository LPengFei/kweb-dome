<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@ page isELIgnored="false" %>
<%@ include file="../taglib.jsp" %> 
<script type="text/javascript">
   /* if($('#add_user_dept_id').val()!=''){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var node = treeObj.getNodeByParam("id", $('#add_user_dept_id').val());
        treeObj.selectNode(node,false,false);
        do_open_layout(event, treeId, treeNode)
    }*/

	function do_open_layout(event, treeId, treeNode) {
		var id = treeNode.id;
		if("undefined" == typeof id){
			id = "";
		}
 		$(event.target).bjuiajax('doLoad', {url:"${ctx }/kconf/department/edit?dept_id=" + id + "&parent_id=" + treeNode.pId, target:"#layout-01"})
	    $(event.target).bjuiajax('doLoad', {url:"${ctx }/kconf/user/user?deptid=" + id, target:"#layout-02"}) 
	    
	    event.preventDefault();
		
	}
	
	// 默认选中第一个节点 
	var loadUserCount = 0;
     $.CurrentNavtab.on('bjui.afterInitUI', function() {
	     //点击部门树第一个节点
	     if(loadUserCount == 0){
	    	 var zTree = $.fn.zTree.getZTreeObj("tree");//获取ztree对象
             var node;
             if($('#user_dept_id').val()!=''){
                 node = zTree.getNodeByParam("id", $('#user_dept_id').val());
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
	
	function ztree_returnjson() {
     	return ${ztreedata};
    }
	
	//删除部门
	function M_BeforeRemove(treeId, treeNode) {
		$(document).alertmsg('confirm', '部门删除后无法恢复，是否删除？', { displayPosition:'topcenter', okName:'确定', cancelName:'取消', title:'确认信息',okCall: function(){
			$.ajax({
				type: "post",
				url: "${ctx }/kconf/department/delete?dept_id="+treeNode.id, 
				success: function(data){
					if(data.statusCode == "200"){
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						if (treeNode.id != null) {
							treeNode.id = data.pid;
							treeObj.updateNode(treeNode);
						}
						do_open_layout(event, treeId, treeNode);
						treeObj.removeNode(treeNode);
						$.fn.zTree.getZTreeObj(treeId).removeNode(treeNode);
					}else {
					    $('#'+treeNode.tId+'_a').alertmsg('info', '该部门下存在人员数据，无法删除！');
					}
				}
			});
		}})
		
		return false;
	}
	
	//修改部门名称 
	function setRename(treeId, treeNode, newName, isCancel) {
		var id = treeNode.id;
		var pid = treeNode.pId || '';
		if("undefined" == typeof id){
			id = "";
		}
		$.ajax({
			type: "post",
			url: "${ctx }/kconf/department/save",
			data: {"record.id":id, "record.dname": newName, "record.pid":pid },
			success: function(data){
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				if ("undefined" == typeof treeNode.id || treeNode.id == null || treeNode.id == '') {
					treeNode.id = data.id;
					treeObj.updateNode(treeNode);
				}
				var nodes = treeObj.getSelectedNodes();
				if (nodes.length>0) {
					treeObj.reAsyncChildNodes(nodes[0], "refresh");
					console.debug(nodes[0]);
				}
				do_open_layout(event, treeId, treeNode);
			}
		});
	    return true
	}
	
	// 删除用户 
	function delUser(){
		console.debug("treeId:"+that);
		$.ajax({
			type: "post",
			url: "${ctx }/kconf/department/delete?dept_id="+treeNode.id, 
			success: function(data){
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				if (treeNode.id != null) {
					treeNode.id = data.pid;
					treeObj.updateNode(treeNode);
				}
				do_open_layout(event, treeId, treeNode);
				treeObj.removeNode(treeNode);
			}
		});
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
    <input type="hidden" id="user_dept_id" value="${user_dept_id}">
    <div style="float:left; width:300px;height:100%;  overflow:auto;" class="fixedtableScroller">
        <a class="btn" style="display:none; background: #14CAB4; color: white; margin: auto auto 10px 20px" data-url="${ctx}/kconf/department/initJcdwid" data-toggle='doajax' data-confirm-msg='确定要整理部门数据吗么？'>整理部门数据</a>
<!--      border:1px solid #c3ced5; border-radius:5px; height:99%; margin-top:7px; -->
        <fieldset style="width:300px; min-height: 98%;overflow: hidden ">
        	<legend style="display: block;">部门树</legend>
            <ul id="tree" class="ztree" data-toggle="ztree" data-expand-all="false"   data-on-click="do_open_layout" data-edit-enable="true" data-add-hover-dom="edit" data-show-rename-btn="true" data-remove-hover-dom="edit"
                data-before-remove="M_BeforeRemove" data-on-remove="M_NodeRemove" data-max-add-level="6" data-options="{nodes:'ztree_returnjson'}" data-setting="{callback: {beforeRename: setRename}}">
            </ul>
        </fieldset>
    </div>
    <div style="margin-left:210px;margin-right:10px; height:100%; overflow:hidden; padding-left: 10px; ">
         <div style="height:35%; overflow:hidden;">
             <fieldset style="height:100%;">
                 <legend style="display: block;">部门信息</legend>
                 <div id="layout-01" style="height:94%;">
                 </div>
             </fieldset>
        </div>
        <div style="height:65%;  overflow:hidden;">
            <fieldset style="height:96%;margin-top:6px;">
                <legend style="display: block;">人员信息</legend>
                <div id="layout-02" style="height:94%; overflow:hidden;">
                </div>
            </fieldset>
        </div>
    </div>
</div>
