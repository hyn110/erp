var _columns=[[
    {field:'name',title:'商品类别',width:200},
    {field:'value',title:'销售额',width:200}
]];


var pie={
    /**饼状图的子标题*/
    title_subtext:"",
    /**饼状图的标签的文字:数据的key*/
    legend_data:[],
    /**饼状图的数据, key-value 数组*/
    series_data:[]
}




$(function () {
    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById('echart'));

    $('#grid').datagrid({
        url:name+'_orderReport',
        columns:_columns,
        singleSelect:true,
        onLoadSuccess:function (data) {
            var rows = data.rows;



            showEchar(rows);
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
 */
function showEchar(rows) {

    var titles = new Array()
    $.each(rows,function (i,row) {
        titles.push(row.name);
    });
    // 饼状图块的名字
    pie.legend_data = titles;
    // 饼状图的数据
    pie.series_data = rows;

    // 副标题显示时间范围
    pie.title_subtext = $('#date1').datetimebox('getValue') +' --- '+$('#date2').datetimebox('getValue');

    /**
     * echarts 饼状图的配置
     */
    var pie_option = {
        title : {
            text: '销售统计',
            // subtext: '时间范围 ---',
            subtext: pie.title_subtext,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            //  data: ['儿童食品','手机数码','水果']
            data:pie.legend_data
        },
        series : [
            {
                name: '销售统计',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                //       data:[{"name":"儿童食品","value":16.75},{"name":"手机数码","value":54.0},{"name":"水果","value":28.93}],
                data:pie.series_data
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(pie_option);
}