/**
 * 字典类型编辑对话框
 */
layui.use(['layer', 'form', 'admin', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;

    // 让当前iframe弹层高度适应
    // admin.iframeAuto();

    //初始化dictionary的详情数据
    var ajax = new $ax(Feng.ctxPath + "/dictType/detail/" + Feng.getUrlParam("dictTypeId"));
    var result = ajax.start();
    form.val('dictTypeForm',result);


    // 表单提交事件
    /*form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/dictType/update", function (data) {
            if (data.success) {
                Feng.success("编辑成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("编辑失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });*/



    form.on('submit(btnSubmit)', function (data) {
        data.field.evidenceList = fileName;
        var ajax = new $ax(Feng.ctxPath + "/dictType/addLawProduct", function (data) {
            if (data.success) {
                Feng.success("编辑成功!");
                admin.putTempData('formOk', true);//传给上个页面，刷新table用
                admin.closeThisDialog();//关掉对话框
            } else {
                Feng.error(data.message);
            }
        }, function (data) {
            Feng.error("编辑失败!" + data.message + "!");
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    var fileName = "[{\"evidenceContent\":\"测试1\",\"files\":[{\"fileName\":\"data:image/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAH1AfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3aiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKSigBaM0lGaADNFJRQAuaKSigBc0ZpKKYC5ozSUUALmjNJRQAuaKSikAtGaSjNADs0UlFAC0UlGaAFooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiikoAWikozQAuaTNFJQAuaM0lFAC5ozSUUwFzRmkooAXNGaSigBaM0lFIBc0uabRQA6ikooAWikooAWkzRRQAUUZpKAFpKKKYBRRSUALSUUZoAKKSigBaKSigBaKSigBaKSigBaKSjNADqKSigBaKSloAKWkooAWikpaQC0UlLQAUUUUAFFFFABRRRQAUUUUAFFGaSgAoopKAFopKKYBRRSUALRmkozQAZopKKAFopKKAFzRmkopgLRSUUgFopKKAHZozTaWgBaKSigBaKTNGaAFopM0ZoAWikooAM0UUlAC0lFFMAoozSUALRmkpM0ALmikopgLRSUUALmjNJRQA7NFJmikAtFJmloAXNFJRSAWlpKKAFooooAWikpaQC0UlFAC0UUUAFFFFABSUUUAFFJRQAUUUlMBaSiigApKKKYBRSZozQAtJmkzRmgBc0ZpM0maYDs0U3NGaBDs0U3NGaAHUtNzRmgY6im5pc0gFopM0ZoAXNGaM0UALmjNJRQAuaSijNABRSZooAXNJSUZpgLmkpM0maBDs0mabupC1OwD80maZupN1OwEmaM1Hvo30WAkzS5qLd707dRYB+aWo91LmlYB+aWm5paQC0tNpaQxaKKKAFopKWkAtFJS0ALRSUtIAooooAWkoooAKSiimAUUlFABRRSUAFFFJmmAuaTNJmkzTAWjNNzRmgQuaTNJmkzTsA6jNM3UbqdgH5ozUe6jdRYCTNGaj3Cl3UWAfRmm5ozSsA/NGabmjNFgH5ozTc0ZpAOzRmm5ozQA/NGabmjNADs0lJmjNAC5ozTc0maLAOzTSaQmmk1SQDiaaWppaoy1UogSFqaXqMvTC9WoiJS9N31CXpC9VyjuT76N9V99G+nyCLIenB6q76cHpOAFoPTg1Vg9PD1DiMsBs04GoAfSnBqlxHa+xODS1GDTgahoQ+lpoNLSGLRRRSAWikpaQC0UUUALRSUUgFpKKKYBSUUUAFJRRTAKSikzQAUmaCaTNMQZpM0maaTVWAdmkJphamF6pICQtTS9RF6YXq1ECYvTS9Ql6bvqlEVyffRvqvvo30+QLlkPSh6rb6UPS5B6FoPTg9VQ9PD1LiIshqXNQB/enBqhxGTZozTA1KDSsA/NGabmjNKwDs0ZpM0ZosAuaM0maSiwC5pCaQmmFqaQClqYWprNUbPWiiA4vUZemF6jL1ooiJC9ML00ZbpU0djPLzjaPU05SjBXk7BZkJak3VfGnwx/wCumFKRp8ffNZfWYP4U38h8vczt9G+tHztP6bP0o/4l7cfdo+sd4MOUzw2TTt2Dir32C2k5il5+tQS6dMp+Uh6ccTSk7Xt6hyuxEHNSK9Virxth1IpQ1bcqeqJLitUitVNXqZWrNxKT0LQang1WV6kVqycQJwacDUQNPBqGgJKWmA06pGLRRRSAWlpKKQC0UUUAFJRRQAUlFFMApKKQmmAZppNBNNJppCFJppNITimFqpIBxaoy1NZqjZ6tRAez1GXpheometVEHZbEjPTC9RF6RdznCgk+1acthXuS5NNL81YSxuZQPl2j3qUaVx88oFYyxFGOjkNrsUg470m+tD+zoAeZv1o/syBuFmqfrdL+kHK7GfvpQ2auNpD/AMEgNV3srmHPyZHtWka9Ge0hWfUaHpweqxJU/MCD70oeteURcV6kD1SD1Ir1DgO5cDU8NVRXqVXrJxAsBqXNQhqeGqGhkmaXNMzQTSsA7NNJpN3FMLU0gFLUxmprNUZatFENBWaomahmpqq0jhVGSa0SSV2IaW9KswWLzDc/yr6mrEdvDaJ5k5BfsKrXF7JOSqnavoKw9pOo7Utu/wDkOyW5ZM1rZjai739aqS388vAO0egqAD86djmrhh4Rd5avzBtjCWY/MxP1pNhqXbx0pQuK3uKxHgYxjmmlSamK0mw0cwPUiG5ehI+lTxXs8RGGLD3ppSk8ulJRlpJDSZfS/guF2zxgZ70yXTgw327hh6VRIwMYp8U0kBBRj9K5/q7hrRdvLoF+40ho22sCD708NV1Z4L1dkqhZOxqtcWzwc4yvrVQq8z5ZqzC2lwV6lVqqq3NSK3NXKIi4rVIDVVWqZWrGURk4NPBqIGng1k0A+lpopakYtLSUopAFFFFABSUGigApKKQmmAE00mg0hNUIQ03pQTUbNVpBcGao2ahmqFn960jEGKz1Cz012xURYk1tGIm+g9npqq0jbVBJNS2tpJctwCE7mtB5bfT02RgNJWdSuovkgryBK+rIodNCrvuHAHpTmvra3G2CME+tZ81zLcNl249BUYXJxULDynrWd/LoO/YtSajcSdG2j2qBnlblnY/jSbeadgkVvGEIfCheowbgM5pNz9mNSbaNvtV3QBHcTRniRh+NWY9WmQ/OAwqt5frTCtROjSn8SC7Rqi5tLoASKFz61FNpWRvt2DD0NZhXPFTQ3U1s3yscehrH6vOGtGXyew733Qx45Ijhxg0A1rx3NveptlUBqrz6e0WWT5lpwxF3y1FZhy9ioGxUivTChFN5Fb2TCxaV6lVqpK9TK9ZSgBZDUuahDUu6o5QsPJxUbNSM9Rls1SiCQpOTSYzSquanSLcQBVNpFWIFiZ2wBVvfFZR/LgyGld1tk2rgsaznYuxJOTWPK62/w/mGwkkjzOWc5NIF54pQtSKtdGiVkQIExTgnNSKtPC1DkPdkWyl21MFpdlTzA7EO2k21Y2Um2lzCK+2mlaslKaUquYb8iqVpCMCrBSo2SrUhbFfGDV22vcDyp/mQ8Zqsy1GRROnGorSC9i5dWfl/vIjlDzx2qspNWbO68tgkn3Txn0p17beUfMj+4axhUcJezqfJ9yrJ6oiRqnRqpq1TI1aSiTctq1Sg1XRqmU1hJDJRTqYDTqzYDqKSlpDFopKKACkNLSGgBDSGgmmmmICaaTQaYxq0gEZqidqGaoWatYxAR296gZ6V25qEvgYrojEAZ8nrVuxsmnbzH4jH60yxszcPvfiNf1q1e3oQfZ4DjHUisK1STl7Klv1fYEurC8v1hXyLbAxwSKzOWOSck0oAByeacBk9K1pUo0o2X3ikCqeuKftyc05VqQJTcgGBacFqULTtlQ5BYh2UbcVNso2UuYFoVytNKVZKUhWmpDtpcqMmDxSd+asMlRlcVakJPUiHB4rRtbxgNsnI9azyOakRsGlUhGasykacsCSjcnWqEsRUkEVYhmK9+KncLKvvWEXKm7PYu1zHbilV6nmgIJqoQQa6laSHylkSU7zOKqBiKerEjFJwEokpfmnLlj0pscTMavQ2+MZqJNRHawkUdWCRCnvUgjEaZqhcSHJ5rD435BuRSvubJNRdTSEkmpEWujZEMcq1KqUKtShaylIgQLTwtLtzTgMVm2MQLQVp2KMVNwG4OaXFLRRcY3FNK1JSYp3ERFajZasEUwrVKQLQqutQstW2WomWtYyDdlUitGznE0Rt5fTiqLiiNyjAjrTq01UjbqRezHSxmKUoe1CtVqcedEJQOe9VNpopy5o67kSbTLCNVlDVKNqsoeKiaLjK5ZU08VEnFSCsGix9LTRSioGLRRRQAlIaDSGmISmmlNNNUgEY1CzU9jUDmtIoaVxrtULU4mm4JreKsaRjcicelLbWrXEwX+HualERYgAda0ECWduT3qa1VxjaO7BwI765WztxDFwxH5VirlmyTyanlbzmLMfmNNXpjFVQpKlC3V7mclqATBqdFpqLU6LVSkT1FVKkC0qrTwKwch6XGgU7FOxS4qWwG4o207FLSuAzFNIzUuKTFFwICtRslWSKaVqlIRTdKiIwauMtQulbRkNDUc1aieqe3BqRH2mnJXLTNLy1lWq0ll7VJBMDjmrq4cVzuUoMq5ivbbT0pY4ea1XgDdqatvg1ftrofMMhgqyoT8qNmBxUb4C+hrFvmFo9RLiTAx2rJlbc1T3M2eBVZQSc10U48qFsKi81ZRaai1Oq0pSIYqrTgOaUCnAVi2IRQadS4oqWxsTFGKdiikIbRS4opgJikp1JQAlNIp9JTAhYVEy1ZYVEwq4sGVHWohgGrMgqueK6IsxnoXbVgQUPQ1HJEVJFJbnDAircydG9ayfuz9SUnJXKAXFTxmmsuDQvFU9S4FpTUoNV1NTqawkjUkFLTRThWYC0UlFIYhptKaQ0xDTTWNONRuatAROagdqkc1XY10QQ0KBmpUTNRoMmrkUeaqTsdEdh0MYX5jVa8kLnb2FXHbZH71mynJrOmry5mEn0K2Pmp4GTxQV5qRFrokznl2HotTqtNQVKBXPJkigU4CkAp1ZtjClopcUgExS4paKQCYoxS0UANIppFSUhFMCIiomSrBFMK1SYFVkqMrVtlqJlrWMgTI42KmtGGXpzWceKeku005R5i7m0jBh707FZ0Vz71dSVZB15rklFxEOZto6VnXM/Xmp7mcdB2rMkcsa1pQ6saGH5mqZEpiJVlFxW0pCbHItSgUiingVztkgBTsUYpai4BS0YpaQCYoxS4pcUANxSU6kxQAlJTsUlMBtFLSUwENRsKkprCqTArOKquOauOKqS8GuiDMamxJAecVpKN8HuKyYX+atW3YbSPUVFZdTOnIrSLUGcGrc4xmqTNg1UdUDkkywhqwlUo2q0jVnNHRBtosClFNFOrBlC0UUUDGmm0tIaaENNQualbpUDmtIoCCQ1FmnyGox1rpihx3J4hzWlEgWPJqjbLuYVflbamBWFV3aSNkVZ3zmqMnJqzM1VG5NbQVkJsao5qdFpiCp0FE2YseoqQCkApwFYNiFFLRSgVIwxS0UUgClxRS0gG4opaKAEooopgJikIp1FAERFMZamIppFUmBWZKhZCKulajKVpGYXKoJU1KszDuacY6Ty6u6e5VxrOWoVMmniOpVSk5W2FcaiYqYLQBT1rJsNRQKcBQBTgKybEGKXFApcUgCilopDDFFGKMUAJiilooAbSU6kpiG0lONJTAaaaelPNMzmmgIXqnNV16qTV0UzKotCsjYetW2fpWRzvrStT0rSqrxOODakTTnIrOkPNaE/SsyTrU0loTVlaRLEaux1Qj4NXojSqI66D0syytPFMWniuVnQLRSUUhjTSGlNIaaEMbpVdzU79KrvWsAK701RzTmpUHNdC2Lii7Zp82fSpZjk0224FOk71zPWdzR6FKXrVcjmrEo5qDHNdC2M5PQegqwoqJBU4FZyZmOApaBS1kMUUtIKWkAUtIKUUgFooopAFFJmkzTAWkpM0madgHUU3NJmnYB9JSZozQAEU3bT6MUXAj20balxRii4EYWnBadilApNgIBTgKUClAqWwAClopaQAKWiikMMUtFLSASilooASkpaQ0AFJS0hpgJSGlpKYhDTSOKdTT0qkBA45qrLVt6py1vAibKjcScVftj0qgR89aFsOlb1PhOFXcnYmuDxWZIea0rngVlyH5qmjsY1dySLrV+LtWfD1rQiqap24fYtJ0p4pidKeK5GdQtFFFIYw0hpaQ0xEb9KrvVh6rvWsBpXICKkjXpTSOamiHNbN6G0FoXIRhKR8VJEPlIpjqOTXOviFIoy9ah71PL1qHvXQtjNkiVOKhSphWciBwpaaKdWYxRS0lLSABTqbRSAdmkJpM0hNOwATSFqaWppaqSAcWppam7qTdTsIfuozUe6l3U7DJM0uajBpQaVgJQaWowaeDUtAOpRSClFSA4UtIKWkAUtFLSAKWiikMKWkFLSAUUUUUAFFGKKAA0hpTSUAJSGloNMBDTTTjSGmIbTTTqY1NAQyVTmNWpDxVKZq6aaMarsiFeXrTth0rMjOXrVtu1aVdEccNWF1WVJ96tO6bJNZzjnGKKOiFOF2OhHNaEVU4Vq9GKiqzroxsidafTFp9crOgKKKKQDaQ040000BG9QOKsN0qBxWsRkPepoutQnrUsZ5rV7GsXoaEXSmO2c0QHnmnSAAcVz7SBlGWq/erM3Wq3euiOxmyRamFQrUqmokSPFOptLUAKKWkpaQC0maTNITQApNMJoJphNNIALUwtSE1MsSRr5k3fovrVSkorUNyJUdz8qk1J9mfI3Mq/U0kl07cJ8i+gqHknkk1KVR+X4j0LBt8f8tU/OkNvIMYIb6GoMUoyOhp8s19r8BD2V0OGUigNTknkXGTuA7GnnypRwNj/oaXNJfEvuDQYGp4NV2VlODT0Y96trS6IU9bFgGnColapAazaLH04U0GnCoYDqKBS1IwooooAWiilFIApaSigBaSlpKQBSUppDTASkNLSGmAGmmnGmmmIQ1G3SpD0qNulUgK0p61QmPNXpe9UJRzXXSOWvew2H71a1t0zWZCvNasQ2wFqK70sc1KLuVpzkmquCWqxIc1GoyacdEbKN2SxLVtBioIxVlRWE2dcVZEgp1IKUVgyhaKKKBjTTTTjTTTQhpqF6nNROKuLGVm60qHmlcUwda3Ww4suwt0qzKPlBFUojV1PmiIrCejuVcozdaqHg1clHWqb8Gt4bA0PU1KpqupqVTSkjO5MKdTAadms2gHUtNzRmlYBSaaTQTTSaEgAmo2NOJpmCzADqavYRLCgCmV+g6D1NRO5dizHk1LcEBhGp+VOPxqGppq/vvr+Q2JTgKAKeBVtgNApdtPApcUrgRYpp4qYimsuRTTE9UOikDja/4GnPDjpVU5Rs1egmEibWpSXLqjBOzsytu2nBqZWBqK6QqciooZecGm43Vxwq+9ysvCniolORUgNYNG5IKWminVDGFLSUtIApaSloAKKKKAFpKKKAA000tJQAUlLSUwENIaWmmmIQ1E5qQ1E5q4gQSVUkXmrb1Aw5rpgzKcbjIl5ArQnPlxKlV7OPzJx6DmluZN8xx0HFZzfNUUexCp2RExzSoKQDNSotaSZUYkqCp1FRoKmUVzyZsOFOFJS1mMKKWikA00004000xDTTGFPNNIq0BA4qEjmrDCoWFbRY0SRGrsLetZ6HBq0jYFTNXKHXMe3kdDWdLwa1jiaEjuKyZ+GI7inRd9GNsYDipFbiq2acrVu0ZtloNTw1V1anhhWTiJE26jdUW6jdU2Ak3UhamZpCadgHFqlg+Vi5H3Rmq6nLVaI22596VRaW7lpFcnJJpQKYp5qZRVPQl7igU8LSKKkArNsBAtLtp+KMVFwIytNIqUimkU0wK7rkVWDNFJkVdIqvMmRW0H0ZjWp8yuiySJoc96zzlJKntJCG2mlu4cHcKqPuvlOKd/iRNC+RU4NUbdj0q4prGorM76cuaNyYU8VGDTxWLNRaWkpakApaSigBaKSigBaKSigApKKKYBSUGkpgFNNKaaaaENJqJjT29aiatIoGRtULVK1Iib5AK1TsritcsRDyLRpP4m4FUgMmrN1IGYIv3VqFVrOinZze7BroOVamVaaq8VKopyYJD1FSAU0CnismMcKUUlLUDCiiigBppDTjTTTENpppxpDVICJhULCrDComFaRYIhzg1IJMVE1MLYrVK47luO42SDJ470zUIv8Alqg4PWqTSGtGwuFnQwSdccZ71nUi6f7xfMafQy8cZzS5GM0+8ga2nx2PSq5yDzXVFqSTRLJ1fFODU2OGST7kbH8KnNnP2jNZynBOzaFZjA1Lup/2WZRzGaa0LDqCKlSi9mOw3dQW96jcMtM3EmtFEpQLEOWkAFX50ItxUFjFzk1aum/c4xgVhN+8ki9jOj5NWFqvFVlaqZnLcetSCmCnisWIdRRRUAFNIp1IaaAjIqNxkVMaYwq0wZRJ8uUGrzESwe9UrhT1FTWblhtNbS+Hm7HBLSfL3GIu16toeKhcYepV6VnN31OqkrKxKtSCoxTxWLNR9LSUVIxaKSlpAFFFFABRSUUAFFFJTAKSg0hNMQhNMJpT0qM8VaQxGOaiY08moia0SENNTxARQtIRyeBUcaGSQL+dPuH3MEX7q8VM/eagvmC7kHU5NPUUAVIorRsBVFSqKao5qQCsmwHAU4Ugp1ZsAFLRRSGFFLRQA000040hpgNNNNONNNNCGmomFSnpUbVoguQOKrvVlxUL421tFhYr4LHApFZ4plZT8wpzAjkGlgha4nCg47k+grVtKLcthGs6LqVqDnDqevpVF5oLY7Y08yQcFmFP+3rBMqQj90vB9/el1C3Dxi4iwVPJxXFTg1JRnpF7L/MtvsVzfXL/AMZHsoxSNNKcZkb86r5GBjOak5YgE12KlCOyRJOlzKuP3jGrS3ZYfNg1Q2EHFHINQ6cH0GnY0MRSnkYqVbKPHFZyuwqxFcsp5NQ4SWzLuaENuI6ivuI+OKRb8CoLm4E2AKzjGXNdiuV4hVhajjXAqVaubJY9aeKQU4VkxC0UUVIC0hpaDQAw00inmmmqQEEiAimQr5cgPvU5phHNaJ6WM5QTdx0q4kNKvSllxvB9QKBWcXeKLSsPFSCoxTxSYyQUUgpagYUUUUAFFFFABRRSUAFFFIaYCE00mg00mmkIQmo2NKxqMmtEgtoIxplBNSwR5O9vuiqlJRV2CHj9xBn+NqrgZp0jmSQnt2oAqacWld7sYoFSgUiipAKGxCgU8CkAp4FZtgLS0lLUjCgUUtIAooooAQ0006mmmAhppp1NNUIaajapDUTdatARPUDjmpmqFq3iIhcVZx9nsQBxJN/KoUTfMqjnJqS+bdckL0QbRSn701D5jRTZdvQ1e0+52DypOY2/SqR5peAOOtbVIKceViW5ZvLY275UZRuQarKeeav2tws0Zt5+R/CarT27W8u1hx2NZU5tPknv+YPuMBzTx1pnfgU4VowJOCaUjPSmrUgqGwuN2Zp6pinAU8Cpch3FAxTwKQU8Vk2AopwpKUVLAWiiipAWiiigBppDSmkNNAMNMNPNNNWgY5xwvuKBQ/3U+lC1MfhGPFPFMFOFJiJBS5popc1Ixc0ZpM0ZpAGaKTNFMBaKSkzQIUmmk0E0wmmkAMeKjLUrNUbNWiQ/IRjUZNBNIMk4HWtEhX6Do0MjhRUs7hQIk6Dr705sW0W0f6xuvtVYcnNYx/eS5ui2/wAw2HAVIopFFPAxWjYbj1FPXmkUcU8Cs2xigU6gClqACiiikAtFFLSAKKKKAG0lLSGmA2kNONNNMQxqiapWqJq0iBC1QtUzVGRk1vEErj7JM3QP90E1WkJd2J9avWq7S7f7NU3HJpQ/iN+hbjZEYz6cUm3NPHFAHpW1zMbtIAar8MqXUXkzfe/haqRB70nIOR1FZ1IKa8x7EksbwSbWHToaYKvRSpdx+VNw46NVSWF4ZNrCop1G3yz3BiinioQcGnq1W0ImFSCoQakU1m0BIKeKjBp4NQxj6KQGlqQHZoptLmkA6ikopAIaaaU00mqQCGmmlJplUgJH6IPagUS8MB6AUgqYfChjxTxTAacKGIeKXNNFLmoAXNGabmjNAC5opM0E0wDNJmkJpCaaQATTGakLVGzVaiAM1RlqGNMY1okDFJq1GggTzX+8egplvCFXzpeFHQetRyymZ8np2FZSftHyLbr/AJAtBGYyMWPU0qikUVKFrR2SsgFUVIozQq04KazbHoOAp4FIBThWbELRRRSGFLRRSAWiiigAopaKQDKDSmkqgGmkp1NNMQxqhep2qF60iBA1MHWpGqPvW0SoOzLcK/KfcVTlXBq/b4ZCPaqtwuDUxfvM1k7oq4pwFFOArVnONIzSEVJigrSuBCeDkVdhnW4TypvvfwtVQimnrSnBTXmCdiSeB4Hw3TsajBq5BcrInkz8js3pUNxatAcj5kPQiohUd+Se/wCYPuNDU8NVcGnhq1cRFlWqQGqwanhqzcRlgNTgahDU4NWbQyXNLUYanA0rAOozSZpCaVgFJphNBNNJqkgAmkHLAe9ITToeZR7c05aRbGOkOZWpBTM5YmnA0krJIRIKcKjFPFJgPFGaSjNSAtFJmkoAXNJmimk07AKTTGakLUwtVJABao2ahjUZNapAwJqe3g3fvJDhB+tEFv5h3v8ALGO9FxP5h2JwgrKc3J8kPmx7biTzmU4HCDoKjApFFSqtWkoKyJ3FUVKopFFSL6VDY0hwFOAoApwrNsBQKWiipGFLSUtIApaKKAClpKWkAUUUUANpKWkpgIaSnUlMCNqiYVMwqNhVxEQMKhI5qwwqJhW0WBNaPhsZ6064Sq8R2sKvkbkz1pS0lc26GURg04VLNHtNRCtL3MZIdRigU6kIjIqMipiKaRmmmBARVq3u9o8uYboz69qgYUwinKEZq0gLVxZ4Hmwncn8qp7iDU9vdvbtjqncVZlto7tPNtyA3dayVSVJ8tTbv/mFr7FINT1eoGVo22sCCPWlDV0WvsItBqeGqsrc1IG96zcRlgNTs1CGp4aoaAlzSE0zNGamwDiaaTSE00mqSACaki4SR/bAqEmpW+W3Qd2Oama0S7jQ0GnioxTxVMB4p4qMU8GoYD6KbmkzU2AdmgmmFqaWppAPLYphamlqYWq1EaFLUwtSFqYTVpE3FJqeC33je/CD9aWC348yXhP502e4Mp2qMIO1ZSm5vkh82O1tWE9wXGxOEHpUQFCipFWtIxUFZA9QValUUgFSKPWpbCw4CnAAUAU4rmsrjQoFOFAGKWpYBRRS0gCiilpAFFFLQAUUUtIAooooAZQaWkpgJSUtFMBpqNhUpphFUhELComFWCKiYVpFgQdDV+3bK4NU2FOjfacVclzI1jJWsWJ49wJxVBl2mtRGEi4qpPDhjUwl0YmrlanCm9DRWjM2h1IaM0UCGkVGRUtNIqkxEBFEczwPuQ4p5FRsKvRqzA0lkg1BNr4WWqM9rLbNyMr2NQZKnIOCKv2+oBgI7gbl9a5/Zzo609Y9v8h3T3KQbnmnq1WbuyCjzoDlD2FUgcVvCcakbxE9CyrU8NVdWqRWpOIXJw1GajBpc1Fhik0hNJmmk00gHr8zBR3NSTsDLtHReBSW/y75T/COPrUWcnNRvP0DoSCng1GKcDTYySnZqPdRuqbCJN1NLVGXphamogSlqYWqMvTd9UogSFqYWppbNOijeVsKMmq0SuwE5Y4AyatxwJAvmTHnstOAis15w8v8AKqryPK+5zWF5VdI6R/Mew+adpm9F7CmBaAKkVa0SUVZC3BVqVVoVakC1DkMAvFPUUAU8Cs2wACnUAUtSMKKKKQC0UUopAFFFFABS0UUgCloooAKKKKAG0lLRTASkpaKAEppFOpCKYEZFMIqYimEVSYiBlqMirBFRla1TASKTaauArKuG/OqBGKeshFEo3LTEuIWQ5xketV+lWvPKnnBX0NIYop8mI7X/ALpo9o4fHt3E9SuDRQyMhwwINGa00exIUGig0xEbCo2FSmmGrQiBhTakYUnRcitExMuafcbSYn5VulMvIRFJkDg1Xj+8DWhcfvrQP3Fc8lyVVJbPcj2nu2M3OKlDehqBuDTkPNdDRMJ3LANKTiowdppS+azsbjiaTJJxTScVYtFGWmf7ic/U1M5KEbh1HT4ijSHv95vrUINNeQu5dupOaTdRCDjHXcbJd1LuqHdRup8oibfSbsioC9IJMU1AFbqSl6aXqIvmm7jVKIiUvSBiTin29pNcH5VwPU1oCK2sVy5DyelY1K8YPlWr7DSZBBZs67pPkT3qSS5SJfLtxx/eqvPdSTnk7V7AVGoNQqUpvmq/cO44ZY5Jyaeq0KtSqtaNiEValUUqrTgMGsmxpXFC808CgCnAVm2AAU4UYpakYoooopAFLRRSAKWiigApaKKQBS0lLQAUUUUAFFFFADaKWkpgIaKWkoASilpKAEIppFPpCKYEZFMIqUimkVaYiArTCKsEVGVq0wICKb3qYrTCtaJgOWc4CyKHUevWjy45OY3wf7rVGVpCKnkS1joArxun3lIqOpVmkTgNkeh5FO8yJ8749p9Vo5pLdX9A0K5qNqteQjDMcoJ9G4qKS2mX+Akeo5q41IXtcViDcADxULOcYp8gK8EEVAx4rpikZVJOxNE3atGE5hZDWVESTWlagnNZ1loccZ6lCXhiKYnJwKmuVIdvrVdT83Nax1RpT31Jt3PNOLDtUXG3OaTdSsdd7E8atLIqLyTVq8kWNEtk6Lyx9TToF+xWhnYfvXGEB7VnMxZiepPJrniva1L9F+LHeyJcZGaQkdqYGO32ppat7BcfupC1RlqaWquURIXppapYLOe4PyIceprUh0uG3G+dskflWFXE0qWjd32Gk2ZkFtNcNhFOPWtOLT4bZfMuHBI7dqSfVIoRst1Bx37VmSzSztmRiaw/f19/dj+JWiNC41PjZbrtX1qkCzNuYkk+tNQdhUoU9K2hShSVooV+o7FSKtCrUqrSlId7gq1Kq0KtSAVk5AAFOxQBS7ec1ncaFApwFAFLUiCloopDCloopAFLRRQAUUUtABRRS0gCiiigAooooAKKKKAEpKWigBKKKKYCUUtJQAlFLRigBpFIRTqKYEZFMIqYimkVSYiErTCtTEU0irTAgIppWpitNIq0xEJFNIqUrTSKpMCEihZXj6Ow/GnkVC4q0k9GGw838ob5gjj/AGlqI3dq7YktAM90NQyVWPLdK0jh6fRW9NDnqzaNNLZChkt2LL3B6irFt8uc1n2cvlyHBFaBIPzLWc1Je69Tj9onqitdJksc1TJXbjHNXpGzVBlbzOPxrentZlurazQocOuPSrdjbqc3E3+qTn6mora1Dt8/CjrVq5lWVREgxGnQDvWVWTk/Zw+b7G1OrzK8iG5uWuHZyPl6AelVQpwTU4Q7cVItq7gBVx9auPLTjZaIam5srBlCEHrTArOcKM1qw6Wn3pX/AAFWDPZWY4wW9uTWEsXG9qacmdEdVqZsGl3E2Cw2L71oR2FpaDdKQxHc1Um1eRuIhtHrVKSZ5T8zE/WpdPEVfjfKvIv3Uak+rog2W6Z9+1ZslxLcNmVyR6VEEqRUranh6VL4VqTdsaFqRUp6pUqpVOQWGolSqlOVKlC1lKQxqrUqrShakArGUinYQCnAUoFOAqGxABS0UtSAUtFFIYUtFFIApaKKACiiloAKKKWkAUUUUAFFFFABRRRQAUUUUAFJS0lABSUtFACUUUUwExRS0UAJSUtFACUhFLRTAYRTSKkNNIppiIyKYRUpFNIq0wISKaRUxFMIq0wIWFQSCrTCq8i1pFkspSVVLEHg1blFVnChxjmuyBxV7joVOcitezZZFK96p26qYyx702CbyrrAPBrOp790eemqckyzcRMGIHSkhtsnJ6VfcqVDHFZ092q5VDzWUZSkrI2moxdyaXA+ReBT4oFxlqz1nbIJNSm6461TpySsh05Xd2XmeGP0qCS/28ItUnmLUzOR70Kgvtam8W29CSW6mkPzOcegpjENg9TSIOeRmpNg7CtLRjsdkL2IgmTTwlSqpBp4Spcy7EapUqpUipUipWcpjsMVKlVKcq1IErJyGhqrUgWlApwFZtgIBTgKUClAqLgAFLijFLSAKWiikMKWiikAUtFFABRS0UgCiiloAKKKKACiiigAooooAKKKKACiiigAooooASilooASkpaKAEopaSmAUmKWigBKSnUlADaQ06kpgNNMIp5pCKpCGU0inkUlUmBERUMi8VZIqJhVxYmZ8qVTdPm5rUkSqrxV105nNVp3KrSFBhTkVWMhVw3Oc1baGmNFuAGK3jJHFVw7mE1/LIQi8AVXUsTk1MIPapBFTTjFWRMcLJu8hiDccGnlSDipFj4xiniOoczsjQSRGiZPNOEdTLHUojrJzNo00iFY6kVKlCU8JWbmapEYSnhKkC08LWbkMYEqQLTgtOArNsYgWnYopwFTcBAKcKMUoFSAAUtLRSGFLRRSAKWiikAUtFFABS0UUgCilooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigApKWigBKKXFJQAmKKWigBKKMUUwG0UtJQAlNp9NNUA0imkU8ikNMQw1GwqUikIqkwK7LmoWjq2VphStFITRSaKmGKrxSmmOtFUFyop+VSiKrfl0BKftAUUV1jwacI6n2UoX2qXMfkRhKcEqTbS7ahyAYFpwWnAU7FTcY0CnAUoFLSuAUuKAKUCpbAAKXFLilxSAMUtGKWkMKKKWkAlLRS0gEpaKKACloopAFLRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABikpaKAEopaSgBKSlopgNopaSmA0ikNPpuKYDTSEU7FJincQykxT8UmKdwGEUm2n0Yp3Aj20bafijFO4DNtLinYoxRcBMUYp1GKVwExS0uKXFFwExS4oxTsVNwExSgUuKWlcAxRilopDCjFLRikAUUtFABRRS0AJS0UuKQCUtFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAJijFFFACYo20UUwExRtoooANvvSbPeiii4CbPejy/eiincQnle9Hle9FFF2Anle9Hle9FFO7APK9/wBKXyvf9KKKV2AeV70vl+9FFK7APL96PL96KKdwF2e9Ls96KKVwDbRiiigYu33oxRRQAYpcUUUAGKMUUUALijFFFIAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/9k=\"}]}]";
});