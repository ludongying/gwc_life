/**
 * 物资信息编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;



    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //表单校验
    loadVerify(form);

    //初始化物资信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/munitionInfo/detail/" + Feng.getUrlParam("munitionInfoId"));
    var result = ajax.start();
    form.val('munitionInfoForm',result);
    $('#unit').text(result.unit);

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/munitionInfo/update", function (data) {
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