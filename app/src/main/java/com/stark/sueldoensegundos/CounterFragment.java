package com.stark.sueldoensegundos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.stark.SQLite.Tables.SettingsLite;
import com.stark.SQLite.Utils.SQLiteMapper;

public class CounterFragment extends Fragment {

    boolean num9 = true;
    boolean num8 = true;
    boolean num7 = true;

    TextSwitcher txt9;
    TextSwitcher txt8;
    TextSwitcher txt7;
    TextSwitcher txt6;
    TextSwitcher txt5;
    TextSwitcher txt4;
    TextSwitcher txt3;
    TextSwitcher txt2;
    TextSwitcher txt1;
    TextSwitcher txtDec1;
    TextSwitcher txtDec2;
    Context ctx;



    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            TextView myText = new TextView(ctx);
            myText.setTextAppearance(ctx,R.style.counterTextView);
            return myText;
        }
    };

    public CounterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.counter_fragment, container, true);
        ctx = getActivity();
        SettingsLite set = SQLiteMapper.Map(new SettingsLite(ctx).select(), SettingsLite.class).get(0);
        removeUselessTextViews(set.Salary, v);

        txt9 = (TextSwitcher) v.findViewById(R.id.counter_txtNum9);
        txt9.setFactory(mFactory);
        setTextAnimation(txt9);
        txt8 = (TextSwitcher) v.findViewById(R.id.counter_txtNum8);
        txt8.setFactory(mFactory);
        setTextAnimation(txt8);
        txt7 = (TextSwitcher) v.findViewById(R.id.counter_txtNum7);
        txt7.setFactory(mFactory);
        setTextAnimation(txt7);
        txt6 = (TextSwitcher) v.findViewById(R.id.counter_txtNum6);
        txt6.setFactory(mFactory);
        setTextAnimation(txt6);
        txt5 = (TextSwitcher) v.findViewById(R.id.counter_txtNum5);
        txt5.setFactory(mFactory);
        setTextAnimation(txt5);
        txt4 = (TextSwitcher) v.findViewById(R.id.counter_txtNum4);
        txt4.setFactory(mFactory);
        setTextAnimation(txt4);
        txt3 = (TextSwitcher) v.findViewById(R.id.counter_txtNum3);
        txt3.setFactory(mFactory);
        setTextAnimation(txt3);
        txt2 = (TextSwitcher) v.findViewById(R.id.counter_txtNum2);
        txt2.setFactory(mFactory);
        setTextAnimation(txt2);
        txt1 = (TextSwitcher) v.findViewById(R.id.counter_txtNum1);
        txt1.setFactory(mFactory);
        setTextAnimation(txt1);
        txtDec1 = (TextSwitcher) v.findViewById(R.id.counter_txtDec1);
        txtDec1.setFactory(mFactory);
        setTextAnimation(txtDec1);
        txtDec2 = (TextSwitcher) v.findViewById(R.id.counter_txtDec2);
        txtDec2.setFactory(mFactory);
        setTextAnimation(txtDec2);
        return v;
    }

    private void setTextAnimation(TextSwitcher txt) {
        if(txt!=null) {
            txt.setInAnimation(ctx, R.anim.abc_slide_in_bottom);
            txt.setOutAnimation(ctx, R.anim.abc_slide_out_top);
        }
    }

    private void removeUselessTextViews(float salary, View v) {
        ViewGroup container = ((ViewGroup) v.findViewById(R.id.counter_container));
        if ((salary / 100000000) < 1) {
            container.removeView(v.findViewById(R.id.counter_txtNum9));
            num9 = false;
        }
        if ((salary / 10000000) < 1) {
            container.removeView(v.findViewById(R.id.counter_txtNum8));
            num8 = false;
        }
        if ((salary / 1000000) < 1) {
            container.removeView(v.findViewById(R.id.counter_txtNum7));
            container.removeView(v.findViewById(R.id.counter_txtMillionSeparator));
            num7 = false;
        }
    }

    public void setSalary(float salary) {
        int subSalary = (int) salary;
        int n9 = (int) (subSalary / 100000000);
        subSalary -= (n9 * 100000000);
        int n8 = (int) (subSalary / 100000000);
        subSalary -= (n8 * 100000000);
        int n7 = (int) (subSalary / 1000000);
        subSalary -= (n7 * 1000000);
        int n6 = (int) (subSalary / 100000);
        subSalary -= (n6 * 100000);
        int n5 = (int) (subSalary / 10000);
        subSalary -= (n5 * 10000);
        int n4 = (int) (subSalary / 1000);
        subSalary -= (n4 * 1000);
        int n3 = (int) (subSalary / 100);
        subSalary -= (n3 * 100);
        int n2 = (int) (subSalary / 10);
        subSalary -= (n2 * 10);
        int n1 = (int) (subSalary / 1);
        if (num9) {
            txt9.setText(String.valueOf(n9));
        }
        if (num8) {
            txt8.setText(String.valueOf(n8));
        }
        if (num7) {
            txt7.setText(String.valueOf(n7));
        }
        txt6.setText(String.valueOf(n6));
        txt5.setText(String.valueOf(n5));
        txt4.setText(String.valueOf(n4));
        txt3.setText(String.valueOf(n3));
        txt2.setText(String.valueOf(n2));
        txt1.setText(String.valueOf(n1));

        String s = (salary + "");
        s = s.substring(s.indexOf('.') + 1);
        if (s.length() < 2) s += "0";
        txtDec1.setText(String.valueOf((Integer.parseInt(s.substring(0, 1)))));
        txtDec2.setText(String.valueOf((Integer.parseInt(s.substring(1, 2)))));
    }
}
