package org.example.domain.ai.service;

import java.io.IOException;

/**
 * @description: ai大模型service
 * @author: symphony
 * @create: 2024/06/06
 **/
public interface AiService {
    // 获取gpt回答
    String getAnswer(String gptUrl, String openAiKey, String modelType, String question) throws IOException;
}
