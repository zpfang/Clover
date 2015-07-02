package com.clover.utils;

import android.app.Application;

import com.clover.entities.Relationship;

/**
 * Created by zpfang on 2015/6/30.
 */
public class CloverApplication extends Application{

    private Relationship relationship;

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
}
