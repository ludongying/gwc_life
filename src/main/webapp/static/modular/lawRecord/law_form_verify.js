
function loadVerify(form){
    //自定义验证规则
    form.verify({
        title: function(value){
            if(value.length < 5){
                return '标题至少得5个字符啊';
            }
        }
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]
        ,money:[
            /^\d+\.?\d{0,2}$/
            ,'请输入正确金额'
        ]

    });
}


/**
 * 金额自动切换大小写
 * @param $
 * @param id
 */
function money_verify($,id){
    $("#"+id).focus(function(){
        //修改规则 小写金额
        $("#"+id).attr("style","");
        lay.verify=false;
        var val=$(this).val();
        if(val){
            $(this).val(chinese2Number(val));
        }
    });
    $("#"+id).blur(function(){
        var val=$(this).val();
        if(val){
            var msg="请输入正确金额";
            var rex=/^\d+\.?\d{0,2}$/i;
            if(val.toString().search(rex)<0){
                lay.verify=true;
                msg_error($,msg)
                $("#"+id).attr("style","border:1px solid red");
            }else{
                var money = number2Chinese(val);
                $(this).val(money);
            }
        }

    });
}

function msg_error($,msg){
    layer.msg(msg, {icon: 5, shift: 6},function(){});
}
