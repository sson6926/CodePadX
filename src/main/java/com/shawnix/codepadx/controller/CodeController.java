package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.code.ExecuteCodeRequest;
import com.shawnix.codepadx.dto.request.code.SaveCodeRequest;
import com.shawnix.codepadx.dto.request.code.UpdateCodeRequest;
import com.shawnix.codepadx.dto.response.ApiResponse;
import com.shawnix.codepadx.dto.response.PaginationResponse;
import com.shawnix.codepadx.dto.response.code.CodeResponse;
import com.shawnix.codepadx.dto.response.code.SaveCodeResponse;
import com.shawnix.codepadx.service.CodeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    ApiResponse<PaginationResponse<CodeResponse>> getAllCodes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<PaginationResponse<CodeResponse>>builder()
                .message("Get all codes ok")
                .data(codeService.getAllCodes(page, size))
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
        return ApiResponse.builder()
                .message("Update code ok")
                .data(codeService.updateCode(id, request))
                .build();
    }

    @PostMapping("/execute")
    ApiResponse executeCode(@RequestBody ExecuteCodeRequest request) {
        return ApiResponse.builder()
                .message("Execute code ok")
                .data(codeService.executeCode(request))
                .build();
    }

}
