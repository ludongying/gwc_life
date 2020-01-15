layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func','upload'], function () {
    var table = layui.table;

    var LookUser = {
        tableId: "lookUserTable",
        condition: {
            lookUserName: ""
        }
    };

    LookUser.initColumn = function (d) {
        return [[
            {title: '角色名', field: 'name', align: "center"},
            {title: '别名', field: 'description', align: 'center'}
        ]];
    };

    var tableResult = table.render({
        elem: '#' + LookUser.tableId,
        url: Feng.ctxPath + '/menu/getUserListById?id='+Feng.getUrlParam("id"),
        page: false,
        height: "full-158",
        cellMinWidth: 100,
        cols: LookUser.initColumn()
    });
})