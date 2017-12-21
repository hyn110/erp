/**
 * Created by huangyunning on 2017/12/21.
 */
$(function () {
   // 初始化列表
    $("#grid").datagrid({
        url:'role_list',
        columns:[[
            {field:'uuid',title:'编号',width:100},
            {field:'name',title:'名称',width:100}
        ]],
        singleSelect:true,
        onClickRow:function (rowIndex, rowData) {
            $("#tree").tree({
                                url:'role_readRoleMenus?uuid='+rowData.uuid,
                                animate:true,
                                checkbox:true
                            });
        }
    });
    // 初始话tree

    $("#tree").tree({
        url:'role_readRoleMenus?id',
        animate:true,
        checkbox:true
    });

    $('#btnSave').click(function(){
       var nodes = $('#tree').tree('getChecked');
       var checkedStr = new Array();
       $.each(nodes,function(i,node){
          checkedStr.push(node.id);
       });

       checkedStr = checkedStr.join(',');
       // 构建数据
        var formData = {};
        formData.uuid=$('#grid').datagrid('getSelected').uuid;
        formData.checkedStr = checkedStr;

        $.ajax({
            url:'role_updateRoleMenus',
            data:formData,
            type:'post',
            dataType:'json',
            success:function (res) {
                $.messager.alert('提示', res.message, 'info');
            }
       });
    });
});