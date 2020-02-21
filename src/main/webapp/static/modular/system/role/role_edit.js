var RoleInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};
/**
 * 角色编辑对话框
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
        type: 'datetime',
        trigger: 'click'
    });
    laydate.render({
        elem: '#updateTime',
        type: 'datetime',
        trigger: 'click'
    });


    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化角色的详情数据
    var ajax = new $ax(Feng.ctxPath + "/role/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('roleForm', result);

    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.RoleInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.RoleInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/role/roleTreeList");

        layer.open({
            type: 2,
            title: '父级角色选择',
            area: ['300px', '280px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                if (RoleInfoDlg.data.pName.length != 0) {

                    $("#pid").val(RoleInfoDlg.data.pid);
                    $("#pName").val(RoleInfoDlg.data.pName);
                }
            }
        });
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/role/update", function (data) {
            if (data.success) {
                Feng.success("编辑成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("编辑失败!" + data.responseJSON.message + "!");
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });
});