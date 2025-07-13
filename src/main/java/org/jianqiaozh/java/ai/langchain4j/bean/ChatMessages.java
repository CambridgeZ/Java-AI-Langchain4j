package org.jianqiaozh.java.ai.langchain4j.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_messages")
public class ChatMessages {
    // 唯一标识注解，表示映射到MongoDB 当中 _id 的字段当中去
    @Id
    private ObjectId MessageId;

    private String memoryId;

    private String content; // 存储聊天记录列表在json字符串当中
}
