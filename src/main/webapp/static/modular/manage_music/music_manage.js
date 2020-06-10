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
    var MusicManage = {
        tableId: "musicManageTable",
        condition: {
            musicManageName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MusicManage.initColumn = function () {
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
        ]];
    };

// 渲染表格
    var tableResult = table.render({
        elem: '#' + MusicManage.tableId,
        // url: Feng.ctxPath + '/musicManage/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: MusicManage.initColumn(),
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

    /**
     * 左侧搜索
     */
// 搜索按钮点击事件
    $('#btnSearch').click(function () {
        // MusicManage.search();
    });
// 重置按钮点击事件
    $('#btnReset').click(function () {
        MusicManage.btnReset();
    });


    /**
     * 右侧操作
     */
// 添加按钮点击事件
    $('#btnAdd').click(function () {
        MusicManage.openAddMusicManage();
    });


// 工具条点击事件
    table.on('tool(' + MusicManage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            //MusicManage.onEditMusicManage(data);
        } else if (layEvent === 'delete') {
            //MusicManage.onDeleteMusicManage(data);
        } else if (layEvent === 'detail') {
            //MusicManage.onDetailMusicManage(data);
        } else if (layEvent === 'details') {
            MusicManage.onDetailsMusicManage(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    MusicManage.search = function () {
        var queryData = {};
        queryData['musicManageName'] = $("#musicManageName").val().trim();
        table.reload(MusicManage.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    MusicManage.btnReset = function () {
        $("#musicManageName").val("");
    };

    /**
     * 弹出增加船员信息
     */
    MusicManage.openAddMusicManage = function () {
        func.open({
            title: '增加音乐信息',
            area: ['600px', '700px'],
            content: Feng.ctxPath + '/musicManage/musicManage_add',
            tableId: MusicManage.tableId
        });
    };

    /**
     * 点击编辑船员信息
     */
    MusicManage.onEditMusicManage = function (data) {
        // if($('#roleNames').val().includes('超级管理员') || $('#roleNames').val().includes('船长') || $('#roleNames').val().includes('船务管理员') ||
        //     $('#userId').val() === data.musicManageId){
        func.open({
            title: '编辑船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/musicManage/musicManage_edit?id=' + data.id,
            tableId: MusicManage.tableId
        });
        // }else{
        //         Feng.error('无权限编辑！');
        // }


    };

    /**
     * 点击查看船员信息
     */
    MusicManage.onDetailMusicManage = function (data) {
        func.open({
            title: '查看船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/musicManage/musicManage_detail?id=' + data.id,
            tableId: MusicManage.tableId
        });
    };

    /**
     * 点击剧集管理页
     */
    MusicManage.onDetailsMusicManage = function (data) {
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
        });
    };


    /**
     * 点击删除船员信息
     *
     * @param data 点击按钮时候的行数据
     */
    MusicManage.onDeleteMusicManage = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/musicManage/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MusicManage.tableId);
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
