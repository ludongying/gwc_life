/**
 * 执法船信息管理详情对话框
 */


layui.use(['layer', 'form', 'admin', 'ax', 'carousel', 'func'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var carousel = layui.carousel;
    var func = layui.func;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //图片轮播
    var ins =  carousel.render({
        elem: '#imgBoat'
        , width: '90%'
        , height: '240px'
        , arrow: 'always'
        , indicator: 'inside'
    });

    //初始化执法船信息管理的详情数据
    var ajax = new $ax(Feng.ctxPath + "/ship/detailFirst/");
    var result = ajax.start();
    // form.val('shipForm',result);
    $('#id').val(result.id);
    $('#name').text(result.name);
    $('#shipCode').text(result.shipCode);
    $('#owner').text(result.owner);
    $('#nationality').text(result.nationalityDesp);
    $('#type').text(result.typeDesp);
    $('#length').text(result.length + "米");
    $('#width').text(result.width + "米");
    $('#deep').text(result.deep + "米");
    $('#material').text(result.material);
    $('#tonnage').text(result.tonnage + "吨");
    $('#area').text(result.area);
    $('#gunwaleCount').text(result.gunwaleCount + "毫米");
    $('#peopleNum').text(result.peopleNum + " 人");
    $('#mainPower').text(result.mainPower + " kW");
    $('#manufacturer').text(result.manufacturer);
    $('#finishDate').text(result.finishDate);
    $('#remark').text(result.remark);
    // 证书信息
    var url = Feng.ctxPath + '/certificate?id=' + result.certificateId;
    // var url = Feng.ctxPath + '/certificate/certificate_list?id=' + result.certificateId + '&htmltype=ship';
    if(result.certificateId === null || result.certificateId === ''){
        $('#certificateId').append('<a style="color: #01AAED;" href="' + url + '">共0张</a>');
    }
    else {
        var certificateStrs = result.certificateId.split(",");
        //alert(certificateStrs);
        $('#certificateId').append('<a style="color: #01AAED;" href="' + url + '">共'+ certificateStrs.length + '张</a>');
    }
    //加载船舶图像轮播
    var images = result.image.split(",");
    var count = 0;
    $.each(images, function (index, item) {
        $('#image').append('<div style="text-align :center"><img class="img-thumbnail" src=' + item + '></div>');
        // count = count + 1;
        // if(count%2!=0)
        // {
        //     $('#image').append('<div style="text-align :center"><img class="img-thumbnail" src=' + item + '>');
        // }
        // else {
        //     $('#image').append('<img class="img-thumbnail" src=' + item + '></div>');
        // }
    })
    ins.reload({
        elem: '#imgBoat'
        , width: '90%'
        , height: '240px'
        , arrow: 'always'
        , indicator: 'inside'
    });
    /**
     * 点击编辑执法船信息管理
     */
    $('#edit').click(function () {
        // alert($('#id').val());
        func.open({
            title: '编辑执法船信息',
            content: Feng.ctxPath + '/ship/ship_edit?shipId=' + $('#id').val()
            // tableId: Ship.tableId
        });
    });

    // //船籍获取下拉框
    // $.ajax({
    //     url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=BOAT_NATION',
    //     dataType: 'json',
    //     type: 'get',
    //     success: function (data) {
    //         $.each(data, function (index, item) {
    //             $('#nationality').append(new Option(item.name, item.id));//往下拉菜单里添加元素
    //         })
    //         $('#nationality').val(result.nationality);
    //         form.render('select');//表单渲染 把内容加载进去
    //
    //     }
    // });
    //
    // //船舶类型获取下拉框
    // $.ajax({
    //     url: Feng.ctxPath + '/dict/getDictListByDictTypeCode?dictTypeCode=BOAT_TYPE',
    //     dataType: 'json',
    //     type: 'get',
    //     success: function (data) {
    //         $.each(data, function (index, item) {
    //             $('#type').append(new Option(item.name, item.id));//往下拉菜单里添加元素
    //         })
    //         $('#type').val(result.type);
    //         form.render('select');//表单渲染 把内容加载进去
    //
    //     }
    // });


});