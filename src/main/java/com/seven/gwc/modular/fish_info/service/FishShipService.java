package com.seven.gwc.modular.fish_info.service;

import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.gwc.modular.fish_info.vo.ExportFishShipVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * description : 渔船信息服务类
 *
 * @author : SHQ
 * @date : 2020-03-02
 */

public interface FishShipService extends IService<FishShipEntity> {

    /**
     * 渔船信息查询列表
     *
     * @param fishShipEntity 船类型
     * @return List<渔船信息服务对象>
     */
    List<FishShipEntity> selectFishShip(FishShipEntity fishShipEntity);

    /**
     * 渔船信息新建
     *
     * @param fishShip 实体对象
     * @param user 当前用户
     */
    void addFishShip(FishShipEntity fishShip, ShiroUser user);

    /**
     * 渔船信息删除
     *
     * @param fishShipId 唯一标识
     * @param user 当前用户
     */
    void deleteFishShip(String fishShipId, ShiroUser user);

    /**
     * 渔船信息编辑
     *
     * @param fishShip 实体对象
     * @param user 当前用户
     */
    void editFishShip(FishShipEntity fishShip, ShiroUser user);

    /**
     * 获取渔船信息
     *
     * @param fishShipId 实体ID
     */
    FishShipEntity detailFishShip(String fishShipId);

    /**
     * 需导出的List转成需要的VO
     * @param shipEntityList
     * @return
     */
    List<ExportFishShipVO> getExportData(List<FishShipEntity> shipEntityList);

    BaseResult importExcelFile(MultipartFile file, HttpServletResponse resp) throws IOException;

}
