package com.seven.gwc.modular.lawrecord.vo;

import com.seven.gwc.core.state.SexEnum;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.lawrecord.dto.AgencyDTO;
import com.seven.gwc.modular.lawrecord.dto.InquireDTO;
import com.seven.gwc.modular.lawrecord.dto.InquireSupplementDTO;
import com.seven.gwc.modular.lawrecord.entity.InquireEntity;
import com.seven.gwc.modular.lawrecord.enums.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AppSupplementVO implements Serializable {

    List<InquireSupplementDTO> list;

    public AppSupplementVO(InquireDTO dto, AgencyDTO agencyDTO){
        this.list=new ArrayList<>();
        //主
        InquireEntity entity=new InquireEntity();
        BeanUtils.copyProperties(dto,entity);
        InquireSupplementDTO inquireSupplementDTO=new InquireSupplementDTO();
        BeanUtils.copyProperties(entity,inquireSupplementDTO);
        inquireSupplementDTO.setPersonName1(agencyDTO.getPersonName1());
        inquireSupplementDTO.setCredentialCode1(agencyDTO.getCredentialCode1());
        inquireSupplementDTO.setPersonName2(agencyDTO.getPersonName2());
        inquireSupplementDTO.setCredentialCode2(agencyDTO.getCredentialCode2());

        inquireSupplementDTO.setInvestigateSexName(SexEnum.findByCode(dto.getInvestigateSex()).getMessage());
        inquireSupplementDTO.setInvestigatePositionName(InvestigatePositionEnum.findByCode(dto.getInvestigatePosition()).getMessage());
        if (ToolUtil.isNotEmpty(dto.getShipStatus())) {
            inquireSupplementDTO.setShipStatusName(ShipStatusEnum.findByCode(dto.getShipStatus()).getMessage());
        }
        if (ToolUtil.isNotEmpty(dto.getShipRealType())) {
            inquireSupplementDTO.setShipRealTypeName(ShipRealTypeEnum.findByCode(dto.getShipRealType()).getMessage());
        }

        if (ToolUtil.isNotEmpty(dto.getShipRatedType())) {
            inquireSupplementDTO.setShipRatedTypeName(ShipRatedTypeEnum.findByCode(dto.getShipRatedType()).getMessage());
        }

        if (ToolUtil.isNotEmpty(dto.getShipRealPowerUnit())) {
            inquireSupplementDTO.setShipRealPowerUnitName(PowerUnitEnum.findByCode(dto.getShipRealPowerUnit()).getMessage());
        }

        if (ToolUtil.isNotEmpty(dto.getShipRatedPowerUnit())) {
            inquireSupplementDTO.setShipRatedPowerUnitName(PowerUnitEnum.findByCode(dto.getShipRatedPowerUnit()).getMessage());
        }

        list.add(inquireSupplementDTO);
        //补录
        List<InquireSupplementDTO> inquireContent = dto.getInquireContent();
        if (ToolUtil.isNotEmpty(inquireContent)) {
            for (InquireSupplementDTO supplementDTO : inquireContent) {
                supplementDTO.setInvestigateSexName(SexEnum.findByCode(supplementDTO.getInvestigateSex()).getMessage());
                supplementDTO.setInvestigatePositionName(InvestigatePositionEnum.findByCode(supplementDTO.getInvestigatePosition()).getMessage());
                if (ToolUtil.isNotEmpty(dto.getShipStatus())) {
                    inquireSupplementDTO.setShipStatusName(ShipStatusEnum.findByCode(dto.getShipStatus()).getMessage());
                }
                if (ToolUtil.isNotEmpty(dto.getShipRealType())) {
                    inquireSupplementDTO.setShipRealTypeName(ShipRealTypeEnum.findByCode(dto.getShipRealType()).getMessage());
                }

                if (ToolUtil.isNotEmpty(dto.getShipRatedType())) {
                    inquireSupplementDTO.setShipRatedTypeName(ShipRatedTypeEnum.findByCode(dto.getShipRatedType()).getMessage());
                }

                if (ToolUtil.isNotEmpty(dto.getShipRealPowerUnit())) {
                    inquireSupplementDTO.setShipRealPowerUnitName(PowerUnitEnum.findByCode(dto.getShipRealPowerUnit()).getMessage());
                }

                if (ToolUtil.isNotEmpty(dto.getShipRatedPowerUnit())) {
                    inquireSupplementDTO.setShipRatedPowerUnitName(PowerUnitEnum.findByCode(dto.getShipRatedPowerUnit()).getMessage());
                }
            }
            list.addAll(inquireContent);
        }
    }
}
