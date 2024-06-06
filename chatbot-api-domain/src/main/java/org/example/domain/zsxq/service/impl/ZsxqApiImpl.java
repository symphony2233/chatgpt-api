package org.example.domain.zsxq.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.example.domain.zsxq.model.req.ZsxqReq;
import org.example.domain.zsxq.model.req.ReqData;
import org.example.domain.zsxq.model.res.ZsxqResp;
import org.example.domain.zsxq.service.ZsxqApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 小傅哥，微信：fustack
 * @description
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Service
public class ZsxqApiImpl implements ZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApiImpl.class);

    @Override
    public ZsxqResp getAllComments(String groupId, String cookie) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String type = "all";// 或者为unanswered_questions，不同类型不同
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + "51111528442184" + "/topics?scope=" + type + "&count=20");
        get.addHeader("cookie", cookie);
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, ZsxqResp.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String answer, boolean silenced) throws IOException {
        // 构造响应请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String type = "comments";
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/" + type);
        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type", "application/json;charset=utf8");
        post.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");


        // // 构造响应结果
        // String paramJson = "{\n" +
        //     "  \"req_data\": {\n" +
        //     "    \"text\": \""+ answer +"\\n\",\n" +
        //     "    \"image_ids\": [],\n" +
        //     "    \"silenced\": false\n" +
        //     "  }\n" +
        //     "}";

        ReqData reqData = new ReqData(answer, null, null);
        ZsxqReq zsxqReq = new ZsxqReq(reqData);
        String paramJson = JSONObject.toJSONString(zsxqReq);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        // 判断响应是否正确
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);
            ZsxqResp zsxqResp = JSON.parseObject(jsonStr, ZsxqResp.class);
            return zsxqResp.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

}
