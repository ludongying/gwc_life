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
    var deptName = "";
    var deptId = "";


    /**
     * 系统管理--用户管理
     */
    var User = {
        tableId: "userTable",    //表格id
        condition: {
            name: "",
            deptId: ""
        }
    };

    /**
     * 初始化表格的列
     */
    User.initColumn = function () {
        return [[
            {title: '用戶id', field: 'id', hide: true, sort: true},
            // {title: '账号', field: 'account', align: "center"},
            {title: '姓名', field: 'name', align: "center"},
            {title: '性别', field: 'sex', align: "center", templet: function (d) {
                if (d.sex === 'M')
                    return "<span class='layui-badge layui-bg-blue'>男</span></b>";
                else
                    return "<span class='layui-badge layui-bg-orange'>女</span></b>";
            }},
            {title: '角色', field: 'roleName', align: "center"},
            {title: '部门', field: 'deptName', align: "center", templet: function (d) {
                if (d.deptName == '' || d.deptName == null)
                    return "顶级";
                else
                    return d.deptName;
            }},
            {title: '电话', field: 'phone', align: "center", width: 120},
            // {title: '创建时间', field: 'createTime', align: "center", width: 110, templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd')}}</div>"},
            {title: '状态', field: 'status', align: "center", width: 100, templet: '#statusTpl'},
            {title: '操作', toolbar: '#tableBar', width: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + User.tableId,
        url: Feng.ctxPath + '/user/list',
        page: true,
        height: "full-95",
        cellMinWidth: 100,
        cols: User.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        User.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        User.btnReset();
    });

    /**
     * 左侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        User.openAddUser();
    });

    /**
     * 工具条点击事件
     */
    table.on('tool(' + User.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            User.onEditUser(data);
        } else if (layEvent === 'delete') {
            User.onDeleteUser(data);
        } else if (layEvent === 'roleAssign') {
            User.roleAssign(data);
        } else if (layEvent === 'reset') {
            User.resetPassword(data);
        } else if (layEvent === 'configure') {
            User.configure(data);
        }
    });

    /**
     * 点击查询按钮
     */
    User.search = function () {
        var queryData = {};
        queryData['deptId'] = User.condition.deptId;
        queryData['name'] = $("#name").val().trim();
        table.reload(User.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    User.btnReset = function () {
        $("#name").val("");
    };

    /**
     * 弹出添加用户
     */
    User.openAddUser = function () {
        func.open({
            title: '添加用户',
            content: Feng.ctxPath + '/user/user_add?deptId=' + deptId + "&deptName=" + deptName,
            tableId: User.tableId
        });
    };

    /**
     * 点击编辑用户
     */
    User.onEditUser = function (data) {
        func.open({
            title: '编辑用户',
            content: Feng.ctxPath + '/user/user_edit?id=' + data.id,
            tableId: User.tableId
        });
    };

    /**
     * 点击删除用户
     */
    User.onDeleteUser = function (data) {
        Feng.confirm("是否删除用户《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/user/deleteLogic", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(User.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

    // 修改user状态
    form.on('switch(status)', function (data) {
        var id = data.elem.value;
        var checked = data.elem.checked ? true : false;

        User.changeUserStatus(id, checked);
    });

    /**
     * 修改用户状态
     * @param id 用户id
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    User.changeUserStatus = function (id, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/user/unfreeze", function (data) {
                if (data.success) {
                    Feng.success("解除冻结成功!");
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("解除冻结失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/user/freeze", function (data) {
                if (data.success) {
                    Feng.success("冻结成功!");
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("冻结失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.start();
        }
    };

    /**
     * 重置密码
     */
    User.resetPassword = function (data) {
        Feng.confirm("是否重置密码为《888888》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/user/reset", function (data) {
                if (data.success) {
                    Feng.success("重置密码成功!");
                    table.reload(User.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("重置密码失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

    /**
     * 分配角色
     */
    User.roleAssign = function (data) {
        func.open({
            title: '角色分配',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/user/user_role_assign/' + data.id,
            tableId: User.tableId
        });
    };

    /**
     * 选择部门时
     */

    User.onClickDept = function (e, treeId, treeNode) {
        User.condition.deptId = treeNode.id;
        deptName = treeNode.name;
        deptId = treeNode.id;
        User.search();
    };

    //初始化左侧部门树
    var ztree = new $ZTree("deptTree", "/dept/tree");
    ztree.bindOnClick(User.onClickDept);
    ztree.init();



});
