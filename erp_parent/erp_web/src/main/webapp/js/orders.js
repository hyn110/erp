/**
 * Created by huangyunning on 2017/12/4.
 */
var columns=[[
    {field:'uuid',title:'编号',width:100},
    {field:'createtime',title:'生成日期',width:100},
    {field:'checktime',title:'审核日期',width:100},
    {field:'starttime',title:'确认日期',width:100},
    {field:'endtime',title:'入库或出库日期',width:100},
    {field:'type',title:'1:采购 2:销售',width:100,formatter:function (value) {
        if(value=='1')
            return '采购'
        else if(value=='2')
            return '销售';
    }},
    {field:'creater',title:'下单员',width:100,formatter:function (value,order,index) {
            return order.creater.name;
    }},
    {field:'checker',title:'审核员',width:100},
    {field:'starter',title:'采购员',width:100},
    {field:'ender',title:'库管员',width:100},
    {field:'supplieruuid',title:'供应商或客户',width:100,formatter:function (value,order,index) {
        if(typeof(order.supplier)=='undefined'){
            return '';
        }
        return order.supplier.name;
    }},
    {field:'totalmoney',title:'合计金额',width:100},
    {field:'state',title:'采购: 0:未审核 1:已审核, 2:已确认, 3:已入库；销售：0:未出库 1:已出库',width:100,formatter:function (value,order,index) {
        switch (parseInt(value)){
            case 0:
                return '未审核';
            case 1:
                return '已审核';
            case 2:
                return '已确认';
            case 3:
                return '已入库';

        }
    }},
    {field:'waybillsn',title:'运单号',width:100},

    {field:'-',title:'操作',width:200,formatter: function(value,row,index){
        var tmpRow = {};
        for(var key in row){ // 重新构建json对象,给key的名字加前缀
//				        console.log(key+" : "+row[key]);
            tmpRow['t.'+key]=row[key];
        }

        var obj = JSON.stringify(tmpRow); // 行数据转换成 json

        var op = "<a href='#' onclick='del("+row.uuid+")'>删除</a>";
        op = "<a href='#' onclick='edit("+obj+")'>修改</a>"+" | "+op;
        return op;
    }}
]];


$(function () {
    // 供应商和客户标识
    if(typeof(listParam)=='undefined'){
        listParam='';
    }
    if(typeof(saveParam)=='undefined'){
        saveParam='';
    }

    $('#grid').datagrid({
                            url: name+'_listByPage'+listParam,
                            columns: columns,
                            pagination:true,
                            pageList:[5,10,15,20,50],
                            toolbar:[{iconCls:'icon-add',
                                text:'新增',
                                handler:add}],
                            singleSelect:true,
                            onDblClickRow:function (rowIndex,rowData) {
                                console.log(rowIndex+"  ,  "+rowData);
                                // 订单详情加载数据
                                $('#ordersDlg').dialog('open');
                                // 加载订单详情的数据
                                appendSpan('#uuid',rowData.uuid);
                                appendSpan('#supplier',rowData.supplier.name);

                                switch (parseInt(rowData.type)){
                                    case 0:
                                        appendSpan('#type','未审核');
                                        break;
                                    case 1:
                                        appendSpan('#type','已审核');
                                        break;
                                    case 2:
                                        appendSpan('#type','已确认');
                                        break;
                                    case 3:
                                        appendSpan('#type','已入库');
                                        break;

                                }

                                appendSpan('#creater',rowData.creater.name);
                                appendSpan('#checker',rowData.checker);
                                appendSpan('#starter',rowData.starter);
                                appendSpan('#ender',rowData.ender);
                                appendSpan('#createtime',rowData.createtime);
                                appendSpan('#checktime',rowData.checktime);
                                appendSpan('#starttime',rowData.starttime);
                                appendSpan('#endtime',rowData.endtime);


                                // 订单明细表格
                                $('#itemgrid').datagrid(
                                    {
                                        columns:[[
                                            {field:'uuid',title:'编号',width:80},
                                            {field:'goodsuuid',title:'商品编号',width:90},
                                            {field:'goodsname',title:'商品名称',width:100},
                                            {field:'price',title:'价格',width:100},
                                            {field:'num',title:'数量',width:100},
                                            {field:'money',title:'金额',width:100},
                                            {field:'state',title:'状态',width:100,formatter:function (value,order,index) {
                                                switch (parseInt(value)){
                                                    case 0:
                                                        return '未入库';
                                                    case 1:
                                                        return '已入库';
                                                }
                                            }}]],

                                        singleSelect:true,
                                        data:rowData.orderdetails
                                    })
                            }
                        });


    //
    $("#btnSearch").click(function () {
        // 下拉框一般为关联对象,当没选中关联对象时,关联对象的值为空,此时
        // 因为Bean对象的主键都是 Long 类型,所以约定将其赋值为-1,否则数据封装出问题
       // if($('#searchForm :input[class="combo-text validatebox-text"]')&&''== $('#searchForm :input[class="combo-text validatebox-text"]').val()){
       //     $('#searchForm :input[class="combo-value"]').val(-1);
       // }

        var parameterJson = $("#searchForm").serializeJSON();
        //console.log(parameterJson);
        console.log(JSON.stringify($("#grid").datagrid('getData')));
        // 重新加载数据
        $("#grid").datagrid('load',parameterJson);
    })
    // 初始化订单详情
    $('#ordersDlg').dialog({
                               title:'订单详情',
                               width:700,
                               height:320,
                               closed:true,
                               modal:true
                           });

});
//
function add(){
    $("#editDlg").window('open');
    // 修改 form 表单地址
    edit_form_url = name +'_save';
    // $("#editForm").form('clear');
    // 给隐藏字段赋初值,否则提交空字符串,数据封装出错 , 如果使用包装类型不会出问题
    // $('#editForm > input[type="hidden"]').val(-1);
}
// 删除对象
function del(id) {
    $.messager.confirm('确认','确认删除该条数据吗?',function (yes) {
        if(yes){
            // $.post(name+"_del",{"uuid":id},function (code,result) {
            //     console.log("code = "+code);
            //     if(result=='success'){
            //         $("#grid").datagrid('reload');
            //     }
            // })

            $.ajax({
                type:'post',
                url:name+"_del",
                data:{"uuid":id},
                success:function (result) {
                        if(result=='success'){
                            $("#grid").datagrid('reload');
                        }
                },
                error:function (code,result) {
                        console.log("code = "+code);
                }
            });
        }
    });
}
// 修改对象
function edit(rowData){
    console.log(rowData)
    $("#editForm").form('clear');
    $("#editDlg").window('open');
    $("#editForm")
        .form('load', rowData);
    // 修改 form 表单地址
    edit_form_url = name+'_update';
}

// 新增时保存
function save() {
    var t = $("#editForm").form('validate');
    console.log(t);
    if(t){
        //$("#editForm").submit();
        $.messager.progress(); // 显示进度条
        // 异步提交
        $("#editForm").form('submit',{
            url: edit_form_url+''+saveParam,
            success:function () { // 成功后重新加载数据
                $("#grid").datagrid('reload');
                $.messager.progress('close');
                $("#editDlg").window('close')
            }
        })
    }else{
        $.messager.alert('提示','必须输入完整信息...');
    }
}
// 关闭编辑窗口
function close() {
    $("#editDlg").window('close');
}

function appendSpan(id,content) {
    $(id).html('<span>'+content+'</span>');
}