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
    /**
     * 执法记录管理
     */
    let Instrument = {
        tableId: "instrumentTable",
    };

     initPage();
    function initPage(){
       var  initColumn = function () {
            return [[
                {title: '序号', type: "numbers"},
                {title: '文书模板名称', field: 'name', align: "center"},
                {title: '文书模板地址', field: 'path', align: "center"},
                {title: '文件地址', field: 'filePath', align: "center"},
                {title: '操作', toolbar: '#tableBar', minWidth: 360, align: 'center'}
            ]];
        };
        table.render({
            elem: '#'+Instrument.tableId,
            url: Feng.ctxPath + 'instrument/list?id='+Feng.getUrlParam("id"),
            height: "full-30",
            cellMinWidth: 100,
            cols: initColumn()
        });
    }


    /**
     * 工具条点击事件
     */
    table.on('tool(' + Instrument.tableId + ')', function (obj) {
        let data = obj.data;
        let layEvent = obj.event;
        if (layEvent === 'downTemplate') {
            downFile($,data.path)
        } else if (layEvent === 'change') {
            msg_tip($,"change还未开发");
        } else if (layEvent === 'upload') {
            msg_tip($,"upload还未开发");
        }else if (layEvent === 'down') {
            msg_tip($,"down还未开发");
        }
    });


})