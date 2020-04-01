package com.seven.gwc.modular.lawrecord.enums;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.dto.FilePathDTO;
import com.seven.gwc.modular.lawrecord.dto.LawTypeDTO;
import com.seven.gwc.modular.lawrecord.entity.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : zzl
 * @Date: 2020-02-28
 * @description :文书模板
 */
@Getter
public enum InstrumentEnum {

//                  编号---------- 文书名称-----------------------文书生成方式---案件类型(1生2安)--决定类型(1法2自)-----数据来源
INSTRUMENT_01(1,"01封面_法人.docx",               1,Arrays.asList(1,2),Arrays.asList(1),Arrays.asList(1,2,4,5)),
INSTRUMENT_02(2,"01封面_自然人.docx",             1,Arrays.asList(1,2),Arrays.asList(2),Arrays.asList(1,2,4,5)),
INSTRUMENT_03(3,"02目录.docx",                    1,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_04(4,"03行政处罚决定书_法人.docx",      1,Arrays.asList(1),Arrays.asList(1),Arrays.asList(1,2,20)),
INSTRUMENT_05(5,"03行政处罚决定书_法人_安.docx",   1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1,4,21)),
INSTRUMENT_06(6,"03行政处罚决定书_自然人.docx",    1,Arrays.asList(1),Arrays.asList(2),Arrays.asList(1,2,20)),
INSTRUMENT_07(7,"03行政处罚决定书_自然人_安.docx", 1,Arrays.asList(2),Arrays.asList(2),Arrays.asList(1,4,21)),
INSTRUMENT_08(8,"04案件移送函.docx",              3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_09(9,"05行政处罚立案审批表_法人.docx",  1,Arrays.asList(1),Arrays.asList(1),Arrays.asList(1,5,20)),
INSTRUMENT_10(10,"05行政处罚立案审批表_法人_安.docx", 1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1,5,21)),
INSTRUMENT_11(11,"05行政处罚立案审批表_自然人.docx", 1,Arrays.asList(1),Arrays.asList(2),Arrays.asList(1,5,20)),
INSTRUMENT_12(12,"05行政处罚立案审批表_自然人_安.docx", 1,Arrays.asList(2),Arrays.asList(2),Arrays.asList(1,4,5,21)),
INSTRUMENT_13(13,"06勘验笔录.docx", 1,Arrays.asList(1),Arrays.asList(1,2),Arrays.asList(1,2,3)),
INSTRUMENT_14(14,"06勘验笔录_安.docx", 1,Arrays.asList(2),Arrays.asList(1,2),Arrays.asList(1,4,21)),
INSTRUMENT_INQUIRE(15,"07询问笔录.docx", 1,Arrays.asList(1),Arrays.asList(1,2),Arrays.asList(1,2,3)),
INSTRUMENT_16(16,"07询问笔录_安.docx", 1,Arrays.asList(2),Arrays.asList(1,2),Arrays.asList(1,3,4)),
INSTRUMENT_17(17,"08相关证书复印件.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_18(18,"09照片.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_19(19,"10证据登记保存清单.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_20(20,"11登记保存物品处理通知书.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_21(21,"12查封（扣押）决定书和清单.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,20,21)),
INSTRUMENT_22(22,"13案件处理意见书_法人.docx", 1,Arrays.asList(1),Arrays.asList(1),Arrays.asList(1,2,5,20)),
INSTRUMENT_23(23,"13案件处理意见书_法人_安.docx", 1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1,4,5,21)),
INSTRUMENT_24(24,"13案件处理意见书_自然人.docx", 1,Arrays.asList(1),Arrays.asList(2),Arrays.asList(1,2,5,20)),
INSTRUMENT_25(25,"13案件处理意见书_自然人_安.docx", 1,Arrays.asList(2),Arrays.asList(2),Arrays.asList(1,4,21)),
INSTRUMENT_26(26,"14行政处罚事先告知书_法人.docx", 1,Arrays.asList(1),Arrays.asList(1),Arrays.asList(1,2,5,20)),
INSTRUMENT_27(27,"14行政处罚事先告知书_法人_安.docx", 1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1,4,5,21)),
INSTRUMENT_28(28,"14行政处罚事先告知书_自然人.docx", 1,Arrays.asList(1),Arrays.asList(2),Arrays.asList(1,2,5,20)),
INSTRUMENT_29(29,"14行政处罚事先告知书_自然人_安.docx", 1,Arrays.asList(2),Arrays.asList(2),Arrays.asList(1,4,5,21)),
INSTRUMENT_30(30,"15陈述和申辩笔录.docx", 1,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,2,4,5)),
INSTRUMENT_31(31,"16行政处罚听证会通知书.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_32(32,"17听证笔录.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_33(33,"18行政处罚听证会报告书.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_34(34,"19解除查封（扣押）通知书.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,5,20,21)),
INSTRUMENT_35(35,"20罚没物品处理记录.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_36(36,"21送达回证二.docx", 1,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,5,20,21)),
INSTRUMENT_37(37,"21送达回证一.docx", 1,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,5,20,21)),
INSTRUMENT_38(38,"22要求当场缴纳罚款的申请书.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_39(39,"23执行的票据.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(100)),
INSTRUMENT_40(40,"24行政处罚结案报告_法人.docx", 1,Arrays.asList(1,2),Arrays.asList(1),Arrays.asList(1,5,20,21)),
INSTRUMENT_41(41,"24行政处罚结案报告_自然人.docx", 1,Arrays.asList(1,2),Arrays.asList(2),Arrays.asList(1,5,20,21)),
INSTRUMENT_42(42,"25备考表.docx", 1,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_43(43,"26行政处罚决定审批表_法人.docx", 1,Arrays.asList(1,2),Arrays.asList(1),Arrays.asList(1,5,20,21)),
INSTRUMENT_44(44,"26行政处罚决定审批表_自然人.docx", 1,Arrays.asList(1,2),Arrays.asList(2),Arrays.asList(5,20,21)),
INSTRUMENT_45(45,"27责令返港通知书_法人.docx", 1,Arrays.asList(1),Arrays.asList(1),Arrays.asList(1,5,20)),
INSTRUMENT_46(46,"27责令返港通知书_法人_安.docx", 1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1,5,21)),
INSTRUMENT_47(47,"27责令返港通知书_自然人.docx", 1,Arrays.asList(1),Arrays.asList(2),Arrays.asList(1,5,20)),
INSTRUMENT_48(48,"27责令返港通知书_自然人_安.docx", 1,Arrays.asList(2),Arrays.asList(2),Arrays.asList(1,5,21)),
INSTRUMENT_49(49,"28限时返港承诺书.docx", 1,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,5)),
INSTRUMENT_50(50,"29责令整改通知书_法人.docx", 1,Arrays.asList(1),Arrays.asList(1),Arrays.asList(1,20)),
INSTRUMENT_51(51,"29责令整改通知书_法人_安.docx", 1,Arrays.asList(2),Arrays.asList(1),Arrays.asList(1,21)),
INSTRUMENT_52(52,"29责令整改通知书_自然人.docx", 1,Arrays.asList(1),Arrays.asList(2),Arrays.asList(1,20)),
INSTRUMENT_53(53,"29责令整改通知书_自然人_安.docx", 1,Arrays.asList(2),Arrays.asList(2),Arrays.asList(1,21)),
INSTRUMENT_54(54,"30整改承诺书.docx", 1,Arrays.asList(1),Arrays.asList(1,2),Arrays.asList(1)),
INSTRUMENT_55(55,"30整改承诺书_安.docx", 1,Arrays.asList(2),Arrays.asList(1,2),Arrays.asList(1,5)),
INSTRUMENT_56(56,"31遵守伏季休渔制度保证书.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1,2,4,20,21));
//INSTRUMENT_57(57,"32渔船安全检查记录表.docx", 2,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(2,4,5,20,21));



    Integer code;
    /**
     * 模板名称
     */
    String message;
    /**
     *  文书生成方式 1：系统根据录入生成 2需要根据录入生成并且需要手工填写 3 需要手工填写
     */
    Integer generate;

    /**
     * 案件类型 1 生产   2安全
     */
    List<Integer> law;

    /**
     * 决定类型 1 法人  2 公民
     */
    List<Integer> decision;

    /**
     * 数据来源
     *     办案机关：1
     *     询问笔录生产：2
     *     勘验笔录 3
     *     笔录安全 4
     *     案由 5
     *     决定生产 20
     *     决定安全 21
     *     固定变量 99 //此项目前不可能出现 预留 录入时要录入 为今后扩展用
     */
    List<Integer> source;


    public static String getPath(){
        return FileUtils.getStaticPath()+"\\lawrecord\\instrument\\";
    }




    public static Integer getBusinessCode(Class<?> clazz){
        if (AgencyEntity.class.equals(clazz)) {
            return 1;
        } else if (InquireEntity.class.equals(clazz)) {
            return 2;
        }else if(InquisitionEntity.class.equals(clazz)){
            return 3;
        }else if(InquireSafeEntity.class.equals(clazz)){
            return 4;
        }else if(LawRecordEntity.class.equals(clazz)){
            return 5;
        } else if(DecisionEntity.class.equals(clazz)){
            return 20;
        }else if(DecisionSafeEntity.class.equals(clazz)){
            return 21;
        }
        return 99;
    }



    InstrumentEnum(Integer code, String message,Integer generate,List<Integer> law,List<Integer> decision,List<Integer> source) {
        this.code = code;
        this.message = message;
        this.generate=generate;
        this.law=law;
        this.decision=decision;
        this.source=source;
    }

    public static InstrumentEnum findByCode(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (InstrumentEnum ms : InstrumentEnum.values()) {
                if (ms.getCode().equals(value)) {
                    return ms;
                }
            }
            return null;
        }
    }



    /**
     * 根据实体修改，修改文书
     * @param clazz
     * @return
     */
    public static List<InstrumentEnum> getAutoByClass(LawTypeDTO law,Class<?> clazz){
        return  getBusiness(law,clazz,1);
    }
    public static List<InstrumentEnum> getAutoManuallyByClass(LawTypeDTO law,Class<?> clazz){
        return  getBusiness(law,clazz,2);
    }

    private static List<InstrumentEnum> getBusiness(LawTypeDTO law,Class<?> clazz,Integer generate){
        List<InstrumentEnum> list=new ArrayList<>();
        Integer sour = getBusinessCode(clazz);
        for (InstrumentEnum ms : InstrumentEnum.values()) {
            if(ms.getLaw().contains(law.getLawType()) && ms.getDecision().contains(law.getPunishPersonType())) {
                if (ms.getSource().contains(sour) && ms.getGenerate().equals(generate)) {
                    list.add(ms);
                }
            }
        }
        return list;
    }

    /**
     * 根据案件类型，和决定类型获取需要系统生成的模板
     * @param law
     * @return
     */
    public static List<InstrumentEnum> getAuto(LawTypeDTO law){
        return getByGenerate(law.getLawType(),law.getPunishPersonType(),1);
    }


    /**
     * 根据案件类型，和决定类型获取需要系统生成的模板
     * @param law
     * @return
     */
    public static List<InstrumentEnum> getAutoManually(LawTypeDTO law){
        return getByGenerate(law.getLawType(),law.getPunishPersonType(),2);
    }



    /**
     * 根据案件类型，和决定类型获取需要手动填写的模板
     * @param law
     * @return
     */
    public static List<FilePathDTO> getManually(LawTypeDTO law){
        List<InstrumentEnum> list = getByGenerate(law.getLawType(), law.getPunishPersonType(), 3);
        List<FilePathDTO> res=new ArrayList<>(16);
        if(!list.isEmpty()){
            for (InstrumentEnum instrumentEnum : list) {
                FilePathDTO filePathDTO = new FilePathDTO(instrumentEnum.getCode(), getPath() + instrumentEnum.getMessage());
                filePathDTO.setName(instrumentEnum.getMessage());
                res.add(filePathDTO);
            }
        }
        return res;
    }


    private static List<InstrumentEnum> getByGenerate(Integer law,Integer decision,Integer generate){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
            if(ms.getLaw().contains(law) && ms.getDecision().contains(decision)){
                if(ms.getGenerate().equals(generate)){
                    list.add(ms);
                }
            }
        }
        return list;
    }


    /**
     * 根据案件类型，和决定类型获取所有模板
     * @param law
     * @return
     */
    public static List<InstrumentEnum> getList(LawTypeDTO law){
        List<InstrumentEnum> list=new ArrayList<>();
        for (InstrumentEnum ms : InstrumentEnum.values()) {
            if(ms.getLaw().contains(law.getLawType()) && ms.getDecision().contains(law.getPunishPersonType())){
                list.add(ms);
            }
        }
        return list;
    }


}

