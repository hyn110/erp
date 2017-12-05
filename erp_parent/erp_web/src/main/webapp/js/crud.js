/**
 * Created by huangyunning on 2017/12/4.
 */
$(function () {
    $('#grid').datagrid({
                            url: name+'_listByPage',
                            columns: columns,
                            pagination:true,
                            pageList:[5,10,15,20,50],
                            toolbar:[{iconCls:'icon-add',
                                text:'新增',
                                handler:add}]
                        });


    //
    $("#btnSearch").click(function () {
        var parameterJson = $("#searchForm").serializeJSON();
        //console.log(parameterJson);
        console.log(JSON.stringify($("#grid").datagrid('getData')));
        // 重新加载数据
        $("#grid").datagrid('load',parameterJson);
    })


});
//
function add(){
    $("#editDlg").window('open');
    // 修改 form 表单地址
    edit_form_url = name +'_save';
    // $("#editForm").form('clear');
    $("#editForm_uuid").val(-1); // 给隐藏字段赋初值,否则提交空字符串,数据封装出错
}
// 删除对象
function del(id) {
    $.messager.confirm('确认','确认删除该条数据吗?',function (yes) {
        if(yes){
            $.post(name+"_del",{"uuid":id},function (result) {
                if(result=='success'){
                    $("#grid").datagrid('reload');
                }
            })
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
            url: edit_form_url,
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
