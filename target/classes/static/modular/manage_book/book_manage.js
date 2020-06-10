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
    var BookManage = {
        tableId: "bookManageTable",
        condition: {
            bookManageName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    BookManage.initColumn = function () {
        return [[
            {title: '序号', type:'numbers'},
            {title: 'ID', field: 'id', sort: true, align: "center", hide: true},
            {title: '书名', field: 'bookName', sort: true, align: "center"},
            // {title: '总集数', field: 'totalNum', sort: false, align: "center"},
            {title: '作者', field: 'author', sort: false, align: "center"},
            {title: '分类', field: 'type', sort: false, align: "center"},
            {title: '简介', field: 'abstract', sort: false, align: "center"},
            {title: '更新时间', field: 'updateTime', sort: false, align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

// 渲染表格
    var tableResult = table.render({
        elem: '#' + BookManage.tableId,
        // url: Feng.ctxPath + '/bookManage/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: BookManage.initColumn(),
        data: [
            {
                "id": "1",
                "bookName": "时间简史",
                // "totalNum": "20集",
                "author":"张国立",
                // "actors":"陈建斌，张三，李四，王五",
                "type":"犯罪，搞笑",
                "abstract":"被警界荣称为“三叉戟”的三位人民警察：崔铁军（陈建斌饰）、徐国柱（董勇饰）、潘江海（郝平饰），在退居二线之际，遇到了一起洗钱大案，“三叉戟”重新面对20年前的对手并被多方势力挑战。金融巨骗为了解冻资产，不惜雇佣黑道动用极端手段。面对新型犯罪，“三叉戟”用传统的工作手段进行对抗，纷纷使出了自己的看家本事。在对手巧施离间计时，“三叉戟”的关系分崩离析。但在警察职责面前，他们最终齐心合力，一举击破了金融犯罪集团，同时也将幕后的腐败黑手绳之以法，续写了“三叉戟”的辉煌",
                "updateTime":"2020-03-15",
            },
            {
                "id": "2",
                "bookName": "锦衣之下",
                // "totalNum": "50集",
                "author":"陈凯歌",
                // "actors":"任嘉伦，谭松韵，韩栋",
                "type":"悬疑，爱情",
                "abstract":"天赋异禀的六扇门女捕快袁今夏因为一桩案件和性情狠辣的锦衣卫陆绎结下梁子，今夏本以为此生与他再无交集，奈何冤家路窄。朝廷十万两修河款不翼而飞，今夏奉命协助陆绎一起下扬州查案，替朝廷找回丢失的官银。本是道不同不相为谋，却因惊天密案联手。两人从势同水火到刮目相看再到情难自已，命运的齿轮从此旋转在一起。然而事与愿违，今夏竟是当年夏然案的遗孤，背负家族血仇的她与陆绎之间横生了无法跨越的鸿沟。最后，两个有情人历经苦难，为救百姓、抗倭寇、锄奸佞，放下家族仇怨，联手对敌，冲破世俗枷锁，勇敢地走到了一起",
                "updateTime":"2019-03-09",
            }]
    });

    /**
     * 左侧搜索
     */
// 搜索按钮点击事件
    $('#btnSearch').click(function () {
        // BookManage.search();
    });
// 重置按钮点击事件
    $('#btnReset').click(function () {
        BookManage.btnReset();
    });


    /**
     * 右侧操作
     */
// 添加按钮点击事件
    $('#btnAdd').click(function () {
        BookManage.openAddBookManage();
    });


// 工具条点击事件
    table.on('tool(' + BookManage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            //BookManage.onEditBookManage(data);
        } else if (layEvent === 'delete') {
            //BookManage.onDeleteBookManage(data);
        } else if (layEvent === 'detail') {
            //BookManage.onDetailBookManage(data);
        } else if (layEvent === 'preview') {
            BookManage.onPreviewBookManage(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    BookManage.search = function () {
        var queryData = {};
        queryData['bookManageName'] = $("#bookManageName").val().trim();
        table.reload(BookManage.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    BookManage.btnReset = function () {
        $("#bookManageName").val("");
    };

    /**
     * 弹出增加船员信息
     */
    BookManage.openAddBookManage = function () {
        func.open({
            title: '增加书籍信息',
            area: ['600px', '700px'],
            content: Feng.ctxPath + '/bookManage/bookManage_add',
            tableId: BookManage.tableId
        });
    };

    /**
     * 点击编辑船员信息
     */
    BookManage.onEditBookManage = function (data) {
        // if($('#roleNames').val().includes('超级管理员') || $('#roleNames').val().includes('船长') || $('#roleNames').val().includes('船务管理员') ||
        //     $('#userId').val() === data.bookManageId){
        func.open({
            title: '编辑船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/bookManage/bookManage_edit?id=' + data.id,
            tableId: BookManage.tableId
        });
        // }else{
        //         Feng.error('无权限编辑！');
        // }


    };

    /**
     * 点击查看船员信息
     */
    BookManage.onDetailBookManage = function (data) {
        func.open({
            title: '查看船员信息',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/bookManage/bookManage_detail?id=' + data.id,
            tableId: BookManage.tableId
        });
    };

    /**
     * 弹出预览书籍信息
     */
    BookManage.onPreviewBookManage = function () {
        layer.open({
            type:2,
            title:false,
            area:['1100px','700px'],
            shade:0.8,
            closeBtn:1,
            shadeClose:true,
            content:"././common/images/book.txt"
        })
    };


    /**
     * 点击删除船员信息
     *
     * @param data 点击按钮时候的行数据
     */
    BookManage.onDeleteBookManage = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/bookManage/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(BookManage.tableId);
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
