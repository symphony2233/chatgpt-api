package org.example.domain.zsxq.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
@AllArgsConstructor
public class ReqData {
    private String text;

    private List<String> image_ids;

    private List<String> mentioned_user_ids;
}
