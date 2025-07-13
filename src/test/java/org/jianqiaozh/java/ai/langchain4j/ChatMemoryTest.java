package org.jianqiaozh.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import org.jianqiaozh.java.ai.langchain4j.assistant.Assistant;
import org.jianqiaozh.java.ai.langchain4j.assistant.MemoryChatAssistant;
import org.jianqiaozh.java.ai.langchain4j.assistant.SeperateChatAssistant;
import org.jianqiaozh.java.ai.langchain4j.store.MongoChatMemoryStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private Assistant assistant;

    @Test
    public void testChatMemory1() {
        String answer1 = assistant.chat("我是一名研究生");
        System.out.println(answer1);

        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testChatMemory2() {
        UserMessage userMessage1 = UserMessage.userMessage("我是李小三");
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        System.out.println(aiMessage1.text());

        UserMessage userMessage2 = UserMessage.userMessage("你知道我是谁吗？");
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(userMessage1, aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        System.out.println(aiMessage2.text());
    }

    @Test
    public void testChatMemory3() {
        MessageWindowChatMemory messageWindowChatMemory = MessageWindowChatMemory.withMaxMessages(10);

        AiServices
                .builder(Assistant.class)
                .chatLanguageModel(qwenChatModel)
                .chatMemory(messageWindowChatMemory)
                .build();

        String answer1 = assistant.chat("我是李小二");
        System.out.println(answer1);

        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testChatMemory4() {
        String answer1 = memoryChatAssistant.chat("我是小明");
        System.out.println(answer1);
        String answer2 = memoryChatAssistant.chat("我是谁？");
        System.out.println(answer2);
    }

    @Autowired
    private SeperateChatAssistant seperateChatAssistant;

    @Test
    public void testSeperateChatAssistant() {
        String answer1 = seperateChatAssistant.chat(1, "我是小明");
        System.out.println(answer1);

        String answer2 = seperateChatAssistant.chat(2, "我是小红");
        System.out.println(answer2);

        String answer1_1 = seperateChatAssistant.chat(1, "我是谁");
        System.out.println(answer1_1);

        String answer2_1 = seperateChatAssistant.chat(2, "我是谁");
        System.out.println(answer2_1);
    }

}
