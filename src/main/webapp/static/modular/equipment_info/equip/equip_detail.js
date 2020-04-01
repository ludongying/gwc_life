/**
 * 设备信息详情对话框
 */


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

    // //设备状态下拉框
    // $.ajax({
    //     url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=EQUIPMENT_STATE',
    //     dataType: 'json',
    //     type: 'get',
    //     success: function (data) {
    //         $.each(data, function (index, item) {
    //             $('#state').append(new Option(item.name, item.id));//往下拉菜单里添加元素
    //         })
    //         $('#state').val(result.state);
    //         form.render('select');//表单渲染 把内容加载进去
    //     }
    // });

    //初始化设备信息的详情数据
    var ajax = new $ax(Feng.ctxPath + "/equip/detail/" + Feng.getUrlParam("equipId"));
    var result = ajax.start();
    form.val('equipForm',result);



});