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
     * 证书信息管理
     */
    var Certificate = {
        tableId: "certificateTable",
        condition: {
            certificateName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Certificate.initColumn = function () {
        return [[
            {title: '', field: 'id', align: "center", hide:true},
            {title: '证书编码', field: 'certificateId', align: "center"},
            {title: '证书名称', field: 'name', align: "center"},
            {title: '归属类型', field: 'ownerTypeName', align: "center"},
            {title: '证书类型', field: 'certificateTypeName', align: "center"},
            {title: '签发机构', field: 'issuer', align: "center"},
            {title: '签发日期', field: 'issueDate', align: "center", templet: "<div>{{layui.util.toDateString(d.issueDate, 'yyyy-MM-dd')}}</div>"},
            {title: '到期日期', field: 'outDate', align: "center", templet: "<div>{{layui.util.toDateString(d.outDate, 'yyyy-MM-dd')}}</div>"},
            {title: '存放地点', field: 'storePlace', align: "center"},
            {title: '是否常用', field: 'isOften', align: "center", templet: function (d) {
                    if (d.isOften === 0)
                        return "<span class='layui-badge layui-bg-blue'>常用</span></b>";
                    else if(d.isOften === 1)
                        return "<span class='layui-badge layui-bg-orange'>不常用</span></b>";
                    else
                        return "</b>";
                }},
            {title: '窗口期', field: 'windowPhase', align: "center"},
            {title: '责任部门', field: 'responseDept', align: "center"},
            {title: '发证地点', field: 'certificatePlace', align: "center"},
            {title: '联系方式', field: 'contact', align: "center"},
            {title: '附件', field: 'attachment', align: "center", hide: true},
            {title: '备注', field: 'remark', align: "center", hide: true},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };



    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Certificate.tableId,
        // url: Feng.ctxPath + '/certificate/list/'+ Feng.getUrlParam("id") + '&' + + Feng.getUrlParam("htmltype"),
        url: Feng.ctxPath + '/certificateShip/list?ids='+ $('#ids').val(),
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: Certificate.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 关闭页面
    $('#btnBack').click(function () {
        window.location.href = Feng.ctxPath + "/ship";
    });
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Certificate.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        Certificate.btnReset();
    });


    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Certificate.openAddCertificate();
    });


    /**
     * 工具条点击事件
     */
    table.on('tool(' + Certificate.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Certificate.onEditCertificate(data);
        } else if (layEvent === 'delete') {
            Certificate.onDeleteCertificate(data);
        } else if (layEvent === 'detail') {
            Certificate.onDetailCertificate(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    Certificate.search = function () {
        var queryData = {};
        queryData['certificateName'] = $("#certificateName").val().trim();
        table.reload(Certificate.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    Certificate.btnReset = function () {
        $("#certificateName").val("");
    };

    /**
     * 弹出增加证书信息
     */
    Certificate.openAddCertificate = function () {
        func.open({
            title: '增加证书信息',
            area: ['1000px', '710px'],
            content: Feng.ctxPath + '/certificateShip/certificate_add?personId='+$('#personId').val().trim(),
            tableId: Certificate.tableId
        });
    };

    /**
     * 点击编辑证书信息
     */
    Certificate.onEditCertificate = function (data) {
        func.open({
            title: '编辑证书信息',
            area: ['1000px', '710px'],
            content: Feng.ctxPath + '/certificateShip/certificate_edit?certificateId=' + data.id,
            tableId: Certificate.tableId
        });
    };

    /**
     * 点击查看证书信息
     */
    Certificate.onDetailCertificate = function (data) {
        func.open({
            title: '查看证书信息',
            area: ['1000px', '710px'],
            content: Feng.ctxPath + '/certificateShip/certificate_detail?certificateId=' + data.id,
            tableId: Certificate.tableId
        });
    };


    /**
     * 点击删除证书信息
     *
     * @param data 点击按钮时候的行数据
     */
    Certificate.onDeleteCertificate = function (data) {
        Feng.confirm("是否删除证书信息《" + data.name + "》吗?", function () {
            var ajax = new $ax(Feng.ctxPath + '/certificateShip/delete?personId='+$('#personId').val().trim(), function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(Certificate.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("certificateId", data.id);
            ajax.start();
        });
    };

});
