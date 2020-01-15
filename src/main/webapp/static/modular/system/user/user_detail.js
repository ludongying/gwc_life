/**
 * 用户查看对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var formSelects = layui.formSelects;


    //获取下拉框分组多选下拉框
    $.ajax({
        url: Feng.ctxPath + '/position/listPositions',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            var content = data.content;
            formSelects.data('selPosition', 'local', {
                arr: content
            });
        }
    });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化用户的详情数据
    var ajax = new $ax(Feng.ctxPath + "/user/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('userForm',result);


});