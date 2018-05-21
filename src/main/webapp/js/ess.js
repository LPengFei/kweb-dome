
$(function() {
	var option = {
		yAxis: {
			gridLineColor: '#F0F0F0',
			gridLineWidth: 2,
			title: {
				text: ""
			}
		},
		legend: {
			align: 'right',
			x: -30,
			verticalAlign: 'middle',
			y: 25,
			floating: false,
			shadow: false,
			layout: 'vertical'
		},
		plotOptions: {
			column: {
				stacking: 'normal',
				dataLabels: {
					enabled: true,
					color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
				}
			}
		},
		colors: ['#F96A6A', '#FF9147', '#FFC000', '#019CDF', '#95D7BB'],
		chart:{ 
			height:360,
			alignTicks: true,
			animation: true,
			backgroundColor: "#FFFFFF",
			borderColor: "#4572A7",
			borderRadius: 0,
			borderWidth: 0
		}
	}

	// loadHightDiv($($(".chart-base-container")[0]), option);
})

/**
 * @param checkboxName 复选框name
 * @param $p 复选框所在container
 */
function selectAll(checkboxName, $p){
	var checkboxs = $('input[name="'+checkboxName+'"]', $($p||document));
	var checkeds = checkboxs.filter(":checked");
	if(checkboxs.length == checkeds.length){//已全选
		checkboxs.prop("checked", false);
	}else{
		checkboxs.prop("checked", true);
	}
}

function selectAllNextUl(that){
	var ul = $(that).next("ul");
	var checkbox = ul.find("input:checkbox");
	var checked = checkbox.filter(":checked");

	//已全选，取消全选
	if(checkbox.length == checked.length){
		checkbox.prop("checked", false);
	} else{//未全选，选择全部
		checkbox.prop("checked", true);
	}
}

