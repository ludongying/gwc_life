/**
 * 附件添加对话框
 */

layui.use(['layer', 'form', 'admin', 'ax', 'laydate', 'upload', 'element'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var upload = layui.upload;
    var laydate = layui.laydate;
    var element = layui.element;

    laydate.render({
        elem: '#createTime',
        type: 'datetime'
    });

    // 让当前iframe弹层高度适应
    admin.iframeAuto();


    var count = 0;
    //多文件列表示例
    var demoListView = $('#demoList'), uploadListIns = upload.render({
        elem: '#enclosureList',
        size: 10240,
        accept: 'file',
        url: "/system/uploadFiles",
        multiple: true,
        auto: false,
        bindAction: '#enclosureListUpload',
        xhr: function (index, e) {
            var percent = e.loaded / e.total;//计算百分比
            percent = parseFloat(percent.toFixed(2));
            element.progress('progress_' + index + '', percent * 100 + '%');
            console.log("-----" + percent);
        },
        progress: function (value) {
            element.progress('demoList', value + '%'); //可配合 layui 进度条元素使用
        },
        choose: function (obj) {
            files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function (index, file, result) {
                var tr = $(['<tr id="upload-' + index + '">'
                    , '<td>' + count++ + '</td>'
                    , '<td>' + file.name + '</td>'
                    , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                    , '<td><div class="layui-progress layui-progress-big" lay-filter="progress_'+index+'" lay-showPercent="true"><div class="layui-progress-bar" lay-percent="0%"></div></div></td>'
                    , '<td>等待上传</td>'
                    , '<td>'
                    , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                    , '</td>'
                    , '</tr>'].join(''));

                //单个重传
                tr.find('.demo-reload').on('click', function () {
                    obj.upload(index, file);
                });

                //删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });

                demoListView.append(tr);
            });
        },
        done: function (res, index, upload) {
            if (res.code == 200) { //上传成功
                $("#fileList").val($("#fileList").val()+res.content+"#");
                var tr = demoListView.find('tr#upload-' + index), tds = tr.children();
                tds.eq(4).html('<span style="color: #5FB878;">上传成功</span>');
                tds.eq(5).html(''); //清空操作
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(res, index, upload);
        },
        error: function(res,index, upload){
            var tr = demoListView.find('tr#upload-'+ index),tds = tr.children();
            tds.eq(4).html('<span style="color: #FF5722;">上传失败,'+res.message+'</span>');
            tds.eq(5).find('.demo-reload').removeClass('layui-hide'); //显示重传
        }
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if ($("#fileList").val() == ""){
            Feng.error("请选择要上传的附件")
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/enclosure/add", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
    });
});