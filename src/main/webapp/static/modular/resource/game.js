
layui.use(['layer', 'form', 'admin', 'ax', 'carousel', 'func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var carousel = layui.carousel;
    var func = layui.func;

    var table = layui.table;

    /**
     * 音乐信息管理
     */
    var Game = {
        tableId: "gameTable",
        condition: {
            gameName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Game.initColumn = function () {
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
        elem: '#' + Game.tableId,
        // url: Feng.ctxPath + '/game/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: Game.initColumn(),
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
                "brief": "这是一款棋牌游戏，通过XXXXXXXXXX",
                "updateTime":"2019-03-25",
                "size":"120M",
            }]
    });
});