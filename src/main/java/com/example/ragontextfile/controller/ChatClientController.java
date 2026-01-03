package com.example.ragontextfile.controller;

import com.example.ragontextfile.service.AiService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/ai/chat-client")
public class ChatClientController {
    private final ChatClient chatClient;
    private final AiService aiService;

    public ChatClientController(ChatClient chatClient, AiService aiService) {
        this.chatClient = chatClient;
        this.aiService = aiService;
    }
    /**
     * This method uses the ChatClient to generate a response to a user's query using a QuestionAnswerAdvisor.
     *
     */
    @PostMapping("/query")
    public Map<String, String> chat(@RequestParam String query) {
        VectorStore vectorStore = aiService.getVectorStore();
        return Map.of("response",
                Objects.requireNonNull(chatClient.prompt()
                        .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                        .user(query)
                        .call()
                        .content()));


    }



}
