package com.stark.sueldoensegundos;

import android.content.Context;
import android.os.AsyncTask;

import java.text.DecimalFormat;

public class GetSalaryTask extends AsyncTask<Void, Float, Void> {

    private Context context;
    private boolean keep = true;
    public GetSalaryTask(Context c){
        context = c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        while(keep) {
            float salary = new SalaryCalc(context).getSalaryNow();
            publishProgress(salary);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Float... values) {
        super.onProgressUpdate(values);
        ((ISalarySetter)context).setSalary(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        keep = false;
    }
}


