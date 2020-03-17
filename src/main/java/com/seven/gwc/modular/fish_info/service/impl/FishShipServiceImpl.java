package com.seven.gwc.modular.fish_info.service.impl;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.gwc.core.base.BaseResult;
import com.seven.gwc.core.util.CalculationDateUtil;
import com.seven.gwc.core.util.ExcelUtil;
import com.seven.gwc.modular.fish_info.vo.ExportFishShipVO;
import com.seven.gwc.modular.system.dao.DictMapper;
import com.seven.gwc.modular.system.entity.DictEntity;
import com.seven.gwc.modular.system.service.DictService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.seven.gwc.core.shiro.ShiroUser;
import com.seven.gwc.core.util.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seven.gwc.modular.fish_info.entity.FishShipEntity;
import com.seven.gwc.modular.fish_info.dao.FishShipMapper;
import com.seven.gwc.modular.fish_info.service.FishShipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description : 渔船信息服务实现类
 *
 * @author : SHQ
 * @date : 2020-03-02
 */
@Service
public class FishShipServiceImpl extends ServiceImpl<FishShipMapper, FishShipEntity> implements FishShipService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FishShipMapper fishShipMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictService dictService;
    @Value("${FILE_UPLOAD_PATH_FILE}")
    private String uploadPathFile;

    @Override
    public List<FishShipEntity> selectFishShip(String code, String phone, String shipType){
        LambdaQueryWrapper<FishShipEntity> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(ToolUtil.isNotEmpty(code), FishShipEntity::getCode, code)
                //.like(ToolUtil.isNotEmpty(phone), FishShipEntity::getPhone, phone)
                .eq(ToolUtil.isNotEmpty(shipType), FishShipEntity::getShipType, shipType);

        List<FishShipEntity> list = fishShipMapper.selectList(lambdaQuery);

        for (FishShipEntity fishShip : list) {
            if (ToolUtil.isNotEmpty(fishShip.getShipType())) {
                DictEntity shipTypeDict = dictMapper.selectById(fishShip.getShipType());
                fishShip.setShipTypeName(shipTypeDict.getName());
            }
            if (ToolUtil.isNotEmpty(fishShip.getAreaType())) {
                DictEntity areaTypeDict = dictMapper.selectById(fishShip.getAreaType());
                fishShip.setAreaTypeName(areaTypeDict.getName());
            }

            if (ToolUtil.isNotEmpty(fishShip.getSeaState())) {
                DictEntity seaStateDict = dictMapper.selectById(fishShip.getSeaState());
                fishShip.setSeaStateName(seaStateDict.getName());
            }

            DictEntity workTypeDict = dictMapper.selectById(fishShip.getWorkType());
            fishShip.setWorkTypeName(workTypeDict.getName());
        }
        return list;
    }

    @Override
    public void addFishShip(FishShipEntity fishShip, ShiroUser user) {
        fishShip.setFilePath(uploadPathFile);
        fishShipMapper.insert(fishShip);
    }

    @Override
    public void deleteFishShip(String fishShipId, ShiroUser user) {
        fishShipMapper.deleteById(fishShipId);
    }

    @Override
    public void editFishShip(FishShipEntity fishShip, ShiroUser user) {
        fishShip.setFilePath(uploadPathFile);
        fishShipMapper.updateById(fishShip);
    }

    @Override
    public FishShipEntity detailFishShip(String fishShipId) {
        FishShipEntity fishShipEntity = fishShipMapper.selectById(fishShipId);
        if (ToolUtil.isNotEmpty(fishShipEntity.getShipType())) {
            DictEntity shipTypeDict = dictMapper.selectById(fishShipEntity.getShipType());
            fishShipEntity.setShipTypeName(shipTypeDict.getName());
        }
        if (ToolUtil.isNotEmpty(fishShipEntity.getAreaType())) {
            DictEntity areaTypeDict = dictMapper.selectById(fishShipEntity.getAreaType());
            fishShipEntity.setAreaTypeName(areaTypeDict.getName());
        }

        if (ToolUtil.isNotEmpty(fishShipEntity.getSeaState())) {
            DictEntity seaStateDict = dictMapper.selectById(fishShipEntity.getSeaState());
            fishShipEntity.setSeaStateName(seaStateDict.getName());
        }

        DictEntity workTypeDict = dictMapper.selectById(fishShipEntity.getWorkType());
        fishShipEntity.setWorkTypeName(workTypeDict.getName());
        return fishShipEntity;
    }

    @Override
    public List<ExportFishShipVO> getExportData(List<FishShipEntity> shipEntityList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ExportFishShipVO> exportDataList = new ArrayList<>();
        for (FishShipEntity fishShipEntity : shipEntityList) {
            ExportFishShipVO exportFishShipVO = new ExportFishShipVO();
            exportFishShipVO.setName(fishShipEntity.getName());
            if (ToolUtil.isNotEmpty(fishShipEntity.getAreaType())) {
                DictEntity areaTypeDict = dictMapper.selectById(fishShipEntity.getAreaType());
                exportFishShipVO.setAreaTypeName(areaTypeDict.getName());
            }
            exportFishShipVO.setCode(fishShipEntity.getCode());
            exportFishShipVO.setRegistry(fishShipEntity.getRegistry());
            if (ToolUtil.isNotEmpty(fishShipEntity.getShipType())) {
                DictEntity shipTypeDict = dictMapper.selectById(fishShipEntity.getShipType());
                exportFishShipVO.setShipTypeName(shipTypeDict.getName());
            }
            exportFishShipVO.setHullMaterial(fishShipEntity.getHullMaterial());
            DictEntity workTypeDict = dictMapper.selectById(fishShipEntity.getWorkType());
            exportFishShipVO.setWorkTypeName(workTypeDict.getName());
            exportFishShipVO.setShipLong(fishShipEntity.getShipLong());
            exportFishShipVO.setShipWide(fishShipEntity.getShipWide());
            exportFishShipVO.setShipDeep(fishShipEntity.getShipDeep());
            exportFishShipVO.setTotalPower(fishShipEntity.getTotalPower());
            exportFishShipVO.setTonnage(fishShipEntity.getTonnage());
            if (ToolUtil.isNotEmpty(fishShipEntity.getProductDate())) {
                exportFishShipVO.setProductDate(sdf.format(fishShipEntity.getProductDate()));
            }
            if (ToolUtil.isNotEmpty(fishShipEntity.getProductDate())) {
                Integer shipAge = CalculationDateUtil.getAge(fishShipEntity.getProductDate());
                exportFishShipVO.setShipAge(shipAge);
            }

            exportFishShipVO.setShipOwner(fishShipEntity.getShipOwner());
            exportFishShipVO.setIdentity(fishShipEntity.getIdentity());
            //exportFishShipVO.setPhone(fishShipEntity.getPhone());
            exportFishShipVO.setAddress(fishShipEntity.getAddress());
            if (ToolUtil.isNotEmpty(fishShipEntity.getSeaState())) {
                DictEntity seaStateDict = dictMapper.selectById(fishShipEntity.getSeaState());
                exportFishShipVO.setSeaStateName(seaStateDict.getName());
            }
            if (fishShipEntity.getKeyPoints()) {
                exportFishShipVO.setKeyPoints("是");
            } else {
                exportFishShipVO.setKeyPoints("否");
            }
            exportDataList.add(exportFishShipVO);
        }
        return exportDataList;
    }

    @Override
    public BaseResult importExcelFile(MultipartFile file, HttpServletResponse resp) throws IOException {
        BaseResult baseResult = new BaseResult();
        String message = "";
        message = ExcelUtil.saveFile(uploadPathFile, file);
        if (!message.equals("文件为空")) {
            Workbook workbook = ExcelUtil.importJudgeEdition(message);
            message = this.readExcelValue(workbook, message, resp);
        }
        baseResult.setMessage(message);
        return baseResult;
    }

    public String readExcelValue(Workbook wb, String fileFullName, HttpServletResponse resp) {
        String sheetNumber = "";
        long date = System.currentTimeMillis();
        int addNum = 0;
        Sheet sheet = wb.getSheetAt(0);
        if (wb.getNumberOfSheets() > 1) {
            sheetNumber = "只有第一个sheet数据导入成功。";
        }
        String rowMessage = "操作成功。" + sheetNumber;

        List<Integer> errorList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();     //行数
        Row rowHead = sheet.getRow(0);  //获取表头一行
        if (rowHead != null) {
            int lastCellNum = rowHead.getLastCellNum();   //列数
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取表头
            String heads = "";
            for (int i = 0; i < lastCellNum; i++) {
                heads += rowHead.getCell(i) + ",";
            }
            heads = heads.substring(0, heads.length() - 1);
            String[] head = heads.split(",");
            List<String> listHead = Arrays.asList(head);

            if (listHead.get(0).equals("船名称") && listHead.get(2).equals("编码") && listHead.get(13).equals("船主名称")) {
                for (int i = 1; i <= lastRowNum; i++) {
                    try {
                        FishShipEntity fishShipEntity = new FishShipEntity();
                        fishShipEntity.setName(ToolUtil.getCellValue(sheet.getRow(i).getCell(0)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(1)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(1)), "AREA_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setAreaType(dictEntity.getId());
                            }
                        }
                        fishShipEntity.setCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(2)));
                        fishShipEntity.setRegistry(ToolUtil.getCellValue(sheet.getRow(i).getCell(3)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(4)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(4)), "SHIP_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setShipType(dictEntity.getId());
                            }
                        }
                        fishShipEntity.setHullMaterial(ToolUtil.getCellValue(sheet.getRow(i).getCell(5)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(6)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(6)), "WORK_TYPE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setWorkType(dictEntity.getId());
                            }
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(7)))) {
                            fishShipEntity.setShipLong(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(7))));
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(8)))) {
                            fishShipEntity.setShipWide(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(8))));
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(9)))) {
                            fishShipEntity.setShipDeep(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(9))));
                        }
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(10)))) {
                            fishShipEntity.setTotalPower(Double.parseDouble(ToolUtil.getCellValue(sheet.getRow(i).getCell(10))));
                        }
                        fishShipEntity.setTonnage(ToolUtil.getCellValue(sheet.getRow(i).getCell(11)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(12)))) {
                            fishShipEntity.setProductDate(sdf.parse(ToolUtil.getCellValue(sheet.getRow(i).getCell(12))));
                        }
                        fishShipEntity.setShipOwner(ToolUtil.getCellValue(sheet.getRow(i).getCell(13)));
                        fishShipEntity.setIdentity(ToolUtil.getCellValue(sheet.getRow(i).getCell(14)));
                        //fishShipEntity.setPhone(ToolUtil.getCellValue(sheet.getRow(i).getCell(15)));
                        fishShipEntity.setAddress(ToolUtil.getCellValue(sheet.getRow(i).getCell(16)));
                        if (ToolUtil.isNotEmpty(ToolUtil.getCellValue(sheet.getRow(i).getCell(17)))) {
                            DictEntity dictEntity = dictService.findByNameAndTypeCode(ToolUtil.getCellValue(sheet.getRow(i).getCell(17)), "SEA_STATE");
                            if (ToolUtil.isNotEmpty(dictEntity)) {
                                fishShipEntity.setSeaState(dictEntity.getId());
                            }
                        }
                        if (ToolUtil.getCellValue(sheet.getRow(i).getCell(18)).equals("是")) {
                            fishShipEntity.setKeyPoints(true);
                        } else {
                            fishShipEntity.setKeyPoints(false);
                        }
                        fishShipMapper.insert(fishShipEntity);
                        addNum++;
                    } catch (Exception e) {
                        errorList.add(i);
                        rowMessage = "有错误数据！";
                    }
                }
                if (errorList.size() > 0) {
                    ExcelUtil.errorExcel(uploadPathFile, head, errorList, sheet, lastCellNum, date, resp);
                }
                if (addNum > 0 || errorList.size() > 0) {
                    rowMessage = date + "插入" + addNum + "条，失败" + errorList.size() + "条。" + sheetNumber;
                }
            } else {
                rowMessage = "不是有效模板";
            }
        } else {
            rowMessage = "不是有效模板";
        }
        File folder = new File(fileFullName);
        if (folder != null) {
            folder.delete();
        }
        return rowMessage;
    }

}
