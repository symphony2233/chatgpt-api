package org.example.domain.ai.model.vo;

import lombok.Data;

/**
 * @description:
 * @author: symphony
 * @create: 2024/06/06
 **/
@Data
public class Usage
{
    private int prompt_tokens;

    private int completion_tokens;

    private int total_tokens;
}
