package com.seven.gwc.modular.lawrecord.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.gwc.config.constant.GwcConsts;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.base.BaseResultPage;
import com.seven.gwc.modular.lawrecord.dao.LawRecordMapper;
import com.seven.gwc.modular.lawrecord.data.local.LocData;
import com.seven.gwc.modular.lawrecord.data.local.StateData;
import com.seven.gwc.modular.lawrecord.dto.*;
import com.seven.gwc.modular.lawrecord.entity.LawRecordEntity;
import com.seven.gwc.modular.lawrecord.enums.LawCaseSourceEnum;
import com.seven.gwc.modular.lawrecord.enums.LawTypeEnum;
import com.seven.gwc.modular.lawrecord.enums.RecordStatusEnum;
import com.seven.gwc.modular.lawrecord.service.*;
import com.seven.gwc.modular.lawrecord.vo.AgencyVO;
import com.seven.gwc.modular.lawrecord.vo.LawRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.seven.gwc.modular.lawrecord.data.file.FileUtils.file_2_file_sep;

/**
 * description : 执法记录服务实现类
 *
 * @author : ZZL
 * @date : 2020-02-26
 */
@Service
@Slf4j
public class LawRecordServiceImpl extends ServiceImpl<LawRecordMapper, LawRecordEntity> implements LawRecordService {

    @Autowired
    private LocData locData;
    @Autowired
    private LawRecordMapper lawRecordMapper;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private InquireService inquireService;
    @Autowired
    private InquireSafeService inquireSafeService;
    @Autowired
    private InquisitionService inquisitionService;
    @Autowired
    private EvidenceService evidenceService;
    @Autowired
    private DecisionService decisionService;
    @Autowired
    private DecisionSafeService decisionSafeService;


    @Override
    public List<StateData> getStates(){
       return locData.getStateDataList();
    }

    @Override
    public LawRecordEntity createAgencyRecord(String userId,Integer lawType) {
        LawRecordEntity lawRecordEntity=new LawRecordEntity();
        lawRecordEntity.init(userId);
        lawRecordEntity.setLawType(lawType);
        lawRecordEntity.setStatus(RecordStatusEnum.OPEN_CASE.getCode());
        this.save(lawRecordEntity);
        return lawRecordEntity;
    }

    @Override
    public LawRecordEntity createLawRecord(String userId, Integer lawType) {
        LawRecordEntity lawRecord = createAgencyRecord(userId, lawType);
        AgencyVO agencyVO=new AgencyVO();
        agencyVO.setId(lawRecord.getId());
        agencyVO.setShortName(GwcConsts.shortName);
        agencyVO.setLawCaseFineCode(LocalDate.now().getYear());
        agencyVO.setLawCaseCode(agencyService.getLawCode(agencyVO.getLawCaseFineCode()));
        //设置默认值
        agencyVO.setLawShipCode(GwcConsts.lawShipCode).setEnforcementAgency(GwcConsts.enforcementAgency)
                .setAgencyPerson(GwcConsts.agencyPerson).setAgencyTel(GwcConsts.agencyTel)
                .setAgencyAddr(GwcConsts.agencyAddr).setBank(GwcConsts.bank)
                .setReviewAgency(GwcConsts.reviewAgency).setLawsuitAgency(GwcConsts.lawsuitAgency);
        agencyService.updateAgency(agencyVO);
        return lawRecord;
    }

    @Override
    public BaseResultPage<LawRecordDTO> listLawRecord(LawRecordVO lawRecordVO) {
        Page page = BaseResultPage.defaultPage();
        PageHelper.startPage((int) page.getCurrent(), (int) page.getSize());
        List<LawRecordDTO> lawRecordDTOS = lawRecordMapper.listLawRecord(lawRecordVO);
        PageInfo pageInfo = new PageInfo<>(lawRecordDTOS);
        if (Objects.nonNull(lawRecordDTOS) && !lawRecordDTOS.isEmpty()) {
            lawRecordDTOS.stream().forEach(dto -> {
                if (Objects.nonNull(dto.getLawCaseSource())) {
                    dto.setLawCaseSourceName(LawCaseSourceEnum.findByCode(dto.getLawCaseSource()).getMessage());
                }
                //数据美化
                dto.setShipName(handleStr(dto.getShipName()));
                dto.setInvestigateName(handleStr(dto.getInvestigateName()));
                dto.setInvestigateTel(handleStr(dto.getInvestigateTel()));
            });
        }
        return new BaseResultPage().createPage(pageInfo);
    }

    private String handleStr(String str){
          if(Objects.nonNull(str) && !str.trim().isEmpty()){
              if(str.contains(file_2_file_sep)){
                  String[] arr = str.split(file_2_file_sep);
                  return Stream.of(arr).map(String::trim).filter(s -> !s.isEmpty()).distinct().collect(Collectors.joining(","));
              }
          }
          return str;
    }

    @Override
    public BaseResult invalidRecord(String id) {
        LawRecordEntity lawRecordEntity = this.getById(id);
        lawRecordEntity.setStatus(RecordStatusEnum.INVALID.getCode());
        this.updateById(lawRecordEntity);
        return new BaseResult(true,"");
    }

    @Override
    public BaseResult finishRecord(String id) {
        LawRecordEntity lawRecordEntity = this.getById(id);
        lawRecordEntity.setStatus(RecordStatusEnum.FINISH.getCode());
        this.updateById(lawRecordEntity);
        return new BaseResult(true,"");
    }

    @Override
    public void detail(String id,Model model) {
        LawRecordEntity lawRecordEntity = this.getById(id);
        Integer lawType = lawRecordEntity.getLawType();
        model.addAttribute("lawType",lawType);
        AgencyDTO agencyDTO = agencyService.detail(id);
        if(Objects.nonNull(agencyDTO)){ agencyDTO.setDetailContent(); }
        model.addAttribute("agency",agencyDTO);
        model.addAttribute("evidence",evidenceService.detail(id));
        if(LawTypeEnum.PRODUCE.getCode().equals(lawType)){
            InquireDTO inquireDTO = inquireService.detail(id);
            if(Objects.nonNull(inquireDTO)){inquireDTO.setDetailContent();}
            model.addAttribute("inquire",inquireDTO);
            InquisitionDTO inquisitionDTO = inquisitionService.detail(id);
            if(Objects.nonNull(inquisitionDTO)){inquisitionDTO.setDetailContent();}
            model.addAttribute("inquisition",inquisitionDTO);
            model.addAttribute("reason",new ReasonProduceDTO(lawRecordEntity));
            DecisionDTO decisionDTO = decisionService.detail(id);
            if(Objects.nonNull(decisionDTO)){decisionDTO.setDetailContent();}
            model.addAttribute("decision",decisionDTO);
        }else{
            InquireSafeDTO inquire = inquireSafeService.detail(id);
            if(Objects.nonNull(inquire)){inquire.setDetailContent();}
            model.addAttribute("inquire",inquire);
            model.addAttribute("reason",new ReasonSafeDTO(lawRecordEntity));
            DecisionSafeDTO decisionSafeDTO = decisionSafeService.detail(id);
            if(Objects.nonNull(decisionSafeDTO)){decisionSafeDTO.setDetailContent();}
            model.addAttribute("decision",decisionSafeDTO);
        }
    }

    @Override
    public LawTypeDTO findLawType(String id){
        return lawRecordMapper.findLawType(id);
    }



}
