(function($) {
	$.extend({
		//生成图列数据或是x轴数据
		generateLegend: function(_data,option) {
			if(_data) {
				var xData = [];
				$.each(_data[0].data,function(i,e){
					xData.push(e.name)
				});
				return xData;
			}
		},

		filterString:function(datalist) {//过滤数据null undefined
			if (!datalist || datalist == "") {
				return "无";
			} else {
				return datalist
			}
		},

		//初始化echarts
		initChart: function (_node, _option) {
			_node instanceof jQuery ? _node = _node.get(0) : '';
			var eChart = echarts.init(_node);
			eChart.setOption(_option);
			return eChart
		},

		//根据图表数据生成table 用于生成echarts图表下的table展示  与echart共用同一条数据
		createTable: function (option) {  //option:{data:数据,node:容器节点}
			if(!option.node){
				return false
			}
			option.node.empty();
			var $table = $("<table></table>");
			var $thead = $("<thead></thead>");
			var $th = $("<tr></tr>");
			var $body = $("<tbody></tbody>");
			$th.append('<td></td>');

			//生成thead
			$.each(option.data[0].data, function (i, e) {
				$th.append("<td>" + e.name + "</td>")
			});
			$thead.append($th);
			$table.append($thead);

			//生成tbody
			$.each(option.data, function (k, v) {
				var $tr = $("<tr></tr>");
				if(v.data[0]['itemStyle']){
					$tr.append("<td><span style='width: 8px;display: inline-block;height: 8px;margin-right: 4px;background-color: "+v.data[0]['itemStyle'].normal.color+"'></span>" + $.filterString(v.name) + "</td>");
				} else {
					$tr.append("<td>" + $.filterString(v.name) + "</td>");
				}

				$.each(v.data, function (i, e) {
					$tr.append("<td>" + $.filterString(e.value) + "</td>");
				});
				$body.append($tr);
			});
			$table.append($body);
			option.node.append($table);
		},


		//给需要改变样式的数据增加样式
		addItem: function (option, data) { //option:需要改变的数据的增加样式
			// {
			//typeName: '发现数' ,//表示同一组数据的样式渲染, 通常用于柱状图
			//name:同一种数据的样式渲染 通常用于饼图
			//
			// style:{ itemStyle:{normal:{color:'#60c721'}},}//需要增加的样式        会对原数据产生改变
			//attribute:给单条数据下面的data、name同级增加属性
			// }

			$.each(data, function (k, v) {
				$.each(option,function(index,e){
					//if (Object.prototype.isPrototypeOf([e['style'],e['attribute']]))
					if(e.typeName){
						if(e.typeName == v.name){
							//给基于typeName为参照物的数据下的data增加属性
							if(Object.prototype.isPrototypeOf(e['style'])){
								$.each(v.data,function(index,ele){
									$.each(e['style'],function(key,val){
										ele[key]=val;
									})
								});
							}
							if(Object.prototype.isPrototypeOf(e['attribute'])){
								//给基于typeName为参照物的同级增加属性
								if(e['attribute'])
									$.each(e['attribute'],function(key,val){
										v[key]=val;
									})
							}

						}
					} else if(e.name){
						$.each(v.data, function (index, ele) {
							if (ele.name == e.name) {
								$.each(e['style'],function(key,val){
									ele[key]=val;
								})
							}
						})
					}
				})
			})
		},



		//将后台数据改变状态成为Echarts所需数据

		toSeries: function (option) {// option:{
			// data:需要改变的数据  type:将此数据改变成bar（柱状图） pie（饼状图） line（折线图）
			// native: {} 在series中自定义增加属性 为了满足不同的图表类型所需的配置  filed:配置Y轴双Y轴  属性分配Y轴 类型:['a','b']
			// } item:需要分组的数据 传入数据下标进行分组
			var array = [];
			$.each(option.data, function (k, v) {
				v.type = option.type;
				if (option.hasOwnProperty('native')) {
					Object.assign(v, option.native);//合并添加的属性
				}

				if(option.hasOwnProperty('field')){
					option.data[option.field].yAxisIndex = 1;//配置折线图双Y轴
				}

				if(option.hasOwnProperty('item')){//给柱状图分组实现两条或者多条数据重叠
					$.each(option['item'],function(i, e){
						if(k == e){
							v.stack = option['item'][0]+'item'
						}
					})
				}
				array.push(v);
			});
			return array;
		},
	})
})(jQuery)