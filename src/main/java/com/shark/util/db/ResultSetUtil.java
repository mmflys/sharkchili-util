package com.shark.util.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * ResultSet util.
 * @Author: SuLiang
 * @Date: 2018/10/24 0024
 */
public class ResultSetUtil {

	/**
	 * Get record that not exist class mapped to it from ResultSet
	 * @param resultSet ${@link ResultSet}
	 * @param <T> the result type.
	 * @return record
	 * @throws SQLException if throw SQLException
	 */
	public static <T> T getRecord(ResultSet resultSet) throws SQLException {
		List<T> result= Lists.newArrayList();
		while (resultSet.next()) {
			Map<String,Object> record= Maps.newHashMap();
			ResultSetMetaData rsmd=resultSet.getMetaData();
			int count=rsmd.getColumnCount();
			for (int i = 1; i <= count; i++) {
				Object value=resultSet.getObject(i);
				String name=rsmd.getColumnName(i);
				record.put(name,value);
			}
			if (!record.isEmpty()){
				result.add((T) record);
			}
		}

		if (result.isEmpty()) return null;
		else {
			// 只有一行记录
			if (result.size()==1){
				return result.get(0);
			}else {
				return (T) result;
			}
		}
	}
}
