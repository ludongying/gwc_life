/**
 * 物资入库详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化物资入库的详情数据
    var ajax = new $ax(Feng.ctxPath + "/munitionIn/detail/" + Feng.getUrlParam("munitionInandoutId"));
    var result = ajax.start();
    form.val('munitionInandoutForm',result);


});