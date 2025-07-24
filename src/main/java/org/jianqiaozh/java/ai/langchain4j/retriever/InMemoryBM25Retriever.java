package org.jianqiaozh.java.ai.langchain4j.retriever;

import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBM25Retriever implements ContentRetriever {

    private final List<TextSegment> segments;

    // 倒排索引，每个关键字在哪些文档当中出现过
    private final Map<String, Set<Integer>> invertedIndex = new HashMap<>();
    private final Map<Integer, Map<String, Integer>> termFreq = new HashMap<>();
    private final Map<Integer, Integer> docLengths = new HashMap<>();
    private final double avgDocLength;
    private final int totalDocs;

    private final double k1 = 1.5;
    private final double b = 0.75;

    public InMemoryBM25Retriever(List<TextSegment> segments) {
        this.segments = segments;
        this.totalDocs = segments.size();

        int totalLength = 0;

        for(int docId = 0; docId < totalDocs; docId++) {
            String content = segments.get(docId).toString();
            String[] tokens = tokenize(content);

            totalLength += tokens.length;

            // 统计每个token 的词频个数
            Map<String, Integer> freqMap = new HashMap<>();

            for(String token : tokens) {
                freqMap.put(token, freqMap.getOrDefault(token, 0) + 1);
                invertedIndex.computeIfAbsent(
                        token, k -> new HashSet<>()
                ).add(docId);
            }

            termFreq.put(docId, freqMap);

        }

        this.avgDocLength = totalLength / (double) totalDocs;

    }

    @Override
    public List<Content> retrieve(dev.langchain4j.rag.query.Query query)  {
        String queryString = query.toString();
        String[] queryTokens = tokenize(queryString);
        Map<Integer, Double> scores = new HashMap<>();

        for(String token : queryTokens) {
            Set<Integer> docWithToken = invertedIndex.getOrDefault(token, Collections.emptySet());
            int df = docWithToken.size();

            double idf = Math.log(1 + (totalDocs - df + 0.5)/(df + 0.5));

            for(Integer docId : docWithToken) {
                int tf = termFreq.get(docId).getOrDefault(token, 0);
                int dl = docLengths.get(docId);

                double score = idf * (tf * (k1 + 1)) / (tf + k1 * (1 - b + b * dl / avgDocLength));
                scores.put(docId, scores.getOrDefault(docId, 0.0) + score);
            }
        }
        return scores.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(5)
                .map(entry -> (Content) new TextContent(segments.get(entry.getKey()).text()))
                .collect(Collectors.toList());
    }

    private String[] tokenize(String text) {
        // 按照文本的段落进行划分
        return text.split("[\\s\\p{Punct}]+");
    }

}
