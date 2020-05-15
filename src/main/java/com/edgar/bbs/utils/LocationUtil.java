package com.edgar.bbs.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class LocationUtil {
    private static String url = "http://api.map.baidu.com/location/ip?ak=DpyV2iBGqsjMenuApVwj4BpQxKG7XZBS&ip=";

    public static JSONObject getLocation(String ip) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {

        System.out.println(getLocation("202.198.16.3"));
    }
}
