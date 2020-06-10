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
    var TvSeriesDetail = {
        tableId: "tvSeriesDetailTable",
        condition: {
            tvSeriesName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    TvSeriesDetail.initColumn = function () {
        return [[
            {title: '序号', type:'numbers'},
            {title: '集数', field: 'num', align: "center"},
            {title: '视频名称', field: 'tvName', align: "center"},
            {title: '操作', toolbar: '#tableBar', align: 'center'}
        ]];
    };



        var tableResult = table.render({
            elem: '#' + TvSeriesDetail.tableId,
            // url: Feng.ctxPath + '/tvSeriesDetail/list?tvSeriesMainId=' + $('#mainId').text(),
            page: true,
            height: "full-300",
            cellMinWidth: 100,
            cols: TvSeriesDetail.initColumn(),
            data: [
                {
                    "num": "第1集",
                    "tvName": "XXXXXX_01.mp4"
                },
                {
                    "num": "第2集",
                    "tvName": "XXXXXX_02.mp4"
                }]
        });





    // 增加入库物资提交事件
    form.on('submit(btnAdd)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/tvSeriesDetail/add", function (data) {
            if (data.success) {
                Feng.success("增加成功!");
                //admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
                table.reload(TvSeriesDetail.tableId);
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

    // // 修改入库物资提交事件
    // form.on('submit(btnEdit)', function (data) {
    //     var ajax = new $ax(Feng.ctxPath + "/tvSeriesDetail/update", function (data) {
    //         if (data.success) {
    //             Feng.success("编辑成功!");
    //             //admin.putTempData('formOk', true);//传给上个页面，刷新table用
    //             admin.closeThisDialog();//关掉对话框
    //             table.reload(TvSeriesDetail.tableId);
    //         } else {
    //             Feng.error(data.message);
    //         }
    //     }, function (data) {
    //         Feng.error("编辑失败!" + data.message + "!");
    //     });
    //     ajax.set(data.field);
    //     ajax.start();
    //     return false;
    // });

    // 删除入库物资提交事件
    form.on('submit(btnDelete)', function (data) {
        Feng.confirm("您确定要删除所选数据吗？", function () {
            // var ajax = new $ax(Feng.ctxPath + "/tvSeriesDetail/delete", function (data) {
            var ajax = new $ax(Feng.ctxPath + "/tvSeriesDetail/delete?tvSeriesId=" + $('#tvSeriesMainId').val().trim()
                + "&tvSeriesDetailId=" + $('#id').val().trim(), function (data) {
                if (data.success) {
                    Feng.success("删除成功!");
                    table.reload(TvSeriesDetail.tableId);
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            // ajax.set("tvSeriesDetailId", data.id);
            ajax.start();
        });
        return false;
    });

});
