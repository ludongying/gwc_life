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
 * 用户增加对话框
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

    if (Feng.getUrlParam("deptId") != "") {
        if (Feng.getUrlParam("deptId") == 0) {
            $("#deptId").val(Feng.getUrlParam("deptId"));
            $("#deptName").val("顶级");
        } else {
            $.ajax({
                url:Feng.ctxPath + "/dept/getDeptById?deptId=" + Feng.getUrlParam("deptId"),
                dataType:'json',
                type:'post',
                success:function(data){
                    $("#deptId").val(Feng.getUrlParam("deptId"));
                    $("#deptName").val(data.fullName);
                }
            })
        }
    }


    // 添加表单验证方法
    form.verify({
        psw: [/^[\S]{6,12}$/, '密码必须6到12位，且不能出现空格'],
        repsw: function (value) {
            if (value !== $('#userForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }
    });

    //获取下拉框分组多选下拉框
    $.ajax({
        url: Feng.ctxPath + '/position/listPositions?ids=',
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
    // admin.iframeAuto();

    //window.parent.$('#name').val("");
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
        var ajax = new $ax(Feng.ctxPath + "/user/add", function (data) {
            if (data.success) {
                Feng.success("增加成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("增加失败!" + data.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });
});