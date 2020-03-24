/**
 * 航行日志编辑对话框
 */
document.write("<script language=javascript src='/modular/lawRecord/law_record_util.js'></script>");
layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    laydate.render({
        elem: '#startTime',
        type: 'datetime',
        trigger: 'click'
    });
    laydate.render({
        elem: '#endTime',
        type: 'datetime',
        trigger: 'click'
    });


    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化航行日志的详情数据
    var ajax = new $ax(Feng.ctxPath + "/sailLog/detail/" + Feng.getUrlParam("sailLogId"));
    var result = ajax.start();
    result.startTime= (new Date(result.startTime)).format("yyyy-MM-dd hh:mm:ss");
    result.endTime = (new Date(result.endTime)).format("yyyy-MM-dd hh:mm:ss");
    form.val('sailLogForm',result);

    //执法船信息获取
    $.ajax({
        url: Feng.ctxPath + '/ship/listShips/',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#shipId').val(item.id);
                $('#shipName').val(item.name);
            })
            form.render('input');//表单渲染 把内容加载进去
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/sailLog/update", function (data) {
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

});