package com.example.ragontextfile.controller;

import com.example.ragontextfile.service.AiService;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ai/rag")
public class RAGController {

    private final ChatModel chatModel;
    private final AiService aiService;
    public RAGController(ChatModel chatModel, AiService aiService) {
        this.chatModel = chatModel;
        this.aiService = aiService;
    }

    @PostMapping("/v1/query")
    public String queryResponseV1(@RequestParam String query) throws IOException {
        return aiService.loadSampleText(query).get(0).getText();
    }
    @PostMapping("/v2/query")
    public String queryResponseV1SR(@RequestParam String query) throws IOException {
        return aiService.getDocSR(query).get(0).getText();
    }

    @PostMapping("/v3/query")
    public String queryResponseV3(@RequestParam String query) throws IOException {
        return chatModel.call(new Prompt(query)).getResult().getOutput().getText();
    }


}
