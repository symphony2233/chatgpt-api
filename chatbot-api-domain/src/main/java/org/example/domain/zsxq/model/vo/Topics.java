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
public class Topics {
    private String topic_id;

    private Group group;

    private String type;

    private Talk talk;

    private List<ShowComments> show_comments;

    private int likes_count;

    private int rewards_count;

    private int comments_count;

    private int reading_count;

    private int readers_count;

    private boolean digested;

    private boolean sticky;

    private String create_time;

    private UserSpecific user_specific;
}
