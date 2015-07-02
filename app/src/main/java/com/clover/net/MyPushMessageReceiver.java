package com.clover.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.inteface.OnReceiveListener;

/**
 * Created by dan on 2015/6/26.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    String tag = null;
    String SLEEP_ACTION = "我睡觉啦";
    String message;
    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(final Context context, Intent intent) {
        String json = intent.getStringExtra("msg");
        Toast.makeText(context, json, Toast.LENGTH_LONG).show();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String ex = jsonObject.getString("ex");
            if(ex.equals("ex")){
                BmobChatManager.getInstance(context).createReceiveMsg(json, new OnReceiveListener() {
                    @Override
                    public void onSuccess(BmobMsg bmobMsg) {
                        Intent intent1 = new Intent("NEW_MESSEAGE_COMING");
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("msg", bmobMsg);
                        intent1.putExtra("msg", bundle);
                        context.sendBroadcast(intent1);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}