package com.realestate.backend.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents a request to the MCP server.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPRequest {
    private String toolName;
    private Map<String, Object> parameters;
}
