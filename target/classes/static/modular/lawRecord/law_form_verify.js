//转移到common.js
/*function loadVerify(form){
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
            let reg=/^-?[1-9][0-9]*\.?[0-9]*$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正确的数字";
                }
            }
        },positiveFloatNumber:function (value) {
            let reg=/^[1-9][0-9]*\.?[0-9]*$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正确的数字";
                }
            }
        },positiveNumber:function(value){
            let reg=/^[1-9][0-9]*$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正整数";
                }
            }
        },identityNumber:function(value){
            let reg=/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return "请输入正确的身份证号";
                }
            }
        },phoneNumber:function(value){
            let reg=/^1\d{10}$/i;
            if(value){
                if(value.toString().search(reg)<0){
                    return"请输入正确的手机号";
                }
            }
        }


    });
}*/


/**
 * 金额自动切换大小写
 * @param lay
 * @param id
 */
function money_verify(lay,id){
     let $=lay.$;
     $("#"+id).focus(function(){
        //修改规则 小写金额
        $("#"+id).attr("style","");
        lay.verify=false;
        // let val=$(this).val();
        // if(val){
        //     $(this).val(chinese2Number(val));
        // }
    });
    $("#"+id).blur(function(){
        let val=$(this).val();
        if(val){
            let msg="请输入正确金额";
            let rex=/^\d+\.?\d{0,2}$/i;
            if(val.toString().search(rex)<0){
                lay.verify=true;
                msg_error($,msg)
                $("#"+id).attr("style","border:1px solid red");
            }else{
                // let money = number2Chinese(val);
                // $(this).val(money);
                //修改到表述
                  let fine=$("#fine").val();
                  let resourceCompensation=$("#resourceCompensation").val();
                  let arr=[];
                  if(fine){
                    arr.push($("#fine").parent().prev().text()+"人民币"+number2Chinese(fine))
                  }
                  if(resourceCompensation){
                      arr.push($("#resourceCompensation").parent().prev().text()+"人民币"+number2Chinese(resourceCompensation))
                  }
                  if(arr){
                      let str="";
                      for (let i=0;i<arr.length;i++){
                        str+=(i+1)+"、"+arr[i]+"。";
                      }
                      $("#description").val(str);
                  }

            }
        }

    });
}

function msg_style($,id,msg){
    $("#"+id).attr("style","border:1px solid red");
    $("#"+id).focus(function(){
        //修改规则 小写金额
        $("#"+id).attr("style","");
    });
    layer.msg(msg, {icon: 5, shift: 6},function(){});
}

function msg_error($,msg){
    layer.msg(msg, {icon: 5, shift: 6},function(){});
}



