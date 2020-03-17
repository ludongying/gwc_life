// 用common.js必须加上Feng.addCtx("${ctxPath}");
Feng.info = function (info) {
    top.layer.msg(info, {icon: 6});
};
Feng.success = function (info) {
    top.layer.msg(info, {icon: 1});
};
Feng.error = function (info) {
    top.layer.msg(info, {icon: 2});
};
Feng.confirm = function (tip, ensure) {
    top.layer.confirm(tip, {
        skin: 'layui-layer-admin'
    }, function () {
        ensure();
    });
};
Feng.currentDate = function () {
    // 获取当前日期
    var date = new Date();

    // 获取当前月份
    var nowMonth = date.getMonth() + 1;

    // 获取当前是几号
    var strDate = date.getDate();

    // 添加分隔符“-”
    var seperator = "-";

    // 对月份进行处理，1-9月在前面添加一个“0”
    if (nowMonth >= 1 && nowMonth <= 9) {
        nowMonth = "0" + nowMonth;
    }

    // 对月份进行处理，1-9号在前面添加一个“0”
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }

    // 最后拼接字符串，得到一个格式为(yyyy-MM-dd)的日期
    return date.getFullYear() + seperator + nowMonth + seperator + strDate;
};
Feng.getUrlParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    } else {
        return null;
    }
};
Feng.infoDetail = function (title, info) {
    var display = "";
    if (typeof info === "string") {
        display = info;
    } else {
        if (info instanceof Array) {
            for (var x in info) {
                display = display + info[x] + "<br/>";
            }
        } else {
            display = info;
        }
    }
    top.layer.open({
        title: title,
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['950px', '600px'], //宽高
        content: '<div style="padding: 20px;">' + display + '</div>'
    });
};
Feng.zTreeCheckedNodes = function (zTreeId) {
    var zTree = $.fn.zTree.getZTreeObj(zTreeId);
    var nodes = zTree.getCheckedNodes();
    var ids = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        ids += "," + nodes[i].id;
    }
    return ids.substring(1);
};
Feng.closeAllLoading = function () {
    layer.closeAll('loading');
};

// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
    base: Feng.ctxPath + '/common/plugins/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    ax: 'ax/ax',
    ztree: 'ztree/ztree-object',
    func: 'func/func',
    admin: 'admin',
    iconPicker: 'iconPicker/iconPicker',
    webuploader: 'webuploader/webuploader.min'
}).use(['admin'], function () {
    var $ = layui.jquery;
    var admin = layui.admin;

    // 移除loading动画
    setTimeout(function () {
        admin.removeLoading();
    }, window == top ? 600 : 100);

    //注册session超时的操作
    $.ajaxSetup({
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        complete: function (XMLHttpRequest, textStatus) {

            //通过XMLHttpRequest取得响应头，sessionstatus，
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            if (sessionstatus === "timeout") {

                //如果超时就处理 ，指定要跳转的页面
                window.location = Feng.ctxPath + "/sessionError";
            }
        }
    });

});


/**
 * 文件上传 下载 删除 图片预览
 * @param $ layui对象
 * @param upload layui 对象
 * @param fileParam 参数 是否开启上传后 删除 下载 功能
 *        {
 *            delete: true 上传成功后 是否可以删除文件
 *            down: true   上传成功后 是否提供下载功能
 *            preview: true 图片上传成功后预览
 *            ext:  layui 限制文件格式 参考官方
 *            data:[] 后台FileManager-->listFile 接口返回对象
 *        }
 *@param index （一般一个页面多个文件 提交列表会用）id索引 （索引大于0 或者有意义的字符串）
 *             一个页面中出现多个上传框的时候避免id重复设置
 *             使用此项时需要配合layui模板使用
 * 注意：
 *    1文件列表 <tbody id="file_list"></tbody> id必须为 file_list
 *    2选择多文件  <button id="select_list">选择多文件</button> id必须为select_list
 *    3开始上传 <button id="start_upload">选择多文件</button> id必须为start_upload
 *
 */
var initFiles=function ($,upload,fileParam,index){
    var img_type=new Array("png","jpg","jpeg");
    if(!index){
        index='';
    }
    if(fileParam){
        var demoListView = $('#file_list'+index);
        addData(fileParam.data);
        var uploadParam={
            elem: '#select_list'+index
            ,url: '/file/uploadFile'
            ,accept: 'file'
            ,exts:fileParam.exts
            ,size:fileParam.size
            ,multiple: true
            ,auto: false
            ,bindAction: '#start_upload'+index
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){

                    var td_preview;
                    if(result && fileParam.preview && getFileType(file.name)===1){
                        td_preview='<button type="button" style="margin-left: 10px" data-index="" class="layui-btn layui-btn-xs demo-preview">预览</button>'
                    }

                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1024).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button type="button" style="margin-left: 0;margin-right:10px" class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        ,'<button type="button" style="margin-left: 0" class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        , td_preview
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });
                    //预览
                    tr.find('.demo-preview').on('click',function(){
                        var index=$(this).data("index");
                        if(!index){
                            $(this).data("index","1")
                        }
                        var reader = new FileReader();
                        reader.readAsDataURL(file);
                        reader.onload = function() {
                            preview_img(reader.result,index);
                        }
                    })

                    demoListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.success){ //上传成功
                    var tr = demoListView.find('tr#upload-'+ index);
                    var tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;" data-file-path="'+res.content.path+'">已上传</span>');
                    tds.eq(3).html('');
                    if(fileParam.delete){
                        tds.eq(3).append('<button type="button" class="layui-btn layui-btn-xs layui-btn-danger demo-delete-2" data-file-path="'+res.content.path+'">删除</button>');
                    }
                    if(fileParam.down){
                        tds.eq(3).append('<button type="button" class="layui-btn layui-btn-xs layui-btn-normal demo-down-1" data-file-path="'+res.content.path+'">下载</button>');
                    }
                    if(fileParam.preview && res.content.type===1){
                        tds.eq(3).append('<button type="button" class="layui-btn layui-btn-xs layui-btn-normal demo-preview-1" data-file-path="'+res.content.url+'">预览</button>');
                    }
                    addClickEvent(tr);
                    delete this.files[index]; //删除文件队列已经上传成功的文件
                    // saveFilePath(res.content.path);
                    return;
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        }
        var uploadListIns = upload.render(uploadParam);
    }

    function addClickEvent(tr){
        tr.find('.demo-delete-2').on('click', function(){
            $.post(Feng.ctxPath + "/file/deleteFile",{path:$(this).data("filePath")},function(result){});
            tr.remove();
        });
        tr.find('.demo-down-1').on('click', function(){
            downLoad($(this).data("filePath"));
        });
        tr.find('.demo-preview-1').on('click', function(){
            var url = $(this).data("filePath");
            preview_img(url);
        });
    }
    function getFileType(name){
        if(name){
            var suf=name.toString().split(".")[1];
            if(img_type.includes(suf)){
               return 1;
            }
        }
        return 99;

    }
    var preview_img=function(url,index){
        var html='<div><img src="'+url+'"/></div>'
        if(url){
            var area='auto'
            if(index){
                area=['auto','auto'];
            }
            layer.open({
                type: 1,
                area: area,
                offset:'100px',
                title:false,
                closeBtn:false,
                shadeClose:true,
                content: html
            });
        }
    }
    function addData(data){
        if(data){
            var length = data.length;
            if(length>0){
                for(var i=0;i<length;i++){
                    var file=data[i];
                    var td_del='';
                    var td_down='';
                    var td_preview='';
                    if(fileParam.delete){
                        td_del='<button type="button" class="layui-btn layui-btn-xs layui-btn-danger demo-delete-2" data-file-path="'+file.path+'">删除</button>'
                    }
                    if(fileParam.down){
                        td_down='<button type="button" class="layui-btn layui-btn-xs layui-btn-normal demo-down-1" data-file-path="'+file.path+'">下载</button>';
                    }
                    if(fileParam.preview && file.type===1){
                        td_preview='<button type="button" class="layui-btn layui-btn-xs layui-btn-normal demo-preview-1" data-file-path="'+file.url+'">预览</button>';
                }

                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.length/1024).toFixed(1) +'kb</td>'
                        ,'<td><span style="color: #5FB878;" data-file-path="'+file.path+'">已上传</span></td>'
                        ,'<td>'
                        ,td_del
                        ,td_down
                        ,td_preview
                        ,'</td>'
                        ,'</tr>'].join(''));
                    addClickEvent(tr);
                    demoListView.append(tr);
                }

            }
        }
    }
    var downLoad = function(filePath){
        var dlform = document.createElement('form');
        dlform.style = "display:none;";
        dlform.method = 'post';
        dlform.action = Feng.ctxPath + "/file/downFile";
        dlform.target = 'callBackTarget';
        var hdnFilePath = document.createElement('input');
        hdnFilePath.type = 'hidden';
        hdnFilePath.name = 'path';
        hdnFilePath.value = filePath;
        dlform.appendChild(hdnFilePath);
        document.body.appendChild(dlform);
        dlform.submit();
        document.body.removeChild(dlform);
    }
    return{
        preview_img:preview_img,
        downLoad:downLoad
    }

}


function loadVerify(form){
    //自定义验证规则
    form.verify({
        length20: function(value){
            if(value.length > 20){
                return '字符长度不能超过20';
            }
        }
        ,length50:function(value){
            if(value.length > 50){
                return '字符长度不能超过50';
            }
        },floatNumber:function (value) {
            var reg=/^-?[1-9][0-9]*\.?[0-9]*$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正确的数字";
                }
            }
        },positiveFloatNumber:function (value) {
            var reg=/^[1-9][0-9]*\.?[0-9]*$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正确的数字";
                }
            }
        },positiveNumber:function(value){
            var reg=/^[1-9][0-9]*$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正整数";
                }
            }
        },identityNumber:function(value){
            var reg=/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正确的身份证号";
                }
            }
        },phoneNumber:function(value){
            var reg=/^1\d{10}$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return"请输入正确的手机号";
                }
            }
        }


    });
}
