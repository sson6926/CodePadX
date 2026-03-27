package com.shawnix.codepadx.dto.response.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LocalExecuteResponse {
    String stdout;
    String stderr;
}
