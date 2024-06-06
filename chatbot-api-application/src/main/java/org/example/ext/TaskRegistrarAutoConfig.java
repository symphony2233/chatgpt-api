package org.example.ext;


import org.apache.commons.lang3.StringUtils;
import org.example.common.PropertyUtil;
import org.example.domain.ai.service.AiService;
import org.example.domain.zsxq.service.ZsxqApi;
import org.example.job.ChatbotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: symphony
 * @create: 2024/06/06
 **/
@Configuration
@EnableScheduling
public class TaskRegistrarAutoConfig implements EnvironmentAware, SchedulingConfigurer {

    private Logger logger = LoggerFactory.getLogger(TaskRegistrarAutoConfig.class);

    /**
     * 任务配置组
     */
    private Map<String, Map<String, Object>> taskGroupMap = new HashMap<>();

    @Resource
    private ZsxqApi zsxqApi;
    @Resource
    private AiService aiService;


    // 读取参数配置
    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "chatbot-api.";
        String launchListStr = environment.getProperty(prefix + "launchList");
        if (StringUtils.isEmpty(launchListStr)) {
            return;
        }
        for (String groupKey : launchListStr.split(",")) {
            Map<String, Object> taskGroupProps = PropertyUtil.handle(environment, prefix + groupKey, Map.class);
            taskGroupMap.put(groupKey, taskGroupProps);
        }
    }

    // 任务注册
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Set<String> taskGroups = taskGroupMap.keySet();
        for (String groupKey : taskGroups) {
            Map<String, Object> taskGroup = taskGroupMap.get(groupKey);
            String groupId = taskGroup.get("groupId").toString();
            String zxxqCookie = taskGroup.get("cookie").toString();
            String groupName = taskGroup.get("groupName").toString();
            String cookie = taskGroup.get("cookie").toString();
            Map<String,String> gptMap = (Map<String, String>) taskGroup.get("gpt1");


            String gptUrl = gptMap.get("gptUrl").toString();
            String openAiKey = gptMap.get("openAiKey").toString();
            String modelType = gptMap.get("modelType").toString();

            // 获取定时任务表达式
            String cronExpression = taskGroup.get("cronExpression").toString();
            // String cronExpression = new String(Base64.getDecoder().decode(cronExpressionBase64), StandardCharsets.UTF_8);// 不加密也是可以的
            logger.info("创建任务 groupName：{} groupId：{} cronExpression：{}", groupName, groupId, cronExpression);
            // 添加任务
            taskRegistrar.addCronTask(new ChatbotTask(groupId, zxxqCookie, gptUrl,
                    openAiKey, modelType, groupName, cookie,
                    zsxqApi, aiService), cronExpression);
        }
    }

}