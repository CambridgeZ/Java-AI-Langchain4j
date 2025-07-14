package org.jianqiaozh.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import dev.langchain4j.service.MemoryId;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    @Tool(name = "加法运算", value = "将a,b两个参数相加并返回运算结果")
    public double sum(@ToolMemoryId int memoryId,
                      @P(value = "加数1", required = true) double a,
                      @P(value = "加数2", required = true) double b) {
        System.out.println("调用了工具当中的 a + b, 调用加法运算的memoryId 为" + memoryId);
        return a + b;
    }

    @Tool(name = "平方根运算", value = "将a开平方根并返回结果")
    public double squareRoot(
            @ToolMemoryId int memoryId,
            double a) {
        return Math.sqrt(a);
    }
}
