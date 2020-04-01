
function loadUpload(lay,index,data){
    let $=lay.$;

    if(!index){
      index="";
    }

    //加载数据
    if(data){
       let len= data.length;
       let fileName="";
       for(let i=0;i<len;i++){
           $('#uploader-list'+index).append(getImgHtml(data[i].url));
           $('#uploader-list'+index+' img').on('click', function () {
               reviewImg(index)
           })

           fileName+=data[i].path+",";
       }
       $("#name"+index).val(fileName);

    }

    lay.upload.render({
         elem: '#uploadImg'+index
        ,url: '/file/uploadFile'
        ,multiple: true
        ,before: function(obj){
            layer.msg('图片上传中...', {
                icon: 16,
                shade: 0.01,
                time: 0
            })
        }
        ,done: function(res){
            layer.close(layer.msg());//关闭上传提示窗口
            //上传完毕
            if(res.success){
                $('#uploader-list'+index).append(getImgHtml(res.content.url));
                let fileName=$("#name"+index).val();
                fileName = fileName + res.content.path + ","
                $("#name"+index).val(fileName);
                $('#uploader-list'+index+' img').on('click', function () {
                    reviewImg(index)
                })
            }

        }
    });

    function getImgHtml(path){
        return '<div id="" class="file-iteme">'+
               '<div class="handle del'+index+'"><i class="layui-icon layui-icon-delete"></i></div>'+
               '<img style="width: 100px;height: 100px;" src='+path+'>'+
               '<div class="info">' + path+ '</div>'+
               '</div>' ;
    }

    function reviewImg(index){
        /*layer.open({
            type: 2,
            title: '视频播放',
            area: ['600px', '500px'],
            content: Feng.ctxPath + '/fishShip/test',

        });*/
        layer.photos({
            photos: '#uploader-list'+index,
            anim: 0,
            type: 1,
            title:false,
            shade:0.1,
            closeBtn:false,
            shadeClose:true
        });
    }

    $(document).on("mouseenter mouseleave", ".file-iteme", function(event){
        if(event.type === "mouseenter"){
            //鼠标悬浮
            $(this).children(".handle").fadeIn("fast");   //删除按钮
        }else if(event.type === "mouseleave") {
            //鼠标离开
            $(this).children(".handle").hide();
        }
    });

    // 删除图片
    $(document).on("click", ".file-iteme .del"+index, function(event){
        $(this).parent().remove();
        deleteFile($(this).parent()[0].textContent)
    });

    function deleteFile(deleteFile) {
        let name = "";
        let fileName=$("#name"+index)
        let files = fileName.split(",");
        for (let i = 0; i < files.length - 1; i++ ) {
            if (files[i] !== deleteFile) {
                name += files[i] + ",";
            }
        }
        fileName = name;
        $("#name"+index).val(fileName);
    }
}
