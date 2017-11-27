package com.gaoli.apptimes;

import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS=1101;
   // private List<String> appname=new ArrayList<String>();
   // private List<String> videolist = new ArrayList<String>();
   // private int[] times=new int[100];
    private List<String> theappname=new ArrayList<String>();
    private List<String>secTheappname=new ArrayList<String>();
    public  int updateui=1;
    private Map<String,Integer> appandtimes=new HashMap<String,Integer>();
    private String TherunningApp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.apptimelist);
        Log.i("test", "update UI ...");
        secTheappname.addAll(theappname);
        secTheappname.add("amdg");
        secTheappname.add("smssss");
        ApplistviewAdapter myadapter = new ApplistviewAdapter(MainActivity.this, secTheappname);
             listView.setAdapter(myadapter);
                secTheappname.clear();
        new Thread(new Runnable(){
            @Override
            public void run() {
                PowerManager powerManager = (PowerManager) MainActivity.this.getSystemService(Context.POWER_SERVICE);
//true为打开，false为关闭
                KeyguardManager mKeyguardManager = (KeyguardManager) MainActivity.this.getSystemService(Context.KEYGUARD_SERVICE);
                //boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
                while(true){
                    try {
                        if(powerManager.isScreenOn()&&!mKeyguardManager.inKeyguardRestrictedInputMode())checkPermission();
                        sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(updateui==1) {
                    }
                }
            }
        }).start();
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i("test11","resumed");
        secTheappname.clear();
        ListView listView = (ListView) findViewById(R.id.apptimelist);
        Log.i("test", "update UI ...");
        secTheappname.addAll(theappname);
        ApplistviewAdapter myadapter = new ApplistviewAdapter(MainActivity.this, secTheappname);
        listView.setAdapter(myadapter);
        theappname.clear();
    }
    private boolean hasPermission(){
        AppOpsManager appOps=(AppOpsManager)getSystemService(Context.APP_OPS_SERVICE);
        int mode=0;
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
            mode=appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,android.os.Process.myUid(),getPackageName());

        }
        return mode==AppOpsManager.MODE_ALLOWED;
    }
    protected void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            UsageStatsManager m=(UsageStatsManager)MainActivity.this.getSystemService(Context.USAGE_STATS_SERVICE);
            if(m!=null){
                long now= System.currentTimeMillis();
                List<UsageStats>stats=m.queryUsageStats(UsageStatsManager.INTERVAL_BEST,now-60*1000,now);

                //Log.i("test","Runing app number in last 1 seconds:"+stats.size());
                String topActivity="";
                if((stats!=null)&&(!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                    if (topActivity.equals("com.gaoli.apptimes") || topActivity.equals("")) {

                        if(updateui==-1)updateui=1;
                        Log.i("test", "xiaomazi");
                        if(topActivity.equals(""))topActivity=TherunningApp;
                    } else {
                        updateui = -1;
                        TherunningApp=topActivity;
                    }
                    //sleep(500);
                    if(m.isAppInactive(TherunningApp)){
                        Log.i("test1",TherunningApp+"is runing......");
                    }
                    if (!appandtimes.isEmpty()) {

                        if (appandtimes.get(topActivity) == null) {
                            appandtimes.put(topActivity, 500);
                        } else {
                            int timee = appandtimes.get(topActivity);
                            appandtimes.put(topActivity, timee + 500);
                        }
                    } else {
                        appandtimes.put(topActivity, 500);
                    }
                    theappname.clear();
                    for (Map.Entry<String,Integer> entry : appandtimes.entrySet()) {
                        theappname.add("使用 "+entry.getKey()+"  :  "+Double.toString((double)entry.getValue()/1000)+"秒");
                    }
                }
                Log.i("test","top runing app is:"+topActivity);
            }
        }

    }
    public int getUsedApps(Map<String,Integer> appandtimes){
        return 0;//appandtimes.size();
    }
}
