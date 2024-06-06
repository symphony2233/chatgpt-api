package org.example.domain.zsxq.service;


import org.example.domain.zsxq.model.res.ZsxqResp;

import java.io.IOException;

/**
 * @author 小傅哥，微信：fustack
 * @description 知识星球 API 接口
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public interface ZsxqApi {

    ZsxqResp getAllComments(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie, String topicId, String answer, boolean silenced) throws IOException;

}