package com.realestate.backend.mcp.service;
import com.realestate.backend.mcp.model.MCPTool;
import com.realestate.backend.service.AiService;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for handling MCP tool registration and management.
 */
@Service
public class MCPToolService {
    
    private final AiService aiService;
    
    public MCPToolService(AiService aiService) {
        this.aiService = aiService;
    }
    
    /**
     * Returns a list of all available tools in the MCP server.
     * 
     * @return List of MCPTool objects
     */
    public List<MCPTool> listTools() {
        return Arrays.asList(
            createSuggestPropertyImprovementsTool(),
            createSuggestPropertyMatchesTool()
        );
    }
    
    /**
     * Creates the tool definition for property improvements suggestions.
     */
    private MCPTool createSuggestPropertyImprovementsTool() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("title", createStringProperty("Title of the property", "Beautiful 3-bedroom house"));
        properties.put("description", createStringProperty("Description of the property", "Spacious house with garden and garage"));
        properties.put("location", createStringProperty("Location of the property", "San Francisco, CA"));
        
        Map<String, Object> inputSchema = new HashMap<>();
        inputSchema.put("type", "object");
        inputSchema.put("properties", properties);
        inputSchema.put("required", Arrays.asList("title", "description", "location"));
        
        Map<String, Object> outputSchema = new HashMap<>();
        outputSchema.put("type", "object");
        outputSchema.put("properties", Map.of(
            "improvements", Map.of(
                "type", "array",
                "items", Map.of("type", "string"),
                "description", "List of suggested improvements for the property"
            )
        ));
        
        return MCPTool.builder()
                .name("suggestPropertyImprovements")
                .description("Suggests improvements for a property based on its details")
                .inputSchema(inputSchema)
                .outputSchema(outputSchema)
                .apiPath("/api/mcp/execute")
                .build();
    }
    
    /**
     * Creates the tool definition for property matches suggestions.
     */
    private MCPTool createSuggestPropertyMatchesTool() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("buyerPreferences", createStringProperty("Buyer's preferences for property search", "Looking for a 2-bedroom apartment near downtown with parking"));
        
        Map<String, Object> inputSchema = new HashMap<>();
        inputSchema.put("type", "object");
        inputSchema.put("properties", properties);
        inputSchema.put("required", Arrays.asList("buyerPreferences"));
        
        Map<String, Object> outputSchema = new HashMap<>();
        outputSchema.put("type", "object");
        outputSchema.put("properties", Map.of(
            "matches", Map.of(
                "type", "array",
                "items", Map.of("type", "string"),
                "description", "List of suggested property matches based on buyer preferences"
            )
        ));
        
        return MCPTool.builder()
                .name("suggestPropertyMatches")
                .description("Suggests property matches based on buyer preferences")
                .inputSchema(inputSchema)
                .outputSchema(outputSchema)
                .apiPath("/api/mcp/execute")
                .build();
    }
    
    /**
     * Helper method to create a string property definition.
     */
    private Map<String, Object> createStringProperty(String description, String example) {
        Map<String, Object> property = new HashMap<>();
        property.put("type", "string");
        property.put("description", description);
        property.put("example", example);
        return property;
    }
}
