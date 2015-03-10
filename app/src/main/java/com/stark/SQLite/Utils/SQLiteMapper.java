package com.stark.SQLite.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class SQLiteMapper {

	public static <T> ArrayList<T> Map(ArrayList<SQLLiteTable> source,
			Class<T> destiny) {
		try {
			ArrayList<T> result = new ArrayList<>();
			for (SQLLiteTable s : source) {
				T aux = destiny.newInstance();
				for (Field f : aux.getClass().getDeclaredFields()) {
					String key = f.getName();
					if (s.propertyExists(key)) {
						f.set(aux, s.getClass().getField(key).get(s));
					}
				}
				result.add(aux);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
