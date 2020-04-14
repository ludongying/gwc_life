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
     * 物资入库管理
     */
    var MunitionIn = {
        tableId: "munitionInTable",
        condition: {
            code: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitionIn.initColumn = function () {
        return [[
            {title: '入库编号', field: 'code', align: "center", templet: function (d) {
                var url = Feng.ctxPath + "/munitionInDetail?code=" + d.code + "&type=" + d.munitionType;
                return '<a style="color: #01AAED;" href="' + url + '">' + d.code + '</a>';
            }},
            {title: '物资类型', field: 'munitionTypeDesp', align: "center"},
            {title: '申请缘由', field: 'content', align: "center"},
            {title: '申请人', field: 'applyPersonDesp', align: "center"},
            {title: '入库人', field: 'inOutPersonDesp', align: "center"},
            {title: '入库日期', field: 'inOutTime', align: "center"},
            {title: '审核状态', field: 'status', align: "center", templet: function (d) {
                if(d.status === 0){
                    return "<div> 已保存 </div>";
                }else if(d.status === 1){
                    return "<div> 已提交 </div>";
                }else if(d.status === 2){
                    return "<div> 已归档 </div>";;
                }else{
                    return "<div></div>";
                }
            }},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MunitionIn.tableId,
        url: Feng.ctxPath + '/munitionIn/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: MunitionIn.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MunitionIn.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        MunitionIn.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MunitionIn.openAddMunitionIn();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + MunitionIn.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MunitionIn.onEditMunitionIn(data);
        } else if (layEvent === 'delete') {
            MunitionIn.onDeleteMunitionIn(data);
        } else if (layEvent === 'inMunition') {
            MunitionIn.onMunitionIn(data);
        }
    });

    /**
     * 点击搜索按钮
     */
    MunitionIn.search = function () {
        var queryData = {};
        queryData['code'] = $("#munitionInName").val().trim();
        table.reload(MunitionIn.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    MunitionIn.btnReset = function () {
        $("#munitionInName").val("");
    };

    /**
     * 弹出增加物资入库
     */
    MunitionIn.openAddMunitionIn = function () {
        func.open({
            title: '增加物资入库',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/munitionIn/munitionIn_add',
            tableId: MunitionIn.tableId
        });
    };

    /**
     * 点击编辑物资入库
     */
    MunitionIn.onEditMunitionIn = function (data) {
        func.open({
            title: '编辑物资入库',
            area: ['1000px', '500px'],
            content: Feng.ctxPath + '/munitionIn/munitionIn_edit?munitionInId=' + data.id,
            tableId: MunitionIn.tableId
        });
    };

    /**
     * 点击入库
     */
    MunitionIn.onMunitionIn = function (data) {
        // func.open({
        //     title: '查看物资入库',
        //     area: ['1000px', '500px'],
        //     content: Feng.ctxPath + '/munitionIn/munitionIn_detail?munitionInId=' + data.id,
        //     tableId: MunitionIn.tableId
        // });
    };


    /**
     * 点击删除物资入库
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionIn.onDeleteMunitionIn = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitionIn/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MunitionIn.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("munitionInId", data.id);
            ajax.start();
        });
    };

});
