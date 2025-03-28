package com.realestate.backend.mcp.service;

import com.realestate.backend.mcp.model.MCPRequest;
import com.realestate.backend.mcp.model.MCPResponse;
import com.realestate.backend.service.AiService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service for handling MCP tool execution.
 */
@Service
public class MCPExecutionService {
    
    private final AiService aiService;
    
    public MCPExecutionService(AiService aiService) {
        this.aiService = aiService;
    }
    
    /**
     * Executes a tool based on the MCP request.
     * 
     * @param request The MCP request containing tool name and parameters
     * @return MCPResponse with the result or error
     */
    public MCPResponse executeToolCall(MCPRequest request) {
        try {
            String toolName = request.getToolName();
            Map<String, Object> parameters = request.getParameters();
            
            switch (toolName) {
                case "suggestPropertyImprovements":
                    return executeSuggestPropertyImprovements(parameters);
                case "suggestPropertyMatches":
                    return executeSuggestPropertyMatches(parameters);
                default:
                    return MCPResponse.builder()
                            .status("error")
                            .error("Unknown tool: " + toolName)
                            .build();
            }
        } catch (Exception e) {
            return MCPResponse.builder()
                    .status("error")
                    .error("Error executing tool: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * Executes the suggestPropertyImprovements tool.
     */
    private MCPResponse executeSuggestPropertyImprovements(Map<String, Object> parameters) {
        String title = (String) parameters.get("title");
        String description = (String) parameters.get("description");
        String location = (String) parameters.get("location");
        
        String result = aiService.suggestPropertyImprovements(title, description, location);
        
        return MCPResponse.builder()
                .status("success")
                .result(result)
                .build();
    }
    
    /**
     * Executes the suggestPropertyMatches tool.
     */
    private MCPResponse executeSuggestPropertyMatches(Map<String, Object> parameters) {
        String buyerPreferences = (String) parameters.get("buyerPreferences");
        
        String result = aiService.suggestPropertyMatches(buyerPreferences);
        
        return MCPResponse.builder()
                .status("success")
                .result(result)
                .build();
    }
}
