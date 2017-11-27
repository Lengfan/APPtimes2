package com.gaoli.apptimes;

import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by gaoli on 2017/11/20.
 */
public class MainActivityTest extends AppCompatActivity {
    private MainActivity mMainactivity1=new MainActivity();
    @Test
    public void getUsedApps() throws Exception {
        Map<String,Integer> appandtimes=new HashMap<String,Integer>();
        appandtimes.put("QQ",5000);
        appandtimes.put("cloudmusic",10000);
        assertEquals(mMainactivity1.getUsedApps(appandtimes),2);
    }
    @Test
    public void getUsedApps2() throws Exception {
        Map<String,Integer> appandtimes=new HashMap<String,Integer>();
        appandtimes.put("QQ",5000);
        appandtimes.put("cloudmusic",10000);
        assertEquals(mMainactivity1.getUsedApps(appandtimes),3);
    }
}