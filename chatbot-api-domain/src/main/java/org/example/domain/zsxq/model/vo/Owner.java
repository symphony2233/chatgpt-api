package org.example.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 启动类
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
@AllArgsConstructor
public class Owner
{
    private String user_id;

    private String name;

    private String avatar_url;

    private String location;
}
