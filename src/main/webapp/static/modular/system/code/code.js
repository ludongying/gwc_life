var MenuInfoDlg = {
    data: {
        pid: "",
        pcodeName: ""
    }
};

layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var table = layui.table;
    var form = layui.form;

    /**
     * 系统管理--角色管理
     */
    var Code = {
        tableId: "dbTable",
        seItem: null		//选中的条目
    };

    /**
     * 初始化表格的列
     */
    Code.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {title: '表名称', field: 'tableName', sort: true, align: "center"},
            {title: '表名称注释', field: 'tableComment', sort: true, align: "center"}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Code.tableId,
        url: Feng.ctxPath + '/code/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Code.initColumn()
    });

    $("#business span").css({"width": "200px", "text-align": "center"});


    // 点击父级菜单
    $('#pcodeName').click(function () {
        var formName = encodeURIComponent("parent.MenuInfoDlg.data.pcodeName");
        var formId = encodeURIComponent("parent.MenuInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/menu/selectMenuTreeList");

        layer.open({
            type: 2,
            title: '父级菜单',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#pid").val(MenuInfoDlg.data.pid);
                $("#pcodeName").val(MenuInfoDlg.data.pcodeName);
            }
        });
    });


    form.on('submit(btnSubmit)', function (data) {
        var arr = new Array();
        var tableNames = "";
        //获取所选列表值
        var checkRows = table.checkStatus(Code.tableId);
        //获取checkbox[name='like']的值
        $("input:checkbox[name='businessModules']:checked").each(function (i) {
            arr[i] = $(this).val();
        });
        data.field.businessModules = arr.join(",");


        if (checkRows.data.length === 0) {
            Feng.error("请选择要生成的数据看表");
        } else if (arr.length === 0) {
            Feng.error("请选择要生成的业务代码");
        } else {
            for (var i = 0; i < checkRows.data.length; i++) {
                tableNames += checkRows.data[0]["tableName"] + ",";
            }
            var ajax = new $ax(Feng.ctxPath + "/code/generate", function (data) {
                if (data.success == true) {
                    Feng.success("生成成功！");
                } else {
                    Feng.success(data.message);
                }
            }, function (data) {
                Feng.error("生成失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.set("tableNames", tableNames);
            ajax.start();
        }
    });

});
