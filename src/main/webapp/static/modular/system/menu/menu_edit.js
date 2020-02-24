/**
 * 详情对话框
 */
var MenuInfoDlg = {
    data: {
        pid: "",
        pcodeName: ""
    }
};
/**
 * 菜单编辑对话框
 */
layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'iconPicker'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var iconPicker = layui.iconPicker;

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

    //初始化菜单的详情数据
    var ajax = new $ax(Feng.ctxPath + "/menu/detail/" + Feng.getUrlParam("id"));
    var result = ajax.start();
    form.val('menuForm',result);


    // 点击父级菜单
    $('#pcodeName').click(function () {
        var formName = encodeURIComponent("parent.MenuInfoDlg.data.pcodeName");
        var formId = encodeURIComponent("parent.MenuInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/menu/selectMenuTreeList");

        layer.open({
            type: 2,
            title: '父级菜单',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#pid").val(MenuInfoDlg.data.pid);
                $("#pcodeName").val(MenuInfoDlg.data.pcodeName);
            }
        });
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/menu/update", function (data) {
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


    //初始化图标选择
    iconPicker.render({
        elem: '#icon',
        type: 'fontClass',
        search: true,
        page: true,
        limit: 12,
        click: function (data) {
        }
    });

    iconPicker.checkIcon('iconPicker', result.data.icon);
});