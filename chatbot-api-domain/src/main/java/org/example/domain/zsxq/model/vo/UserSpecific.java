package org.example.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @description: 启动类
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
@AllArgsConstructor
public class UserSpecific {

    private boolean liked;

    private List<String> liked_emojis;

    private boolean subscribed;



}
