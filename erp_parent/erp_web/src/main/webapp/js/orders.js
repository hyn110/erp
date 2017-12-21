/**
 * 返回消息窗口的信息
 * @param msg
 * @returns
 */
function getMessage(msg) {
    return {
        title:'提示',
        msg:msg,
        showType:'slide',
        showSpeed : 200,
        timeout:2000,
        style:{
            right:'',
            top:document.body.scrollTop+document.documentElement.scrollTop,
            bottom:''
        }};
}
/**
 * Created by huangyunning on 2017/12/4.
 */
var columns=[[
    {field:'uuid',title:'编号',width:50},
    {field:'createtime',title:'生成日期',width:130},
    {field:'checktime',title:'审核日期',width:130},
    {field:'starttime',title:'确认日期',width:130},
    {field:'endtime',title:'入库或出库日期',width:130},
    {field:'type',title:'1:采购 2:销售',width:80,formatter:function (value) {
        if(value=='1')
            return '采购'
        else if(value=='2')
            return '销售';
    }},
    {field:'creater',title:'下单员',width:80,formatter:function (value,order,index) {
            return order.creater==null?'':order.creater.name;
    }},
    {field:'checker',title:'审核员',width:80,formatter:function (value,order,index) {
        return order.checker==null?'':order.checker.name;
    }},
    {field:'starter',title:'采购员',width:80,formatter:function (value,order,index) {
        return order.starter==null?'':order.starter.name;
    }},
    {field:'ender',title:'库管员',width:80,formatter:function (value,order,index) {
        return order.ender==null?'':order.ender.name;
    }},
    {field:'supplieruuid',title:'供应商或客户',width:80,formatter:function (value,order,index) {
        if(typeof(order.supplier)=='undefined'){
            return '';
        }
        return order.supplier.name;
    }},
    {field:'totalmoney',title:'合计金额',width:80},
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

    {field:'-',title:'操作',width:100,formatter: function(value,row,index){
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
                            toolbar:[
                                {
                                    iconCls:'icon-add',
                                    text:'新增',
                                    handler:add
                                }
                            ],
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
                                appendContent('#starter', rowData.starter==null?'':rowData.starter.name);
                                appendContent('#ender', rowData.ender==null?'':rowData.ender.name);
                                appendContent('#createtime', rowData.createtime);
                                appendContent('#checktime', rowData.checktime);
                                appendContent('#starttime', rowData.starttime);
                                appendContent('#endtime', rowData.endtime);

                                $('#itemgrid').datagrid('loadData',rowData.orderdetails);

                            }
                        });




    // 初始化订单详情
    $('#ordersDlg').dialog({
                               title:'订单详情',
                               width:700,
                               height:320,
                               closed:true,
                               modal:true
                           });

    // 入库明细窗口初始化
    $('#itemDlg').dialog({
                             title:'入库明细',
                             width: 300,
                             height: 200,
                             modal:true,
                             closed:true,
                             buttons:[
                                 {
                                     text:'入库',
                                     iconCls:'icon-save',
                                     handler:doInStore
                                 }
                             ]
                         });
    // 入库明细窗口初始化
    $('#itemDlg').dialog({
                             title:'入库明细',
                             width: 300,
                             height: 200,
                             modal:true,
                             closed:true,
                             buttons:[
                                 {
                                     text:'入库',
                                     iconCls:'icon-save',
                                     handler:doInStore
                                 }
                             ]
                         });

    //===================== 明细窗口配置 start ===============//
    /**
     * 1 非入库操作时,只加载表格
     * 2 当请求是入库操作时,需要添加行的双击事件
     * 3 !!! 表格的配置 itemgridConfig 必须是配置好后 , 再让 datagrid 加载
     * 如果datagrid 已经加载配置文件,再给配置文件进行双击绑定,那双击事件不生效!!
     * @type {{columns: [*], singleSelect: boolean}}
     */
    var itemgridConfig =  {
        columns:[[
            {field:'uuid',title:'编号',width:80},
            {field:'goods',title:'商品编号',width:90,formatter:function (value,goods,index) {
                    return goods.uuid==null?'':goods.uuid;
                }},
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
        pagination:true
    };

    // 入库操作时,订单详情可以双击操作
    if(Request['operation']=='doInStore'){
        // 绑定双击事件
        itemgridConfig.onDblClickRow = function (rowIndex,rowData) {
            // 1 打开商品入库窗口
            // 2 加载数据
            // 3 提交入库
            // 4 刷新订单详情页面
            // 5 所有订单都入库时,更新订单状态为入库
            $('#itemDlg').dialog('open');
            appendContent('#goodsuuid',rowData.uuid);
            appendContent('#goodsname',rowData.goodsname);
            appendContent('#num',rowData.num);
            $('#itemuuid').val(rowData.uuid);
        }
    }

    // 订单明细表格初始化
    $('#itemgrid').datagrid(itemgridConfig);

    //===================== 明细窗口配置 end ===============//

    // 审核操作时,显示审核按钮
    if(Request['operation']=='check'){
        $('#btnCheck').show();
    }

    // 确认操作时,显示确认按钮
    if(Request['operation']=='start'){
        $('#btnStart').show();
    }




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
                            $.messager.show(getMessage(result.message));
                        }else{
                            $.messager.show(getMessage(result));
                        }
                    },
                    error:function (result) {
                        $.messager.show(getMessage('操作失败,请联系后台管理员..'));
                    }
                })
            }
        })

    });

    // 入库确认按钮的点击事件
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
                                   $.messager.show(getMessage(result.message));
                               }else{
                                   $.messager.show(getMessage(result));
                               }
                           },
                           error:function (result) {
                               $.messager.show(getMessage('操作失败,请联系后台管理员..'));
                           }
                       })
            }
        })

    });

    // 导出excel 按钮
    $('#btnExport').click(function(){
        var orderUuid = $('#uuid').html();
        $.download("orders_export", {uuid: orderUuid});
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
        $.messager.show(getMessage('必须输入完整信息...'));
    }
}
// 关闭编辑窗口
function close() {
    $("#editDlg").window('close');
}
/**
 * 往指定id的标签添加数据
 * @param id
 * @param content
 */
function appendContent(id, content) {
    $(id).html(content);
}



// 商品入库操作
function doInStore() {
    var param = $('#itemForm').serializeJSON();
    console.log('入库操作 :'+JSON.stringify(param));
    // 1 提示进行入库操作
    // 2 执行入库操作
    // 3 刷新明细表 , 订单表
    $.messager.confirm('提示','确认进行入库操作?'+JSON.stringify(param),function (yes) {
        if(yes){
            $.ajax({
                url:'orderdetail_doInStore',
                data:param,
                dataType:'json',
                type:'post',
                success:function(result){
                    if(result.success){
                        // 1 关闭入库对话框
                        // 2 设置当前选中的订单明细状态为入库
                        // 3 刷新订单明细
                        // 4 遍历订单明细,如果明细全部入库则刷新订单状态
                        $('#itemDlg').dialog('close');
                        $('#itemgrid').datagrid('getSelected').state='1';
                        $('#itemgrid').datagrid('loadData',$('#itemgrid').datagrid('getData'));

                        var rows = $('#itemgrid').datagrid('getRows');
                        var flag = true;
                        $.each(rows,function (i,row) {
                            if(row.state*1==0){
                                flag = false;
                                return false;
                            }
                        });

                        if(flag){
                            // 关闭订单明细窗口
                            // 刷新明细表格
                            $('#ordersDlg').dialog('close');
                            $('#grid').datagrid('reload');
                            $.messager.show(getMessage('订单入库完成...'));
                        }else{
                            $.messager.show(getMessage('还有未入库的明细,请继续处理...'));
                        }

                    }
                }
            });
        }
    })
}