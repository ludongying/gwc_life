/**
 * 设备维护详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化设备维护的详情数据
    var ajax = new $ax(Feng.ctxPath + "/equipMaintain/detail/" + Feng.getUrlParam("equipMaintainId"));
    var result = ajax.start();
    form.val('equipMaintainForm',result);



    //设备名称下拉框
    $.ajax({
        url: Feng.ctxPath + '/equip/listByTypeAndName',
        dataType: 'json',
        type: 'post',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#equipId').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#equipId').val(result.equipId);
            form.render('select');//表单渲染 把内容加载进去
        }
    });

    //设备型号下拉框
    $.ajax({
        url: Feng.ctxPath + '/equip/listByTypeAndName',
        dataType: 'json',
        type: 'post',
        success: function (data) {
            $("#specification").empty();//清空下拉框的值
            $.each(data, function (index, item) {
                $('#specification').append(new Option(item.specification, item.specification));// 下拉菜单里添加元素
            });
            $('#specification').val(result.specification);
            layui.form.render("select");//重新渲染
        }
    });

    //工作类型下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=EQUIP_WORK_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#problemType').append(new Option(item.name, item.id));//往下拉菜单里添加元素
            })
            $('#problemType').val(result.problemType);
            form.render('select');//表单渲染
        }
    });

    //负责人员下拉框
    $.ajax({
        url: Feng.ctxPath + '/person/listPersonsByDept?deptId=',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#maintainPerson').append(new Option(item.personName, item.id));//往下拉菜单里添加元素
            })
            $('#maintainPerson').val(result.maintainPerson);
            form.render('select');//表单渲染
        }
    });


});