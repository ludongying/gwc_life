layui.use(['layer', 'form', 'table', 'ztree', 'laydate', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var admin = layui.admin;
    var func = layui.func;

    var lay = {
        '$': $,
        'layer': layer,
        'form': form,
        'table': table,
        '$ax': $ax,
        'admin': admin,
        'func': func,
    }
    // initPage();
    function initPage(){
       var  initColumn = function () {
            return [[
                {title: '序号', type: "numbers"},
                {title: '文书模板名称', field: 'lawType', align: "center"},
                {title: '操作', toolbar: '#tableBar', minWidth: 360, align: 'center'}
            ]];
        };
        table.render({
            elem: '#instrumentTable' ,
            url: Feng.ctxPath + '#',
            page: false,
            height: "full-30",
            cellMinWidth: 100,
            cols: initColumn()
        });
    }


})