package org.jianqiaozh.java.ai.langchain4j;

import org.jianqiaozh.java.ai.langchain4j.assistant.SeperateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsTest {
    @Autowired
    private SeperateChatAssistant seperateChatAssistant;

    @Test
    public void testCaculate() {
        String answer = seperateChatAssistant.chat(11, "119212+332334 等于多少");
        System.out.println(answer);
    }
}
