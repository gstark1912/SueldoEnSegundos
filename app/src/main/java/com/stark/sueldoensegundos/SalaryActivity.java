package com.stark.sueldoensegundos;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SalaryActivity extends FragmentActivity implements ISalarySetter  {
    GetSalaryTask t;
    CounterFragment firstFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);
        if (findViewById(R.id.salary_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            firstFragment = new CounterFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.salary_fragment_container, firstFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        t = new GetSalaryTask(this);
        t.execute();
    }

    @Override
    protected void onPause(){
        super.onPause();
        t.cancel(true);
        t = null;
    }

    @Override
    public void setSalary(float salary) {
        if(salary>0)
            firstFragment.setSalary(salary);
    }
}
