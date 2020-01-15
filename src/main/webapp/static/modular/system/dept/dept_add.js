/**
 * 角色详情对话框
 */
var DeptInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};

/**
 * 部门添加对话框
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


    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.DeptInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.DeptInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/dept/tree");

        layer.open({
            type: 2,
            title: '父级部门',
            area: ['300px', '300px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                if (DeptInfoDlg.data.pName.length != 0) {
                    $("#pid").val(DeptInfoDlg.data.pid);
                    $("#pName").val(DeptInfoDlg.data.pName);
                }
            }
        });
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/dept/add", function (data) {
            if (data.success == true) {
                Feng.success("添加成功！");

                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

                //关掉对话框
                admin.closeThisDialog();
            } else {
                Feng.success(data.message);
            }

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });
});