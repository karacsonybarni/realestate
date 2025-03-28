package com.realestate.backend.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a tool definition in the MCP protocol.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPTool {
    private String name;
    private String description;
    private Map<String, Object> inputSchema;
}
