package com.seven.gwc.modular.lawrecord.enums;

import com.seven.gwc.modular.lawrecord.data.file.FileUtils;
import com.seven.gwc.modular.lawrecord.data.instrument.dos.FilePathDO;
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

    //编号-- 文书生成方式--案件类型--决定类型--数据来源
    INSTRUMENT_01(1,"01封面_法人.docx", 1,Arrays.asList(1,2),Arrays.asList(1),Arrays.asList(1,2,4,5,20,21)),
    INSTRUMENT_02(2,"02目录.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1)),
    INSTRUMENT_03(3,"03行政处罚决定书_法人.docx", 3,Arrays.asList(1,2),Arrays.asList(1),Arrays.asList(1,2,4,5,20,21)),
    INSTRUMENT_04(4,"06勘验笔录.docx", 3,Arrays.asList(1),Arrays.asList(1,2),Arrays.asList(1)),
    INSTRUMENT_INQUIRE(5,"07询问笔录.docx", 3,Arrays.asList(1),Arrays.asList(1,2),Arrays.asList(1)),
    INSTRUMENT_06(6,"12查封（扣押）决定书和清单.docx", 3,Arrays.asList(1,2),Arrays.asList(1,2),Arrays.asList(1));


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
    public static List<FilePathDO> getManually(LawTypeDTO law){
        List<InstrumentEnum> list = getByGenerate(law.getLawType(), law.getPunishPersonType(), 3);
        List<FilePathDO> res=new ArrayList<>(16);
        if(!list.isEmpty()){
            for (InstrumentEnum instrumentEnum : list) {
                FilePathDO filePathDO = new FilePathDO(instrumentEnum.getCode(), getPath() + instrumentEnum.getMessage());
                filePathDO.setName(instrumentEnum.getMessage());
                res.add(filePathDO);
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

