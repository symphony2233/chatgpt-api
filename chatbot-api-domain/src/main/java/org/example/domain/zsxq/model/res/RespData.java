package org.example.domain.zsxq.model.res;

import lombok.Data;
import org.example.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * @description: 响应结果
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
public class RespData {
    private List<Topics> topics;
}
