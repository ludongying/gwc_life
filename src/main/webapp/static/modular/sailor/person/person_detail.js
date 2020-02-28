/**
 * 船员信息详情对话框
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


layui.use(['layer', 'form', 'admin', 'ax', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var formSelects = layui.formSelects;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化船员信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/person/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('personForm',result);

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

});