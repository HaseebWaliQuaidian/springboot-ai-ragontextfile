package com.example.ragontextfile.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final VectorStore vectorStore;

    public AiService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Value("classpath:data/sample.txt")
    private Resource sampleTextResource;

    @Value("classpath:prompt/system.st")
    private Resource systemPromptResource;

    public List<Document> loadSampleText(String query) throws IOException {
        extractVectorDocuments();
        return vectorStore.similaritySearch(query);

    }

    public List<Document> getDocSR(String query) {
        extractVectorDocuments();
        return vectorStore.similaritySearch(
            SearchRequest.builder()
                    .query(query)
                    .topK(1)
                    .similarityThreshold(0.9)
                    .build()
        );

    }

    public Message getSystemMessage(List<Document> similarDocuments) {
        String documents = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPromptResource);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("documents", documents));
        System.out.println(systemMessage.getText());
        return systemMessage;
    }


    private void extractVectorDocuments() {
        TextReader textReader = new TextReader(sampleTextResource);

        //Converts text to documents. This will be used for vectorization and is in numbers (binary)
        List<Document> documents = textReader.read();

        System.out.println(documents);

        //Chunking of Text (As LLMs can load small chunks of Documents Objects)
        var textSplitter = new TokenTextSplitter();

        vectorStore.add(textSplitter.split(documents));
    }




}
