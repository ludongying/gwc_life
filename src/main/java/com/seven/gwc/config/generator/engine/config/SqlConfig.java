package com.seven.gwc.config.generator.engine.config;

import com.seven.gwc.config.generator.action.model.GenQo;
import com.seven.gwc.config.generator.action.model.IsMenu;
import com.seven.gwc.core.util.ToolUtil;
import com.seven.gwc.modular.system.entity.MenuEntity;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 全局配置
 */
@Controller
public class SqlConfig {

    private GenQo genQo;

    private Connection connection;


    private static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


    public void init() {
        List<MenuEntity> menus = new ArrayList<>(6);

        if (genQo.getParentMenuName() == null) {
            return;
        }

        //根据父菜单查询数据库中的pcode和pcodes
        String[] pcodeAndPcodes = getPcodeAndPcodes();
        if (pcodeAndPcodes == null) {
            System.err.println("父级菜单名称输入有误!!!!");
            return;
        }

        //业务菜单
        MenuEntity menu = new MenuEntity();
        menu.setId(generateUUID());
        menu.setCode(genQo.getBizEnName());
        menu.setPcode(pcodeAndPcodes[0]);
        menu.setPcodes(pcodeAndPcodes[1] + "[" + pcodeAndPcodes[0] + "],");
        menu.setName(genQo.getBizChName());
        menu.setIcon("");
        menu.setUrl("/" + genQo.getBizEnName());
        menu.setSort(99);

        if ("顶级".equals(genQo.getParentMenuName())) {
            menu.setLevels(1);
        } else {
            menu.setLevels(2);
        }
        menu.setMenuFlag(IsMenu.YES.getCode());
        menu.setStatus("ENABLE");
        menu.setOpenFlag("0");
        menus.add(menu);

        //列表
        MenuEntity list = createSubMenu(menu);
        list.setCode(genQo.getBizEnName() + "_list");
        list.setName(genQo.getBizChName() + "列表");
        list.setUrl("/" + genQo.getBizEnName() + "/list");
        menus.add(list);

        //添加
        MenuEntity add = createSubMenu(menu);
        add.setCode(genQo.getBizEnName() + "_add");
        add.setName(genQo.getBizChName() + "添加");
        add.setUrl("/" + genQo.getBizEnName() + "/add");
        menus.add(add);

        //更新
        MenuEntity update = createSubMenu(menu);
        update.setCode(genQo.getBizEnName() + "_update");
        update.setName(genQo.getBizChName() + "更新");
        update.setUrl("/" + genQo.getBizEnName() + "/update");
        menus.add(update);

        //删除
        MenuEntity delete = createSubMenu(menu);
        delete.setCode(genQo.getBizEnName() + "_delete");
        delete.setName(genQo.getBizChName() + "删除");
        delete.setUrl("/" + genQo.getBizEnName() + "/delete");
        menus.add(delete);

        //详情
        MenuEntity detail = createSubMenu(menu);
        detail.setCode(genQo.getBizEnName() + "_detail");
        detail.setName(genQo.getBizChName() + "详情");
        detail.setUrl("/" + genQo.getBizEnName() + "/detail");
        menus.add(detail);


        for (MenuEntity menuEntity : menus) {
            PreparedStatement pstmt = null;
            try {
                pstmt = connection.prepareStatement("INSERT INTO `gwc_work`.`sys_menu` (`code`, `pcode`, `pcodes`, `name`, `icon`, `url`, `sort`, `levels`, `menu_flag`, `description`, `status`, `open_flag`,`id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);");
                pstmt.setString(1, menuEntity.getCode());
                pstmt.setString(2, menuEntity.getPcode());
                pstmt.setString(3, menuEntity.getPcodes());
                pstmt.setString(4, menuEntity.getName());
                pstmt.setString(5, menuEntity.getIcon());
                pstmt.setString(6, menuEntity.getUrl());
                pstmt.setInt(7, menuEntity.getSort());
                pstmt.setInt(8, menuEntity.getLevels());
                pstmt.setString(9, menuEntity.getMenuFlag());
                pstmt.setString(10, null);
                pstmt.setString(11, menuEntity.getStatus());
                pstmt.setString(12, menuEntity.getOpenFlag());
                pstmt.setString(13,menuEntity.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        // 释放资源
                        pstmt.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if (connection != null) {
                // 释放资源
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private MenuEntity createSubMenu(MenuEntity parentMenu) {
        MenuEntity menu = new MenuEntity();
        menu.setId(generateUUID());
        menu.setPcode(parentMenu.getCode());
        menu.setPcodes(parentMenu.getPcodes() + "[" + parentMenu.getCode() + "],");
        menu.setIcon("");
        menu.setSort(99);
        menu.setLevels(parentMenu.getLevels() + 1);
        menu.setMenuFlag(IsMenu.NO.getCode());
        menu.setStatus("ENABLE");
        menu.setOpenFlag("0");
        return menu;
    }

    public String[] getPcodeAndPcodes() {
        if ("顶级".equals(genQo.getParentMenuName())) {
            return new String[]{"0", ""};
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from sys_menu where name like ?");
            preparedStatement.setString(1, "%" + genQo.getParentMenuName() + "%");
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                String pcode = results.getString("code");
                String pcodes = results.getString("pcodes");
                if (ToolUtil.isNotEmpty(pcode) && ToolUtil.isNotEmpty(pcodes)) {
                    String[] strings = {pcode, pcodes};
                    return strings;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public GenQo getGenQo() {
        return genQo;
    }

    public void setGenQo(GenQo genQo) {
        this.genQo = genQo;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


}
