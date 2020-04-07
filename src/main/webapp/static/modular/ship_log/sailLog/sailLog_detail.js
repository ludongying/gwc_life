/**
 * 航行日志详情对话框
 */

document.write("<script language=javascript src='/modular/lawRecord/law_record_util.js'></script>");
layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

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

    //初始化航行日志的详情数据
    var ajax = new $ax(Feng.ctxPath + "/sailLog/detail/" + Feng.getUrlParam("sailLogId"));
    var result = ajax.start();
    result.startTime= (new Date(result.startTime)).format("yyyy-MM-dd hh:mm:ss");
    result.endTime = (new Date(result.endTime)).format("yyyy-MM-dd hh:mm:ss");
    form.val('sailLogForm',result);


});