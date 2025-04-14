package com.realestate.backend.mcp.controller;
import com.realestate.backend.mcp.model.MCPRequest;
import com.realestate.backend.mcp.model.MCPResponse;
import com.realestate.backend.mcp.model.MCPServerInfo;
import com.realestate.backend.mcp.model.MCPTool;
import com.realestate.backend.mcp.service.MCPExecutionService;
import com.realestate.backend.mcp.service.MCPToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling MCP protocol requests.
 * Implements the Model Context Protocol specification for LLM integration.
 */
@RestController
@RequestMapping("/api/mcp")
public class MCPController {
    
    private final MCPToolService mcpToolService;
    private final MCPExecutionService mcpExecutionService;
    
    public MCPController(MCPToolService mcpToolService, MCPExecutionService mcpExecutionService) {
        this.mcpToolService = mcpToolService;
        this.mcpExecutionService = mcpExecutionService;
    }
    
    /**
     * Lists all available tools in the MCP server.
     * 
     * @return List of available tools
     */
    @GetMapping("/tools")
    public ResponseEntity<List<MCPTool>> listTools() {
        return ResponseEntity.ok(mcpToolService.listTools());
    }
    
    /**
     * Executes a tool call based on the request.
     * This endpoint follows the MCP specification for tool execution.
     * 
     * @param request The MCP request
     * @return The execution result
     */
    @PostMapping("/execute")
    public ResponseEntity<MCPResponse> executeToolCall(@RequestBody MCPRequest request) {
        MCPResponse response = mcpExecutionService.executeToolCall(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Health check endpoint for the MCP server.
     * 
     * @return Status information
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of(
            "status", "healthy",
            "version", "1.0.0",
            "protocol", "MCP"
        ));
    }
    
    /**
     * Returns server information including available tools.
     * This is the main endpoint that LLMs will call to discover available tools.
     * 
     * @return Server information
     */
    @GetMapping
    public ResponseEntity<MCPServerInfo> getServerInfo() {
        MCPServerInfo serverInfo = MCPServerInfo.builder()
                .name("RealEstate MCP Server")
                .version("1.0.0")
                .description("Model Context Protocol server for real estate AI services")
                .tools(mcpToolService.listTools())
                .build();
        return ResponseEntity.ok(serverInfo);
    }
}
