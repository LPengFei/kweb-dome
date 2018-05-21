/*** 折线图 **/
var optionLine = {
	title: {
		text: ' '
	},
	tooltip: {
		trigger: 'axis'
	},
    toolbox:{
        show:true
    },
	grid: {
		left: '3%',
		right: '4%',
		bottom: '3%',
		containLabel: true
	},
	xAxis: [{
		type: 'category',
		boundaryGap: false,
		data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
	}],
	yAxis: [{
		type: 'value'
	}],
    toolbox: {
        show: true,
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
};

/******* 柱状图 *******/
var optionColumn = {
	tooltip: {
		trigger: 'axis',
		axisPointer: { // 坐标轴指示器，坐标轴触发有效
			type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
		}
	},
	grid: {
		left: '3%',
		right: '4%',
		bottom: '3%',
		containLabel: true
	},
	xAxis: [{
		type: 'category',
		data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
	}],
	yAxis: [{
		type: 'value'
	}],
    toolbox:{
		show:true
	},
    toolbox: {
        show: true,
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
};
/******** 饼图 **********/
var optionPie = {
    title: {
		text: '',
		subtext: '',
		x: 'center'
    },
    tooltip: {
		trigger: 'item',
		formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        show:true,
        orient: 'horizontal',
        left: 'left',
    },
    toolbox: {
        show: true,
        feature: {
            dataView: {readOnly: false},
            restore: {},
            saveAsImage: {}
        }
    },
};

/**
 * 加载highchart的div。div必须是一下格式
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
	loadAndDrawEChart(container, url, paras, clickFunction, options);
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
 */
function loadAndDrawEChart(container, url, paras, clickFunction, options) {
	$.ajax({
		type: "get",
		url: url,
		data: paras,
		dataType: 'json',
		error: function(request) {
			console.debug(url+paras+"网络错误");
		},
		success: function(result) {
			if(result.status == 'ok' && container) {
				drawEChartWithData(container, result.data, clickFunction, options);
			} else {
				$container.html(result.data);
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
 */
function drawEChartWithData(container, result, clickFunction, options) {
	var paras = {};
	// 根据图形类型整合数据
	if(result.type == "pie") { // 饼图
		paras = {
			series: result.series
		};
		paras = $.extend({}, optionPie, paras);


	} else if(result.type == "line") { // 线形图
		paras = {
			title: {
				text: result.title
			},
			series: result.series
		};
		paras = $.extend({}, optionLine, paras);

	} else { //柱状图
		paras = {
			title: {
				text: result.title
			},
			series: result.series
		};
		paras = $.extend({}, optionColumn, paras);
	}
	options = $.extend({}, paras, options);
	var myChart = echarts.init($(container)[0],e_macarons);
	myChart.setOption(options);
	return myChart
}