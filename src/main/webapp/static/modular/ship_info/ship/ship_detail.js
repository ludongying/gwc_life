/**
 * 执法船信息管理详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    //初始化执法船信息管理的详情数据
    var ajax = new $ax(Feng.ctxPath + "/ship/detail/" + Feng.getUrlParam("shipId"));
    var result = ajax.start();
    form.val('shipForm',result);

    //船籍获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/selectDictListByDictType?code=BOAT_NATION',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#nationality').append(new Option(item.name, item.sort));//往下拉菜单里添加元素
            })
            $('#nationality').val(result.nationality);
            form.render('select');//表单渲染 把内容加载进去

        }
    });

    //船舶类型获取下拉框
    $.ajax({
        url: Feng.ctxPath + '/dict/selectDictListByDictType?code=BOAT_TYPE',
        dataType: 'json',
        type: 'get',
        success: function (data) {
            $.each(data, function (index, item) {
                $('#type').append(new Option(item.name, item.sort));//往下拉菜单里添加元素
            })
            $('#type').val(result.type);
            form.render('select');//表单渲染 把内容加载进去

        }
    });



});