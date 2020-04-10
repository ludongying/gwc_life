layui.use(['form', 'table', 'element', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var table = layui.table;
    var $ax = layui.ax;
    var laydate = layui.laydate;
    var element = layui.element;

    //渲染时间选择框
    laydate.render({
        elem: '#calendar',
        position: 'static',
        showBottom: false
    });

    element.on('tab(demo)', function(elem){});

    /**
     * 系统管理--首頁
     */
    var RchengIdnex = {
        tableId: "rchengTable",    //表格id
    };

    /**
     * 初始化表格的列
     */
    RchengIdnex.initColumn = function () {
        return [[
            {title: '用戶id', field: 'id', hide: true, sort: true, style:'border-width: 0 0 1px 0; border-style: solid; border-color: #e6e6e6;'},
            {title: '标题', field: 'name', style:'border-width: 0 1px 1px 0; border-style: solid; border-color: #e6e6e6;'},
            {title: '时间', field: 'createTime', align: "center", width: 110, style:'border-width: 0 0 1px 0; border-style: solid; border-color: #e6e6e6;', templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd')}}</div>"},
        ]];
    };



    // 渲染表格
    var rchengIdnexResult = table.render({
        elem: '#' + RchengIdnex.tableId,
        url: Feng.ctxPath + '/user/list',
        skin: 'nob',
        page: {
            layout: ['prev', 'page', 'next'],
            first: false,
            last: false
        },
        height: "205",
        cellMinWidth: 100,
        cols: RchengIdnex.initColumn()
    });

    /**
     * 系统管理--首頁
     */
    var GongzuoTable = {
        tableId: "gongzuoTable",    //表格id
    };

    /**
     * 初始化表格的列
     */
    GongzuoTable.initColumn = function () {
        return [[
            {title: '用戶id', field: 'id', hide: true, sort: true, style:'border-width: 0 0 1px 0; border-style: solid; border-color: #e6e6e6;'},
            {title: '工作内容', field: 'account', align: "center", style:'border-width: 0 1px 1px 0; border-style: solid; border-color: #e6e6e6;'},
            {title: '时间', field: 'createTime', align: "center", style:'border-width: 0 1px 1px 0; border-style: solid; border-color: #e6e6e6;', width: 110, templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd')}}</div>"},
            {title: '参与人员', field: 'name', align: "center", style:'border-width: 0 0 1px 0; border-style: solid; border-color: #e6e6e6;' , width: 110},
        ]];
    };

    // 渲染表格
    var gongzuoTableResult = table.render({
        elem: '#' + GongzuoTable.tableId,
        url: Feng.ctxPath + '/user/list',
        skin: 'nob',
        page: {
            layout: ['prev', 'page', 'next'],
            first: false,
            last: false
        },
        height: "205",
        cellMinWidth: 100,
        cols: GongzuoTable.initColumn()
    });

    //$("#map").load('../system/map');
});