/**
 * 岗位增加对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'ztree'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var $ZTree = layui.ztree;


    // 让当前iframe弹层高度适应
    admin.iframeAuto();


    //初始化岗位的详情数据
    var ajax = new $ax(Feng.ctxPath + "/position/detail/" + Feng.getUrlParam("positionId"));
    var result = ajax.start();
    if (result.dataScope == 2)
        $("#authDataScope").show();
    form.val('positionForm',result);


    //初始化部门树
    var setting = {
        check: {enable: true,chkboxType: {"Y": "ps","N": "ps"}},
        data: {simpleData: {enable: true}}
    };
    var ztree = new $ZTree("deptTree", "/dept/tree");
    ztree.setSettings(setting);
    ztree.init();


    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var menuIds = Feng.zTreeCheckedNodes("deptTree");
        var ajax = new $ax(Feng.ctxPath + "/position/update", function (data) {
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
        ajax.set("menuIds", menuIds);
        ajax.set(data.field);
        ajax.start();
    });


    /**
     * 自定义权限范围显示组织结构
     */
    form.on('select', function(data){
        if (data.value == 2) {
            $("#authDataScope").show();
        } else {
            $("#authDataScope").hide();
        }
    });


});