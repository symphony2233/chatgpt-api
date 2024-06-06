package org.example.domain.ai.model.aggregates;

import lombok.Data;
import org.example.domain.ai.model.vo.Choices;
import org.example.domain.ai.model.vo.Usage;

import java.util.List;

/**
 * @description: 大模型返回结果
 * @author: symphony
 * @create: 2024/06/06
 **/
@Data
public class AIAnswer{
    private String id;

    private String object;

    private int created;

    private String model;

    private List<Choices> choices;

    private Usage usage;

    private String system_fingerprint;
}
