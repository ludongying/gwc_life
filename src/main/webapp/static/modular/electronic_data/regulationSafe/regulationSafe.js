layui.use(['layer', 'form', 'table', 'ztree', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ZTree = layui.ztree;
    var $ax = layui.ax;
    var func = layui.func;

    /**
     * 法律法规/航线安全管理
     */
    var RegulationSafe = {
        tableId: "regulationSafeTable",
        condition: {
            treeId: "",
            regulationSafeName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    RegulationSafe.initColumn = function () {
        return [[
            {title: 'id', field: 'id', hide: true},
            {field: 'fileName', hide: true},
            {title: '序号', type: 'numbers'},
            {title: '分类名称', field: 'lawRegularName', align: "center"},
            {title: '文档名称', field: 'name', align: "center"},
            {
                title: '创建时间',
                field: 'createDate',
                align: "center",
                templet: "<div>{{layui.util.toDateString(d.createDate, 'yyyy-MM-dd HH:mm:ss')}}</div>"
            },
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    table.render({
        elem: '#' + RegulationSafe.tableId,
        url: Feng.ctxPath + '/regulationSafe/list',
        where:{
            type:'REGULATIONS'
        },
        page: true,
        height: "full-97",
        cellMinWidth: 100,
        cols: RegulationSafe.initColumn()
    });

    /**
     * 左侧搜索
     */
    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        RegulationSafe.search();
    });
    // 重置按钮点击事件
    $('#btnReset').click(function () {
        RegulationSafe.btnReset();
    });

    /**
     * 右侧操作
     */
    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        RegulationSafe.openAddRegulationSafe();
    });

    /**
     * 工具条点击事件
     */
    table.on('tool(' + RegulationSafe.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'preview') {
            RegulationSafe.onPreviewRegulationSafe(data);
        } else if (layEvent === 'delete') {
            RegulationSafe.onDeleteRegulationSafe(data);
        }
    });


    /**
     * 点击搜索按钮
     */
    RegulationSafe.search = function () {
        var queryData = {};
        queryData['regulationSafeName'] = $("#regulationSafeName").val().trim();
        queryData['lawRegularId'] = RegulationSafe.condition.treeId;
        table.reload(RegulationSafe.tableId, {where: queryData});
    };

    /**
     * 重置查询条件
     */
    RegulationSafe.btnReset = function () {
        $("#regulationSafeName").val("");
    };

    /**
     * 弹出增加法律法规/航线安全
     */
    RegulationSafe.openAddRegulationSafe = function () {
        func.open({
            title: '增加法律法规',
            area: ['800px', '500px'],
            content: Feng.ctxPath + '/regulationSafe/navigationSafety_add?treeId=' + RegulationSafe.condition.treeId,
            tableId: RegulationSafe.tableId
        });
    };

    /**
     * 预览
     */
    RegulationSafe.onPreviewRegulationSafe = function (data) {
        func.open({
            title: '预览',
            maxmin: true,
            content: Feng.ctxPath + '/system/previewPdf?fileName=' + data.fileName
        });
    };


    /**
     * 点击删除法律法规/航线安全
     *
     * @param data 点击按钮时候的行数据
     */
    RegulationSafe.onDeleteRegulationSafe = function (data) {
        Feng.confirm("是否删除法律法规《" + data.name + "》吗?", function () {
            if (deleteFile(data.fileName)) {
                var ajax = new $ax(Feng.ctxPath + "/regulationSafe/delete", function (data) {
                    if (data.success) {
                        Feng.success("删除成功!");
                        table.reload(RegulationSafe.tableId);
                    } else {
                        Feng.error(data.message);
                    }
                }, function (data) {
                    Feng.error("删除失败!" + data.message + "!");
                });
                ajax.set("regulationSafeId", data.id);
                ajax.start();
            }
            return false
        });
    };


    function deleteFile(fileName) {
        var deleteStatus = false;
        $.ajax({
            url: Feng.ctxPath + '/system/deleteFile',
            type: "GET",
            async: false,
            data: {
                fileName: fileName
            },
            success: function (res) {
                if (res.CODE === 200) {
                    deleteStatus = true;
                }
            }
        });
        return deleteStatus;
    }


    RegulationSafe.onClickTree = function (e, treeId, treeNode) {
        RegulationSafe.condition.treeId = treeNode.id;
        RegulationSafe.search();
    };

    //初始化左侧部门树
    var ztree = new $ZTree("deptTree", "/dict/getDictTreeByDictTypeCode?dictTypeCode=LAWS_REGULATION");
    ztree.bindOnClick(RegulationSafe.onClickTree);
    ztree.init();

});
