package org.jianqiaozh.java.ai.langchain4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jianqiaozh.java.ai.langchain4j.assistant.XiaozhiAgent;
import org.jianqiaozh.java.ai.langchain4j.bean.ChatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Tag(name = "小智学术")
@RestController
@RequestMapping("/xiaozhi")
public class XiaozhiController {
    @Autowired
    private XiaozhiAgent xiaozhiAgent;

    @Operation(summary = "对话")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public String chat(@RequestBody ChatForm chatForm) {
        return xiaozhiAgent.chat(
                chatForm.getMemoryId(),
                chatForm.getMessage()
        );
    }
}
