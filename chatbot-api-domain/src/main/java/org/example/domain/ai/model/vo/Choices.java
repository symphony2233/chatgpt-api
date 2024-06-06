package org.example.domain.ai.model.vo;

import lombok.Data;

/**
 * @description:
 * @author: symphony
 * @create: 2024/06/06
 **/
@Data
public class Choices {
    private int index;

    private Message message;

    private String logprobs;

    private String finish_reason;
}
