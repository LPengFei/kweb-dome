/**
 * 统计图
 */


/******* echarts js 柱状图 ******/
var e_option_column = {
    title: {
        show: true,
        x: 'center',
        textAlign: "center",
        text: '各区县本周作业任务承载力分析',
    },
    tooltip: {
        trigger: 'axis',
        backgroundColor: 'rgba(0,0,0,0.5)',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        },
        formatter: function (params) {
            return params[0].name + '<br/><br/>'
                + params[0].seriesName + ' : ' + params[0].value + '<br/><br/>'
                + params[1].seriesName + ' : ' + (params[1].value + params[0].value);
        }
    },
    // 网格
    grid: {
        show:true,
        backgroundColor: 'rgba(0,0,0,0)',
        borderWidth: 1,
        borderColor:'#F0F0F0',
    },
    legend: {
        show: true,
        x: 'right',
        y: 'top',
        right: 'left',
        align: 'left',
        orient: 'vertical',
        textStyle:{
            color: "#929292",
            fontWeight: "bolder",
        },
        selectedMode: false,
        data: ['实际计划', '承载分析']
    },
    calculable: true,
    xAxis: [{
        type: 'category',
        data: ["夹江", "峨眉山", "井研", "沙湾", "五通桥", "变电运维室", "运维检修部", "输电运检室", "变电检修室", "配电运检室"],
        axisLabel:{
            textStyle:{
                color: '#929292',
                fontSize: "16px",
                fontWeight: 700,
            }
        },
        axisLine:{
            lineStyle:{
                width: 1,
                color:"#F0F0F0"
            }
        },
        splitLine:{
            lineStyle:{
                width: 1,
                color:"#F0F0F0"
            }
        },
        axisTick:{
            lineStyle:{
                width: 1,
                color:"#F0F0F0"
            }
        }
    }],

    yAxis: [
        {
            type: 'value',
            boundaryGap: [0, 0],
            axisLine:{
                lineStyle:{
                    width: 1,
                    color:"#F0F0F0"
                }
            },
            splitLine:{
                lineStyle:{
                    width: 1,
                    color:"#F0F0F0"
                }
            }
        }
    ],
    series: [
        {
            name: '实际计划',
            type: 'bar',
            stack: 'sum',
            barCategoryGap: '50%',
            itemStyle: {
                normal: {
                    color: '#019CDF',
                    barBorderColor: '#019CDF',
                    barBorderWidth: 6,
                    barBorderRadius: 0,
                    label: {
                        show: true, position: 'insideTop'
                    }
                }
            },
            //data:data_czlfx_1,
        },
        {
            name: '承载分析',
            type: 'bar',
            stack: 'sum',
            itemStyle: {
                normal: {
                    color: '#fff',
                    barBorderColor: 'tomato',
                    barBorderWidth: 6,
                    barBorderRadius: 0,
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            for (var i = 0, l = option.xAxis[0].data.length; i < l; i++) {
                                if (option.xAxis[0].data[i] == params.name) {
                                    return option.series[0].data[i] + params.value;
                                }
                            }
                        },
                        textStyle: {
                            color: 'tomato'
                        }
                    }
                }
            },
            //data:data_czlfx_2,
        }
    ],
}

var e_option_pie = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/><br/>{b} : {c} ({d}%)"
    },
    toolbox: {
        show: true,
    },
    series: [
        {
            name: '预警占比',
            type: 'pie',
            radius: ['50%', '70%'],
            itemStyle: {
                normal: {
                    label: {
                        show: false
                    },
                    labelLine: {
                        show: false
                    }
                },
                emphasis: {
                    label: {
                        show: true,
                        formatter: '{b}\n  {d}%',
                        position: 'center',
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                }
            },
            data:data_fxdj,
        }
    ],
    color: ["#F96A6A", "#FF9146", "#FFC100", "#EBD3AD", "#B185F9", "#6ABBFF", "#7ADFC3", "#BDEB74"],
}

/**
 * 加载echart的div。div必须是一下格式
 * <div class="chart-base-container" data-url="json/line.json">
		<h3>这里是一些其他内容不是不必须的</h3>
		<div class="chart"></div>
	</div>
 * @param {Object} baseContainer
 * @param {Object} options
 */
function loadEchartDiv(baseContainer, options, clickFunction) {
	var url = baseContainer.data("url");
	var container = baseContainer.find(".chart");
	var paras = baseContainer.find("input,select").serialize();
	loadAndDrawChartloadAndDrawChart(container, url, paras, clickFunction, options);
}

/**
 * 加载并画统计图
 *
 * @param $container
 *            放土统计图的jquery对象
 * @param url
 *            加载数据的url
 * @param paras
 *            url的参数
 * @param clickFunction
 *            点击图表的函数
 * @param options
 *            图标显示的参数，会覆盖默认设置
 * @param isname（String名称）
 * 			  选中的X轴名称变红
 * @param scroll（int指定具体数据值）
 * 			 是否出现滚动条
 * @param maxval（int指定具体数据值）
 * 			线性图中超出指定值点变红
 */
function loadAndDrawChart(container, url, paras, clickFunction, options) {
	console.log(url);
	$.ajax({
		type: "get",
		url: url,
		data: paras,
		dataType: 'json',
		error: function(request) {
			console.debug(url+paras+"网络错误");
		},
		success: function(result) {
			if (result.status == 'ok') {
				drawChartWithData(container, result.data, clickFunction, options);
			} else {
				container.html(result.data);
			}
		}
	});
}

/**
 * 画统计图
 *
 * @param $container
 *            放土统计图的jquery对象
 * @param result
 *            统计图的数据
 * @param clickFunction
 *            点击图表的函数
 * @param options
 *            图标显示的参数，会覆盖默认设置
 * @param isname（String名称）
 * 			  选中的X轴名称变红
 * @param scroll（int指定具体数据值）
 * 			 是否出现滚动条
 * @param maxval（int指定具体数据值）
 * 			线性图中超出指定值点变红

 */
function drawChartWithData($container, result, clickFunction, options) {
	var paras = {}; // echarts的参数
	// 根据图形类型整合数据
	if (result.type == "column") { // 饼图
		paras = {
			series: result.series
		};
		paras = $.extend({}, e_option_column, paras);
	} else if(result.type == "pie"){ // 线形图和符合图
		paras = {
			series: result.series
		};
		paras = $.extend({}, e_option_pie, paras);
	}

	// 合并与用户的自定义参数
	if (!options) options = {};
	options = $.extend({}, paras, options);
	var echart = echarts.init($container);
    echart.setOption(options);
}

function drawMainChart(){
	$(".chart[data-url]").each(function(){
		var $charDiv = $(this);
		$.getJSON($(this).data("url"), function (json) {
			console.debug(json);
			if(!json) return false;

			var xname = json.xname; //x轴数据对应的字段
			var sname = json.sname; //series name
			var category = [];
			var series0 = {
				name: sname,
				type: json.type,
				data:[]
			};

			if(json.type && json.type.indexOf("pie") != -1){
				$.each(json.data, function (i, obj) {
					series0.data.push([this[xname], this.count]);
				});
			}else{
				$.each(json.data, function (i, obj) {
					if(xname) category.push(this[xname]);
					series0.data.push(this.count);
				});
			}
			// console.debug(category);
			var chartData = {};
			$.extend(chartData, json, {categories: category, series: [series0]} );
			console.debug(chartData);

			var options = {
				title: {
					text: json.title,
					useHTML: true,
					style: {
						fontFamily: '微软雅黑',
						fontSize: '16px',
						color: 'black',
					}
				},
			}

			if(json.colors){
				var colors = json.colors;
				if(typeof(colors) == "string") colors = colors.split(",	");
				options.colors = colors;
			}
			drawChartWithData($charDiv, chartData, null, options);

		});
	});
}