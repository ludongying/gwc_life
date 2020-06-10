
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
    var Music = {
        tableId: "musicTable",
        condition: {
            personName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Music.initColumn = function () {
        return [[
            {title: '序号', type:'numbers'},
            {title: '序号', field: 'id', sort: true, align: "center",hide:true},
            {title: '歌曲名称', field: 'song', sort: true, align: "center"},
            {title: '歌手', field: 'singer', sort: false, align: "center"},
            {title: '分类', field: 'type', sort: false, align: "center"},
            {title: '更新时间', field: 'updateTime', sort: false, align: "center"},
            {title: '大小', field: 'size', sort: false, align: "center"},
            {title: '时长', field: 'totalTime', sort: false, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]]
            ;
    };

// 渲染表格
    var tableResult = table.render({
        elem: '#' + Music.tableId,
        // url: Feng.ctxPath + '/music/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: Music.initColumn(),
        data: [
        {
            "id": "1",
            "song": "起风了",
            "singer": "买辣椒页用券",
            "type": "流行",
            "updateTime":"2020-03-15",
            "size":"5M",
            "totalTime":"03:15"
        },
        {
            "id": "2",
            "song": "听妈妈的话",
            "singer": "周杰伦",
            "type": "复古",
            "updateTime":"2019-03-09",
            "size":"4M",
            "totalTime":"04:12"
        }]
    });

    // 工具条点击事件
    table.on('tool(' + Music.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Music.onEditPerson(data);
        } else if (layEvent === 'delete') {
            Music.onDeletePerson(data);
        } else if (layEvent === 'detail') {
            Music.onDetailPerson(data);
        }
    });

    /**
     * 点击查看船员信息
     */
    Music.onDetailPerson = function (data) {
        layer.open({
            type:2,
            title:false,
            area:['1095px','60px'],
            shade:0,
            closeBtn:0,
            shadeClose:true,
            // skin: 'demo-class',
            offset: 'b',
            content:"././common/images/music.png"
        })
    };


});