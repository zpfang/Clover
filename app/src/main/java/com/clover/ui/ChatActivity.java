package com.clover.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.clover.R;
import com.clover.adapter.MSListViewAdapter;
import com.clover.entities.User;
import com.clover.utils.CloverApplication;

import java.util.Arrays;
import java.util.List;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ChatActivity extends BaseActivity {
    private Button bt_send;
    private EditText et_input;
    private ListView mListView;
    private String objectId;
    private String msg_content;
    private MSListViewAdapter adapter;
    private CloverApplication application;
    private List<BmobMsg> messageList;
    private User targetUser; //对方的用户
    User woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        application = (CloverApplication) getApplication();
        checkSetting();
        //获取情侣用户对象
        targetUser = application.getRelationship().getW_user();

        messageList = BmobDB.create(this).queryMessages(targetUser.getObjectId(), 1);

        initView();         //初始化视图
        adapter = new MSListViewAdapter(ChatActivity.this, messageList);
        mListView.setAdapter(adapter);
        mListView.setSelection(adapter.getCount() - 1);

        initMsgData();      //加载数据
        ShowLog("设置adapter");

        //注册接受广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("NEW_MESSEAGE_COMING");
        MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(broadcastReceiver, filter);

    }

    private void initView(){
        bt_send = (Button) findViewById(R.id.btn_send);
        et_input = (EditText) findViewById(R.id.et_sendmessage);
        mListView = (ListView) findViewById(R.id.listview);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg_content = et_input.getText().toString();
                if (TextUtils.isEmpty(msg_content) || msg_content == null) {
                    ShowToast("请输入信息内容");
                    return;
                }
                BmobMsg msg = (BmobMsg) BmobMsg.createTextSendMsg(ChatActivity.this, targetUser.getObjectId(), msg_content);
                msg.setExtra("chat");

                ShowLog("添加消息内容：" + msg.getContent());
                adapter.add(msg);
                chatManager.sendTextMessage(targetUser, msg);
                BmobDB.create(ChatActivity.this).saveMessage(msg);
                et_input.setText("");// 清空编辑框数据

                mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
            }
        });
    }


    private void initMsgData() {
        BmobQuery<BmobMsg> query = new BmobQuery<>();
        String[] msgName = {targetUser.getObjectId(), BmobChatUser.getCurrentUser(this).getObjectId()};
        query.addWhereContainedIn("belongId",Arrays.asList(msgName));
        query.findObjects(this, new FindListener<BmobMsg>() {
            @Override
            public void onSuccess(List<BmobMsg> list) {
                ShowLog("信息内容更新成功");
                for (BmobMsg msg : list) {
                    adapter.add(msg);
                    ShowLog("msg内容"+msg.getContent());
                    BmobDB.create(ChatActivity.this).saveMessage(msg);
                }
                mListView.setSelection(adapter.getCount() - 1);

            }

            @Override
            public void onError(int i, String s) {
                ShowLog("加载数据失败");
            }
        });

    }

    private void checkSetting(){

        if(application.getRelationship() == null){
            ShowToast("请设置您的Lover");
            Intent intent = new Intent(this, LoverManagerActivity.class);
            startActivity(intent);
           // finish();
        }

        ShowLog("woman user:" + application.getRelationship().getW_user().getUsername());
        ShowLog("man user:" + application.getRelationship().getM_user().getUsername());
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("msg");
            BmobMsg msg = (BmobMsg) bundle.getSerializable("msg");
            adapter.add(msg);
            BmobDB.create(context).saveMessage(msg);
            mListView.setSelection(adapter.getCount() - 1);
        }
    }

}
