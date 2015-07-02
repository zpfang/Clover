package com.clover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clover.R;

import java.util.List;

import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobMsg;

/**
 * Created by dan on 2015/6/29.
 */
public class MSListViewAdapter extends BaseAdapter{

    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
    }

    private static final int ITEMCOUNT = 2;
    private List<BmobMsg> coll;
    private LayoutInflater mInflater;
    private String currentObjectId = "";

    public MSListViewAdapter(Context context, List<BmobMsg> coll) {
        currentObjectId = BmobUserManager.getInstance(context).getCurrentUserObjectId();
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }

    public void add(BmobMsg msg){
        coll.add(msg);
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public int getItemViewType(int position) {
        BmobMsg entity = coll.get(position);

        if (entity.getBelongId() == currentObjectId) {
            return IMsgViewType.IMVT_TO_MSG;
        } else {
            return IMsgViewType.IMVT_COM_MSG;
        }
    }


    public int getViewTypeCount() {
        return ITEMCOUNT;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        BmobMsg entity = coll.get(position);


        ViewHolder viewHolder = null;
     //   if (convertView == null) {
            if (entity.getToId().equals(currentObjectId)) {
                convertView = mInflater.inflate(
                        R.layout.chatting_item_msg_text_left, null);
            } else if(entity.getBelongId().equals(currentObjectId)){
                convertView = mInflater.inflate(
                        R.layout.chatting_item_msg_text_right, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView
                    .findViewById(R.id.tv_sendtime);
            viewHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_chatcontent);

       //     convertView.setTag(viewHolder);
      //  } else {
      //      viewHolder = (ViewHolder) convertView.getTag();
      //  }
        //时间格式化
        viewHolder.tvSendTime.setText(entity.getCreatedAt());
        viewHolder.tvContent.setText(entity.getContent());
        return convertView;
    }

    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvContent;
    }
}
