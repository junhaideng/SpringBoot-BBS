package com.edgar.bbs.service;

import com.edgar.bbs.dao.JwcNoticeDao;
import com.edgar.bbs.domain.JwcNotice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JwcService {

    @Resource
    private JwcNoticeDao jwcNoticeDao;

    private String base_url = "http://www.jwc.sjtu.edu.cn/web/sjtu/198015.htm";

    public void updateNewNotice() {
        try {
            URL url = new URL(base_url);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "gb2312"));
            String line = null;
            int num = 0;

            Pattern pattern = Pattern.compile("&nbsp;&nbsp;<a.*?href=\"(.*?)\".*?>(.*?)</a></td>", Pattern.MULTILINE);
            Pattern pattern1 = Pattern.compile("\\[(.*?)]", Pattern.MULTILINE|Pattern.DOTALL);
            List<String> titleArray = new ArrayList<>();
            List<String> linkArray = new ArrayList<>();
            List<String> pubDateArray = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                Matcher matcher1 = pattern1.matcher(line);
                if (matcher1.find()) {
                    String pubDate = matcher1.group(1).trim();
                    pubDateArray.add(pubDate);
                }
                if (matcher.find()) {
                    String title = matcher.group(2);
                    String link = matcher.group(1);
                    titleArray.add(title);
                    linkArray.add("http://www.jwc.sjtu.edu.cn/web/sjtu/"+ link);
                }
            }
            for(num=1; num<=10; num++){
                jwcNoticeDao.insert(titleArray.get(num), linkArray.get(num), pubDateArray.get(num));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<JwcNotice> getNewNotice() {
        jwcNoticeDao.clear();
        updateNewNotice();
        return jwcNoticeDao.findAll();
    }
    public static void main(String[] args) {
        new JwcService().updateNewNotice();
    }
}
