package com.clover.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.clover.R;

import cn.bmob.push.PushConstants;

/**
 * Created by dan on 2015/6/26.
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 获取推送消息
        String message = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
        Log.i("BmobClient", "收到的推送消息："+message);
        // 发送通知
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification n = new Notification();
        n.icon = R.mipmap.ic_launcher;
        n.tickerText = "BmobExample收到消息推送";
        n.when = System.currentTimeMillis();
        //n.flags=Notification.FLAG_ONGOING_EVENT;
        Intent i = new Intent();
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
        n.setLatestEventInfo(context, "消息", message, pi);
        n.defaults |= Notification.DEFAULT_SOUND;
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(1, n);
        /*
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容：" + intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
        }
        */
    }

}