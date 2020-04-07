layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 系统管理--岗位管理
     */
    var Position = {
        tableId: "positionTable",
        condition: {
            positionName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Position.initColumn = function () {
        return [[
            {title: 'id', field: 'id', hide: true},
            {title: '岗位名称', field: 'name', sort: true, align: "center"},
            {title: '岗位编码', field: 'code', sort: true, align: "center"},
            {title: '创建时间', field: 'createTime', sort: true, align: "center", templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd')}}</div>"},
            {title: '排序', field: 'sort', sort: true, align: "center"},
            {title: '状态', field: 'state', sort: true, align: "center", templet: '#statusTpl'},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Position.tableId,
        url: Feng.ctxPath + '/position/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Position.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Position.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Position.openAddPosition();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Position.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Position.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Position.onEditPosition(data);
        } else if (layEvent === 'delete') {
            Position.onDeletePosition(data);
        } else if (layEvent === 'detail') {
            Position.onDetailPosition(data);
        } else if (layEvent === 'dataAuthority') {
            Position.onDataAuthority(data);
        }

    });


    /**
     * 点击查询按钮
     */
    Position.search = function () {
        var queryData = {};
        queryData['positionName'] = $("#positionName").val().trim();
        table.reload(Position.tableId, {where: queryData});
    };

    /**
     * 弹出添加岗位
     */
    Position.openAddPosition = function () {
        func.open({
            title: '添加岗位',
            content: Feng.ctxPath + '/position/position_add',
            tableId: Position.tableId
        });
    };

    /**
     * 点击编辑岗位
     */
    Position.onEditPosition = function (data) {
        func.open({
            title: '编辑岗位',
            content: Feng.ctxPath + '/position/position_edit?positionId=' + data.id,
            tableId: Position.tableId
        });
    };

    /**
     * 点击查看岗位
     */
    Position.onDetailPosition = function (data) {
        func.open({
            title: '查看岗位',
            content: Feng.ctxPath + '/position/position_detail?positionId=' + data.id,
            tableId: Position.tableId
        });
    };

    /**
     * 配置数据权限
     */
    Position.onDataAuthority = function (data) {
        func.open({
            title: '数据权限',
            content: Feng.ctxPath + '/position/position_dataAuthority?positionId=' + data.id,
            tableId: Position.tableId
        });
    };



    /**
     * 点击删除岗位
     *
     * @param data 点击按钮时候的行数据
     */
    Position.onDeletePosition = function (data) {
        Feng.confirm("是否删除岗位 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/position/delete", function (data) {
                if (data.success == true) {
                    Feng.success("删除成功!");
                    table.reload(Position.tableId);
                } else {
                    Feng.error(data.message);
                }
                /*Feng.success("删除成功!");
                table.reload(Position.tableId);*/
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("positionId", data.id);
            ajax.start();
        });
    };

    /**
     * 导出excel按钮
     */
    Position.exportExcel = function () {
        var checkRows = table.checkStatus(Position.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 修改position状态
     */
    form.on('switch(state)', function (data) {
        var id = data.elem.value;
        var checked = data.elem.checked ? true : false;
        Position.changeUserStatus(id, checked);
    });

    /**
     * 修改用户状态
     */
    Position.changeUserStatus = function (id, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/position/unfreeze", function (data) {
                Feng.success("启用成功!");
            }, function (data) {
                Feng.error("启用失败!");
                table.reload(Position.tableId);
            });
            ajax.set("id", id);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/position/freeze", function (data) {
                Feng.success("禁用成功!");
            }, function (data) {
                Feng.error("禁用失败!" + data.responseJSON.message + "!");
                table.reload(Position.tableId);
            });
            ajax.set("id", id);
            ajax.start();
        }
    };
});
function test() {
    
}