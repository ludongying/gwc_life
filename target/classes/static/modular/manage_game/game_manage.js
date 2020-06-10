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
     * 船员信息管理
     */
    var GameManage = {
        tableId: "gameManageTable",
        condition: {
            gameManageName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    GameManage.initColumn = function () {
        return [[
            {title: '序号', type:'numbers'},
            {title: '序号', field: 'id', sort: true, align: "center",hide:true},
            {title: '类别', field: 'type', sort: true, align: "center"},
            {title: '游戏名称', field: 'name', sort: true, align: "center"},
            {title: '适用平台', field: 'platform', sort: false, align: "center"},
            {title: '游戏简介', field: 'brief', sort: false, align: "center"},
            {title: '更新时间', field: 'updateTime', sort: false, align: "center"},
            {title: '大小', field: 'size', sort: false, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

// 渲染表格
    var tableResult = table.render({
        elem: '#' + GameManage.tableId,
        // url: Feng.ctxPath + '/gameManage/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: GameManage.initColumn(),
        data: [
            {
                "id": "1",
                "type":"网络游戏",
                "name": "王者荣耀",
                "platform": "Android",
                "brief": "这是一款网络游戏，集成了XXXXXXXXXX",
                "updateTime":"2020-04-25",
                "size":"400M",
            },
            {
                "id": "2",
                "type":"棋牌游戏",
                "name": "欢乐斗地主",
                "platform": "Window",
                "brief": "这是一款网络游戏，集成了XXXXXXXXXX",
                "updateTime":"2019-03-25",
                "size":"120M",
            }]
    });

    /**
     * 左侧搜索
     */
// 搜索按钮点击事件
    $('#btnSearch').click(function () {
        // GameManage.search();
    });
// 重置按钮点击事件
    $('#btnReset').click(function () {
        GameManage.btnReset();
    });


    /**
     * 右侧操作
     */
// 添加按钮点击事件
    $('#btnAdd').click(function () {
        GameManage.openAddGameManage();
    });


// 工具条点击事件
    table.on('tool(' + GameManage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            //GameManage.onEditGameManage(data);
        } else if (layEvent === 'delete') {
            //GameManage.onDeleteGameManage(data);
        } else if (layEvent === 'detail') {
            //GameManage.onDetailGameManage(data);
        } else if (layEvent === 'details') {
            // GameManage.onDetailsGameManage(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    GameManage.search = function () {
        var queryData = {};
        queryData['gameManageName'] = $("#gameManageName").val().trim();
        table.reload(GameManage.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    GameManage.btnReset = function () {
        $("#gameManageName").val("");
    };

    /**
     * 弹出增加船员信息
     */
    GameManage.openAddGameManage = function () {
        func.open({
            title: '增加游戏信息',
            area: ['600px', '700px'],
            content: Feng.ctxPath + '/gameManage/gameManage_add',
            tableId: GameManage.tableId
        });
    };

    /**
     * 点击编辑船员信息
     */
    GameManage.onEditGameManage = function (data) {
        // if($('#roleNames').val().includes('超级管理员') || $('#roleNames').val().includes('船长') || $('#roleNames').val().includes('船务管理员') ||
        //     $('#userId').val() === data.gameManageId){
        func.open({
            title: '编辑船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/gameManage/gameManage_edit?id=' + data.id,
            tableId: GameManage.tableId
        });
        // }else{
        //         Feng.error('无权限编辑！');
        // }


    };

    /**
     * 点击查看船员信息
     */
    GameManage.onDetailGameManage = function (data) {
        func.open({
            title: '查看船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/gameManage/gameManage_detail?id=' + data.id,
            tableId: GameManage.tableId
        });
    };

    /**
     * 点击剧集管理页
     */
    GameManage.onDetailsGameManage = function (data) {
        func.open({
            title: '剧集管理',
            area: ['550px', '800px'],
            content: Feng.ctxPath + '/gameManage/gameManage_series?id=' + data.id,
            tableId: GameManage.tableId
        });
    };


    /**
     * 点击删除船员信息
     *
     * @param data 点击按钮时候的行数据
     */
    GameManage.onDeleteGameManage = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/gameManage/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(GameManage.tableId);
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

})
;
