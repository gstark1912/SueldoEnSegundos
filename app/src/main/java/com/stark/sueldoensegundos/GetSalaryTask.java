package com.stark.sueldoensegundos;

import android.content.Context;
import android.os.AsyncTask;

import java.text.DecimalFormat;

public class GetSalaryTask extends AsyncTask<Void, String, Void> {

    private Context context;
    private boolean keep = true;
    public GetSalaryTask(Context c){
        context = c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        while(keep) {
            String salary = new SalaryCalc(context).getSalaryNow();
            publishProgress(salary);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        ((SalaryActivity)context).setSalary(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        keep = false;
    }
}

