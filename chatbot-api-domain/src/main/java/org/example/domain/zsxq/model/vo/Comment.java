package org.example.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 评论
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
@AllArgsConstructor
public class Comment {
    private String comment_id;

    private String create_time;

    private Owner owner;

    private String text;

    private int likes_count;

    private int rewards_count;

    private boolean sticky;
}
