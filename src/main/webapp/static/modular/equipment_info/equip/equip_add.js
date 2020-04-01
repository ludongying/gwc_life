/**
 * 设备信息增加对话框
 */

/**
 * 设备类型对话框
 */
var MenuInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    laydate.render({
        elem: '#produceDate',
        type: 'date',
        trigger: 'click'
    });

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    if (Feng.getUrlParam("treeId") !== "") {
        $.ajax({
            url: Feng.ctxPath + "/dict/detail/" + Feng.getUrlParam("treeId"),
            dataType: 'json',
            type: 'get',
            success: function (data) {
                //console.log(data);
                $("#type").val(Feng.getUrlParam("treeId"));
                $("#typeDesp").val(data.name);
            }
        })
    }

    // 点击父级菜单
    $('#typeDesp').click(function () {
        var formName = encodeURIComponent("parent.MenuInfoDlg.data.pcodeName");
        var formId = encodeURIComponent("parent.MenuInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/dict/getDictTreeByDictTypeCode?dictTypeCode=EQUIPMENT_TYPE");
        layer.open({
            type: 2,
            title: '设备类型',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#type").val(MenuInfoDlg.data.pid);
                $("#typeDesp").val(MenuInfoDlg.data.pcodeName);
            }
        });
    });

    // //设备状态下拉框
    // $.ajax({
    //     url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=EQUIPMENT_STATE',
    //     dataType: 'json',
    //     type: 'get',
    //     success: function (data) {
    //         $.each(data, function (index, item) {
    //             $('#state').append(new Option(item.name, item.id));//往下拉菜单里添加元素
    //         })
    //         form.render('select');//表单渲染 把内容加载进去
    //     }
    // });


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/equip/add", function (data) {
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