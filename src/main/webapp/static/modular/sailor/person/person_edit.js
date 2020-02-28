/**
 * 船员信息编辑对话框
 */

/**
 * 用户详情对话框
 */
var UserInfoDlg = {
    data: {
        personId: "",
        personName: "",
        birthday: "",
        sex: "",
        phone: "",
        email: "",
        position_id: ""
    }
};

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

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化船员信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/person/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('personForm',result);

    // 点击用户姓名时
    $('#selUsers').click(function () {
        var formId = encodeURIComponent("parent.UserInfoDlg.data.personId");
        var formName = encodeURIComponent("parent.UserInfoDlg.data.personName");
        var treeUrl = encodeURIComponent("/user/tree");

        layer.open({
            type: 2,
            title: '人员选择',
            area: ['300px', '350px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                if (UserInfoDlg.data.personName.length != 0) {
                    $("#personId").val(UserInfoDlg.data.personId);
                    $("#personName").val(UserInfoDlg.data.personName);

                    //获取并自动填入的用户信息
                    var ajax = new $ax(Feng.ctxPath + "/user/detail/" + UserInfoDlg.data.personId);
                    var result = ajax.start();
                    form.val('personForm',result);

                    //获取岗位下拉框分组多选
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
                }
            }
        });
    });

    //获取岗位下拉框分组多选
    $.ajax({
        url: Feng.ctxPath + '/position/listPositions?ids='+result.position_id,
        dataType: 'json',
        type: 'get',
        success: function (data) {
            var content = data.content;
            formSelects.data('selPosition', 'local', {
                arr: content
            });
        }
    });

    //执法船获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/ship/listShips/',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#belongShip').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#belongShip').val(result.shipId);
            form.render();//表单渲染 把内容加载进去
        }
    });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/person/update", function (data) {
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