package com.jalcoholapi.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiliyang on 4/30/16.
 */
@ConfigurationProperties
@Service
public class CodoonCrawlService {

    private HttpClient httpClient;

    @Value("${app.config.codoon.crawler.loginURL}")
    private String codoonCrawlerLoginURL;
    @Value("${app.config.codoon.crawler.agentName}")
    private String codoonCrawlerAgentName;

    private synchronized void init() {
        if (httpClient == null)
            httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).setUserAgent(codoonCrawlerAgentName).build();
    }

    public boolean login(String userName, String password) {
        init();

        HttpPost httpPost = new HttpPost(codoonCrawlerLoginURL);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("login_id", userName));
        formParams.add(new BasicNameValuePair("password", password));
        formParams.add(new BasicNameValuePair("code", ""));
        formParams.add(new BasicNameValuePair("app_id", "www"));
        formParams.add(new BasicNameValuePair("next", "/"));

        boolean succeeded = false;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams));
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                if (responseBody.contains("sinanwu")) {
                    succeeded = true;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return succeeded;
        }
    }

    public byte[] crawl(String url, String httpMethod, List<NameValuePair> formParams) {
        init();

        HttpUriRequest request;
        if (HttpPost.METHOD_NAME.equals(httpMethod)) {
            request = new HttpPost(url);
            try {
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(formParams));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (HttpGet.METHOD_NAME.equals(httpMethod)) {
            request = new HttpGet(url);
        } else {
            return null;
        }

        byte[] bytes = null;
        try {
            HttpResponse response = httpClient.execute(request);
            bytes = EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bytes;
        }
    }
}
