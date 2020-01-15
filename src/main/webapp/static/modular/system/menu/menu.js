layui.use(['layer', 'form', 'table', 'admin', 'ax', 'treetable'], function () {
    var $ = layui.$;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var treetable = layui.treetable;

    /**
     * 系统管理--菜单管理
     */
    var Menu = {
        tableId: "menuTable",
        condition: {
            menuName: "",
            level: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Menu.initColumn = function () {
        return [[
            {type: 'numbers'},
            {title: '主键id', field: 'id', hide: true},
            {title: '菜单名称', field: 'name', minWidth: 240},
            {title: '菜单编号', field: 'code', align: "center"},
            {title: '菜单父编号', field: 'pcode', align: "center"},
            {
                title: '菜单图标', field: 'icon', align: "center", templet: function (d) {
                    return "<i class='layui-icon " + d.icon + "'></i>";  //库中存储图标样式
                }
            },
            {title: '请求地址', field: 'url', align: "center"},
            {title: '排序', field: 'sort', align: "center"},
            {title: '层级', field: 'levels', align: "center"},
            {
                title: '是否是菜单', field: 'menuFlag', align: "center", templet: function (d) {
                    if (d.menuFlag === 'Y')
                        return "<span class='layui-badge layui-bg-blue'>菜单</span></b>";
                    else
                        return "<span class='layui-badge layui-bg-cyan'>按钮</span></b>";
                }
            },
            {title: '状态', field: 'status', align: "center", templet: '#statusTpl'},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格 树形列表
    Menu.initTable = function (id, data) {
        return treetable.render({
            elem: '#' + id,
            url: Feng.ctxPath + '/menu/listTree',
            where: data,
            page: false,
            height: "full-158",
            cellMinWidth: 100,
            cols: Menu.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'code',
            treePidName: 'pcode',
            treeDefaultClose: true,
            treeLinkage: false
        });
    };
    Menu.initTable(Menu.tableId); //初始化


    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Menu.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Menu.openAddMenu();
    });

    //展开
    $('#expandAll').click(function () {
        treetable.expandAll('#' + Menu.tableId);
    });

    //折叠
    $('#foldAll').click(function () {
        treetable.foldAll('#' + Menu.tableId);
    });

    // 工具条点击事件
    table.on('tool(' + Menu.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Menu.onEditMenu(data);
        } else if (layEvent === 'delete') {
            Menu.onDeleteMenu(data);
        } else if (layEvent === 'look') {
            Menu.onLookMenu(data);
        }
    });


    /**
     * 点击查询按钮
     */
    Menu.search = function () {
        var queryData = {};
        queryData['menuName'] = $("#menuName").val().trim();
        queryData['level'] = $("#level").val().trim();
        Menu.initTable(Menu.tableId, queryData);
    };

    Menu.onLookMenu = function (data) {
        top.layui.admin.open({
            type: 2,
            area: ['600px','500px'],
            title: '查看关联角色',
            content: Feng.ctxPath + '/menu/menu_look?id=' + data.id,
        });
    }

    /**
     * 弹出添加菜单
     */
    Menu.openAddMenu = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加菜单',
            content: Feng.ctxPath + '/menu/menu_add',
            end: function () {
                admin.getTempData('formOk') && Menu.initTable(Menu.tableId);
            }
        });
    };

    /**
     * 点击编辑菜单
     */
    Menu.onEditMenu = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改菜单',
            content: Feng.ctxPath + '/menu/menu_edit?id=' + data.id,
            end: function () {
                admin.getTempData('formOk') && Menu.initTable(Menu.tableId);
            }
        });
    };

    /**
     * 点击删除菜单
     * @param data 点击按钮时候的行数据
     */
    Menu.onDeleteMenu = function (data) {
        Feng.confirm("是否删除菜单 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/menu/delete", function () {
                Feng.success("删除成功!");
                Menu.initTable(Menu.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };


    // 修改菜单状态
    form.on('switch(status)', function (data) {
        var id = data.elem.value;
        var checked = data.elem.checked ? true : false;

        Menu.changeUserStatus(id, checked);
    });

    /**
     * 修改菜单状态
     * @param id 菜单id
     * @param checked 是否选中（true,false），选中就是启动菜单，未选中就是禁用菜单
     */
    Menu.changeUserStatus = function (id, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/menu/unfreeze", function (data) {
                Feng.success("启动成功!");
            }, function (data) {
                Feng.error("启动失败!");
                table.reload(Menu.tableId);
            });
            ajax.set("id", id);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/menu/freeze", function (data) {
                Feng.success("禁用成功!");
            }, function (data) {
                Feng.error("禁用失败!" + data.responseJSON.message + "!");
                table.reload(Menu.tableId);
            });
            ajax.set("id", id);
            ajax.start();
        }
    };

});
