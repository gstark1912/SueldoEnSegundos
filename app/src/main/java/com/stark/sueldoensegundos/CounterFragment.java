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

    int n9 = 0;
    int n8 = 0;
    int n7 = 0;
    int n6 = 0;
    int n5 = 0;
    int n4 = 0;
    int n3 = 0;
    int n2 = 0;
    int n1 = 0;
    int dec1 = 0;
    int dec2 = 0;

    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {
            TextView myText = new TextView(ctx);
            myText.setTextAppearance(ctx, R.style.counterTextView);
            return myText;
        }
    };

    public CounterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.counter_fragment, container, false);
        ctx = getActivity();
        SettingsLite set = SQLiteMapper.Map(new SettingsLite(ctx).select(), SettingsLite.class).get(0);
        removeUselessTextViews(set.Salary, v);

        txt9 = (TextSwitcher) v.findViewById(R.id.counter_txtNum9);
        setTextAnimation(txt9);
        txt8 = (TextSwitcher) v.findViewById(R.id.counter_txtNum8);
        setTextAnimation(txt8);
        txt7 = (TextSwitcher) v.findViewById(R.id.counter_txtNum7);
        setTextAnimation(txt7);
        txt6 = (TextSwitcher) v.findViewById(R.id.counter_txtNum6);
        setTextAnimation(txt6);
        txt5 = (TextSwitcher) v.findViewById(R.id.counter_txtNum5);
        setTextAnimation(txt5);
        txt4 = (TextSwitcher) v.findViewById(R.id.counter_txtNum4);
        setTextAnimation(txt4);
        txt3 = (TextSwitcher) v.findViewById(R.id.counter_txtNum3);
        setTextAnimation(txt3);
        txt2 = (TextSwitcher) v.findViewById(R.id.counter_txtNum2);
        setTextAnimation(txt2);
        txt1 = (TextSwitcher) v.findViewById(R.id.counter_txtNum1);
        setTextAnimation(txt1);
        txtDec1 = (TextSwitcher) v.findViewById(R.id.counter_txtDec1);
        setTextAnimation(txtDec1);
        txtDec2 = (TextSwitcher) v.findViewById(R.id.counter_txtDec2);
        setTextAnimation(txtDec2);
        return v;
    }

    private void setTextAnimation(TextSwitcher txt) {
        if (txt != null) {
            txt.setFactory(mFactory);
            txt.setInAnimation(ctx, R.anim.abc_slide_in_top);
            txt.setOutAnimation(ctx, R.anim.abc_slide_out_bottom);
            txt.setCurrentText("0");
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

        if (num9)
            n9 = changeTextIfNecessary(txt9, n9, (subSalary / 100000000));
        subSalary -= (n9 * 100000000);
        if (num8)
            n8 = changeTextIfNecessary(txt8, n8, (subSalary / 10000000));
        subSalary -= (n8 * 10000000);
        if (num7)
            n7 = changeTextIfNecessary(txt7, n7, (subSalary / 1000000));
        subSalary -= (n7 * 1000000);
        n6 = changeTextIfNecessary(txt6, n6, (subSalary / 100000));
        subSalary -= (n6 * 100000);
        n5 = changeTextIfNecessary(txt5, n5, (subSalary / 10000));
        subSalary -= (n5 * 10000);
        n4 = changeTextIfNecessary(txt4, n4, (subSalary / 1000));
        subSalary -= (n4 * 1000);
        n3 = changeTextIfNecessary(txt3, n3, (subSalary / 100));
        subSalary -= (n3 * 100);
        n2 = changeTextIfNecessary(txt2, n2, (subSalary / 10));
        subSalary -= (n2 * 10);
        n1 = changeTextIfNecessary(txt1, n1, (subSalary / 1));

        String s = (salary + "");
        s = s.substring(s.indexOf('.') + 1);
        if (s.length() < 2) s += "0";

        dec1 = changeTextIfNecessary(txtDec1, dec1, Integer.parseInt(s.substring(0, 1)));
        dec2 = changeTextIfNecessary(txtDec2, dec2, Integer.parseInt(s.substring(1, 2)));
    }

    private int changeTextIfNecessary(TextSwitcher txt, int n, int newValue) {
        if (n != newValue) {
            n = newValue;
            txt.setText(String.valueOf(n));
        }
        return n;
    }
}
