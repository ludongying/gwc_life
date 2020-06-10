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
            {title: '主键id', field: 'id', hide: true},
            {title: '角色名称', field: 'name', align: "center"},
            {title: '上级角色', field: 'pName', align: "center", templet: function (d) {
                if (d.pName == '' || d.pName == null)
                    return "顶级";
                else
                    return d.pName;
            }},
            {title: '别名', field: 'description', align: "center"},
            {title: '排序', field: 'sort', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Role.tableId,
        url: Feng.ctxPath + '/role/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: Role.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Role.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        Role.btnReset();
    });

    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Role.openAddRole();
    });

    /**
     * 工具条点击事件
    */
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
     * 点击搜索按钮
     */
    Role.search = function () {
        var queryData = {};
        queryData['name'] = $("#roleName").val().trim();
        table.reload(Role.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    Role.btnReset = function () {
        $("#roleName").val("");
    };

    /**
     * 弹出增加角色
     */
    Role.openAddRole = function () {
        func.open({
            title: '增加角色',
            area: ['500px','500px'],
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
            area: ['500px','500px'],
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
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/role/delete", function (data) {
                if (data.success == true) {
                    Feng.success("删除成功!");
                    table.reload(Role.tableId);
                } else {
                    Feng.success(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

    /**
     * 分配菜单
     */
    Role.roleAssign = function (data) {
        func.open({
            title: '权限配置',
            area: ['300px','500px'],
            content: Feng.ctxPath + '/role/role_menu_assign/' + data.id,
            tableId: Role.tableId
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
