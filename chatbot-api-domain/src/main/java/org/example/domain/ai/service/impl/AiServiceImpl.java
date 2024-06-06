package org.example.domain.ai.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.example.domain.ai.model.aggregates.AIAnswer;
import org.example.domain.ai.model.vo.Choices;
import org.example.domain.ai.service.AiService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: symphony
 * @create: 2024/06/06
 **/
@Service
public class AiServiceImpl implements AiService {

    @Override
    public String getAnswer(String gptUrl, String openAiKey, String modelType, String question) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(gptUrl);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAiKey);


        String paramJson = "{\"model\": \"" + modelType + "\", \"messages\": [{\"role\": \"user\", \"content\": \""+ question +"\"}]}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.APPLICATION_JSON);
        // StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8")); // 与上面的倒霉都可以
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
