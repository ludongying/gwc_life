
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
           $('#uploader-list'+index).append(getImgHtml(data[i]));
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
                $('#uploader-list'+index).append(getImgHtml(res.content));
                let fileName=$("#name"+index).val();
                fileName = fileName + res.content.path + ",";
                $("#name"+index).val(fileName);

            }

        }
    });


    function getImgHtml(data){
        return '<div id="" class="file-iteme">'+
               '<div class="handle del'+index+'"><i data-path="'+data.path+'" class="layui-icon layui-icon-download-circle"></i><i data-path="'+data.path+'" class="layui-icon layui-icon-delete"></i></div>'+
               '<img style="width: 100px;height: 100px;" src='+data.url+'>'+
               '<div class="info">' + data.path+ '</div>'+
               '</div>' ;
    }

    function reviewImg(){
        layer.photos({
            photos:'#uploader-list'+index,
            anim: 0,
            type: 1,
            title:false,
            shade:0.1,
            closeBtn:true,
            shadeClose:true
        });
    }



    //预览
    $(document).on('click','#uploader-list'+index+' img',function () {
        $(".layui-layer-shade").remove();
        $(".layui-layer").remove();
        reviewImg(this);


    });

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
    $(document).on("click", ".file-iteme .del"+index + " .layui-icon-delete", function(event){
        deleteFile($(this).data("path"));
        $(this).parent().parent().remove();

    });

    //下载点击方法
    $(document).on("click", ".file-iteme .del"+index + " .layui-icon-download-circle", function(event){
        downFile($,$(this).data("path"));
    });

    function deleteFile(deleteFile) {
        let name = "";
        let fileName=$("#name"+index)
        let files = fileName.toString().split(",");
        for (let i = 0; i < files.length - 1; i++ ) {
            if (files[i] !== deleteFile) {
                name += files[i] + ",";
            }
        }
        fileName = name;
        $("#name"+index).val(fileName);
    }
}
