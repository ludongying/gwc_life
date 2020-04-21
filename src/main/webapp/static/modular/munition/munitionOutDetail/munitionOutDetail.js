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
     * 物资出库详情管理
     */
    var MunitionOutDetail = {
        tableId: "munitionOutDetailTable",
        condition: {
            munitionName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    MunitionOutDetail.initColumn = function () {
        return [[
            {title: '出入库物资列表编码', field: 'id', align: "center", hide: true},
            {title: '物资表id', field: 'munitionId', align: "center", hide: true},
            {title: '物资名称', field: 'munitionName', align: "center"},
            {title: '物资编码', field: 'code', align: "center"},
            {title: '仓库id', field: 'depotId', align: "center", hide: true},
            {title: '仓库名称', field: 'depotName', align: "center"},
            {title: '数量', field: 'totalNum', align: "center"},
            {title: '单位', field: 'unit', align: "center"},
            {title: '操作', toolbar: '#tableBar', minWidth: 280, align: 'center', hide: true}
        ]];
    };

    /**
     * 初始化按钮
     */
    if ($('#status').val() === '1' || $('#status').val() === '2' || $('#status').val() === '4') {
        $("#munitionDiv").attr("style", "display:none;");
        // 渲染表格
        var tableResult = table.render({
            elem: '#' + MunitionOutDetail.tableId,
            url: Feng.ctxPath + '/munitionOutDetail/list?munitionMainId=' + $('#mainId').text(),
            page: true,
            height: "full-97",
            cellMinWidth: 100,
            cols: MunitionOutDetail.initColumn()
        });
    } else {
        var tableResult = table.render({
            elem: '#' + MunitionOutDetail.tableId,
            url: Feng.ctxPath + '/munitionOutDetail/list?munitionMainId=' + $('#mainId').text(),
            page: true,
            height: "full-180",
            cellMinWidth: 100,
            cols: MunitionOutDetail.initColumn()
        });
    }


    //监听表格行单击事件
    table.on('row(munitionOutDetailTable)', function (obj) {
        var dataChecked = obj.data;
        $('#id').val(dataChecked.id);
        $('#munitionName').val(dataChecked.munitionName);
        // //物资编码随物资名称联动（未有select事件时）
        munitionNameFilter();
        $('#depotId').val(dataChecked.depotId);
        $('#totalNum').val(dataChecked.totalNum);
        $('#unit').text(dataChecked.unit);
        form.render('select');//表单渲染
        //标注选中样式
        obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
    });

    //物资名称下拉框
    $.ajax({
        url: Feng.ctxPath + '/munitionInfo/listByType?type=' + $('#type').val(),
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
    layui.use('form', function () {
        form.on('select(nameFilter)', function (data) {
            $('#munitionId').empty();
            //物资规格下拉框初始化
            $.ajax({
                url: Feng.ctxPath + '/munitionInfo/listByName?name=' + $('#munitionName').val(),
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    $.each(data, function (index, item) {
                        $('#munitionId').append(new Option(item.code, item.id));//往下拉菜单里添加元素
                    })
                    //物资规格改变，物资名称重新加载
                    munitionSpecFilter();
                    form.render('select');//表单渲染
                }
            });
        });
    });

    //当物资名称赋值时，未启动select事件，则物资联动事件
    function munitionNameFilter() {
        //物资规格下拉框初始化
        $('#munitionId').empty();
        $.ajax({
            url: Feng.ctxPath + '/munitionInfo/listByName?name=' + $('#munitionName').val(),
            dataType: 'json',
            type: 'get',
            success: function (data) {
                $.each(data, function (index, item) {
                    $('#munitionId').append(new Option(item.code, item.id));
                })
                //$('#munitionId').val(munitionId);
                form.render('select');//表单渲染
            }
        });
    }

    //物资编码下拉框联动(物资单位初始化）
    layui.use('form', function () {
        form.on('select(specFilter)', function (data) {
            //单位初始化
            $.ajax({
                url: Feng.ctxPath + '/munitionInfo/detail/' + $('#munitionId').val(),
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    $('#unit').text(data.unit);
                    $('#store').text(data.store);
                    $('#unit_store').text(data.unit);
                }
            });
        });
    });

    //当物资规格赋值时，未启动select事件，则单位联动事件
    function munitionSpecFilter() {
        $.ajax({
            url: Feng.ctxPath + '/munitionInfo/detail/' + $('#munitionId').val(),
            dataType: 'json',
            type: 'get',
            success: function (data) {
                $('#unit').text(data.unit);
                $('#store').text(data.store);
                $('#unit_store').text(data.unit);
            }
        });
    }

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

    // 增加出库物资提交事件
    form.on('submit(btnAdd)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/munitionOutDetail/add", function (data) {
            if (data.success) {
                Feng.success("增加成功!");
                //admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
                table.reload(MunitionOutDetail.tableId);
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

    // 修改出库物资提交事件
    form.on('submit(btnEdit)', function (data) {
        if ($('#totalNum').val() <= $('#store').text()) {
            var ajax = new $ax(Feng.ctxPath + "/munitionOutDetail/update", function (data) {
                if (data.success) {
                    Feng.success("编辑成功!");
                    //admin.putTempData('formOk', true);//传给上个页面，刷新table用
                    admin.closeThisDialog();//关掉对话框
                    table.reload(MunitionOutDetail.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("编辑失败!" + data.message + "!");
            });
            ajax.set(data.field);
            ajax.start();
        } else {
            Feng.error("库存数量小于出库数量，请重新输入出库数量！");
        }
        return false;
    });

    // 删除出库物资提交事件
    form.on('submit(btnDelete)', function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            // var ajax = new $ax(Feng.ctxPath + "/munitionOutDetail/delete", function (data) {
            var ajax = new $ax(Feng.ctxPath + "/munitionOutDetail/delete?munitionOutId=" + $('#munitionMainId').val().trim()
                + "&munitionOutDetailId=" + $('#id').val().trim(), function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(MunitionOutDetail.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            // ajax.set("munitionOutDetailId", data.id);
            ajax.start();
        });
        return false;
    });

});