package org.jianqiaozh.java.ai.langchain4j.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jianqiaozh.java.ai.langchain4j.retriever.CompositeRetriever;
import org.jianqiaozh.java.ai.langchain4j.retriever.InMemoryBM25Retriever;
import org.jianqiaozh.java.ai.langchain4j.store.MongoChatMemoryStore;
import org.jianqiaozh.java.ai.langchain4j.tools.XiaozhiTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class XiaozhiAgentConfig {
    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProviderXiaozhi(){
        return memoryId ->
            MessageWindowChatMemory.builder()
                    .id(memoryId)
                    .maxMessages(30)
                    .chatMemoryStore(mongoChatMemoryStore)
                    .build();

    }

    @Bean
    ContentRetriever contentRetrieverXiaozhi(){
        Document document1 = FileSystemDocumentLoader.loadDocument("src/main/resources/knowledge/医院信息.md");
        Document document2 = FileSystemDocumentLoader.loadDocument("src/main/resources/knowledge/科室信息.md");
        Document document3 = FileSystemDocumentLoader.loadDocument("src/main/resources/knowledge/神经内科.md");

        List<Document> documents = Arrays.asList(document1, document2, document3);

        // 使用内存向量存储
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 使用默认文档分割器
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    ContentRetriever contentRetrieverXiaozhiPincone(EmbeddingStore<TextSegment> embeddingStore){
        return EmbeddingStoreContentRetriever.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .maxResults(1)
                .minScore(0.2)
                .build();
    }

    @Bean
    ContentRetriever contentRetrieverXiaozhiAcademic() throws IOException {
        String path = "src/main/resources/knowledge/testPaper.pdf";
        File file = new File(path);

        try (PDDocument pdDocument = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(pdDocument);

            // 更高效地处理换行符
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c != '\n' && c != '\r') {
                    textBuilder.append(c);
                } else {
                    // 添加双换行符
                    textBuilder.append("\n\n");
                }
            }
            String processedText = textBuilder.toString();

            Document document = Document.from(processedText);
            InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

            EmbeddingStoreIngestor.ingest(Collections.singletonList(document), embeddingStore);

            System.out.println("文件已经被转化为存储在内存当中的向量数据库");

            return EmbeddingStoreContentRetriever.from(embeddingStore);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // 重新抛出异常以便@Bean方法能正确处理
        }
    }

    @Bean
    XiaozhiTools xiaozhiTools() {
        return new XiaozhiTools();
    }

    @Bean
    InMemoryBM25Retriever bm25Retriever() throws IOException {
        String path = "src/main/resources/knowledge/testPaper.pdf";
        File file = new File(path);

        try(PDDocument pdDocument = PDDocument.load(file)){
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(pdDocument);

            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c != '\n' && c != '\r') {
                    textBuilder.append(c);
                } else {
                    textBuilder.append("\n\n");
                }
            }
            String processedText = textBuilder.toString();

            Document document = Document.from(processedText);
            String[] paragraphs = processedText.split("\\n\\s*\\n+");

            List<TextSegment> segments = new ArrayList<>();
            for (String paragraph : paragraphs) {
                String trimmed = paragraph.trim();
                if (!trimmed.isEmpty()) {
                    segments.add(TextSegment.from(trimmed));
                }
            }

            // 构建 InMemoryBM25Retriever
            return new InMemoryBM25Retriever(segments);
        }
    }

    @Bean
    EmbeddingStoreContentRetriever embeddingRetriever() throws IOException {
        String path = "src/main/resources/knowledge/testPaper.pdf";
        File file = new File(path);

        try (PDDocument pdDocument = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(pdDocument);

            // 更高效地处理换行符
            StringBuilder textBuilder = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c != '\n' && c != '\r') {
                    textBuilder.append(c);
                } else {
                    // 添加双换行符
                    textBuilder.append("\n\n");
                }
            }
            String processedText = textBuilder.toString();

            Document document = Document.from(processedText);
            InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

            EmbeddingStoreIngestor.ingest(Collections.singletonList(document), embeddingStore);

            System.out.println("文件已经被转化为存储在内存当中的向量数据库");

            return EmbeddingStoreContentRetriever.from(embeddingStore);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // 重新抛出异常以便@Bean方法能正确处理
        }
    }

    @Bean
    ContentRetriever contentRetrieverXiaozhiAcademicMultiVersion(
            InMemoryBM25Retriever bm25Retriever,
            EmbeddingStoreContentRetriever embeddingRetriever
    ) {
        return new CompositeRetriever(List.of(
                bm25Retriever,
                embeddingRetriever
        ));
    }



}
