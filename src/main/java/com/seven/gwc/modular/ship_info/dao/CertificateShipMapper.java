package com.seven.gwc.modular.ship_info.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.gwc.modular.ship_info.entity.CertificateShipEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description : 证书信息Mapper 接口
 *
 * @author : LDY
 * @date : 2020-02-28
 */
public interface CertificateShipMapper extends BaseMapper<CertificateShipEntity> {

    /**
     * 根据证书信息查询证书列表
     * @param certificateName 证书名称或编码
     * @return
     */
    List<CertificateShipEntity> CertificateEntityList(@Param("certificateName") String certificateName, @Param("ids") String ids);
}
