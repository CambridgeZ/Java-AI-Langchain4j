package org.jianqiaozh.java.ai.langchain4j.retriever;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CompositeRetriever implements ContentRetriever {
    private final List<ContentRetriever> retrievers;

    public CompositeRetriever(List<ContentRetriever> retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public List<Content> retrieve(Query query) {
        // 使用 LinkedHashSet 去重 + 保持顺序
        Set<Content> resultSet = new LinkedHashSet<>();

        for (ContentRetriever retriever : retrievers) {
            List<Content> contents = retriever.retrieve(query);
            resultSet.addAll(contents);  // 自动去重
        }

        return new ArrayList<>(resultSet);
    }
}
