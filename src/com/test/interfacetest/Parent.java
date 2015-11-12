package com.test.interfacetest;

import net.sf.json.JSONObject;

import java.util.Date;

/**
 * Created by uv2sun on 15/11/6.
 */
public interface Parent {
    public JSONObject _msg = JSONObject.fromObject("{id:" + new Date().getTime() + ", msg:{}}");
}
