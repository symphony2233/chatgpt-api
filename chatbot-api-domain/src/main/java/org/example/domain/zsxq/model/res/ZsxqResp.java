package org.example.domain.zsxq.model.res;

import lombok.Data;

/**
 * @description: 知识星球响应结果
 * @author: symphony
 * @create: 2024/06/05
 **/
@Data
public class ZsxqResp {
    private boolean succeeded;

    private RespData resp_data;
}
