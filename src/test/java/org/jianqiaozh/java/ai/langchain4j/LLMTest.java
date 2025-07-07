package org.jianqiaozh.java.ai.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LLMTest {

    @Test
    public void testGPTDemo(){
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();

       String answer =  model.chat("你好");
       System.out.println("Answer: " + answer);
    }

    @Autowired
    private OpenAiChatModel openAiChatModel;

    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    @Test
    public void testSpringBoot(){
        String ans = openAiChatModel.chat("我是谁？");
        System.out.println(BLUE + ans + RESET);
    }
}
