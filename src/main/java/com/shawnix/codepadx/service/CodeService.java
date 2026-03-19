package com.shawnix.codepadx.service;


import com.shawnix.codepadx.dto.request.code.SaveCodeRequest;
import com.shawnix.codepadx.dto.response.code.CodeResponse;
import com.shawnix.codepadx.dto.response.code.SaveCodeResponse;
import com.shawnix.codepadx.entity.Code;
import com.shawnix.codepadx.entity.enums.Visibility;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.CodeRepository;
import com.shawnix.codepadx.repository.LanguageRepository;
import com.shawnix.codepadx.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    public SaveCodeResponse saveCode(SaveCodeRequest request) {
        var language = languageRepository.findById(request.getLanguageId()).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        var user = userRepository.findById(4).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        System.out.println(language.getExampleCode());
        System.out.println(request.getSourceCode());
        var savedCode = codeRepository.save(new Code().builder()
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
}
