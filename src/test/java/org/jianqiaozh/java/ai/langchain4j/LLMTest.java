package org.jianqiaozh.java.ai.langchain4j;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.net.URI;
import java.net.URL;

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

    @Autowired
    private OllamaChatModel ollamaChatModel;
    
    @Test
    public void testOllamaChatModel() {
        String response = ollamaChatModel.chat(BLUE + "你好，Ollama！请介绍一下你自己。" + RESET);
        System.out.println("Ollama Response: " + response);
    }

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testQwenChatModel() {
        String response = qwenChatModel.chat("你好，介绍一下你自己");
        System.out.println(BLUE + "QwenChat Response: " + response + RESET);
    }

    @Test
    public void testWanXiangModel() {
        WanxImageModel wanxxiangImage = WanxImageModel
                .builder()
                .modelName("wanx2.1-t2i-turbo")
                .apiKey("sk-05a6a0b8315c4acf80427c033743f9f9")
                .build();
        Response<Image> response = wanxxiangImage.generate("一个在夜店里的穿着比基尼泳装的性感美女");
        URI url = response.content().url();
        System.out.println(url);
    }
}
