package com.shawnix.codepadx.service;


import com.shawnix.codepadx.dto.request.code.ExecuteCodeRequest;
import com.shawnix.codepadx.dto.request.code.SaveCodeRequest;
import com.shawnix.codepadx.dto.request.code.UpdateCodeRequest;
import com.shawnix.codepadx.dto.response.PaginationResponse;
import com.shawnix.codepadx.dto.response.code.*;
import com.shawnix.codepadx.entity.Code;
import com.shawnix.codepadx.entity.Language;
import com.shawnix.codepadx.entity.User;
import com.shawnix.codepadx.entity.enums.Role;
import com.shawnix.codepadx.entity.enums.Visibility;
import com.shawnix.codepadx.exception.AppException;
import com.shawnix.codepadx.exception.ErrorCode;
import com.shawnix.codepadx.repository.CodeRepository;
import com.shawnix.codepadx.repository.LanguageRepository;
import com.shawnix.codepadx.repository.UserRepository;
import com.shawnix.codepadx.service.executor.CodeExecutor;
import com.shawnix.codepadx.service.executor.JavaExecutor;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var user = getCurrentUser();
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
        validateOwnerOrAdmin(code);
        codeRepository.delete(code);
    }

    public PaginationResponse<CodeResponse> getAllCodes(int page, int size) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(normalizedPage, normalizedSize, Sort.by(Sort.Direction.DESC, "updatedAt", "id"));

        Page<Code> codes;
        if(getCurrentUser().getRole() == Role.ADMIN) {
            codes = codeRepository.findAll(pageable);
        } else {
            codes = codeRepository.findByUserId(getCurrentUser().getId(), pageable);
        }
        List<CodeResponse> items = codes.stream().map(code -> CodeResponse.toResponse(code)).toList();

        return PaginationResponse.<CodeResponse>builder()
                .data(items)
                .page(codes.getNumber())
                .size(codes.getSize())
                .totalElements(codes.getTotalElements())
                .totalPages(codes.getTotalPages())
                .build();
    }

    public UpdateCodeResponse updateCode(Long id, UpdateCodeRequest request) {
        var code = codeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));
        validateOwnerOrAdmin(code);
        var language = languageRepository.findById(request.getLanguageId()).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        code.setTitle(request.getTitle());
        code.setSourceCode(request.getSourceCode());
        code.setInput(request.getInput());
        code.setLanguage(language);
        var updatedCode = codeRepository.save(code);
        return UpdateCodeResponse.builder()
                .id(updatedCode.getId())
                .title(updatedCode.getTitle())
                .sourceCode(updatedCode.getSourceCode())
                .input(updatedCode.getInput())
                .languageId(updatedCode.getLanguage().getId())
                .userId(updatedCode.getUser().getId())
                .createdAt(updatedCode.getCreatedAt())
                .updatedAt(updatedCode.getUpdatedAt())
                .build();
    }

    public CodeResponse getCodeById(Long id) {
        var code = codeRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CODE_NOT_FOUND));
        validateOwnerOrAdmin(code);
        return CodeResponse.toResponse(code);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
        return user;
    }

    private void validateOwnerOrAdmin(Code code) {
        User currentUser = getCurrentUser();
        boolean isOwner = code.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        if (!isOwner && !isAdmin) {
            throw new AppException(ErrorCode.PERMISSION_DENIED);
        }
    }

    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        Language language = languageRepository.findById(request.getLanguageId()).orElseThrow(() -> new AppException(ErrorCode.LANGUAGE_NOT_FOUND));
        CodeExecutor executor = null;
        switch (language.getCode()) {
            case "java" -> executor = new JavaExecutor();
            default -> throw new AppException(ErrorCode.CODE_NOT_FOUND);
        }
        LocalExecuteResponse localExecuteResponse = executor.execute(request.getSourceCode(), request.getInput());
        return ExecuteCodeResponse.builder()
                .sourceCode(request.getSourceCode())
                .input(request.getInput())
                .languageId(request.getLanguageId())
                .stdout(localExecuteResponse.getStdout())
                .stderr(localExecuteResponse.getStderr())
                .build();
    }
}
