package com.stark.SQLite.Tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.stark.SQLite.Utils.SQLLiteTable;

public class SettingsLite extends SQLLiteTable {
	public float Salary;

	public SettingsLite(Context cont) {
		super(cont);
	}

	public SettingsLite() {
	}

    public void Insert(float salary) {
        String query;
        query = "INSERT or replace INTO ";
        query += getTableName() + " (Salary)";
        query += " VALUES("+salary+")";

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public void Update(float salary) {
        String query;
        query = "UPDATE ";
        query += getTableName() + " SET Salary = "+salary+"";

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
