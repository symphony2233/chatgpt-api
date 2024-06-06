package org.example.test;


import com.alibaba.fastjson.JSON;
import org.example.domain.ai.service.AiService;
import org.example.domain.zsxq.model.res.ZsxqResp;
import org.example.domain.zsxq.model.vo.Topics;
import org.example.domain.zsxq.service.ZsxqApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @description: SpringBoot测试类
 * @author: symphony
 * @create: 2024/06/05
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.group01.groupId}")
    private String groupId;
    @Value("${chatbot-api.group01.cookie}")
    private String zxxqCookie;

    @Value("${chatbot-api.gpt1.gptUrl}")
    private String gptUrl;

    @Value("${chatbot-api.gpt1.openAiKey}")
    private String openAiKey;

    @Value("${chatbot-api.gpt1.modelType}")
    private String modelType;




    @Resource
    private ZsxqApi zsxqApi;

    @Resource
    private AiService aiService;

    @Test
    public void test_zsxqApi() throws IOException {
        ZsxqResp zsxqResp = zsxqApi.getAllComments(groupId, zxxqCookie);
        logger.info("测试结果：{}", JSON.toJSONString(zsxqResp));

        List<Topics> topics = zsxqResp.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            logger.info("topicId：{} text：{}", topicId, text);

            // 回答问题
            // zsxqApi.answer(groupId, cookie, topicId, openAI.doChatGPT(openAiKey, text), false);
        }
    }

    @Test
    public void test_openAi() throws IOException {
        String response = aiService.getAnswer(gptUrl, openAiKey,modelType, "帮我写一个java冒泡排序");
        logger.info("测试结果：{}", response);
    }

}
