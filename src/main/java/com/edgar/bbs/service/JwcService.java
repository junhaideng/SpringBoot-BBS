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

    private String base_url = "http://jwc.sjtu.edu.cn/rss/rss_notice.aspx?SubjectID=198015&TemplateID=221027";

    public void updateNewNotice() {
        try {
            URL url = new URL(base_url);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "gb2312"));
            String line = null;
            int num = 0;

            Pattern pattern = Pattern.compile("<item><title>(.*?)</title><link>(.*?)</link><pubDate>(.*?)</pubDate></item>");
            List<String> titleArray = new ArrayList<>();
            List<String> linkArray = new ArrayList<>();
            List<String> pubDateArray = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String title = matcher.group(1);
                    String link = matcher.group(2);
                    String pubDate = matcher.group(3);
                    titleArray.add(title);
                    linkArray.add(link);
                    pubDateArray.add(pubDate);
                    System.out.println(title);
                    System.out.println(pubDate);
                }
            }
            int length = titleArray.size();
            for(num=1; num<=10; num++){
                jwcNoticeDao.insert(titleArray.get(length-num), linkArray.get(length-num), pubDateArray.get(length-num));
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
}
