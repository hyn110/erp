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
