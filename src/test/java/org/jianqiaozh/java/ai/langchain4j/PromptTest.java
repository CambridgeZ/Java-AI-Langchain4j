package org.jianqiaozh.java.ai.langchain4j;

import dev.langchain4j.model.input.Prompt;
import org.jianqiaozh.java.ai.langchain4j.assistant.MemoryChatAssistant;
import org.jianqiaozh.java.ai.langchain4j.assistant.SeperateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {

    @Autowired
    private SeperateChatAssistant seperateChatAssistant;

    @Test
    public void testSystemMessages() {
        String answer = seperateChatAssistant.chat(3, "今天几号");
        System.out.println(answer);
    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Test
    public void testUserMessages() {
        String answer = memoryChatAssistant.chat("我是小红");
        System.out.println(answer);

        answer = memoryChatAssistant.chat("今年我18岁");
        System.out.println(answer);

        answer = memoryChatAssistant.chat("你知道我是谁么？");
        System.out.println(answer);
    }

    @Test
    public void TestV(){
        String answer1 = seperateChatAssistant.chat2(1, "我是小明");
        System.out.println(answer1);
        String answer2 = seperateChatAssistant.chat2(1, "我是谁");
        System.out.println(answer2);
    }

    @Test
    public void TestuserInfo(){
        String username = "翠花";
        int age = 18;

        String answer1 = seperateChatAssistant.chat3(10, "我是谁，我多大了？", username, age);
        System.out.println(answer1);
    }
}
