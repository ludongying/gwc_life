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
     * 物资出库管理
     */
    var MunitionOut = {
        tableId: "munitionOutTable",
        condition: {
            code: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitionOut.initColumn = function () {
        return [[
            {title: '出库编号', field: 'code', align: "center", templet: function (d) {
                var url = Feng.ctxPath + "/munitionOutDetail?code=" + d.code + "&type=" + d.munitionType + "&status=" + d.status;
                return '<a style="color: #01AAED;" href="' + url + '">' + d.code + '</a>';
            }},
            {title: '物资类型', field: 'munitionTypeDesp', align: "center"},
            {title: '申请缘由', field: 'content', align: "center"},
            {title: '申请人', field: 'applyPersonDesp', align: "center"},
            {title: '出库人', field: 'inOutPersonDesp', align: "center"},
            {title: '出库日期', field: 'inOutTime', align: "center"},
            {title: '审核状态', field: 'status', align: "center", templet: function (d) {
                if(d.status === '0'){
                    return "<span class='layui-badge layui-bg-gray'>已保存</span></b>";
                }
                else if(d.status === '1'){
                    return "<span class='layui-badge layui-bg-blue'>已提交</span></b>";
                }
                else if(d.status === '2'){
                    return "<span class='layui-badge layui-bg-green'>出库通过</span></b>";
                }
                else if(d.status === '3'){
                    return "<span class='layui-badge layui-bg-red'>出库驳回</span></b>";
                }
                else if(d.status === '4'){
                    return "<span class='layui-badge layui-bg-green'>审批通过</span></b>";
                }
                else if(d.status === '5') {
                    return "<span class='layui-badge layui-bg-red'>审批驳回</span></b>";
                }
                else{
                    return "</b>";
                }
            }},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MunitionOut.tableId,
        url: Feng.ctxPath + '/munitionOut/list',
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: MunitionOut.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MunitionOut.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        MunitionOut.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MunitionOut.openAddMunitionOut();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + MunitionOut.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MunitionOut.onEditMunitionOut(data);
        } else if (layEvent === 'delete') {
            MunitionOut.onDeleteMunitionOut(data);
        } else if (layEvent === 'approve') {
            MunitionOut.onApprove(data);
        } else if (layEvent === 'out') {
            MunitionOut.onOut(data);
        } else if (layEvent === 'submit') {
            MunitionOut.onSubmit(data);
        } else if (layEvent === 'detail') {
            MunitionOut.onDetailMunitionOut(data);
        }
    });

    /**
     * 点击搜索按钮
     */
    MunitionOut.search = function () {
        var queryData = {};
        queryData['code'] = $("#munitionOutName").val().trim();
        table.reload(MunitionOut.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    MunitionOut.btnReset = function () {
        $("#munitionOutName").val("");
    };

    /**
     * 弹出增加物资出库
     */
    MunitionOut.openAddMunitionOut = function () {
        func.open({
            title: '增加物资出库',
            area: ['1000px', '400px'],
            content: Feng.ctxPath + '/munitionOut/munitionOut_add',
            tableId: MunitionOut.tableId
        });
    };

    /**
     * 点击编辑物资出库
     */
    MunitionOut.onEditMunitionOut = function (data) {
        func.open({
            title: '编辑物资出库',
            area: ['1000px', '400px'],
            content: Feng.ctxPath + '/munitionOut/munitionOut_edit?munitionOutId=' + data.id,
            tableId: MunitionOut.tableId
        });
    };

    /**
     * 点击出库
     */
    MunitionOut.onDetailMunitionOut = function (data) {
        func.open({
            title: '查看物资出库',
            area: ['1000px', '700px'],
            content: Feng.ctxPath + '/munitionOut/munitionOut_detail?munitionOutId=' + data.id,
            tableId: MunitionOut.tableId
        });
    };


    /**
     * 点击删除物资出库
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionOut.onDeleteMunitionOut = function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitionOut/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MunitionOut.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("munitionOutId", data.id);
            ajax.start();
        });
    };

    /**
     * 点击提交物资出库表单
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionOut.onSubmit = function (data) {
        Feng.confirm("您确定要提交出库表单吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitionOut/submit", function (data) {
                if (data.success) {
                    Feng.success("提交成功!");
                    table.reload(MunitionOut.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("提交失败!" + data.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

    /**
     * 点击物资出库同意or驳回
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionOut.onOut = function (data) {
        layer.confirm('请选择出库操作？', {
            btn: ['通过','驳回'], //按钮
        }, function(index){//通过
            // var index = layer.load(0, {shade: false});
            var ajax = new $ax(Feng.ctxPath + "/munitionOut/outApprove", function (data) {
                if (data.success) {
                    Feng.success("出库成功!");
                    layer.close(index);
                    table.reload(MunitionOut.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("出库失败!" + data.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        },function(index){//驳回
            // var index = layer.load(0, {shade: false});
            var ajax = new $ax(Feng.ctxPath + "/munitionOut/outRefused", function (data) {
                if (data.success) {
                    Feng.success("驳回成功!");
                    layer.close(index);
                    table.reload(MunitionOut.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("驳回失败!" + data.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };


    /**
     * 点击审核物资出库表单
     *
     * @param data 点击按钮时候的行数据
     */
    MunitionOut.onApprove = function (data) {
       layer.confirm('请选择审批结果？', {
            btn: ['通过','驳回'], //按钮
        }, function(index){//通过
            // var index = layer.load(0, {shade: false});
            var ajax = new $ax(Feng.ctxPath + "/munitionOut/approve", function (data) {
                if (data.success) {
                    Feng.success("审核成功!");
                    layer.close(index);
                    table.reload(MunitionOut.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("审核失败!" + data.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        },function(index){//驳回
            // var index = layer.load(0, {shade: false});
            var ajax = new $ax(Feng.ctxPath + "/munitionOut/refused", function (data) {
                if (data.success) {
                    Feng.success("驳回成功!");
                    layer.close(index);
                    table.reload(MunitionOut.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("驳回失败!" + data.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        });
    };

});
