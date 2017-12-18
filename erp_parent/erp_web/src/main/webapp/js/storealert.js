var _columns = [[
    {field: 'uuid', title: '商品编号', width: 200},
    {field: 'name', title: '商品类别', width: 200},
    {field: 'storenum', title: '库存量', width: 200},
    {field: 'outnum', title: '待出库量', width: 200}
]];


$(function () {

    $('#grid')
        .datagrid({
                      url: name + '_storeAlertList',
                      columns: _columns,
                      singleSelect: true,
                      pagination: true,
                      pageList: [5, 10, 15, 20, 50],

                  });


})

