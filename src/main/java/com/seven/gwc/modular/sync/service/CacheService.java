package com.seven.gwc.modular.sync.service;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author : zzl
 * @Date: 2020-03-13
 * @description :
 */
public class CacheService {

    private static final String SQL = "SELECT * FROM ";

   static List<String> tables;



    /**
     * 读取数据缓存
     */
    public static  void cache(){
        cacheTableNames();
    }


    /**
     * 获取数据库下的所有表名
     */
    private static void  cacheTableNames() {
        tables = new ArrayList<>();
        Connection conn = DataSouceService.getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(DataSouceService.DATABASE, null, null, new String[] { "TABLE" });
            while(rs.next()) {
                tables.add(rs.getString(3));
            }
        } catch (SQLException e) {

        } finally {
            try {
                rs.close();
            } catch (SQLException e) {

            }
        }
    }



    /**
     * 获取表中所有字段名称
     * @param tableName 表名
     * @return
     */
    public static List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = DataSouceService.getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {

        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                } catch (SQLException e) {

                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     * @param tableName
     * @return
     */
    public static List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = DataSouceService.getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {

        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                } catch (SQLException e) {

                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注释
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = DataSouceService.getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {

                }
            }
        }
        return columnComments;
    }
    /**
     * 获取表中所有字段名称
     * @param tableName 表名
     * @return
     */
    public static List<List<String>> list(String tableName) {

        List<List<String>> list = new ArrayList<>();
//        list.add(Arrays.asList(HEADS));
        //与数据库的连接
        Connection conn = DataSouceService.getConnection();
        PreparedStatement pStemt = null;
        ResultSet rs=null;
        String tableSql = SQL + "`"+tableName+"`";
        boolean tableFlag=true;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            rs = pStemt.executeQuery("show full columns from `" + tableName+"`");


            for (int i = 0; i < size; i++) {
                List<String> columns=new ArrayList<>();
                String name = rsmd.getColumnName(i + 1);
                columns.add(name);
                columns.add(rsmd.getColumnTypeName(i + 1));
                columns.add(rsmd.getColumnDisplaySize(i+1)+"");
                columns.add("id".equalsIgnoreCase(name)?"是":"");
                int nullable = rsmd.isNullable(i + 1);
                columns.add(nullable==1?"":"否");
                //描述
                String des="";
                if (rs.next()) {
                    des=rs.getString("Comment");
                }
                //标记注释是否为数据库原有注释
                if(tableFlag && des!=null && !"".equals(des)){
                    tableFlag=false;
                }



                columns.add(des);
                list.add(columns);
            }

        } catch (SQLException e) {

        } finally {
            if (pStemt != null) {
                try {
                    rs.close();
                    pStemt.close();

                } catch (SQLException e) {

                }
            }
        }
        return list;
    }




}
