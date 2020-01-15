/**
 * 部门查看对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化部门的详情数据
    var ajax = new $ax(Feng.ctxPath + "/dept/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('deptForm',result);


});