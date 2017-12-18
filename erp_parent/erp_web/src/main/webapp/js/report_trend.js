var _columns=[[
    {field:'month',title:'月份',width:200},
    {field:'value',title:'销售额',width:200}
]];


var line={
    /**折线图的子标题*/
    title_text:'年销售趋势分析',
    /** x 轴数据*/
    xAxis_data:[1,2,3,4,5,6,7,8,9,10,11,12],
    /**折线图第一条折线的数据*/
    series0_data:[]
}




$(function () {
    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('echart'));

    $('#grid').datagrid({
        url:name+'_trendReport',
        columns:_columns,
        singleSelect:true,
        onLoadSuccess:function (data) {
            showEchar(data.rows);
        }
    });

    $('#btnSearch').click(function () {
        // 1 获取表单数据
        var param = $('#searchForm').serializeJSON();

       $('#grid').datagrid('load',param);
    })
})

/**
 * 显示echart 图标
 * @param rows 行数据
 * [{month: 2, value: 0},{month: 2, value: 0}]
 */
function showEchar(rows) {

   var year = $('#year').val();// 获取输入的年份
    if(year==''){
        year = new Date().getFullYear();
    }
    // 设置标题
    line.title_text = year + line.title_text;
    var datas = new Array();

    $.each(rows,function (i,row) {
        console.log(row.value);
       datas.push((row.value/1000).toFixed(3));
    });


    line.series0_data = datas;

    /**
     * echarts 折线图的配置
     */
    line_option = {
        title: {
         //   text: '年销售趋势分析',
            text:line.title_text,
            subtext: 'madeBy : www.fmi110.com'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['全部商品']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
          //  data: [1,'2','3','4','5','6',7]
            data:line.xAxis_data
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                formatter: '{value} k'
            }
        },
        series: [
            {
                name:'全部商品',
                type:'line',
               // data:[1, 11, 15, 13, 12, 13, 10],
                data:line.series0_data,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },

        ]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(line_option);
}