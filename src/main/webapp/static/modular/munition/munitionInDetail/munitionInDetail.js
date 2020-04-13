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
     * 物资入库详情管理
     */
    var MunitionInDetail = {
        tableId: "munitionInDetailTable",
        condition: {
            munitionInDetailName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitionInDetail.initColumn = function () {
        return [[
            {title: '出入库物资列表编码', field: 'id', align: "center", hide:true},
            {title: '物资名称', field: 'munitionName', align: "center"},
            {title: '物资编码', field: 'munitionId', align: "center"},
            {title: '仓库名称', field: 'depotName', align: "center"},
            {title: '数量', field: 'totalNum', align: "center"},
            {title: '单位', field: 'unit', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center', hide:true}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MunitionInDetail.tableId,
        url: Feng.ctxPath + '/munitionInDetail/list',
        page: true,
        height: "full-180",
        cellMinWidth: 100,
        cols: MunitionInDetail.initColumn()
    });

    //物资名称下拉框
    $.ajax({
        url: Feng.ctxPath + '/munitionInfo/listByType?type='+ $('#type').val(),
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#munitionName').append(new Option(item.name, item.name));//往下拉菜单里添加元素
            })
            form.render('select');//表单渲染
        }
    });

    //物资名称下拉框联动
    layui.use('form', function() {
        form.on('select(nameFilter)', function (data) {
            //物资规格下拉框初始化
            $.ajax({
                url: Feng.ctxPath + '/munitionInfo/listByName?name='+ $('#munitionName').val(),
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    $.each(data, function (index, item) {
                        $('#munitionId').append(new Option(item.code, item.id));//往下拉菜单里添加元素
                    })
                    form.render('select');//表单渲染
                }
            });
        });
    });

    //物资规格下拉框联动
    layui.use('form', function() {
        form.on('select(specFilter)', function (data) {
            //单位初始化
            $.ajax({
                url: Feng.ctxPath + '/munitionInfo/detail/'+ $('#munitionId').val(),
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    $('#unit').text(data.unit);
                }
            });
        });
    });

    //初始化仓库下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=DEPOT',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#depotId').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            form.render('select');//表单渲染 把内容加载进去
        }
    });

    // 增加入库物资提交事件
    form.on('submit(btnAdd)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/munitionInDetail/add", function (data) {
            if (data.success) {
                Feng.success("增加成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("增加失败!" + data.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    // 修改入库物资提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/munitionInDetail/update", function (data) {
            if (data.success) {
                Feng.success("编辑成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("编辑失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    // 删除入库物资提交事件
    form.on('submit(btnDelete)', function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            var ajax = new $ax(Feng.ctxPath + "/munitionInDetail/delete", function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MunitionInDetail.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("munitionInDetailId", data.id);
            ajax.start();
        });
    });

    // /**
    //  * 左侧搜索
    //  */
    // // 返回页面
    // $('#btnBack').click(function () {
    //     window.location.href = Feng.ctxPath + "/munitionIn";
    // });
    // // 搜索按钮点击事件
    // $('#btnSearch').click(function () {
    //     MunitionInDetail.search();
    // });
    // // 重置按钮点击事件
    // $('#btnReset').click(function () {
    //     MunitionInDetail.btnReset();
    // });

    // /**
    //  * 右侧操作
    //  */
    // // 添加按钮点击事件
    // $('#btnAdd').click(function () {
    //     MunitionInDetail.openAddMunitionInDetail();
    // });
    //

    // /**
    //  * 工具条点击事件
    //  */
    // table.on('tool(' + MunitionInDetail.tableId + ')', function (obj) {
    //     var data = obj.data;
    //     var layEvent = obj.event;
    //
    //     if (layEvent === 'edit') {
    //         MunitionInDetail.onEditMunitionInDetail(data);
    //     } else if (layEvent === 'delete') {
    //         MunitionInDetail.onDeleteMunitionInDetail(data);
    //     } else if (layEvent === 'detail') {
    //         MunitionInDetail.onDetailMunitionInDetail(data);
    //     }
    // });


    // /**
    //  * 点击搜索按钮
    //  */
    // MunitionInDetail.search = function () {
    //     var queryData = {};
    //     queryData['munitionInDetailName'] = $("#munitionInDetailName").val().trim();
    //     table.reload(MunitionInDetail.tableId, {where: queryData});
    // };
    //
    // /**
    //  * 重置查询条件
    //  */
    // MunitionInDetail.btnReset = function () {
    //     $("#munitionInDetailName").val("");
    // };

    // /**
    //  * 弹出增加物资入库详情
    //  */
    // MunitionInDetail.openAddMunitionInDetail = function () {
    //     func.open({
    //         title: '增加物资入库详情',
    //         area: ['1000px', '500px'],
    //         content: Feng.ctxPath + '/munitionInDetail/munitionInDetail_add',
    //         tableId: MunitionInDetail.tableId
    //     });
    // };
    //
    // /**
    //  * 点击编辑物资入库详情
    //  */
    // MunitionInDetail.onEditMunitionInDetail = function (data) {
    //     func.open({
    //         title: '编辑物资入库详情',
    //         area: ['1000px', '500px'],
    //         content: Feng.ctxPath + '/munitionInDetail/munitionInDetail_edit?munitionInDetailId=' + data.id,
    //         tableId: MunitionInDetail.tableId
    //     });
    // };
    //
    // /**
    //  * 点击查看物资入库详情
    //  */
    // MunitionInDetail.onDetailMunitionInDetail = function (data) {
    //     func.open({
    //         title: '查看物资入库详情',
    //         area: ['1000px', '500px'],
    //         content: Feng.ctxPath + '/munitionInDetail/munitionInDetail_detail?munitionInDetailId=' + data.id,
    //         tableId: MunitionInDetail.tableId
    //     });
    // };
});
