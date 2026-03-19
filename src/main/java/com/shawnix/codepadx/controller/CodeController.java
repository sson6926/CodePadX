package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.code.SaveCodeRequest;
import com.shawnix.codepadx.dto.request.code.UpdateCodeRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.code.CodeResponse;
import com.shawnix.codepadx.dto.response.code.SaveCodeResponse;
import com.shawnix.codepadx.service.CodeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/codes")
public class CodeController {
    private CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }


    @PostMapping
    ApiResponse<SaveCodeResponse> saveCode(@Valid @RequestBody SaveCodeRequest request) {
        return ApiResponse.<SaveCodeResponse>builder()
                .data(codeService.saveCode(request))
                .message("Save code ok")
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse deleteCode(@PathVariable Long id) {
        codeService.deleteCode(id);
        return ApiResponse.builder()
                .message("Delete code ok")
                .build();
    }

    @GetMapping
    ApiResponse getAllCodes() {
        codeService.getAllCodes();
        return ApiResponse.builder()
                .message("Get all codes ok")
                .data(codeService.getAllCodes())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse getCodeById(@PathVariable Long id) {
        codeService.getCodeById(id);
        return ApiResponse.builder()
                .message("Get code by id ok")
                .data(codeService.getCodeById(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse updateCode(@Valid @RequestBody UpdateCodeRequest request, @PathVariable Long id) {
        codeService.updateCode(id, request);
        return ApiResponse.builder()
                .message("Update code ok")
                .build();
    }


}
