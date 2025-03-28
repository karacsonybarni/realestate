package com.realestate.backend.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents a response from the MCP server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPResponse {
    private String status;
    private Object result;
    private String error;
}
