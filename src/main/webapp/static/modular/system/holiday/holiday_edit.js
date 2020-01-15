/**
 * 假日修改对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    laydate.render({
        elem: '#createTime',
        type: 'datetime'
    });
    laydate.render({
        elem: '#updateTime',
        type: 'datetime'
    });


    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化假日的详情数据
    var ajax = new $ax(Feng.ctxPath + "/holiday/detail/" + Feng.getUrlParam("holidayId"));
    var result = ajax.start();
    form.val('holidayForm',result);


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/holiday/update", function (data) {
            Feng.success("修改成功!");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });
});