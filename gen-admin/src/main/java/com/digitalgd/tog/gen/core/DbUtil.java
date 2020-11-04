package com.digitalgd.tog.gen.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalgd.tog.gen.modular.model.DatabaseInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据库操作工具类
 *
 * @author fengshuonan
 * @Date 2019/1/13 18:34
 */
@Slf4j
public class DbUtil {

    public static List<Map<String, Object>> selectTables(DatabaseInfo dbInfo) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        try {
            Class.forName(dbInfo.getJdbcDriver());
            Connection conn = DriverManager.getConnection(dbInfo.getJdbcUrl(), dbInfo.getUserName(), dbInfo.getPassword());

            String jdbcUrl = dbInfo.getJdbcUrl();
            int first = jdbcUrl.lastIndexOf("/") + 1;
            int last = jdbcUrl.indexOf("?");
            String dbName = jdbcUrl.substring(first, last);
            PreparedStatement preparedStatement = conn.prepareStatement("select TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA = '" + dbName + "'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HashMap<String, Object> map = new HashMap<>();
                String tableName = resultSet.getString("tableName");
                String tableComment = resultSet.getString("tableComment");
                map.put("tableName", tableName);
                map.put("tableComment", tableComment);
                list.add(map);
            }
            return list;
        } catch (Exception ex) {
            log.error("执行sql出现问题！", ex);
            return null;
        }
    }

    @SuppressWarnings("finally")
	public static boolean connectVerify(DatabaseInfo dbInfo) {
    	boolean flag = false;
    	  try {
			Class.forName(dbInfo.getJdbcDriver());
			Connection conn = DriverManager.getConnection(dbInfo.getJdbcUrl(), dbInfo.getUserName(), dbInfo.getPassword());
			flag = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return flag;
		}
    }
    
}
