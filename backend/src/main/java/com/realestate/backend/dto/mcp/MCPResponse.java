package com.realestate.backend.dto.mcp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MCPResponse {
    private Object content;
    private String status;
    private String errorMessage;
    
    public static MCPResponse success(Object content) {
        return MCPResponse.builder()
                .content(content)
                .status("success")
                .build();
    }
    
    public static MCPResponse error(String errorMessage) {
        return MCPResponse.builder()
                .status("error")
                .errorMessage(errorMessage)
                .build();
    }
}
