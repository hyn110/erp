/**
 * 获取当前行的指定字段的编辑器
 * @param _field
 * @returns {jQuery}
 * @private
 */
function _getEditor(_field) {
    return $('#grid')
        .datagrid('getEditor', {index: currentEdittingRowIndex, field: _field});
}
/**
 * Created by huangyunning on 2017/12/4.
 */

var columns = [[
    {field:'goodsuuid',title:'商品编号',width:100,editor:{
        type:'numberbox',
        options:{disabled:true}
    }},
    {field:'goodsname',title:'商品名称',width:100,editor:{
        type:'combobox',
        options:{
            url:'goods_list',
            valueField:'name',
            textField:'name',
            onSelect:function (good) {
                // good 是后台返回的商品对象
                // 1 设置当前编辑行商品编号
                // 2 设置当前编辑行价格
               var goodsuuidEditor= _getEditor('goodsuuid');
               $(goodsuuidEditor.target).numberbox('setValue',good.uuid);

                var priceEditor= _getEditor('price');
                $(priceEditor.target).numberbox('setValue',good.outprice);

                var numEditor = _getEditor('num');
                $(numEditor.target).select();

                cal();
                sum();
            }
        }
    }},
    {field:'price',title:'价格',width:100,editor:{type:'numberbox', options:{disabled:true,min:0,precision:2}}},
    {field:'num',title:'数量',width:100,editor:'numberbox'},
    {field:'money',title:'金额',width:100,editor:{
        type:'numberbox',options:{disabled:true,min:0,precision:2}
    }},
    {field:'-',title:'操作',width:100,formatter:function (value,row,index) {
        if(row.num=='总金额'){
            return '';
        }
        var op = "<a href='#' onclick='del("+index+")'>删除</a>";
        return op;
    }}
]];


$(function () {
    $('#grid').datagrid({
                            columns: columns,
                            pagination:true,
                            pageList:[5,10,15,20,50],
                            singleSelect:true,
                            rownumbers:true,
                            showFooter:true,
                            toolbar:[
                                {
                                    iconCls:'icon-add',
                                    text:'增加',
                                    handler:add
                                },{
                                    text:'提交',
                                    iconCls:'icon-save',
                                    handler:save
                                }],
                            onClickRow:function (rowIndex,rowData) {
                                // 结束当前编辑的行
                                $('#grid').datagrid('endEdit',currentEdittingRowIndex);
                                // 开启点击行的编辑
                                currentEdittingRowIndex = rowIndex
                                $('#grid').datagrid('beginEdit',currentEdittingRowIndex);
                                bindGridEvent();
                            }
                        });

});
// 当前编辑行的索引
var currentEdittingRowIndex=0;
var newRowIndex = 0; // 新增加的行的索引

function add() {
    // 插入新行
    $("#grid").datagrid('appendRow',{num:0, money:0});
    var rows = $('#grid').datagrid('getRows');
    newRowIndex = rows.length - 1; // 插入新的一行的索引
    // 结束上一次正在编辑的行
    $('#grid').datagrid('endEdit',currentEdittingRowIndex);

    // 开始编辑新行
    currentEdittingRowIndex = newRowIndex;
    $('#grid').datagrid('beginEdit',currentEdittingRowIndex);
    bindGridEvent();

    // 更新页脚行并载入新数据
    $('#grid').datagrid('reloadFooter',[
            {num: '总金额', money: 0}]);


}
/**
 * 计算行的金额
 */
function cal() {
    var numEditor = _getEditor('num');
    var count = $(numEditor.target).val();

    var price = $(_getEditor('price').target).val();

    var money = (count*price).toFixed(2);

    // 设置金额
    $(_getEditor('money').target).val(money);

    // 将值设置到 datagrid 当前对象中,否则为 endEdit 时 , 拿不到 input 中的数据
    $('#grid').datagrid('getRows')[currentEdittingRowIndex].money=money;

}
/**
 * 绑定键盘抬起事件
 */
function bindGridEvent() {
    $(_getEditor('num').target).bind('keyup',function () {
        cal();
        sum();
    });

}
/**
 * 计算总金额
 */
function sum() {
   var rows =  $('#grid').datagrid('getRows');
   var totalMoney = 0;
   $.each(rows,function (i,row) {
       totalMoney += parseFloat(row.money);
   })

    totalMoney = totalMoney.toFixed(2);

    $('#grid').datagrid('reloadFooter',[
        {num: '总金额', money: totalMoney}]);
}

function del(index) {
    $('#grid').datagrid('deleteRow',index);
    sum();
    // 重新舒心datagrid , 更新索引index
    $('#grid').datagrid('loadData',$('#grid').datagrid('getData'));
}
function save() {
    alert('save')
}