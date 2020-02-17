package com.ningmeng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.ningmeng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class ConsumerPostPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    /**
     * 是Json字符串（传递更多的信息、方便拓展、有统一规则、简单）
     * {
     *     id:"1"
     * }
     * RoutIngKey
     * 页面id：发布页面的id
     * @param msg
     * 在没用通知之前  生产者已经先有了静态页面，这个静态页面在MongoDB数据库中存在，在通知消费者消费
     */
    @Autowired
    PageService pageService;
    @RabbitListener(queues={"${ningmeng.mq.queue}"})
    public void postPage(String msg){
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        //消费者应该做什么
        //获得页面的id从MongoDB数据库下载页面到本地
        //等到静态页面(1)
        //调用dao查询页面信息，获取到页面的物理路径，调用dao查询站点信息，得到站点的物理路径
        //所属站点物理路径和页面名称(2)
        //页面物理路径=站点物理路径+页面物理路径+页面名称。

        LOGGER.info("receive cms post page:{}",msg.toString());
        //取出页面id
        String pageId = (String) map.get("pageId");
        //查询页面信息
        //将页面保存到服务器物理路径
        pageService.PageTest(pageId);
    }
}
