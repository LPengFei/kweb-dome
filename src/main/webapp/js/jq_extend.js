/**
 * Created by FHC on 2017/11/24.
 */
$.extend({
    fancyboxInit: function (clickNode, itemName) { //clickNode:需要点击绑定大图查看的节点 itemName：分组查看的class名字
        itemName ? '' : itemName = clickNode;
        var fin = setInterval(function () {
            if ($(itemName)) {
                clearInterval(fin);
                $(clickNode).click(function () {
                    $(itemName).fancybox({
                        openEffect: 'none',
                        closeEffect: 'none',
                        prevEffect: 'none',
                        nextEffect: 'none',
                        closeBtn: false,
                        helpers: {
                            title: {
                                type: 'inside'
                            },
                            buttons: {}
                        },
                        afterLoad: function () {
                            this.title = '图片 ' + (this.index + 1) + ' - ' + this.group.length + (this.title ? ' - ' + this.title : '');
                        }
                    });
                })
            }
        }, 500)
    },
    problem_dia: function (node, data) {
        if ($(node)) {
            $(document).click(function () {
                $('.pro_dialog_contain').remove();
            }).on('click', node, function (event) {
                var event = window.event || event;
                var cont = $(this).attr('content');
                $('.pro_dialog_contain').remove();
                var x = event.clientX - event.offsetX;
                var y = parseInt(event.clientY) - parseInt(event.offsetY);
                var $node = $('<div class="pro_dialog_contain" style="left: ' + (x - 70) + 'px;top: ' + (y + 25) + 'px;"><span class="sjx" style="left:' + 70 + 'px "></span>' + cont + '</div>');
                $('body').append($node);
                return false;
            });
            $('.bjui-pageContent').scroll(function () {
                $('.pro_dialog_contain').remove();
            })
        }
    },
    bindChecked: function ($node, callback) {//$node:当前页面wrap层元素节点 option{addItemFormAction:新增元素表单地址,addItemFormName:表单名字}

        //单选复选选中后增加到对应的位置上
        $($node).on('click', $node + ' .checkinfo_wrap input[type=radio]', function () {

            var cont = $(this).attr('content');
            $($node + ' .dialog_display .radioOrcheckbox-wrap').empty().append(checkFn.checked_add({
                node: $($node + ' .dialog_display .radioOrcheckbox-wrap .input-wrap'),
                type: 'radio',
                content: cont,
                name: $(this).attr('name'),
                value: $(this).siblings('.parame').html(),
            }));
            checkFn.get_html();
        });

//                //添加信息
//                $($node+' .addInfo_btn').click(function(){
//                    $($node+' .essC_table tbody').prepend(checkFn.addInfo());
//                });

        //复选
        $($node).on('click', $node + ' .checkinfo_wrap input[type=checkbox]', function () {

            var cont = $(this).attr('content');
            var check = $(this).get(0).checked;
            var id = $(this).attr('id');
            if (check == true) {
                $($node + ' .dialog_display .radioOrcheckbox-wrap').append(checkFn.checked_add({
                    type: 'checkbox',
                    content: cont,
                    id: id,
                    node: $($node + ' .dialog_display .radioOrcheckbox-wrap .input-wrap'),
                    name: $(this).attr('name'),
                    value: $(this).siblings('.parame').html()
                }));
            } else {
                //根据table列表checkbox状态去改变展示列checkbox
                checkFn.selectCheck({
                    node: $($node + ' .dialog_display .radioOrcheckbox-wrap .input-wrap'),
                    id: id,
                    type: 'z',
                })
            }
            checkFn.get_html();
        });

        //内容展示行反复选
        $($node).on('click', $node + ' .dialog_display input[type=checkbox]', function () {

            var check = $(this).get(0).checked;
            var id = $(this).attr('id');
            //根据展示列checkbox状态去改变table列表checkbox

            checkFn.selectCheck({
                node: $($node + ' .essC_table tbody tr'),
                id: id,
                type: 'f',
                check: check
            });
            if (check == false) {
                $(this).closest('.input-wrap').remove();
            }
            checkFn.get_html();
        });

        //数据带回操作
        $($node).on('click', $node + ' .get_back', function () {
            callback(checkFn.get_back({node: $($node + ' .dialog_display .radioOrcheckbox-wrap .input-wrap')}))
        });

        //新增行操作
        $($node).on('click', $node + ' .addInfo_wrap .delect', function () {
            $(this).closest('tr').remove();

        });

//                //设置隐藏域
//                $($node).on('click',$node+' .pagination li',function(){
//                    $($node+' input[name=chk_lookup]').val($($node+' .dialog_display .radioOrcheckbox-wrap').html())
//                });

        var checkFn = {
            //选中栏添加
            checked_add: function (option) { //option {name:字段名字,value:值,id:唯一表示,type:radio : checkbox,data:所需展现内容,node:当前页面的展示栏checkbox元素节点集合 用于遍历是否已经添加重复元素}
                option.name ? '' : option.name = '';
                option.value ? '' : option.value = '';
                option.id ? '' : option.id = '';
                var flag = false;//标记状态  用于判断是否有重复的id选项 避免重复添加
                $.each(option.node, function (k, v) {
                    var $node = $(this).find('input');
                    var $id = $node.attr('id');
                    if (option.id == $id) {
                        $(this).find('input[type=checkbox]').get(0).checked = true;
                        flag = true;
                        return false;
                    }
                });
                if (flag == false) {
                    return '<span class="input-wrap"><input type="' + option.type + '" name="' + option.name + '" id="' + option.id + '"  content="' + option.content + '" checked="checked"><span class="' + option.type + '-bg"></span><ul class="parame" style="display: none">' + option.value + '</ul><span class="content">' + option.content + '</span></span>'
                }
            },
//                    //新增条目
//                    addInfo:function(){
//                        return '  <tr class="addInfo_wrap"> <td><form action="'+option.addItemFormAction+'" name="'+option.addItemFormName+'"><input type="text" class="form-control"></td> <td><input type="text" class="form-control"></td> <td><input type="text" class="form-control"></td> <td><input type="text" class="form-control"></td> <td align="center"><a class="ess_edit_a save">保存</a><a class="ess_edit_a delect">删除</a></form></td> </tr>'
//                    },
            //列表checkbox和内容展示栏的相互选中联动
            selectCheck: function (option) { // option :{node:用于遍历的父节点组合,id:列表chckbox和展示栏联系标识 type:z(从列表状态筛选展示栏checkbox状态);f(从展示栏状态更改列表checkbox状态)}
                $.each(option.node, function (k, v) {
                    var $node;
                    if (option.type == 'z') {
                        $node = $(this).find('input');
                    } else if (option.type == 'f') {
                        $node = $(this).find('.checkinfo_wrap input[type=checkbox]');
                    }
                    var $id = $node.attr('id');
                    if (option.id == $id && option.type == 'z') {
                        $(this).closest('.input-wrap').remove();
                        return false;
                    } else if (option.id == $id && option.type == 'f') {
                        $(this).find('.checkinfo_wrap .input-wrap input[type=checkbox]').get(0).checked = option.check;
                        return false;
                    }
                })
            },
            //数据带回
            get_back: function (option) {
                var arr = [];
                $.each(option.node, function () {
                    var $node = $(this).find('input');
                    var obj = {};
                    obj.content = $node.attr('content');
                    var arrr = {};
                    $.each($node.siblings('.parame').find('li'), function (k, v) {
                        var key = $(this).attr('key');
                        var val = $(this).text();
                        arrr[key] = val;
                    });
                    obj.value = arrr;
                    arr.push(obj);
                });
                return arr;
            },
            get_html: function () {
                return $($node + ' input[name=chk_lookup]').val($($node + ' .dialog_display .radioOrcheckbox-wrap').html())
            }
        }
    },
    //修改dialog 避免原生弹窗显示不完数据的问题
    setDialogH: function () {
        var $container = $('.bjui-dialog'); //dialog容器
        var $content = $container.find('.bjui-pageContent');//列表content
        var $footer =$container.find('.bjui-pageFooter');//分页footer
        setTimeout(function(){
            var wrap_tables = $container.find('.wrap_tables');//列表容器
            var header =$container.find('.wrap_tables .fixedtableHeader');//列表header 表头
            var tbody =$container.find('.wrap_tables .fixedtableScroller');//列表tbody
            wrap_tables.height($content.height() - 10);
            tbody.height(function(){
                return wrap_tables.height() - header.height() - $footer.height();
            });
        },200)
    }
});