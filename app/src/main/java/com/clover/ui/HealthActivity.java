package com.clover.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.clover.R;


public class HealthActivity extends ActionBarActivity {

    private Button ib_emm;//按钮
    private Button ib_disease;//按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        ib_emm = (Button)findViewById(R.id.health1);
        ib_disease = (Button)findViewById(R.id.disease);
        ib_emm.setOnClickListener(new ImageButtonClickListener());
        ib_disease.setOnClickListener(new ImageButtonClickListener());
    }

    /**
     * 健康页面中的按钮点击事件
     */
    private class ImageButtonClickListener implements View.OnClickListener{
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.health1:
                    break;
                case R.id.disease:
                    break;
            }
        }
    }

    /**
     * 返回
     */
    public void back(View view){
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
