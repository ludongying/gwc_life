layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var admin = layui.admin;

    /**
     * 系统管理--角色管理
     */
    var Role = {
        tableId: "roleTable",
        condition: {
            roleName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Role.initColumn = function () {
        return [[
            {title: '主键id', field: 'id', sort: true, hide: true},
            {title: '角色名称', field: 'name', sort: true, align: "center"},
            {title: '上级角色', field: 'pName', sort: true, align: "center", templet: function (d) {
                if (d.pName == '' || d.pName == null)
                    return "顶级";
                else
                    return d.pName;
            }},
            {title: '别名', field: 'description', sort: true, align: "center"},
            {title: '排序', field: 'sort', sort: true, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Role.tableId,
        url: Feng.ctxPath + '/role/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Role.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Role.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Role.openAddRole();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Role.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Role.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Role.onEditRole(data);
        } else if (layEvent === 'delete') {
            Role.onDeleteRole(data);
        } else if (layEvent === 'roleAssign') {
            Role.roleAssign(data);
        } else if (layEvent === 'roleThreshold') {
            Role.roleThreshold(data);
        }
    });


    /**
     * 点击查询按钮
     */
    Role.search = function () {
        var queryData = {};
        queryData['roleName'] = $("#roleName").val().trim();
        table.reload(Role.tableId, {where: queryData});
    };

    /**
     * 弹出添加角色
     */
    Role.openAddRole = function () {
        func.open({
            title: '添加角色',
            content: Feng.ctxPath + '/role/role_add',
            tableId: Role.tableId
        });
    };

    /**
     * 点击编辑角色
     */
    Role.onEditRole = function (data) {
        func.open({
            title: '编辑角色',
            content: Feng.ctxPath + '/role/role_edit?id=' + data.id,
            tableId: Role.tableId
        });
    };

    /**
     * 点击删除角色
     *
     * @param data 点击按钮时候的行数据
     */
    Role.onDeleteRole = function (data) {
        Feng.confirm("是否删除角色 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/role/delete", function (data) {
                if (data.success == true) {
                    Feng.success("删除成功!");
                    table.reload(Role.tableId);
                } else {
                    Feng.success(data.message);
                }

            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

    /**
     * 导出excel按钮
     */
    Role.exportExcel = function () {
        var checkRows = table.checkStatus(Role.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 分配菜单
     */
    Role.roleAssign = function (data) {
        layer.open({
            type: 2,
            title: '权限配置',
            area: ['300px', '450px'], //宽高
            fix: false,
            maxmin: true,
            content: Feng.ctxPath + '/role/role_menu_assign/' + data.id,
            end: function () {
                table.reload(Role.tableId);
            }
        });
    };

    /**
     * 阈值配置
     */
    Role.roleThreshold = function (data) {
        func.open({
            title: '阈值配置',
            content: Feng.ctxPath + '/role/role_threshold?id=' + data.id,
            tableId: Role.tableId
        });
    };



});
