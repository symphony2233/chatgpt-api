package org.example.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 问题
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
@AllArgsConstructor
public class Talk {
    private Owner owner;

    private String text;
}
