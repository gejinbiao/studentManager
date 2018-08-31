$(function () {
    //构建图表
    zhu();
    //给图表赋值
    getVistCount();
});
// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

function zhu() {

// 指定图表的配置项和数据
    var option = {
        title: {
            text: '当月上门'
        },
        tooltip: {},
        legend: {
            data: []
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '上门',
            type: 'bar',
            data: []
        }]
    };

// 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

function getVistCount() {
    myChart.showLoading();
    var ajax = new $ax(Feng.ctxPath + "/StudentInfos/visitCount", function (data) {

        if (data.code == 200) {
            // 填入数据
            myChart.setOption({
                xAxis: {
                    data: data.data.names
                },
                series: [{
                    // 根据名字对应到相应的系列
                    name: '上门',
                    data: data.data.nums
                }]
            });
        } else {
            Feng.alertFail(data.message);
        }
        myChart.hideLoading();
    }, function (data) {
        Feng.alertFail("加载数据失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
}