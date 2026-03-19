package com.shawnix.codepadx.service;


import com.shawnix.codepadx.dto.request.code.SaveCodeRequest;
import com.shawnix.codepadx.dto.request.code.UpdateCodeRequest;
import com.shawnix.codepadx.dto.response.code.CodeResponse;
import com.shawnix.codepadx.dto.response.code.SaveCodeResponse;
import com.shawnix.codepadx.entity.Code;
import com.shawnix.codepadx.entity.enums.Visibility;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.CodeRepository;
import com.shawnix.codepadx.repository.LanguageRepository;
import com.shawnix.codepadx.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {
    private CodeRepository codeRepository;
    private LanguageRepository languageRepository;
    private UserRepository userRepository;

    public CodeService(CodeRepository codeRepository, LanguageRepository languageRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.languageRepository = languageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public SaveCodeResponse saveCode(SaveCodeRequest request) {
        var language = languageRepository.findById(request.getLanguageId()).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        var user = userRepository.findById(4).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        System.out.println(language.getExampleCode());
        System.out.println(request.getSourceCode());
        var savedCode = codeRepository.save(Code.builder()
                        .sourceCode(request.getSourceCode())
                        .language(language)
                        .user(user)
                        .input(request.getInput())
                        .title(request.getTitle())
                        .visibility(Visibility.PUBLIC)
                .build());
        return SaveCodeResponse.builder()
                .id(savedCode.getId())
                .title(savedCode.getTitle())
                .sourceCode(savedCode.getSourceCode())
                .input(savedCode.getInput())
                .languageId(savedCode.getLanguage().getId())
                .userId(savedCode.getUser().getId())
                .createdAt(savedCode.getCreatedAt())
                .updatedAt(savedCode.getUpdatedAt())
                .build();
    }

    public void deleteCode(Long id) {
        var code = codeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));
        codeRepository.delete(code);
    }

    public List<CodeResponse> getAllCodes() {
        return codeRepository.findAll().stream().map(code -> CodeResponse.builder()
                .id(code.getId())
                .title(code.getTitle())
                .sourceCode(code.getSourceCode())
                .input(code.getInput())
                .languageId(code.getLanguage().getId())
                .userId(code.getUser().getId())
                .createdAt(code.getCreatedAt())
                .updatedAt(code.getUpdatedAt())
                .build()).toList();
    }

    public void updateCode(Long id, UpdateCodeRequest request) {
        var code = codeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));
        var language = languageRepository.findById(request.getLanguageId()).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        code.setTitle(request.getTitle());
        code.setSourceCode(request.getSourceCode());
        code.setInput(request.getInput());
        code.setLanguage(language);
        codeRepository.save(code);
    }

    public CodeResponse getCodeById(Long id) {
        var code = codeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));
        return CodeResponse.builder()
                .id(code.getId())
                .title(code.getTitle())
                .sourceCode(code.getSourceCode())
                .input(code.getInput())
                .languageId(code.getLanguage().getId())
                .userId(code.getUser().getId())
                .createdAt(code.getCreatedAt())
                .updatedAt(code.getUpdatedAt())
                .build();
    }
}
