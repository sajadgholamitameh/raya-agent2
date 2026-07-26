package com.emroozchand.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MainActivity extends Activity {
    private static final int NAVY = Color.rgb(8,52,114);
    private static final int BLUE = Color.rgb(26,89,181);
    private static final int BG = Color.rgb(245,247,251);
    private static final int TEXT = Color.rgb(22,31,48);
    private static final int MUTED = Color.rgb(112,126,147);
    private static final int GREEN = Color.rgb(20,145,91);
    private static final int RED = Color.rgb(216,72,82);
    private static final int BORDER = Color.rgb(224,229,237);

    private FrameLayout content;
    private LinearLayout nav;
    private int page = 0;

    private final String[][] products = {
            {"میلگرد آجدار ۱۶ A3 ذوب‌آهن", "۳۱,۸۵۰", "تومان / کیلو", "+۱.۲٪", "آهن و فولاد"},
            {"تیرآهن ۱۴ ذوب‌آهن", "۴,۹۸۰,۰۰۰", "تومان / شاخه", "+۰.۷٪", "آهن و فولاد"},
            {"سیمان تیپ ۲ تهران", "۱۳۵,۰۰۰", "تومان / پاکت", "−۰.۴٪", "سیمان و بتن"},
            {"بلوک سیمانی ۲۰ سانت", "۴۲,۰۰۰", "تومان / عدد", "+۰.۸٪", "آجر و بلوک"},
            {"گچ سفیدکاری سمنان", "۹۸,۰۰۰", "تومان / کیسه", "بدون تغییر", "گچ و ملات"},
            {"کاشی پرسلان ۶۰×۱۲۰", "۸۹۰,۰۰۰", "تومان / متر", "+۲.۱٪", "کاشی و سرامیک"},
            {"سنگ تراورتن عباس‌آباد", "۱,۷۵۰,۰۰۰", "تومان / متر", "+۱.۵٪", "سنگ ساختمانی"},
            {"شن بادامی شسته", "۴۳۰,۰۰۰", "تومان / تن", "−۰.۲٪", "شن و ماسه"},
            {"لوله پنج‌لایه ۱۶", "۵۸,۰۰۰", "تومان / متر", "+۰.۳٪", "لوله و اتصالات"},
            {"کابل افشان ۲×۱.۵", "۴۹,۵۰۰", "تومان / متر", "+۱.۰٪", "برق و روشنایی"},
            {"پکیج دیواری ۲۴ هزار", "۲۸,۹۰۰,۰۰۰", "تومان / دستگاه", "−۰.۶٪", "تأسیسات"},
            {"ایزوگام دولایه فویل‌دار", "۱,۲۸۰,۰۰۰", "تومان / رول", "+۰.۹٪", "عایق"},
            {"MDF سفید ۱۶ میل", "۳,۶۸۰,۰۰۰", "تومان / ورق", "+۱.۴٪", "چوب و MDF"},
            {"رنگ اکریلیک سفید", "۱,۱۵۰,۰۰۰", "تومان / گالن", "بدون تغییر", "رنگ و چسب"},
            {"شیر روشویی کروم", "۲,۹۸۰,۰۰۰", "تومان / عدد", "+۰.۵٪", "شیرآلات"}
    };

    private final String[][] sources = {
            {"آهن آنلاین", "آهن و فولاد", "متصل", "امروز ۰۸:۱۰"},
            {"آهن ملل", "آهن و فولاد", "متصل", "امروز ۰۸:۱۲"},
            {"مرکزآهن", "آهن و فولاد", "متصل", "امروز ۰۸:۱۵"},
            {"شهرآهن", "آهن و فولاد", "متصل", "امروز ۰۸:۱۸"},
            {"سیمان‌چی", "سیمان و بتن", "در صف اتصال", "—"},
            {"دیجی‌سنگ", "سنگ ساختمانی", "در صف اتصال", "—"},
            {"دمـاجت", "تأسیسات", "در صف اتصال", "—"},
            {"فروشندگان منتخب", "چند دسته", "ثبت دستی تأمین‌کننده", "امروز"}
    };

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(NAVY);
        getWindow().setNavigationBarColor(Color.WHITE);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        content = new FrameLayout(this);
        root.addView(content, new LinearLayout.LayoutParams(-1,0,1));
        nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(4),dp(7),dp(4),dp(8));
        nav.setBackgroundColor(Color.WHITE);
        root.addView(nav, new LinearLayout.LayoutParams(-1,dp(66)));
        setContentView(root);
        showPage(0);
    }

    private void showPage(int p) {
        page=p; content.removeAllViews(); nav.removeAllViews();
        String[] labels={"خانه","قیمت‌ها","فروشندگان","منابع","حساب"};
        String[] icons={"⌂","₺","▣","↻","●"};
        for(int i=0;i<labels.length;i++){
            final int x=i;
            TextView n=text(icons[i]+"\n"+labels[i],11,i==p?NAVY:MUTED,i==p);
            n.setGravity(Gravity.CENTER); n.setOnClickListener(v->showPage(x));
            nav.addView(n,new LinearLayout.LayoutParams(0,-1,1));
        }
        if(p==0) content.addView(home());
        else if(p==1) content.addView(prices());
        else if(p==2) content.addView(suppliers());
        else if(p==3) content.addView(sourcePage());
        else content.addView(account());
    }

    private View home(){
        LinearLayout body=body();
        LinearLayout hero=box(NAVY,18);
        TextView title=text("امروز چند",30,Color.WHITE,true); hero.addView(title);
        hero.addView(text("مرجع قیمت روز مصالح ساختمانی",14,Color.rgb(218,231,251),false));
        TextView live=text("● بروزرسانی روزانه قیمت‌ها",12,Color.rgb(143,239,190),true); live.setPadding(0,dp(12),0,0); hero.addView(live);
        body.addView(hero,margin(0,0,0,14));
        EditText search=new EditText(this); search.setHint("جستجوی مصالح، برند یا کارخانه..."); search.setTextSize(14); search.setSingleLine(true); search.setPadding(dp(14),0,dp(14),0); search.setBackground(round(Color.WHITE,14,BORDER));
        body.addView(search,new LinearLayout.LayoutParams(-1,dp(52)));
        body.addView(section("دسته‌بندی مصالح","مشاهده همه"));
        String[] cats={"آهن و فولاد","سیمان و بتن","آجر و بلوک","گچ و ملات","کاشی و سرامیک","سنگ ساختمانی","شن و ماسه","لوله و اتصالات","برق و روشنایی","تأسیسات","عایق","چوب و MDF","رنگ و چسب","شیرآلات"};
        HorizontalScrollView hsv=new HorizontalScrollView(this); hsv.setHorizontalScrollBarEnabled(false);
        LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL); row.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        String[] ci={"▥","▦","▤","◫","◆","▰","●","⌁","ϟ","◉","▧","▣","◍","♢"};
        for(int i=0;i<cats.length;i++){
            LinearLayout c=box(Color.WHITE,14); c.setGravity(Gravity.CENTER); c.setPadding(dp(12),dp(12),dp(12),dp(12));
            TextView ic=text(ci[i],24,NAVY,true); ic.setGravity(Gravity.CENTER); c.addView(ic);
            TextView tx=text(cats[i],11,TEXT,true); tx.setGravity(Gravity.CENTER); tx.setPadding(0,dp(6),0,0); c.addView(tx);
            LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(dp(105),dp(92)); cp.setMargins(dp(5),0,dp(5),0); row.addView(c,cp);
        }
        hsv.addView(row); body.addView(hsv);
        body.addView(section("قیمت‌های منتخب امروز","همه قیمت‌ها"));
        for(int i=0;i<5;i++) body.addView(productCard(products[i],i));
        LinearLayout stats=box(Color.WHITE,16);
        stats.addView(text("پوشش بازار امروز",16,TEXT,true));
        LinearLayout sr=new LinearLayout(this); sr.setOrientation(LinearLayout.HORIZONTAL);
        sr.addView(metric("۱۵","گروه مصالح"),new LinearLayout.LayoutParams(0,-2,1));
        sr.addView(metric("۲۸","منبع شناسایی‌شده"),new LinearLayout.LayoutParams(0,-2,1));
        sr.addView(metric("۲۴h","چرخه بروزرسانی"),new LinearLayout.LayoutParams(0,-2,1));
        stats.addView(sr); body.addView(stats,margin(0,8,0,18));
        return scroll(body);
    }

    private View prices(){
        LinearLayout body=body(); body.addView(header("قیمت همه مصالح","۱۵ گروه · داده نمایشی قابل توسعه"));
        EditText q=new EditText(this); q.setHint("جستجو در قیمت‌ها..."); q.setSingleLine(true); q.setTextSize(14); q.setBackground(round(Color.WHITE,14,BORDER)); q.setPadding(dp(14),0,dp(14),0); body.addView(q,new LinearLayout.LayoutParams(-1,dp(50)));
        body.addView(section("آخرین قیمت‌ها","مرتب‌سازی"));
        for(int i=0;i<products.length;i++) body.addView(productCard(products[i],i));
        return scroll(body);
    }

    private View productCard(String[] p,int index){
        LinearLayout c=box(Color.WHITE,15); c.setPadding(dp(15),dp(14),dp(15),dp(14));
        TextView cat=text(p[4],11,BLUE,true); c.addView(cat);
        TextView name=text(p[0],15,TEXT,true); name.setPadding(0,dp(5),0,0); c.addView(name);
        LinearLayout line=new LinearLayout(this); line.setOrientation(LinearLayout.HORIZONTAL); line.setGravity(Gravity.CENTER_VERTICAL);
        TextView change=text(p[3],12,p[3].startsWith("+")?GREEN:p[3].startsWith("−")?RED:MUTED,true); change.setGravity(Gravity.LEFT); line.addView(change,new LinearLayout.LayoutParams(0,-2,1));
        LinearLayout price=new LinearLayout(this); price.setOrientation(LinearLayout.VERTICAL); price.setGravity(Gravity.RIGHT);
        price.addView(text(p[1],18,NAVY,true)); price.addView(text(p[2],10,MUTED,false)); line.addView(price);
        c.addView(line);
        final int x=index; c.setOnClickListener(v->showProduct(x));
        return withMargin(c,0,0,0,10);
    }

    private void showProduct(int index){
        content.removeAllViews(); final String[] p=products[index];
        LinearLayout body=body();
        TextView back=text("‹ بازگشت",14,NAVY,true); back.setOnClickListener(v->showPage(1)); body.addView(back);
        body.addView(header(p[0],p[4]));
        LinearLayout price=box(NAVY,18); price.addView(text("قیمت مرجع امروز",12,Color.rgb(205,222,248),false)); price.addView(text(p[1]+"  "+p[2],24,Color.WHITE,true)); price.addView(text(p[3]+" نسبت به روز قبل",12,p[3].startsWith("+")?Color.rgb(148,242,190):Color.rgb(255,180,185),true)); body.addView(price,margin(0,0,0,14));
        LinearLayout chart=box(Color.WHITE,16); chart.addView(text("روند ۳۰ روزه",16,TEXT,true)); chart.addView(new TrendView(this),new LinearLayout.LayoutParams(-1,dp(180))); body.addView(chart,margin(0,0,0,14));
        body.addView(section("پیشنهاد فروشندگان","۳ فروشنده"));
        addSeller(body,"بازرگانی آهن مرکزی",p[1],"تهران · ارسال امروز","۴.۸");
        addSeller(body,"مصالح پارس","۳۲,۰۵۰","کرج · تحویل ۲۴ ساعته","۴.۶");
        addSeller(body,"بازار سازه","۳۲,۳۰۰","تهران · فروش عمده","۴.۵");
        TextView watch=button("افزودن به دیده‌بان قیمت"); watch.setOnClickListener(v->Toast.makeText(this,"به دیده‌بان اضافه شد",Toast.LENGTH_SHORT).show()); body.addView(watch,margin(0,6,0,20));
        content.addView(scroll(body));
    }

    private View suppliers(){
        LinearLayout body=body(); body.addView(header("فروشندگان منتخب","تأمین‌کنندگان مصالح و تجهیزات"));
        addSeller(body,"بازرگانی آهن مرکزی","قیمت مستقیم کارخانه","آهن و فولاد · تهران","۴.۸");
        addSeller(body,"فروشگاه مصالح پارس","فروش عمده و خرده","سیمان، گچ و بلوک · کرج","۴.۷");
        addSeller(body,"تأسیسات نوین","تأمین پروژه‌های ساختمانی","لوله، شیرآلات و مکانیک","۴.۶");
        addSeller(body,"خانه سرامیک","ارسال سراسر کشور","کاشی، سرامیک و سنگ","۴.۵");
        addSeller(body,"برق ساختمان ایرانیان","فروش برندهای معتبر","کابل و روشنایی","۴.۷");
        return scroll(body);
    }

    private void addSeller(LinearLayout body,String name,String price,String meta,String rate){
        LinearLayout c=box(Color.WHITE,16); c.addView(text(name,16,TEXT,true));
        TextView m=text(meta,12,MUTED,false); m.setPadding(0,dp(5),0,dp(8)); c.addView(m);
        LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.HORIZONTAL); r.setGravity(Gravity.CENTER_VERTICAL);
        r.addView(text("★ "+rate,13,Color.rgb(234,166,31),true),new LinearLayout.LayoutParams(0,-2,1));
        TextView pr=text(price,14,NAVY,true); pr.setGravity(Gravity.RIGHT); r.addView(pr); c.addView(r);
        TextView contact=text("مشاهده پروفایل و تماس",12,BLUE,true); contact.setPadding(0,dp(10),0,0); c.addView(contact);
        c.setOnClickListener(v->Toast.makeText(this,"پروفایل فروشنده",Toast.LENGTH_SHORT).show()); body.addView(c,margin(0,0,0,10));
    }

    private View sourcePage(){
        LinearLayout body=body(); body.addView(header("منابع و بروزرسانی","شفافیت وضعیت دریافت قیمت"));
        LinearLayout summary=box(NAVY,18); summary.addView(text("وضعیت سامانه جمع‌آوری",16,Color.WHITE,true));
        summary.addView(text("۴ منبع آزمایشی فعال · ۲۴ منبع در صف اتصال",13,Color.rgb(211,226,249),false));
        summary.addView(text("آخرین بروزرسانی: امروز ۰۸:۱۸",12,Color.rgb(145,239,190),true)); body.addView(summary,margin(0,0,0,14));
        for(String[] s:sources){
            LinearLayout c=box(Color.WHITE,15); c.addView(text(s[0],15,TEXT,true));
            c.addView(text(s[1],12,MUTED,false));
            LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.HORIZONTAL); r.setPadding(0,dp(9),0,0);
            int col=s[2].equals("متصل")?GREEN:s[2].contains("صف")?Color.rgb(225,145,28):BLUE;
            r.addView(text("● "+s[2],12,col,true),new LinearLayout.LayoutParams(0,-2,1));
            TextView tm=text(s[3],11,MUTED,false); tm.setGravity(Gravity.RIGHT); r.addView(tm); c.addView(r);
            body.addView(c,margin(0,0,0,10));
        }
        LinearLayout note=box(Color.rgb(235,243,255),14); note.addView(text("نحوه محاسبه قیمت مرجع",14,NAVY,true)); note.addView(text("قیمت‌های معتبر پس از یکسان‌سازی واحد، حذف داده‌های قدیمی و کنترل داده پرت با میانه چند منبع محاسبه می‌شوند.",12,MUTED,false)); body.addView(note,margin(0,6,0,20));
        return scroll(body);
    }

    private View account(){
        LinearLayout body=body(); body.addView(header("حساب و اعتماد","نسخه آزمایشی امروز چند"));
        LinearLayout profile=box(NAVY,18); profile.addView(text("کاربر مهمان",21,Color.WHITE,true)); profile.addView(text("برای ذخیره دیده‌بان و سفارش‌ها وارد شوید",12,Color.rgb(210,226,249),false)); body.addView(profile,margin(0,0,0,14));
        String[] items={"♡ دیده‌بان قیمت","▣ سفارش‌ها و استعلام‌ها","♢ فروشنده هستم","✓ احراز هویت و اعتماد","◉ تنظیمات اعلان‌ها","؟ راهنما و پشتیبانی"};
        for(String s:items){ TextView t=text(s+"        ‹",15,TEXT,true); t.setPadding(dp(16),dp(17),dp(16),dp(17)); t.setBackground(round(Color.WHITE,14,BORDER)); t.setOnClickListener(v->Toast.makeText(this,"این بخش در نسخه بعدی تکمیل می‌شود",Toast.LENGTH_SHORT).show()); body.addView(t,margin(0,0,0,9)); }
        TextView login=button("ورود / ساخت حساب"); body.addView(login,margin(0,8,0,20));
        return scroll(body);
    }

    private LinearLayout body(){ LinearLayout b=new LinearLayout(this); b.setOrientation(LinearLayout.VERTICAL); b.setPadding(dp(16),dp(16),dp(16),dp(24)); b.setLayoutDirection(View.LAYOUT_DIRECTION_RTL); return b; }
    private ScrollView scroll(View v){ ScrollView s=new ScrollView(this); s.setFillViewport(true); s.setBackgroundColor(BG); s.addView(v); return s; }
    private LinearLayout header(String a,String b){ LinearLayout h=new LinearLayout(this); h.setOrientation(LinearLayout.VERTICAL); h.setPadding(0,dp(3),0,dp(18)); h.addView(text(a,24,TEXT,true)); h.addView(text(b,12,MUTED,false)); return h; }
    private LinearLayout section(String a,String b){ LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.HORIZONTAL); r.setGravity(Gravity.CENTER_VERTICAL); r.setPadding(0,dp(22),0,dp(10)); TextView more=text(b,12,BLUE,true); more.setGravity(Gravity.LEFT); r.addView(more,new LinearLayout.LayoutParams(0,-2,1)); TextView title=text(a,17,TEXT,true); title.setGravity(Gravity.RIGHT); r.addView(title); return r; }
    private TextView metric(String a,String b){ TextView t=text(a+"\n"+b,12,TEXT,true); t.setGravity(Gravity.CENTER); t.setPadding(dp(3),dp(14),dp(3),dp(6)); return t; }
    private TextView button(String s){ TextView t=text(s,15,Color.WHITE,true); t.setGravity(Gravity.CENTER); t.setPadding(dp(12),dp(15),dp(12),dp(15)); t.setBackground(round(BLUE,14,BLUE)); return t; }
    private TextView text(String s,int size,int color,boolean bold){ TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setGravity(Gravity.RIGHT); t.setLayoutDirection(View.LAYOUT_DIRECTION_RTL); t.setLineSpacing(0,1.12f); if(bold)t.setTypeface(Typeface.create("sans-serif",Typeface.BOLD)); else t.setTypeface(Typeface.create("sans-serif",Typeface.NORMAL)); return t; }
    private LinearLayout box(int color,int radius){ LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(dp(16),dp(15),dp(16),dp(15)); l.setBackground(round(color,radius,color==Color.WHITE?BORDER:color)); l.setElevation(dp(1)); return l; }
    private GradientDrawable round(int color,int radius,int stroke){ GradientDrawable g=new GradientDrawable(); g.setColor(color); g.setCornerRadius(dp(radius)); g.setStroke(dp(1),stroke); return g; }
    private LinearLayout.LayoutParams margin(int l,int t,int r,int b){ LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2); p.setMargins(dp(l),dp(t),dp(r),dp(b)); return p; }
    private View withMargin(View v,int l,int t,int r,int b){ v.setLayoutParams(margin(l,t,r,b)); return v; }
    private int dp(int v){ return Math.round(v*getResources().getDisplayMetrics().density); }

    public static final class TrendView extends View {
        private final Paint line=new Paint(1), grid=new Paint(1), fill=new Paint(1);
        private final float[] values={38,43,41,48,46,52,55,51,59,62,58,66,64,71,69,75,72,78,82,79,85,88,84,91,89,94,92,97,95,100};
        public TrendView(Activity c){ super(c); line.setColor(BLUE); line.setStrokeWidth(5); line.setStyle(Paint.Style.STROKE); line.setStrokeCap(Paint.Cap.ROUND); line.setStrokeJoin(Paint.Join.ROUND); grid.setColor(Color.rgb(232,236,243)); grid.setStrokeWidth(2); fill.setColor(Color.argb(25,26,89,181)); }
        @Override protected void onDraw(Canvas c){ super.onDraw(c); float w=getWidth(),h=getHeight(),pad=16; for(int i=1;i<4;i++)c.drawLine(pad,h*i/4,w-pad,h*i/4,grid); Path p=new Path(),f=new Path(); for(int i=0;i<values.length;i++){ float x=pad+i*(w-2*pad)/(values.length-1); float y=h-pad-(values[i]-35)/70f*(h-2*pad); if(i==0){p.moveTo(x,y);f.moveTo(x,h-pad);f.lineTo(x,y);} else {p.lineTo(x,y);f.lineTo(x,y);} } f.lineTo(w-pad,h-pad);f.close();c.drawPath(f,fill);c.drawPath(p,line); }
    }
}
