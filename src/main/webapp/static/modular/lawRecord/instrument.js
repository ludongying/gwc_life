layui.use(['layer', 'form', 'table', 'upload', 'laydate', 'admin', 'ax', 'func'], function () {
    let $ = layui.$;
    let layer = layui.layer;
    let form = layui.form;
    let table = layui.table;
    let $ax = layui.ax;
    let laydate = layui.laydate;
    let admin = layui.admin;
    let func = layui.func;
    let upload = layui.upload;

    let lay = {
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


    function initPage() {
        let initColumn = function () {
            return [[
                {title: '序号', field: 'index', type: "numbers"},
                {title: '文书模板名称', field: 'name', align: "center"},
                // {title: '文书模板地址', field: 'path', align: "center"},
                // {title: '文件地址', field: 'filePath', align: "center"},
                {title: '操作', toolbar: '#tableBar', minWidth: 360, align: 'center'},
            ]];
        };
        table.render({
            elem: '#' + Instrument.tableId,
            url: Feng.ctxPath + 'instrument/list?id=' + Feng.getUrlParam("id"),
            height: "full-30",
            cellMinWidth: 100,
            cols: initColumn(),
            done:function (res) {
                uploadFile(res);
            }
        });

    }


    /**
     * 工具条点击事件
     */
    table.on('tool(' + Instrument.tableId + ')', function (obj) {
        let data = obj.data;
        let layEvent = obj.event;
        if (layEvent === 'downTemplate') {
            downFile($, data.path)
        }  else if (layEvent === 'down') {
            downFile($, $(this).data("filePath"))
        }
    });



    function uploadFile(res){
        let data = res.data;
        if(data){
            for(let i=0;i<data.length;i++){
                if(data[i].filePath){
                    $("#down"+i).removeClass("layui-hide");
                }
                let code=$("#upload"+i).data("code");
                let generateName=$("#upload"+i).data("generateName");
                upload.render({
                    elem: "#upload"+i
                    , url: '/lawRecord/instrument/uploadInstrument?id=' + Feng.getUrlParam("id")+"&code="+code+"&generateName="+generateName
                    , accept: 'file' //普通文件
                    , exts: 'docx'
                    , done: function (res) {
                        if (res.success) {
                            Feng.success("文件上传成功!");
                            $("#down"+i).data("filePath",res.content.path);
                            if( $("#down"+i).hasClass("layui-hide")){
                                $("#down"+i).removeClass("layui-hide");
                                $("#upload"+i).html("更换");
                            }
                        } else {
                            Feng.error("文件上传失败!"+data.message);
                        }
                    }
                });

            }
        }
    }





})

