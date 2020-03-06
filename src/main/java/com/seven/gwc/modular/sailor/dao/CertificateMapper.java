package com.seven.gwc.modular.sailor.dao;

import com.seven.gwc.modular.sailor.entity.CertificateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.ship_info.entity.ShipEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 证书信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-02-28
 */
public interface CertificateMapper extends BaseMapper<CertificateEntity> {

    /**
     * 根据证书信息查询证书列表
     * @param certificateName 证书名称或编码
     * @return
     */
    List<CertificateEntity> CertificateEntityList(@Param("certificateName") String certificateName, @Param("ids") String ids);
}
