package org.jianqiaozh.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.web.search.WebSearchTool;
import dev.langchain4j.web.search.searchapi.SearchApiWebSearchEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class XiaozhiTools {

    @Value("${baiduAPI}")
    private String baiduAPI;

    @Tool(name = "web_search", value = "在网络上搜索关键词相关的信息")
    String searchOnline(String keyword) {

//        System.out.println("搜索引擎被调用");

        Map<String, Object> optionalParameters = new HashMap<>();
        optionalParameters.put("region", "cn");  // 设置为中国地区
        optionalParameters.put("language", "zh");  // 设置为中文
        optionalParameters.put("domain", "baidu.com");  // 使用百度域名

        SearchApiWebSearchEngine searchApiWebSearchEngine = SearchApiWebSearchEngine.builder()
                .apiKey(baiduAPI)
                .engine("baidu")
                .optionalParameters(optionalParameters)
                .build();

        WebSearchTool webSearchTool = WebSearchTool.from(searchApiWebSearchEngine);
        return webSearchTool.searchWeb(keyword);
    }
}