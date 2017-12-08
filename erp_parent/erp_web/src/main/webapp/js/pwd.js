/**
 * Created by huangyunning on 2017/12/4.
 */
$(function () {
    $('#grid').datagrid({
                            url: name+'_listByPage',
                            columns: columns,
                            pagination:true,
                            pageList:[5,10,15,20,50],

                            singleSelect:true
                        });

});

// 修改对象
function updatePwd_reset (uuid){
    console.log('uuid = '+uuid)
    $("#editForm").form('load',{'uuid':uuid,'newPwd':''});

    $("#editDlg").window('open');

}
// 保存新密码
function resetPwd() {

    var formData = $("#editForm").serializeJSON();

    $.ajax({
        url:'emp_updatePwd_reset',
        type:'post',
        dataType:'json',
        data:formData,
        success:function (result) {
            if(result.success){
                $("#editDlg").window('close');
                $.messager.show({
                                    title:'提示消息',
                                    msg:result.message,
                                    timeout:2000,
                                    showType:'slide',
                                    style:{
                                        right:'',
                                        top:document.body.scrollTop+document.documentElement.scrollTop,
                                        bottom:''
                                    }

                                });
            }else{
                $.messager.alert('提示','重置密码失败,请重试...');
            }
        }
    });
}

// 关闭编辑窗口
function close() {
    $("#editDlg").window('close');
}
