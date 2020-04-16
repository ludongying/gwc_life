/**
 * 执法记录详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'table'], function () {
    var $ = layui.jquery;
    var table = layui.table;

    $(".layui-form input").attr("disabled","disabled");

    // var fileManager=new initFiles($);
    // $("#evidence .demo-down-1").click(function () {
    //        fileManager.downLoad($(this).data("filePath"));
    // });
    //
    // $("#evidence .demo-preview-1").click(function () {
    //     fileManager.preview_img($(this).data("filePath"));
    // });

    /**
     * 渔船信息管理
     */
    var Document = {
        tableId: "documentTable",
        condition: {
            fishShipName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Document.initColumn = function () {
        return [[
            {title: '序号', width: 120, type: 'numbers', align: "center"},
            {title: '文书名称', field: 'code', align: "center"},
            {title: '下载', toolbar: '#tableBar', minWidth: 280, align: 'center'}
        ]];
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Document.tableId,
        url: Feng.ctxPath + '/fishShip/list',
        page: false,
        height: "600",
        cellMinWidth: 100,
        cols: Document.initColumn()
    });

    $('#uploader-list img').on('click', function () {
        layer.photos({
            photos: '#uploader-list',
            shadeClose: false,
            closeBtn: 2,
            anim: 0
        });
    });

    layer.open({
        title:'目录',
        type: 1,
        area: 'auto',
        offset: 'r',
        shade:false,
        skin:'layui-layer-dir',
        resize:false,
        content: $("#anchor_id").html(),

    });

    $(document).on("click",".site-dir li",function () {
        $(".site-dir li").removeClass("bold_font");
        $(this).addClass("bold_font");
    });

    $(document).on("click","#document .doc",function () {
           let path=$(this).data("path");

    });

    $('#evidence img').on('click', function () {
        layer.photos({
            photos: '.uploader-list',
            anim: 0,
            type: 1,
            title:false,
            shade:0.1,
            closeBtn:false,
            shadeClose:true
        });
    })



    $(document).on("mouseenter mouseleave", ".file-iteme", function(event){
        if(event.type === "mouseenter"){
            //鼠标悬浮
            $(this).children(".handle").fadeIn("fast");   //删除按钮
        }else if(event.type === "mouseleave") {
            //鼠标离开
            $(this).children(".handle").hide();
        }
    });

    //下载点击方法
    $(document).on("click", ".file-iteme .layui-icon-download-circle", function(event){
        downFile($,$(this).data("path"));
    });


});