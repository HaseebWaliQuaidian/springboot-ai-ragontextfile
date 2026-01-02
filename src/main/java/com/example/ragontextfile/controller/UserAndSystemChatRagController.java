package com.example.ragontextfile.controller;

import com.example.ragontextfile.service.AiService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai/chat")
public class UserAndSystemChatRagController {
    private final AiService aiService;
    private final ChatModel chatModel;

    @Autowired
    public UserAndSystemChatRagController(AiService aiService, ChatModel chatModel, ChatModel chatModel1) {
        this.aiService = aiService;
        this.chatModel = chatModel1;
    }

    @PostMapping("/query")
    public Map<String, String> ragChat(@RequestParam String query) throws IOException {
        List<Document> similarDocuments = aiService.loadSampleText(query);

        UserMessage userMessage = new UserMessage(query);
        Message systemMessage = aiService.getSystemMessage(similarDocuments);

        Prompt prompt = new Prompt(systemMessage, userMessage);
        return Map.of("response", chatModel.call(prompt).getResult().getOutput().getText());

    }
}
