package com.stark.SQLite.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLLiteTable {

	protected DatabaseHelper helper;
	protected String OrderByField = "";

	// 1 for Asc
	// 2 for Desc
	protected int OrderByOrder = 0;

	public void setOrder(String field, int order) {
		OrderByField = field;
		OrderByOrder = order;
	}

	public SQLLiteTable() {

	}

	public SQLLiteTable(Context cont) {
		if (cont != null)
			helper = new DatabaseHelper(cont);
	}

	public void resetTable() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(getDropTableScript());
		db.execSQL(getCreateTableScript());
		String insertQuery = getInsertScript();
		if (insertQuery != null)
			db.execSQL(insertQuery);
		db.close();
	}

	// Select's
	public ArrayList<SQLLiteTable> select() {
		SQLiteDatabase db = helper.getWritableDatabase();
		String query = "SELECT * FROM " + getTableName();
		query = checkForOrder(query);

		ArrayList<SQLLiteTable> result = parseToEntity(db.rawQuery(query, null));
		db.close();
		return result;
	}

	private String checkForOrder(String query) {
		if (OrderByOrder != 0) {
			query += " ORDER BY " + OrderByField;
			query += " " + getOrderByOrder(OrderByOrder);
		}

		return query;
	}

	public ArrayList<SQLLiteTable> select(int primaryKey) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (getPrimaryKeyName() != null) {
			String query = "SELECT * FROM " + getTableName();
			query += " WHERE " + getPrimaryKeyName() + "= ?";
			query = checkForOrder(query);
			ArrayList<SQLLiteTable> result = parseToEntity(db.rawQuery(query,
					new String[] { String.valueOf(primaryKey) }));
			db.close();
			return result;
		} else
			return null;
	}

	public ArrayList<SQLLiteTable> select(String fieldName, Object value) {
		SQLiteDatabase db = helper.getWritableDatabase();
		if (propertyExists(fieldName)) {
			String query = "SELECT * FROM " + getTableName();
			query += " WHERE " + fieldName + "= ?";

			query = checkForOrder(query);
			ArrayList<SQLLiteTable> result = parseToEntity(db.rawQuery(query,
					new String[] { String.valueOf(getInsertValue(
							getFieldType(fieldName), value)) }));
			db.close();
			return result;
		} else
			return null;
	}

	private String getOrderByOrder(int orderBy) {
		switch (orderBy) {
		case 1:
			return "ASC";
		case 2:
			return "DESC";
		default:
			break;
		}
		return "";
	}

	// Devuelve el script para Crear la Tabla
	public String getCreateTableScript() {
		String response;

		response = "CREATE TABLE ";
		response += getTableName() + " (";

		for (Field f : getClass().getDeclaredFields()) {
			String field = " ";
			String key = f.getName();
			Class<?> type = f.getType();

			field += key + " ";
			field += getTypeByClass(type.getSimpleName()) + " ";

			if (isPrimaryKey(key)) {
				field += "PRIMARY KEY ";
			}

			field += ",";
			response += field;
		}
		response = response.substring(0, response.length() - 1);
		response += ")";

		return response;
	}

	// Devuelve el type de SQLite a partir del type en objetos
	private String getTypeByClass(String type) {
		if (type.equalsIgnoreCase("String"))
			return "TEXT";

		if (type.equalsIgnoreCase("int"))
			return "INTEGER";

        if (type.equalsIgnoreCase("float"))
            return "REAL";

		// No existe el tipo Boolean en SQLite
		// http://www.sqlite.org/datatype3.html
		if (type.equalsIgnoreCase("boolean"))
			return "INTEGER";
		return type;
	}

	// Devuelve el nombre de la tabla. Supone que es el mismo que el nombre de
	// la clase
	// En caso de que sea otro, hay que hacer el Override de este m�todo
	public String getTableName() {
		return getClass().getSimpleName().replace("Lite", "");
	}

	// Devuelve si el campo enviado por par�metro es clave primaria o no
	// Por defecto devuelve que no, en caso de tener claves primarias hay que
	// hacer el Override en cada clase
	private boolean isPrimaryKey(String fieldName) {
		String primaryKey = getPrimaryKeyName();
		if (primaryKey == null || !primaryKey.equalsIgnoreCase(fieldName))
			return false;
		else
			return true;
	}

	public String getPrimaryKeyName() {
		return null;
	}

	// Devuelve el script para Dropear la Tabla
	public String getDropTableScript() {
		String response = "DROP TABLE IF EXISTS ";
		response += getTableName();
		return response;
	}

	// Devuelve el script para actualizar los datos a partir de una entidad
	// id�ntica ya cargada
	public String getInsertScript() {
		String response = "";
		ArrayList<Object> entities = getEntity();
		try {
			if (entities != null) {
				for (int i = 0; i < entities.size(); i++) {
					Object entity = entities.get(i);

					if (i == 0) {
						response = "INSERT or replace INTO ";
						response += getTableName();
						response += " SELECT ";
					} else
						response += "UNION SELECT ";

					for (Field f : entity.getClass().getDeclaredFields()) {
						String key = f.getName();
						Class<?> type = f.getType();
						Object value = f.get(entity);

						if (propertyExists(key)) {

							// Value
							response += getInsertValue(type.getSimpleName(),
									value) + " ";

							if (i == 0)
								response += "AS '" + key + "'";

							response += ",";
						}
					}
					response = response.substring(0, response.length() - 1);
					response += " ";
				}
				return response;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Verifica que la Propiedad de una Entidad, existe en clase de la tabla
	// declarada en la BD
	public boolean propertyExists(String key) {
		try {
			Field f1;
			f1 = getClass().getDeclaredField(key);
			if (f1 != null)
				return true;
			else
				return false;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}

	// Devuelve el dato a insertar seg�n el tipo
	public String getInsertValue(String type, Object value) {
		if (type.equalsIgnoreCase("int"))
			return value.toString();

		if (type.equalsIgnoreCase("boolean")) {
			int obj = Boolean.parseBoolean(value.toString()) ? 1 : 0;
			return String.valueOf(obj);
		}

		return "'" + valueWithCharactersCorrection(value.toString()) + "'";
	}

	private String valueWithCharactersCorrection(String value) {
		String response = value.replace("'", "''");
		return response;
	}

	// Devuelve la lista de entidades a copiar en la base de datos a la hora de
	// hacer el Insert
	public ArrayList<Object> getEntity() {
		return null;
	}

	private String getFieldType(String fieldName) {
		for (Field f : this.getClass().getDeclaredFields()) {
			String key = f.getName();
			Class<?> type = f.getType();
			if (key.equalsIgnoreCase(fieldName))
				return type.getSimpleName();
		}
		return null;
	}

	// Devuelve la entidad cargada a partir del Cursor
	private ArrayList<SQLLiteTable> parseToEntity(Cursor cur) {
		try {
			ArrayList<SQLLiteTable> result = new ArrayList<SQLLiteTable>();
			if (cur.moveToFirst() && !cur.isLast()) {
				for (cur.moveToFirst(); !cur.isAfterLast() && !cur.isLast(); cur.moveToNext()) {
					SQLLiteTable aux = getClass().newInstance();
					for (Field f : getClass().getDeclaredFields()) {
						String key = f.getName();
						Class<?> type = f.getType();
						Object value = getValueFromCursor(cur,
								type.getSimpleName(), cur.getColumnIndex(key));
						f.set(aux, value);
					}
					result.add(aux);
				}
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private Object getValueFromCursor(Cursor cur, String type, int columnIndex) {
		if (type.equalsIgnoreCase("int"))
			return cur.getInt(columnIndex);

        if (type.equalsIgnoreCase("float")) {
            return Float.parseFloat(cur.getString(columnIndex));
        }

		if (type.equalsIgnoreCase("boolean")) {
			boolean obj = Integer.parseInt(cur.getString(columnIndex)) == 1 ? true
					: false;
			return obj;
		}

		return cur.getString(columnIndex);
	}

}
