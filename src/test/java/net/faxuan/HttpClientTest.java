package net.faxuan;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by song on 2018/12/25.
 */
public class HttpClientTest {
    @Test
    public void cookieSpac() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("https://fzbd.t.faxuan.net/fzss/service/userService!doLogin.do");
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("userAccount", "AK05"));
        param.add(new BasicNameValuePair("userPassword", "ceshi123"));
        HttpEntity entity = new UrlEncodedFormEntity(param,UTF_8);
        httpPost.setEntity(entity);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(httpResponse.getStatusLine());
        HttpEntity entity1 = httpResponse.getEntity();
        System.out.println(entity1);
        Header[] headers = httpResponse.getAllHeaders();
        for (int i =0;i<headers.length;i++) {
            System.out.println(headers[i]);
        }
    }
}
