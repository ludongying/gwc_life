/**
 * 设备维护编辑对话框
 */


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

    //初始化设备维护的详情数据
    var ajax = new $ax(Feng.ctxPath + "/equipMaintain/detail/" + Feng.getUrlParam("equipMaintainId"));
    var result = ajax.start();
    form.val('equipMaintainForm',result);


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/equipMaintain/update", function (data) {
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