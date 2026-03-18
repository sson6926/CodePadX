package com.shawnix.codepadx.dto.response.code;

import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageResponse {
    private Long id;
    private String code;
    private String name;
    private String fileExtension;
    private String exampleCode;
}
