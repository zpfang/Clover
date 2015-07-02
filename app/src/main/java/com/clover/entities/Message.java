package com.clover.entities;

import cn.bmob.im.bean.BmobMsg;

/**
 * Created by dan on 2015/6/29.
 */
public class Message extends BmobMsg{
    private static final long serialVersionUID = 1L;
    private boolean isCom;

    public boolean isCom() {
        return isCom;
    }

    public void setCom(boolean isCom) {
        this.isCom = isCom;
    }
}
