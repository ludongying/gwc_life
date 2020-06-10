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
    var TvManage = {
        tableId: "filmManageTable",
        condition: {
            filmManageName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    TvManage.initColumn = function () {
        return [[
            {title: '序号', type:'numbers'},
            {title: 'ID', field: 'id', sort: true, align: "center", hide: true},
            {title: '电影名称', field: 'filmName', sort: true, align: "center"},
            {title: '导演', field: 'director', sort: false, align: "center"},
            {title: '主演', field: 'actors', sort: false, align: "center"},
            {title: '分类', field: 'type', sort: false, align: "center"},
            {title: '简介', field: 'abstract', sort: false, align: "center"},
            {title: '时长', field: 'totalTime', sort: false, align: "center"},
            {title: '更新时间', field: 'updateTime', sort: false, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

// 渲染表格
    var tableResult = table.render({
        elem: '#' + TvManage.tableId,
        // url: Feng.ctxPath + '/filmManage/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: TvManage.initColumn(),
        data: [
            {
                "id": "1",
                "filmName": "来自星星的少年",
                "director":"张国立",
                "actors":"任嘉伦，张三，李四，王五",
                "type":"犯罪，搞笑",
                "abstract":"被警界荣称为“三叉戟”的三位人民警察：崔铁军（陈建斌饰）、徐国柱（董勇饰）、潘江海（郝平饰），在退居二线之际，遇到了一起洗钱大案，“三叉戟”重新面对20年前的对手并被多方势力挑战。金融巨骗为了解冻资产，不惜雇佣黑道动用极端手段。面对新型犯罪，“三叉戟”用传统的工作手段进行对抗，纷纷使出了自己的看家本事。在对手巧施离间计时，“三叉戟”的关系分崩离析。但在警察职责面前，他们最终齐心合力，一举击破了金融犯罪集团，同时也将幕后的腐败黑手绳之以法，续写了“三叉戟”的辉煌",
                "totalTime": "120分钟",
                "updateTime":"2020-03-15",
            },
            {
                "id": "2",
                "filmName": "来自星星的少年",
                "director":"陈凯歌",
                "actors":"任嘉伦，谭松韵，韩栋",
                "type":"悬疑，爱情",
                "abstract":"天赋异禀的六扇门女捕快袁今夏因为一桩案件和性情狠辣的锦衣卫陆绎结下梁子，今夏本以为此生与他再无交集，奈何冤家路窄。朝廷十万两修河款不翼而飞，今夏奉命协助陆绎一起下扬州查案，替朝廷找回丢失的官银。本是道不同不相为谋，却因惊天密案联手。两人从势同水火到刮目相看再到情难自已，命运的齿轮从此旋转在一起。然而事与愿违，今夏竟是当年夏然案的遗孤，背负家族血仇的她与陆绎之间横生了无法跨越的鸿沟。最后，两个有情人历经苦难，为救百姓、抗倭寇、锄奸佞，放下家族仇怨，联手对敌，冲破世俗枷锁，勇敢地走到了一起",
                "totalTime": "140分钟",
                "updateTime":"2019-03-09",
            }]
    });

    /**
     * 左侧搜索
     */
// 搜索按钮点击事件
    $('#btnSearch').click(function () {
        // TvManage.search();
    });
// 重置按钮点击事件
    $('#btnReset').click(function () {
        TvManage.btnReset();
    });


    /**
     * 右侧操作
     */
// 添加按钮点击事件
    $('#btnAdd').click(function () {
        TvManage.openAddTvManage();
    });


// 工具条点击事件
    table.on('tool(' + TvManage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            //TvManage.onEditTvManage(data);
        } else if (layEvent === 'delete') {
            //TvManage.onDeleteTvManage(data);
        } else if (layEvent === 'detail') {
            //TvManage.onDetailTvManage(data);
        }
        else if (layEvent === 'preview') {
            TvManage.onPreviewTvManage(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    TvManage.search = function () {
        var queryData = {};
        queryData['filmManageName'] = $("#filmManageName").val().trim();
        table.reload(TvManage.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    TvManage.btnReset = function () {
        $("#filmManageName").val("");
    };

    /**
     * 点击查看船员信息
     */
    TvManage.onPreviewTvManage = function (data) {
        layer.open({
            type:2,
            title:false,
            area:['900px','600px'],
            shade:0.8,
            closeBtn:1,
            shadeClose:true,
            skin: 'demo-class',
            content:"././common/images/film.mp4"
        })
    };

    /**
     * 弹出增加船员信息
     */
    TvManage.openAddTvManage = function () {
        func.open({
            title: '增加电影信息',
            area: ['600px', '800px'],
            content: Feng.ctxPath + '/filmManage/filmManage_add',
            tableId: TvManage.tableId
        });
    };

    /**
     * 点击编辑船员信息
     */
    TvManage.onEditTvManage = function (data) {
        // if($('#roleNames').val().includes('超级管理员') || $('#roleNames').val().includes('船长') || $('#roleNames').val().includes('船务管理员') ||
        //     $('#userId').val() === data.filmManageId){
        func.open({
            title: '编辑船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/filmManage/filmManage_edit?id=' + data.id,
            tableId: TvManage.tableId
        });
        // }else{
        //         Feng.error('无权限编辑！');
        // }


    };

    /**
     * 点击查看船员信息
     */
    TvManage.onDetailTvManage = function (data) {
        func.open({
            title: '查看船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/filmManage/filmManage_detail?id=' + data.id,
            tableId: TvManage.tableId
        });
    };

    /**
     * 点击删除船员信息
     *
     * @param data 点击按钮时候的行数据
     */
    TvManage.onDeleteTvManage = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/filmManage/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(TvManage.tableId);
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
