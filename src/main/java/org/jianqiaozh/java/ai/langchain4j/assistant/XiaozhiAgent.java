package org.jianqiaozh.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
//        chatModel = "qwenChatModel",
//        streamingChatModel = "qwenStreamingChatModel",
        chatModel = "openAiChatModel",
//        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaozhi",
        tools = "xiaozhiTools",
//        contentRetriever = "contentRetrieverXiaozhiPincone" // 采用向量数据库的
        contentRetriever = "contentRetrieverXiaozhiAcademic"
)
public interface XiaozhiAgent {

//    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    @SystemMessage(fromResource = "xiaozhi-academic-prompt.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
