package com.edgar.bbs.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class FileUtil {
    public static HashMap<String, String> dealWithFileName(String fileName){
        SimpleDateFormat ft = new SimpleDateFormat("_yyyy_MM_dd_hh_mm_ss");
        String time = ft.format(new Date()) ;
        String[] result;
        if(fileName.contains(".")){
             result = fileName.split("\\.");
        }else{
             result = new String[]{fileName, ""};
        }
            HashMap<String, String> map = new HashMap<>();
            map.put("origin", result[0]);
            map.put("name", result[0] + time);
            map.put("suffix", result[1]);
        return map;
    }
}
