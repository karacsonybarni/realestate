package com.realestate.backend.mcp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the MCP server information response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MCPServerInfo {
    private String name;
    private String version;
    private String description;
    private List<MCPTool> tools;
}
