layui.use(['layer', 'table', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;

    /**
     * 系统管理--附件管理
     */
    var Enclosure = {
        tableId: "enclosureTable",
        condition: {
            enclosureName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Enclosure.initColumn = function () {
        return [[
            {title: 'id', field: 'id', hide: true},
            {
                title: '图表', field: 'suffix', minWidth: 40, align: "center", templet: function (d) {
                    if (d.suffix == "doc" || d.suffix == "docx")
                        return "<img src='/common/images/file/docx.gif'>";
                    else if (d.suffix == "htm" || d.suffix == "html")
                        return "<img src='/common/images/file/html.gif'>";
                    else if (d.suffix == "pdf")
                        return "<img src='/common/images/file/pdf.gif'>";
                    else if (d.suffix == "png" || d.suffix == "jpeg" || d.suffix == "jpg" || d.suffix == "gif")
                        return "<img src='/common/images/file/jpeg.gif'>";
                    else if (d.suffix == "pptx" || d.suffix == "ppt")
                        return "<img src='/common/images/file/pptx.gif'>";
                    else if (d.suffix == "xls" || d.suffix == "xlsx")
                        return "<img src='/common/images/file/xlsx.gif'>";
                    else if (d.suffix == "txt" || d.suffix == "sql")
                        return "<img src='/common/images/file/txt.gif'>";
                    else
                        return "<img src='/common/images/file/unknow.gif'>";
                }
            },
            {title: '附件名称', field: 'name', sort: true, align: "center"},
            {title: '附件url', field: 'url', sort: true, align: "center"},
            {
                title: '附件大小', field: 'fileSize', sort: true, align: "center", templet: function (d) {
                    if ((d.fileSize / 1048576) > 1)
                        return (d.fileSize / 1048576).toFixed(2) + "MB";
                    else
                        return (d.fileSize / 1024).toFixed(2) + "KB";
                }
            },
            {title: '外键表', field: 'forSurface', sort: true, align: "center"},
            {title: '上传人', field: 'createUser', sort: true, align: "center"},
            {
                title: '上传时间',
                field: 'createTime',
                sort: true,
                align: "center",
                templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd')}}</div>"
            },
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Enclosure.tableId,
        url: Feng.ctxPath + '/enclosure/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Enclosure.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Enclosure.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Enclosure.openAddEnclosure();
    });

    // 工具条点击事件
    table.on('tool(' + Enclosure.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'preview') {
            Enclosure.onPreviewEnclosure(data);
        } else if (layEvent === 'download') {
            Enclosure.onDownloadEnclosure(data);
        } else if (layEvent === 'delete') {
            Enclosure.onDeleteEnclosure(data);
        }
    });

    /**
     * 点击查询按钮
     */
    Enclosure.search = function () {
        var queryData = {};
        queryData['enclosureName'] = $("#enclosureName").val().time();
        table.reload(Enclosure.tableId, {where: queryData});
    };

    /**
     * 弹出添加附件
     */
    Enclosure.openAddEnclosure = function () {
        func.open({
            title: '添加附件',
            content: Feng.ctxPath + '/enclosure/enclosure_add',
            tableId: Enclosure.tableId
        });
    };

    /**
     * 下载附件
     */
    Enclosure.onDownloadEnclosure = function (data) {
        window.location.href = Feng.ctxPath + '/system/downloadFile?file=' + data.url;
    };

    /**
     * 点击预览附件
     */
    Enclosure.onPreviewEnclosure = function (data) {
        window.location.href = Feng.ctxPath + '/enclosure/enclosurePreview?file=' + data.url;
    };

    /**
     * 点击删除附件
     */
    Enclosure.onDeleteEnclosure = function (data) {
        Feng.confirm("是否删除附件 " + data.name + "?", function () {
            var ajax = new $ax(Feng.ctxPath + "/enclosure/delete", function () {
                Feng.success("删除成功!");
                table.reload(Enclosure.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("enclosureId", data.id);
            ajax.start();
        });
    };

});
