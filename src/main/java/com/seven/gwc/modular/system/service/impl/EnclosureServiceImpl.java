package com.seven.gwc.modular.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.gwc.config.constant.SysConsts;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.dao.EnclosureMapper;
import com.seven.gwc.modular.system.dto.FileEntityDTO;
import com.seven.gwc.modular.system.entity.EnclosureEntity;
import com.seven.gwc.modular.system.service.EnclosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * description : 附件服务实现类
 *
 * @author : GD
 * @date : 2019-09-29
 */
@Service
public class EnclosureServiceImpl extends ServiceImpl<EnclosureMapper, EnclosureEntity> implements EnclosureService {
    @Autowired
    private EnclosureMapper enclosureMapper;
    @Value("${FILE_UPLOAD_PATH}")
    private String uploadPath;

    @Override
    public List<EnclosureEntity> selectEnclosure(String enclosureName) {
        LambdaQueryWrapper<EnclosureEntity> lambdaQuery = Wrappers.<EnclosureEntity>lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(enclosureName), EnclosureEntity::getName, enclosureName);
        return enclosureMapper.selectList(lambdaQuery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addEnclosureEntity(ShiroUser user, String fileList, String forSurface, Long forId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int resalt = 0;

        for (String file : fileList.split(SysConsts.STR_WELL)) {
            FileEntityDTO fileEntityDTO = JSONObject.parseObject(file, FileEntityDTO.class);
            EnclosureEntity entity = new EnclosureEntity();

            //输入地址
            String filePath = uploadPath + fileEntityDTO.getUrl();
            //输出地址
            String fileOutPath = ToolUtil.getFileUpSuffix(filePath, ".pdf");
            //输出文件相对地址
            String fileUrlPath = ToolUtil.getFileUpSuffix(fileEntityDTO.getUrl(), ".pdf");

//            if (SysConsts.STR_PNG.equals(fileEntityDTO.getSuffix()) || SysConsts.STR_JPEG.equals(fileEntityDTO.getSuffix()) || SysConsts.STR_JPG.equals(fileEntityDTO.getSuffix()) || SysConsts.STR_GIF.equals(fileEntityDTO.getSuffix())) {
//                FileUtil.imgToPdf(filePath, fileOutPath);
//            } else if (SysConsts.STR_DOC.equals(fileEntityDTO.getSuffix()) || SysConsts.STR_DOCX.equals(fileEntityDTO.getSuffix())) {
//                FileUtil.wordToPdf(filePath, fileOutPath);
//            } else if (SysConsts.STR_PPTX.equals(fileEntityDTO.getSuffix())) {
//                FileUtil.pptToPdf(filePath, fileOutPath);
//            } else if (SysConsts.STR_XLS.equals(fileEntityDTO.getSuffix()) || SysConsts.STR_XLSX.equals(fileEntityDTO.getSuffix())) {
//                FileUtil.excelToPdf(filePath, fileOutPath);
//            } else if (SysConsts.STR_TXT.equals(fileEntityDTO.getSuffix()) || SysConsts.STR_SQL.equals(fileEntityDTO.getSuffix())) {
//                FileUtil.txtToPdf(filePath, fileOutPath);
//            }

            entity.setPdfUrl(fileUrlPath);
            entity.setName(ToolUtil.getFileName(fileEntityDTO.getName()));
            entity.setUrl(fileEntityDTO.getUrl());
            entity.setSuffix(fileEntityDTO.getSuffix());
            entity.setFileSize(fileEntityDTO.getFileSize());
            entity.setCreateId(user.getId());
            entity.setCreateUser(user.getName());
            entity.setCreateTime(new Date());
            entity.setForSurface(forSurface);
            entity.setForId(forId);
            resalt = enclosureMapper.insert(entity);

        }
        return resalt;
    }

}
