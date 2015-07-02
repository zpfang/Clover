package com.clover.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.clover.R;
import com.clover.entities.Relationship;
import com.clover.entities.User;
import com.clover.ui.frgms.GamePage;
import com.clover.ui.frgms.MainPage;
import com.clover.ui.frgms.UserPage;
import com.clover.utils.CloverApplication;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends BaseActivity {
    private ViewPager mPager;//页卡内容
    private ArrayList<Fragment> fragments; // Tab页面列表
    private ImageView cursor;// 动画图片
    private TextView t1, t2, t3;// 页卡头标
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private Button btn_Anniversary;//纪念日按钮
    private LayoutInflater inflater;
    private TextView tv_chat;
    private CloverApplication application;
    User woman;
    User man;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bmob.initialize(this, APPID);
        if(userManager.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            application = (CloverApplication)getApplication();
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);

            chatManager = BmobChatManager.getInstance(this);
            InitTextView();
            InitImageView();
            InitViewPager();


            btn_Anniversary = (Button)findViewById(R.id.anniversary);
            //btn_Anniversary.setOnClickListener(new ButtonOnClickListener());

            initApplication();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();



        /*BmobQuery<Relationship> query = new BmobQuery<Relationship>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY );
        query.findObjects(this, new FindListener<Relationship>() {
            @Override
            public void onSuccess(List<Relationship> list) {
                application.setRelationship(list.get(0));
            }

            @Override
            public void onError(int i, String s) {

            }
        });
*/


    }

    /**
     * 初始化ViewPager
     */
    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragments = new ArrayList<Fragment>();
        Fragment fragment1 = new MainPage();
        Fragment fragment2 = new GamePage();
        Fragment fragment3 = new UserPage();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragments));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }

    /**
     * 初始化头标
     */
    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        tv_chat = (TextView) findViewById(R.id.chart_bar);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        tv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(application.getRelationship() != null){
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(intent);
                }else{
                    initApplication();
                }

            }
        });

    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    /**
     * ViewPager适配器
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;
        public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
            super(fm);
            this.list = list;

        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }


    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
                .getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 页卡切换监听
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }


    /**
     * 主页面函数
     * Anniversary打开纪念日界面
     * Health打开健康界面
     */
    public void onMainClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.anniversary:
                intent = new Intent(this,AnniversaryActivity.class);
                startActivity(intent);
                break;
            case R.id.health:
                intent = new Intent(this,HealthActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 个人资料页面点击函数
     * EditUserInfo打开编辑个人资料的页面
     * MyLover打开添加情侣的界面
     * Setting打开设置界面
     * Quit退出当前账号
     */
    public void onUserClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.editUserInfo:
                intent = new Intent(this, EditUserInfoActivity.class);
                break;
            case R.id.mylover:
                break;
            case R.id.setting:
                break;
            case R.id.quit:
                break;
        }
    }
    private void initApplication(){
        man = new User();
        man = BmobChatUser.getCurrentUser(this, User.class);
        BmobQuery<User> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.addWhereEqualTo("username","11");
        query.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                woman = new User();
                if(list.size()<=0){
                   return;
                }
                woman = list.get(0);
                Relationship relationship = new Relationship();
                relationship.setW_user(woman);
                relationship.setM_user(man);
                if (woman != null) {
                    application.setRelationship(relationship);
                }
                ShowLog("获取application对象成功");
            }

            @Override
            public void onError(int i, String s) {
                ShowToast("获取application对象失败");
                ShowLog("获取application对象失败");
                application.setRelationship(null);
            }
        });

    }


}
