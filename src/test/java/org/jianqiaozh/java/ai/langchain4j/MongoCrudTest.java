package org.jianqiaozh.java.ai.langchain4j;

import org.jianqiaozh.java.ai.langchain4j.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoCrudTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入文档测试
     */

//    @Test
//    public void testInsert1() {
//        mongoTemplate.insert(new ChatMessage(1L, "聊天记录"));
//    }

    @Test
    public void testInsert2() {
        ChatMessages chatMessage = new ChatMessages();
        chatMessage.setContent("聊天记录列表");

        mongoTemplate.insert(chatMessage);
    }

    @Test
    public void testFindById() {
        ChatMessages chatMessage = mongoTemplate.findById("6873a8fa991d6b5b63530ba2", ChatMessages.class);
        System.out.println(chatMessage);
    }

    @Test
    public void testUpdate() {
        Criteria criteria = Criteria.where("_id").is("6873a8fa991d6b5b63530ba2");
        Query query = new Query(criteria);
        Update update = new Update();

        update.set("content", "新的聊天记录列表");
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Test
    public void testDelete() {
        mongoTemplate.remove(new Query(Criteria.where("_id").is("6873a8fa991d6b5b63530ba2")), ChatMessages.class);
    }
}
