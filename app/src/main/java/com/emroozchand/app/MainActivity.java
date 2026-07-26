package com.emroozchand.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public final class MainActivity extends Activity {
    private static final int NAVY = Color.rgb(8, 52, 114);
    private static final int BG = Color.rgb(245, 247, 251);
    private static final int TEXT = Color.rgb(24, 34, 53);
    private static final int MUTED = Color.rgb(123, 135, 153);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(NAVY);
        getWindow().setNavigationBarColor(Color.WHITE);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(BG);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), dp(28));
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        scroll.addView(root);

        TextView title = text("امروز چند", 28, Color.WHITE, true);
        title.setGravity(Gravity.RIGHT);
        LinearLayout hero = card(NAVY);
        hero.setPadding(dp(18), dp(20), dp(18), dp(20));
        hero.addView(title);
        TextView subtitle = text("مرجع قیمت روز مصالح ساختمانی", 14, Color.rgb(220,232,250), false);
        subtitle.setPadding(0, dp(6), 0, 0);
        hero.addView(subtitle);
        root.addView(hero, matchWrap(dp(0), dp(14)));

        root.addView(section("دسته‌بندی مصالح"));
        String[] cats = {"آهن و فولاد","سیمان و بتن","آجر و بلوک","کاشی و سرامیک","سنگ ساختمانی","لوله و اتصالات","برق و روشنایی","تأسیسات","عایق","چوب و MDF","رنگ و چسب","شیرآلات"};
        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.setHorizontalScrollBarEnabled(false);
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        for (String c : cats) {
            TextView chip = text(c, 12, NAVY, true);
            chip.setGravity(Gravity.CENTER);
            chip.setBackgroundResource(android.R.drawable.editbox_background_normal);
            chip.setPadding(dp(14), dp(10), dp(14), dp(10));
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-2, -2);
            cp.setMargins(dp(5), 0, dp(5), 0);
            row.addView(chip, cp);
        }
        hsv.addView(row);
        root.addView(hsv, matchWrap(0, dp(12)));

        root.addView(section("قیمت‌های منتخب امروز"));
        addPrice(root, "میلگرد ۱۶ ذوب‌آهن", "۳۱,۸۵۰ تومان / کیلو", "+۱.۲٪");
        addPrice(root, "سیمان تیپ ۲ تهران", "۱۳۵,۰۰۰ تومان / پاکت", "−۰.۴٪");
        addPrice(root, "بلوک سیمانی ۲۰ سانت", "۴۲,۰۰۰ تومان / عدد", "+۰.۸٪");
        addPrice(root, "کاشی پرسلان ۶۰×۱۲۰", "۸۹۰,۰۰۰ تومان / متر", "+۲.۱٪");
        addPrice(root, "لوله پنج‌لایه ۱۶", "۵۸,۰۰۰ تومان / متر", "بدون تغییر");

        root.addView(section("وضعیت منابع"));
        LinearLayout sourceCard = card(Color.WHITE);
        sourceCard.addView(metric("۲۸", "منبع ثبت‌شده"));
        sourceCard.addView(metric("۴", "آداپتور آزمایشی فعال"));
        sourceCard.addView(metric("۲۴h", "چرخه بروزرسانی"));
        TextView note = text("این نسخه فعلاً نمایشی است. قیمت‌های واقعی پس از اتصال بک‌اند و منابع مجاز به‌صورت روزانه بروزرسانی می‌شوند.", 12, MUTED, false);
        note.setPadding(0, dp(12), 0, 0);
        sourceCard.addView(note);
        root.addView(sourceCard, matchWrap(0, dp(12)));

        root.addView(section("تأمین‌کنندگان منتخب"));
        addSupplier(root, "بازرگانی آهن مرکزی", "آهن و فولاد · تهران");
        addSupplier(root, "فروشگاه مصالح پارس", "سیمان، گچ و بلوک · کرج");
        addSupplier(root, "تأسیسات نوین", "لوله، شیرآلات و تجهیزات مکانیکی");

        setContentView(scroll);
    }

    private void addPrice(LinearLayout root, String name, String price, String change) {
        LinearLayout c = card(Color.WHITE);
        TextView n = text(name, 15, TEXT, true);
        c.addView(n);
        TextView p = text(price, 14, NAVY, true);
        p.setPadding(0, dp(7), 0, dp(3));
        c.addView(p);
        int col = change.startsWith("+") ? Color.rgb(21,145,90) : change.startsWith("−") ? Color.rgb(220,80,87) : MUTED;
        c.addView(text(change, 12, col, true));
        root.addView(c, matchWrap(0, dp(10)));
    }

    private void addSupplier(LinearLayout root, String name, String meta) {
        LinearLayout c = card(Color.WHITE);
        c.addView(text(name, 15, TEXT, true));
        TextView m = text(meta, 12, MUTED, false);
        m.setPadding(0, dp(5), 0, 0);
        c.addView(m);
        root.addView(c, matchWrap(0, dp(10)));
    }

    private TextView section(String s) {
        TextView t = text(s, 16, TEXT, true);
        t.setPadding(0, dp(20), 0, dp(10));
        return t;
    }

    private TextView metric(String value, String label) {
        TextView t = text(value + "   " + label, 14, TEXT, true);
        t.setPadding(0, dp(6), 0, dp(6));
        return t;
    }

    private LinearLayout card(int color) {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(16), dp(15), dp(16), dp(15));
        l.setBackgroundColor(color);
        l.setElevation(dp(2));
        return l;
    }

    private TextView text(String s, int sp, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setGravity(Gravity.RIGHT);
        t.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private LinearLayout.LayoutParams matchWrap(int top, int bottom) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, top, 0, bottom);
        return p;
    }

    private int dp(int v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }
}
