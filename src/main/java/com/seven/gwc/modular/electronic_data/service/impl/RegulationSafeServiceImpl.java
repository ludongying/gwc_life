package com.seven.gwc.modular.electronic_data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.modular.electronic_data.vo.LawsRegulationVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.electronic_data.entity.RegulationSafeEntity;
import com.seven.gwc.modular.electronic_data.dao.RegulationSafeMapper;
import com.seven.gwc.modular.electronic_data.service.RegulationSafeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description : 法律法规/航线安全服务实现类
 *
 * @author : SHQ
 * @date : 2020-02-25
 */
@Service
public class RegulationSafeServiceImpl extends ServiceImpl<RegulationSafeMapper, RegulationSafeEntity> implements RegulationSafeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegulationSafeMapper regulationSafeMapper;
    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String uploadPathFile;

    @Override
    public List<RegulationSafeEntity> selectRegulationSafe(String regulationSafeName,String lawRegularId,String type) {
        return regulationSafeMapper.selectRegulationSafeList(regulationSafeName,lawRegularId,type);
    }

    @Override
    public void addRegulationSafe(RegulationSafeEntity regulationSafe, ShiroUser user) {
        regulationSafe.setFilePath(uploadPathFile);
        regulationSafe.setSynFlag(false);
        regulationSafe.setDeleteFlag(true);
        regulationSafe.setCreateDate(new Date());
        regulationSafe.setCreatePerson(user.getId());
        regulationSafeMapper.insert(regulationSafe);
    }

    @Override
    public void deleteRegulationSafe(String regulationSafeId, ShiroUser user) {
        RegulationSafeEntity regulationSafe = regulationSafeMapper.selectById(regulationSafeId);
        if (regulationSafe!=null){
            regulationSafe.setDeleteFlag(false);
            regulationSafe.setSynFlag(false);
            regulationSafe.setUpdateDate(new Date());
            regulationSafe.setUpdatePerson(user.getId());
        }
        regulationSafeMapper.updateById(regulationSafe);
    }

    @Override
    public boolean selectOnlyByName(String name, String type) {
        LambdaQueryWrapper<RegulationSafeEntity> query = Wrappers.lambdaQuery();
        query.eq(RegulationSafeEntity::getName, name)
                .eq(RegulationSafeEntity::getDeleteFlag,true)
                .eq(RegulationSafeEntity::getType, type);
        return regulationSafeMapper.selectList(query).size() == 0;
    }

    @Override
    public List<LawsRegulationVO> getLawsRegulationList() {
        List<LawsRegulationVO> list = new ArrayList<>();
        List<RegulationSafeEntity> regulationSafeEntityList = this.selectRegulationSafe(null, null, "REGULATIONS");
        for (RegulationSafeEntity regulationSafeEntity : regulationSafeEntityList) {
            LawsRegulationVO lawsRegulationVO = new LawsRegulationVO();
            lawsRegulationVO.setId(regulationSafeEntity.getId());
            lawsRegulationVO.setFileName(regulationSafeEntity.getName());
            lawsRegulationVO.setFilePath(regulationSafeEntity.getFilePath() + "\\" + regulationSafeEntity.getFileName());
            list.add(lawsRegulationVO);
        }
        return list;
    }
}
