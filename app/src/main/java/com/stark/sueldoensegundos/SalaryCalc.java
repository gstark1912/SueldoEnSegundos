package com.stark.sueldoensegundos;

import android.content.Context;

import com.stark.SQLite.Tables.SettingsLite;
import com.stark.SQLite.Utils.SQLiteMapper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SalaryCalc {
    Context _cont;
    private final int secondsPerDay = 86400;

    public SalaryCalc(Context cont){
        _cont = cont;
    }

    public float getSalaryNow(){
        SettingsLite setting = SQLiteMapper.Map(new SettingsLite(_cont).select(),SettingsLite.class).get(0);
        Date now = new Date();
        float salaryPerSecond;
        float actualSalary;
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        Calendar mycal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        salaryPerSecond = setting.Salary/mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // Por dia
        salaryPerSecond = salaryPerSecond / 24; // Por hora
        salaryPerSecond = salaryPerSecond / 60; // Por minuto
        salaryPerSecond = salaryPerSecond / 60; // Por segundo

        actualSalary = (cal.get(Calendar.DAY_OF_MONTH)*24*60*60*salaryPerSecond);
        actualSalary += (cal.get(Calendar.HOUR_OF_DAY)*60*60*salaryPerSecond);
        actualSalary += (cal.get(Calendar.MINUTE)*60*salaryPerSecond);
        actualSalary += (cal.get(Calendar.SECOND)*salaryPerSecond);

        /*
        cal.get(Calendar.DAY_OF_MONTH);
        cal.get(Calendar.MONTH);// Jan = 0, dec = 11
        cal.get(Calendar.MINUTE);
        cal.get(Calendar.HOUR_OF_DAY);// 24 hour clock
        cal.get(Calendar.SECOND);
        */

        return actualSalary;
    }
}
