/**
 * dictionary查看对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化dictionary的详情数据
    var ajax = new $ax(Feng.ctxPath + "/dictType/detail/" + Feng.getUrlParam("dictTypeId"));
    var result = ajax.start();
    form.val('sysDictTypeForm',result);


});
