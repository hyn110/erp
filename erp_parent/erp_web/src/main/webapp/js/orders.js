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
            return order.creater==null?'':order.creater.name;
    }},
    {field:'checker',title:'审核员',width:100,formatter:function (value,order,index) {
        return order.checker==null?'':order.checker.name;
    }},
    {field:'starter',title:'采购员',width:100,formatter:function (value,order,index) {
        return order.starter==null?'':order.starter.name;
    }},
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
                                appendContent('#uuid', rowData.uuid);
                                appendContent('#supplier', rowData.supplier.name);

                                switch (parseInt(rowData.state)){
                                    case 0:
                                        appendContent('#state', '未审核');
                                        break;
                                    case 1:
                                        appendContent('#state', '已审核');
                                        break;
                                    case 2:
                                        appendContent('#state', '已确认');
                                        break;
                                    case 3:
                                        appendContent('#state', '已入库');
                                        break;

                                }

                                appendContent('#creater', rowData.creater.name);
                                appendContent('#checker', rowData.checker==null?'':rowData.checker.name);
                                appendContent('#starter', rowData.starter);
                                appendContent('#ender', rowData.ender);
                                appendContent('#createtime', rowData.createtime);
                                appendContent('#checktime', rowData.checktime);
                                appendContent('#starttime', rowData.starttime);
                                appendContent('#endtime', rowData.endtime);


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
                                        checkOnSelect:true,
                                        singleSelect:true,
                                        data:rowData.orderdetails
                                    })

                                // 审核操作时,显示审核按钮
                                if(Request['operation']=='check'){
                                    $('#btnCheck').show();
                                }

                                // 确认操作时,显示确认按钮
                                if(Request['operation']=='start'){
                                    $('#btnStart').show();
                                }
                            }
                        });


    // 条件查询按钮的点击事件
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

    // 审核按钮的点击事件
    $('#btnCheck').click(function(){


        $.messager.confirm('提示','确定要审核吗?',function (yes) {
            if(yes){
                // 获取订单数据
                // 发送网络请求
                var orderUuid = $('#uuid').html();
                // var param = JSON.stringify(orderUuid);
                $.ajax({
                    url:'orders_doCheck',
                    datatype:'json',
                    type:'post',
                    data:{'uuid':orderUuid},
                    success:function (result) {
                        if(result.success){
                            // 关闭窗口
                            // 刷新表格
                            $('#ordersDlg').dialog('close');
                            $('#grid').datagrid('reload');
                            $.messager.alert('提示',result.message);
                        }else{
                            $.messager.alert('提示',result);
                        }
                    },
                    error:function (result) {
                       $.messager.alert("提示",'操作失败,请联系后台管理员..');
                    }
                })
            }
        })

    });

    // 确认按钮的点击事件
    $('#btnStart').click(function(){


        $.messager.confirm('提示','确定要确认吗?',function (yes) {
            if(yes){
                // 获取订单数据
                // 发送网络请求
                var orderUuid = $('#uuid').html();
                // var param = JSON.stringify(orderUuid);
                $.ajax({
                           url:'orders_doStart',
                           datatype:'json',
                           type:'post',
                           data:{'uuid':orderUuid},
                           success:function (result) {
                               if(result.success){
                                   // 关闭窗口
                                   // 刷新表格
                                   $('#ordersDlg').dialog('close');
                                   $('#grid').datagrid('reload');
                                   $.messager.alert('提示',result.message);
                               }else{
                                   $.messager.alert('提示',result);
                               }
                           },
                           error:function (result) {
                               $.messager.alert("提示",'操作失败,请联系后台管理员..');
                           }
                       })
            }
        })

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
// 往指定的便签添加内容
function appendContent(id, content) {
    $(id).html(content);
}