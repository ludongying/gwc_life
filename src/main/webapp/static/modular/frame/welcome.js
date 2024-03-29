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

    //获取天气信息
    $.ajax({
        type: 'GET',
        url: 'https://www.tianqiapi.com/api/',
        data: 'version=v1&city=天津&appid=23035354&appsecret=8YvlPNrz',
        dataType: 'JSON',
        error: function () {
            alert('网络错误');
        },
        success: function (res) {
            $('#box').append('<li>City: ' + res.city + '</li>');
            $('#box').append('<li>Weather: ' + res.data[0].wea + '</li>');
            $('#box').append('<li>Tips: ' + res.data[0].air_tips + '</li>');
            // 遍历数组
            for (var i = 0; i < res.data[0].hours.length; i++) {
                $('#hours').append('<li>' + (i + 1) + ': 时间: ' + res.data[0].hours[i].day + ' 气温: ' + res.data[0].hours[i].tem + ' </li >');
            }
        }
    });
});