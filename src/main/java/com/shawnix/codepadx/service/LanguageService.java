package com.shawnix.codepadx.service;

import com.shawnix.codepadx.dto.response.code.LanguageResponse;
import com.shawnix.codepadx.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {
    private LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<LanguageResponse> getAllLanguages() {
        return languageRepository.findAll().stream().map(language -> new LanguageResponse().
                builder()
                .id(language.getId())
                .code(language.getCode())
                .name(language.getName())
                .fileExtension(language.getFileExtension())
                .exampleCode(language.getExampleCode())
                .build()).toList();
    }
}
