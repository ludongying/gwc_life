package com.seven.gwc.modular.lawrecord.data.instrument.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zzl
 */
@Data
public class ReasonLawDO implements Serializable {

    private String Type_Violation;
    private String Laws_Violation2;
    private String Laws_Basis2;
    private String Laws_Violation;
    private String Laws_Basis;

    public static ReasonLawDO getByLaw(Integer law){
        ReasonLawDO dto=new ReasonLawDO();
        switch (law){
            case 1:
                dto.setLaws_Violation2("《中华人民共和国渔业法》第三十条第一款，“禁止使用炸鱼、毒鱼、电鱼等破坏渔业资源的方法进行捕捞。禁止制造、销售、使用禁用的渔具。禁止在禁渔区、禁渔期进行捕捞。禁止使用小于最小网目尺寸的网具进行捕捞。捕捞的渔获物中幼鱼不得超过规定的比例。在禁渔区或者禁渔期内禁止销售非法捕捞的渔获物。”");
                dto.setLaws_Basis2("《中华人民共和国渔业法》第三十八条第一款，“使用炸鱼、毒鱼、电鱼等破坏渔业资源方法进行捕捞的，违反关于禁渔区、禁渔期的规定进行捕捞的，或者使用禁用的渔具、捕捞方法和小于最小网目尺寸的网具进行捕捞或者渔获物中幼鱼超过规定比例的，没收渔获物和违法所得，处五万元以下的罚款；情节严重的，没收渔具，吊销捕捞许可证；情节特别严重的，可以没收渔船；构成犯罪的，依法追究刑事责任。”");
                dto.setLaws_Violation("《中华人民共和国渔业法》第三十条第一款");
                dto.setLaws_Basis("《中华人民共和国渔业法》第三十八条第一款");
                return dto;
            case 21:
                dto.setLaws_Violation2("《中华人民共和国渔业法》第二十五条，“从事捕捞作业的单位和个人，必须按照捕捞许可证关于作业类型、场所、时限、渔具数量和捕捞限额的规定进行作业，并遵守国家有关保护渔业资源的规定，大中型渔船应当填写渔捞日志。”");
                dto.setLaws_Basis2("《中华人民共和国渔业法》第四十二条，“违反捕捞许可证关于作业类型、场所、时限和渔具数量的规定进行捕捞的，没收渔获物和违法所得，可以并处五万元以下的罚款；情节严重的，并可以没收渔具，吊销捕捞许可证。”");
                dto.setLaws_Violation("《中华人民共和国渔业法》第二十五条");
                dto.setLaws_Basis("《中华人民共和国渔业法》第四十二条");
                return dto;
            case 41:
                dto.setType_Violation("未依法取得捕捞许可证进行捕捞");
                dto.setLaws_Violation2("《中华人民共和国渔业法》第二十三条第一款，“国家对捕捞业实行捕捞许可证制度。”、《渔业捕捞许可管理规定》第十六条第二款，“渔业捕捞许可证必须随船携带（徒手作业的必须随身携带），妥善保管，并接受渔业行政执法人员的检查。”、第三十五条第三款，“使用无效的渔业捕捞许可证，或未携带渔业捕捞许可证从事渔业捕捞活动的为无证捕捞。”");
                dto.setLaws_Basis2("《中华人民共和国渔业法》第四十一条，“未依法取得捕捞许可证擅自进行捕捞的，没收渔获物和违法所得，并处十万元以下的罚款；情节严重的，并可以没收渔具和渔船。”");
                dto.setLaws_Violation("《中华人民共和国渔业法》第二十三条第一款、《渔业捕捞许可管理规定》第十六条第二款、第三十五条第三款");
                dto.setLaws_Basis("《中华人民共和国渔业法》第四十一条");
                return dto;
            case 42:
                dto.setType_Violation( "未依法取得捕捞许可证进行捕捞");
                dto.setLaws_Violation2("《江苏省渔业管理条例》第十八条，“从事捕捞业的单位和个人，应当向县级以上地方人民政府渔业行政主管部门申领由国务院渔业行政主管部门统一监制的捕捞许可证。”");
                dto.setLaws_Basis2( "《江苏省渔业管理条例》第四十条，“违反本条例规定，造成国家渔业资源损失的，渔业资源损失的赔偿，按照渔业生物致死量的零点五到三倍计算。”");
                dto.setLaws_Violation( "《江苏省渔业管理条例》第十八条");
                dto.setLaws_Basis( "《江苏省渔业管理条例》第四十条");
                return dto;
            case 43:
                dto.setType_Violation( "未依法取得捕捞许可证进行捕捞");
                dto.setLaws_Violation2( "《中华人民共和国渔业法》第二十三条第一款，“国家对捕捞业实行捕捞许可证制度。”、《渔业捕捞许可管理规定》第十六条第二款，“渔业捕捞许可证必须随船携带（徒手作业的必须随身携带），妥善保管，并接受渔业行政执法人员的检查。”、第三十五条第三款，“使用无效的渔业捕捞许可证，或未携带渔业捕捞许可证从事渔业捕捞活动的为无证捕捞。”《江苏省渔业管理条例》第十八条，“从事捕捞业的单位和个人，应当向县级以上地方人民政府渔业行政主管部门申领由国务院渔业行政主管部门统一监制的捕捞许可证。”");
                dto.setLaws_Basis2("《中华人民共和国渔业法》第四十一条，“未依法取得捕捞许可证擅自进行捕捞的，没收渔获物和违法所得，并处十万元以下的罚款；情节严重的，并可以没收渔具和渔船。”及《江苏省渔业管理条例》第四十条，“违反本条例规定，造成国家渔业资源损失的，渔业资源损失的赔偿，按照渔业生物致死量的零点五到三倍计算。”");
                dto.setLaws_Violation( "《中华人民共和国渔业法》第二十三条第一款、《渔业捕捞许可管理规定》第十六条第二款、第三十五条第三款及《江苏省渔业管理条例》第十八条");
                dto.setLaws_Basis("《中华人民共和国渔业法》第四十一条及《江苏省渔业管理条例》第四十条");
                return dto;
            default:
                dto.setLaws_Violation2("");
                dto.setLaws_Basis2("");
                dto.setLaws_Violation("");
                dto.setLaws_Basis("");
                return dto;
        }

    }
}
