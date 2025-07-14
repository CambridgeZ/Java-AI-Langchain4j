package org.jianqiaozh.java.ai.langchain4j.assistant;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculatorTools" // 配置工具
)
public interface SeperateChatAssistant {

    @SystemMessage(fromResource = "my-prompt-template.txt") // 系统提示词
    String chat(@MemoryId int memoryId, @UserMessage String message);


    @UserMessage("你是我的好朋友，请用天津话回答问题， {{message}}")
    String chat2(@MemoryId int memoryId, @V("message") String message);

    @SystemMessage(fromResource = "my-prompt-template3.txt") // 系统提示词
    String chat3(@MemoryId int memoryId,
                 @UserMessage String message,
                 @V("username") String username,
                 @V("age") int age);

}
