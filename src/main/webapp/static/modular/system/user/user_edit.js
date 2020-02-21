/**
 * 用户详情对话框
 */
var UserInfoDlg = {
    data: {
        deptId: "",
        deptName: ""
    }
};

/**
 * 用户编辑对话框
 */
layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var formSelects = layui.formSelects;

    laydate.render({
        elem: '#birthday',
        type: 'date',
        trigger: 'click'
    });
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

    //初始化用户的详情数据
    var ajax = new $ax(Feng.ctxPath + "/user/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('userForm', result);

    //获取下拉框分组多选下拉框
    $.ajax({
        url: Feng.ctxPath + '/position/listPositions?ids='+result.positionId,
        dataType: 'json',
        type: 'get',
        success: function (data) {
            var content = data.content;
            formSelects.data('selPosition', 'local', {
                arr: content
            });
        }
    });


    // 点击部门时
    $('#deptName').click(function () {
        var formName = encodeURIComponent("parent.UserInfoDlg.data.deptName");
        var formId = encodeURIComponent("parent.UserInfoDlg.data.deptId");
        var treeUrl = encodeURIComponent("/dept/tree");

        layer.open({
            type: 2,
            title: '部门选择',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                if (UserInfoDlg.data.deptName.length != 0 ) {
                    $("#deptId").val(UserInfoDlg.data.deptId);
                    $("#deptName").val(UserInfoDlg.data.deptName);
                }
            }
        });
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/user/update", function (data) {
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