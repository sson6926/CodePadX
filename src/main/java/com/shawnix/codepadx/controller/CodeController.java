package com.shawnix.codepadx.controller;

import com.shawnix.codepadx.dto.request.code.SaveCodeRequest;
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

}
