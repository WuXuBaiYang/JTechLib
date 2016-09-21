package com.jtech.test;

import java.io.Serializable;

/**
 * Created by jianghan on 2016/9/21.
 */
public class TestModel implements Serializable {
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}