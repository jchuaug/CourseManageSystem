package xmu.crms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import xmu.crms.service.WeChatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WeChatService {
    private String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx6ab1e6c4ae4c17ef&secret=3404d5c3fcfd0c557378008628c7e0d6&grant_type=authorization_code";

    @Override
    public String getOpenId(String code) {
        String reqUrl = url + "&js_code=" + code;
        StringBuffer json = new StringBuffer();
        try {
            URL oracle = new URL(reqUrl);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(new String(inputLine.getBytes(), "utf-8"));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(json.toString());
        Map auth = null;
        try {
            auth = new ObjectMapper().readValue(json.toString(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (auth == null) {
            return "";
        }

        if (auth.get("errcode") != null) {
            throw new IllegalArgumentException("参数错误");
        }

        return (String) auth.get("openid");
    }
}
