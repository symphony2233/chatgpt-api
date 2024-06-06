package org.example.job;

import com.alibaba.fastjson.JSON;


import org.example.domain.ai.service.AiService;
import org.example.domain.zsxq.model.res.ZsxqResp;
import org.example.domain.zsxq.model.vo.Topics;
import org.example.domain.zsxq.service.ZsxqApi;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @description:
 * @author: symphony
 * @create: 2024/06/06
 **/
@Component
public class ChatbotTask {

    private Logger logger = LoggerFactory.getLogger(ChatbotTask.class);

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

    @Value("${chatbot-api.group01.groupName}")
    private String groupName;

    @Value("${chatbot-api.group01.cookie}")
    private String cookie;

    @Resource
    private ZsxqApi zsxqApi;

    @Resource
    private AiService aiService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        try {
            // 随机打样，防止被知识星球监控
            if (new Random().nextBoolean()) {
                logger.info("{} 随机打烊中...", groupName);
                return;
            }

            // 定制下班时间
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7) {
                logger.info("{} 打烊时间不工作，AI 下班了！", groupName);
                return;
            }

            // 1. 检索问题
            ZsxqResp zsxqResp = zsxqApi.getAllComments(groupId, zxxqCookie);
            logger.info("{} 检索结果：{}", groupName, JSON.toJSONString(zsxqResp));

            List<Topics> topics = zsxqResp.getResp_data().getTopics();
            if (null == topics || topics.isEmpty()) {
                logger.info("{} 本次检索未查询到待会答问题", groupName);
                return;
            }

            // 2. AI 回答
            // Topics topic = topics.get(topics.size() - 1);
            Topics topic = topics.get(0);
            String answer = aiService.getAnswer(gptUrl, openAiKey, modelType, topic.getTalk().getText().trim());

            // 3. 问题回复
            boolean status = zsxqApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info("{} 编号：{} 问题：{} 回答：{} 状态：{}", groupName, topic.getTopic_id(), topic.getTalk().getText(), answer, status);
        } catch (Exception e) {
            logger.error("{} 自动回答问题异常", groupName, e);
        }
    }
}
