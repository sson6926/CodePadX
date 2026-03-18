package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.code.LanguageResponse;
import com.shawnix.codepadx.service.LanguageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {
    private LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    ApiResponse<List<LanguageResponse>> getAllLanguages() {
        return ApiResponse.<List<LanguageResponse>>builder()
                .data(languageService.getAllLanguages())
                .message("Get all languages ok")
                .build();
    }
    
}
