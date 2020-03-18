/**
 * 渔船信息详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var func = layui.func;
    var fuiiName = "";

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化渔船信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/fishShip/detail/" + Feng.getUrlParam("fishShipId"));
    var result = ajax.start();
    $('#fileName').val(result.fileName);

    fuiiName = result.fullName;
    form.val('fishShipForm',result);

    $('#preview').click(function () {
        func.open({
            title: '预览',
            maxmin: true,
            content: Feng.ctxPath + '/system/previewPdf?fileName=' + fuiiName
        });
    });

});