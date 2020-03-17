/**
 * 工作日志记录编辑对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    laydate.render({
        elem: '#recordDate',
        type: 'datetime',
        trigger: 'click'
    });
    laydate.render({
        elem: '#createDate',
        type: 'datetime',
        trigger: 'click'
    });


    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化工作日志记录的详情数据
    var ajax = new $ax(Feng.ctxPath + "/shipWorkLog/detail/" + Feng.getUrlParam("shipWorkLogId"));
    var result = ajax.start();
    form.val('shipWorkLogForm',result);


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/shipWorkLog/update", function (data) {
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

    window.onload = function () {
        // 删除按钮点击事件
        $('#btnDelete').οnclick = function () {
            var delId = document.getElementsByName("id").value;
            // Feng.confirm("是否删除工作日志记录?", function () {
            delData(delId);
            // });
        };
    }


    function delData(delId){
            var ajax = new $ax(Feng.ctxPath + "/shipWorkLog/delete", function (delId) {
                if (data.success) {
                    Feng.success("删除成功!");
                    admin.putTempData('formDelete', true);//传给上个页面，刷新table用
                    admin.closeThisDialog();//关掉对话框
                } else {
                    Feng.error(data.message);
                }
            }, function (data) {
                Feng.error("删除失败!" + data.message + "!");
            });
            ajax.set("shipWorkLogId", data.id);
            ajax.start();
    }

});