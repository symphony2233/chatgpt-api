package org.example.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @description: 测试类
 * @author: symphony
 * @create: 2024/06/05
 **/
public class ApiTest {

    // 查询未回答的问题
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/51111528442184/topics?scope=all&count=20");


        String cookie = "zsxq_access_token=3F1E98B9-0B37-EB47-CDFA-AE29C024517D_0E03217866F93CE3; zsxqsessionid=44caf1948189816272187a7ed7f607e7; abtest_env=product; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22215554242248811%22%2C%22first_id%22%3A%2218fe0bd66917b1-0bdbd474964c8e-26001b51-1327104-18fe0bd669254c%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThmZTBiZDY2OTE3YjEtMGJkYmQ0NzQ5NjRjOGUtMjYwMDFiNTEtMTMyNzEwNC0xOGZlMGJkNjY5MjU0YyIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjIxNTU1NDI0MjI0ODgxMSJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22215554242248811%22%7D%2C%22%24device_id%22%3A%2218fe0bd66917b1-0bdbd474964c8e-26001b51-1327104-18fe0bd669254c%22%7D\n";


        get.addHeader("cookie", cookie);
        get.addHeader("Content-Type", "application/json;charset=utf8"); // application/json, text/plain, */*

        CloseableHttpResponse response = httpClient.execute(get);// 异常直接抛出去
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 200
            String res = EntityUtils.toString(response.getEntity()); // 工具类返回实体信息
            System.out.println("查询问题响应为：" + res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());// 异常打印
        }
    }

    // 测试回答方法
    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String cookie =  "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22215554242248811%22%2C%22first_id%22%3A%2218b2185fddd1dc-0fcda48e6ed3f68-7b515474-1327104-18b2185fdde46%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThiMjE4NWZkZGQxZGMtMGZjZGE0OGU2ZWQzZjY4LTdiNTE1NDc0LTEzMjcxMDQtMThiMjE4NWZkZGU0NiIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjIxNTU1NDI0MjI0ODgxMSJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22215554242248811%22%7D%2C%22%24device_id%22%3A%2218b2185fddd1dc-0fcda48e6ed3f68-7b515474-1327104-18b2185fdde46%22%7D; zsxqsessionid=e04a244564a35d77f51eedb7e5d59237; abtest_env=product; tfstk=fj0jTdOWctXXKCl7IodzPBYJPBU6axTel1NttfQV6rUAB1eSgZWa0fesCYyo7tRDjuHSEYDqgfusqNGnsqR07I4miP49Lp8E5jc0S2VvML3Xy7FswtQOWmUjg7U9Lp8rJRbYfPnwnbcgPbeu1ZQTBAFRw8VFkPeYXTI898eTWPHTyTF4_SBYMNLRebnhO5tbnj9aLuDpzhAqi8_OR6VaDCcRbGs11SrLpoy5Wfg7GowKGqxobDF-z4ZU41JzyXmrH7aBl9Vj2XaY1VAdfRZsozwSdeQgZmHjylgyseGul5ZLWu1OWba8BkguJeB7Z0hqXqDf1NFmu2r_KuOOSlz-8knKh1AnwrFtIk0wKZ2x6X0nYyTfKSi-OzsPGwyCqFI1Nkb_NJRWNGjZmPJ8_T8a_HZYZSZyNQ6xj9bO8TAWNGj3D7VbHQO5Hc1..; zsxq_access_token=03408E3D-72A0-9207-622E-174746DA6522_0E03217866F93CE3";
        // 改成topic
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/1522128511455822/comments");
        post.addHeader("cookie", cookie);
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去百度！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println("+++++++" + res);
        } else {

            System.out.println("回答问题响应为：" + response.getStatusLine().getStatusCode());
        }
    }


    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String gptUrl = "https://api.ai-gaochao.cn" + "/v1/chat/completions";
        String gptToken = "sk-Jwdq1qsie0uhkcJtA994D71f0f1c497f8c97057d846c3aF6";
        String modelType = "gpt-3.5-turbo";
        HttpPost post = new HttpPost(gptUrl);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + gptToken);

        String paramJson = "{\"model\": \"" + modelType + "\", \"messages\": [{\"role\": \"user\", \"content\": \"写一首打油诗\"}]}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.APPLICATION_JSON);
        // StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8")); // 与上面的倒霉都可以
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

}
