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
            Integer num = 0;
            Pattern pattern = Pattern.compile("<item><title>(.*?)</title><link>(.*?)</link><pubDate>(.*?)</pubDate></item>");
            while ((line = br.readLine()) != null && num < 10) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String title = matcher.group(1);
                    String link = matcher.group(2);
                    String pubDate = matcher.group(3);
                    jwcNoticeDao.insert(title, link, pubDate);
                    num++;
                }
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
