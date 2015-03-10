package com.stark.SQLite.Utils;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.stark.SQLite.Tables.SettingsLite;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName = "Sueldos";
	private ArrayList<SQLLiteTable> tables = getTablesToLoad();

	public DatabaseHelper(Context context) {
		super(context, dbName, null, 53);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		getTablesToLoad();
		for (SQLLiteTable h : tables) {
			String query = h.getCreateTableScript();
			if (query != null)
				db.execSQL(query);
		}

		updateDatabaseValues(db);
	}

	public void updateDatabaseValues(SQLiteDatabase db) {
		for (SQLLiteTable h : tables) {
			String query = h.getInsertScript();
			if (query != null)
				db.execSQL(query);
		}
	}

	// Ac� se agregar las SQLLiteTable (tablas) a crear en el OnCreate
	private ArrayList<SQLLiteTable> getTablesToLoad() {
		tables = new ArrayList<SQLLiteTable>();
		tables.add(new SettingsLite());
		return tables;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		restartDB(db);

	}

	public void restartDB(SQLiteDatabase db) {
		for (SQLLiteTable h : tables) {
			db.execSQL(h.getDropTableScript());
		}
		onCreate(db);
	}
}