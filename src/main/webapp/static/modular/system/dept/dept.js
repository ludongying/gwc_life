layui.use(['table', 'ztree', 'ax', 'func', 'treetable'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;
    var treetable = layui.treetable;

    /**
     * 系统管理--部门管理
     */
    var Dept = {
        tableId: "deptTable",
        condition: {
            fullName: "",
            id: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Dept.initColumn = function () {
        return [[
            {type: 'numbers'},
            {title: '主键id', field: 'id', hide: true},
            {title: '简称', field: 'simpleName'},
            {title: '全称', field: 'fullName', align: "center"},
            {title: '排序', field: 'sort', align: "center"},
            {title: '描述', field: 'description', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };


    // 渲染表格 、树形
    Dept.initTable = function (id, data) {
        return treetable.render({
            elem: '#' + id,
            url: Feng.ctxPath + '/dept/listTree',
            where: data,
            page: false,
            height: "full-97",
            cellMinWidth: 100,
            cols: Dept.initColumn(),
            treeColIndex: 2,
            treeSpid: "0",
            treeIdName: 'id',
            treePidName: 'pid',
            treeDefaultClose: true,
            treeLinkage: false
        });
    };
    Dept.initTable(Dept.tableId);  //初始化加载

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Dept.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        Dept.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Dept.openAddDept();
    });

    // 工具条点击事件
    table.on('tool(' + Dept.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            Dept.onEditDept(data);
        } else if (layEvent === 'delete') {
            Dept.onDeleteDept(data);
        }
    });

    /**
     * 点击搜索按钮
     */
    Dept.search = function () {
        var queryData = {};
        queryData['deptName'] = $("#deptName").val().trim();
        Dept.initTable(Dept.tableId, queryData);
    };

    /**
     * 重置查询条件
     */
    Dept.btnReset = function () {
        $("#deptName").val("");
    };

    /**
     * 弹出增加部门
     */
    Dept.openAddDept = function () {
        func.open({
            title: '增加部门',
            area: ['500px','500px'],
            content: Feng.ctxPath + '/dept/dept_add',
            tableId: Dept.tableId
        });
    };

    /**
     * 点击编辑部门
     */
    Dept.onEditDept = function (data) {
        func.open({
            title: '编辑部门',
            area: ['500px','500px'],
            content: Feng.ctxPath + '/dept/dept_edit?id=' + data.id,
            tableId: Dept.tableId
        });
    };

    /**
     * 点击删除部门
     *
     * @param data 点击按钮时候的行数据
     */
    Dept.onDeleteDept = function (data) {
        Feng.confirm("是否删除部门《" + data.fullName + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + "/dept/delete", function (data) {
                if (data.success == true) {
                    Feng.success("删除成功!");
                    Dept.initTable(Dept.tableId);
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


    //展开
    $('#expandAll').click(function () {
        treetable.expandAll('#' + Dept.tableId);
    });

    //折叠
    $('#foldAll').click(function () {
        treetable.foldAll('#' + Dept.tableId);
    });
});
