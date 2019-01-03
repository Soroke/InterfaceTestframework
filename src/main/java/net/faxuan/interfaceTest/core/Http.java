package net.faxuan.interfaceTest.core;

import net.faxuan.interfaceTest.exception.CheckException;
import net.faxuan.objectInfo.caseObject.Case;
import net.faxuan.objectInfo.excel.DBCheck;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.io.IOException;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by song on 2018/12/25.
 */
public class Http {

    /**
     * Cookies
     */
    private static CookieStore cookieStore = null;

    /**
     * 编码默认utf-8
     */
    private static String encode = "utf-8";

    /**
     * log4j打log
     */
    private static Logger log = Logger.getLogger(Http.class);

    /**
     * 请求超时设置时长
     * 单位秒
     */
    private static int timeOut = 5;

    /**
     * 构造httprequest设置
     * 设置请求和传输超时时间
     */
    private static RequestConfig config = RequestConfig.custom().setSocketTimeout(timeOut * 1000).setConnectTimeout(timeOut * 1000).setConnectionRequestTimeout(timeOut * 1000).build();


    /**
     * 发送http请求
     * @param caseInfo 用例信息
     * @return
     */
    public static Response sendRequest(Case caseInfo) {
        Response response = null;
        if (caseInfo.getRequestType().equals("post") || caseInfo.getRequestType().equals("POST")) {
            response = post(caseInfo.getUrl(),caseInfo.getParams());
            response.setCaseInfo(caseInfo);

        } else if (caseInfo.getRequestType().equals("get") || caseInfo.getRequestType().equals("GET")) {
            //待扩展
        } else {
            return null;
        }

        //替换数据库检查中的关键字
        if (response.getCaseInfo().getDbChecks().size() >= 1) {
            Assert.assertEquals(replaceResult(response),true);
        }

        return response;
    }

    /**
     * 发送post请求
     *
     * @return
     *  Request对象
     *  包含：
     *      请求类型
     *      url
     *      参数
     *      返回结果
     *      状态码
     *      响应时间
     */
    public static Response post(String url,Map<Object,Object> ... params) {
        if (cookieStore == null)  cookieStore = new BasicCookieStore();
        HttpClient httpClient =  HttpClientBuilder.create().setDefaultRequestConfig(config).setDefaultCookieStore(cookieStore).build();
        //请求返回实体对象
        Response rsp = new Response();
        //用于计算接口请求响应时间
        Long startTime = 0L;
        long runTime = 0L;
        log.info("------------------------开始请求------------------------");
        log.info("接口类型：post");
        log.info("接口URL为：" + url);
        HttpPost httpPost = new HttpPost(url);
//        addHeaderToHttpRequest(httpPost);
        /**
         * 检查参数是否为空
         * 如果存在参数循环添加
         */
        Map<Object,Object> pam = new HashMap<Object,Object>();
        if (!(params.length == 0)) {
            pam = params[0];
        }
        if(!pam.isEmpty()) {
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            for (Map.Entry<Object,Object> entry : pam.entrySet()) {
                log.info("参数：\"" +entry.getKey() + "\":\"" + entry.getValue() + "\"");
                param.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
            }
            HttpEntity entity = new UrlEncodedFormEntity(param,UTF_8);
            httpPost.setEntity(entity);
        }
        int count = 0;
        HttpResponse response = null;
        /**
         * 接口测试不通过时，暂停1s继续请求；
         * 如果三次都不通过再判定测试失败
         */
        while(count < 3 ) {
            //计算请求开始时间
            startTime = new Date().getTime();
            try {
                response = httpClient.execute(httpPost);
                rsp.setBody(EntityUtils.toString(response.getEntity(), encode));
                runTime = new Date().getTime() - startTime;
            } catch(IOException ioe) {
                log.error("post请求发送时出错");
                rsp.setTestResult(false);
//                ioe.printStackTrace();
                return rsp;
            }catch (NullPointerException npe) {
                log.error("没有设置URL参数！");
                rsp.setTestResult(false);
//                npe.printStackTrace();
                return rsp;
            }
            log.info("接口响应时间为：" + runTime + "ms");
            log.info("------------------------请求结束------------------------");
            if ( response.getStatusLine().getStatusCode() != 200 ) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count ++;
                if (count == 3) {
                    throw new CheckException("接口:" + url + "请求返回结果为失败；已重试3次，接口测试失败。");
                } else {
                    log.error("接口:" + url + "请求返回结果为失败；开始第" + count + "次重试");
                }
            } else {
                count = 5;
            }
        }
        rsp.setRunTime(runTime);
        rsp.setCookies(cookieStore);
        //保存header
        Header[] headers = response.getAllHeaders();
        Map<Object,Object> hashMap= new HashMap<Object,Object>();
        for (Header header:headers) {
            hashMap.put(header.getName(),header.getValue());
        }
        rsp.setHeaders(hashMap);
        rsp.setStatusCode(response.getStatusLine().getStatusCode());
//        log.info(rsp);
        return rsp;
    }

    /**
     * 替换用例的数据库检查点中的关键字标识字段
     * @param response
     * @return
     */
    public static boolean replaceResult(Response response) {
        List<DBCheck> dbChecks = response.getCaseInfo().getDbChecks();
        for (DBCheck dbCheck:dbChecks) {
            //获取请求用例数据库检查中的检查点并替换其中需要替换的值
            Map<Object,Object> checks = dbCheck.getCheckPoint();
            Map<Object,Object> newChecks = new HashMap<>();
            for (Object key:checks.keySet()) {
                String value = checks.get(key).toString();
                if (value.contains("RESPONSE[") && value.contains("]")) {
                    String[] value1 = value.split("\\[");
                    String[] value2 = value1[1].split("]");
                    try {
                        newChecks.put(key,getResponseValue(response.getBody(),value2[0]));
                    } catch (NullPointerException e) {
                        throw new CheckException("用例\"ID：" + response.getCaseInfo().getId() + "--" + response.getCaseInfo().getName() + "\"中数据库检查点需要替换的返回值参数：‘" +value2[0] + "’在接口返回中不存在无法替换" );

                    }
                } else {
                    newChecks.put(key,checks.get(key));
                }
            }
            dbCheck.setCheckPoint(newChecks);

            //获取请求用例数据库检查中的sql并替换其中需要替换的值
            String sql = dbCheck.getSql();
            if (sql.contains("RESPONSE[") && sql.contains("]")) {
                String[] value1 = sql.split("\\[");
                String[] value2 = value1[1].split("]");
                try {
                    sql = sql.replaceAll("RESPONSE\\[" + value2[0] + "]","'" + getResponseValue(response.getBody(),value2[0]) + "'");
                } catch (NullPointerException e) {
                    throw new CheckException("用例\"ID：" + response.getCaseInfo().getId() + "--" + response.getCaseInfo().getName() + "\"中数据库检查sql需要替换的返回值参数：‘" +value2[0] + "’在接口返回中不存在无法替换" );
                }

            }
            dbCheck.setSql(sql);
        }
        return true;
    }

    /**
     * 获取接口返回信息List<Map<Object,Object>>中指定key的值
     * @param responseBody 数据源
     * @param key   key的值
     * @return 返回key对应的值
     */
    private static String getResponseValue(List<Map<Object,Object>> responseBody,String key) {
        List<String> values = new ArrayList<>();
        for (Map<Object,Object> body:responseBody) {
            for (Object key1:body.keySet()) {
                if (key1.toString().equals(key)) {
                    values.add(body.get(key1).toString());
                }
            }
        }
        if (values.size() == 1) {
            return values.get(0);
        }else {
            throw new CheckException("接口返回信息中不存在或存在多个：‘" +key + "’对应的值" );
        }
    }

}
