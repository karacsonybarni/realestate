package com.realestate.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.backend.dto.mcp.*;
import com.realestate.backend.model.AppUser;
import com.realestate.backend.model.Property;
import com.realestate.backend.service.PropertyService;
import com.realestate.backend.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mcp")
public class MCPController {

    private final PropertyService propertyService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public MCPController(PropertyService propertyService, UserService userService, ObjectMapper objectMapper) {
        this.propertyService = propertyService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/info")
    public MCPServerInfo getServerInfo() {
        List<MCPTool> tools = new ArrayList<>();
        
        // Property tools
        tools.add(createTool("listProperties", "List all properties", PropertyListParameters.class));
        tools.add(createTool("getProperty", "Get a property by ID", PropertyGetParameters.class));
        tools.add(createTool("searchProperties", "Search properties by location", PropertySearchParameters.class));
        tools.add(createTool("createProperty", "Create a new property", PropertyCreateParameters.class));
        tools.add(createTool("updateProperty", "Update an existing property", PropertyUpdateParameters.class));
        tools.add(createTool("deleteProperty", "Delete a property", PropertyDeleteParameters.class));
        
        // User tools
        tools.add(createTool("listUsers", "List all users", UserListParameters.class));
        tools.add(createTool("getUser", "Get a user by ID", UserGetParameters.class));
        tools.add(createTool("createUser", "Create a new user", UserCreateParameters.class));
        tools.add(createTool("updateUser", "Update an existing user", UserUpdateParameters.class));
        tools.add(createTool("deleteUser", "Delete a user", UserDeleteParameters.class));
        
        return MCPServerInfo.builder()
                .name("RealEstate MCP Server")
                .version("1.0.0")
                .description("MCP Server for Real Estate application that provides access to properties and users")
                .tools(tools)
                .build();
    }
    
    @PostMapping(value = "/invoke", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MCPResponse invokeFunction(@RequestBody MCPRequest request) {
        try {
            String toolName = request.getName();
            Object parameters = request.getParameters();
            
            switch (toolName) {
                // Property endpoints
                case "listProperties":
                    return MCPResponse.success(propertyService.listAllProperties());
                    
                case "getProperty":
                    PropertyGetParameters getParams = convertParameters(parameters, PropertyGetParameters.class);
                    return MCPResponse.success(propertyService.getPropertyById(getParams.getId()));
                    
                case "searchProperties":
                    PropertySearchParameters searchParams = convertParameters(parameters, PropertySearchParameters.class);
                    return MCPResponse.success(propertyService.searchByLocation(searchParams.getLocation()));
                    
                case "createProperty":
                    PropertyCreateParameters createParams = convertParameters(parameters, PropertyCreateParameters.class);
                    Property createdProperty = propertyService.createProperty(createParams.toPropertyRequest());
                    return MCPResponse.success(createdProperty);
                    
                case "updateProperty":
                    PropertyUpdateParameters updateParams = convertParameters(parameters, PropertyUpdateParameters.class);
                    Property updatedProperty = propertyService.updateProperty(updateParams.getId(), updateParams.toPropertyRequest());
                    return MCPResponse.success(updatedProperty);
                    
                case "deleteProperty":
                    PropertyDeleteParameters deleteParams = convertParameters(parameters, PropertyDeleteParameters.class);
                    propertyService.deleteProperty(deleteParams.getId());
                    return MCPResponse.success("Property deleted successfully");
                
                // User endpoints
                case "listUsers":
                    return MCPResponse.success(userService.listAllUsers());
                    
                case "getUser":
                    UserGetParameters userGetParams = convertParameters(parameters, UserGetParameters.class);
                    return MCPResponse.success(userService.getUserById(userGetParams.getId()));
                    
                case "createUser":
                    UserCreateParameters userCreateParams = convertParameters(parameters, UserCreateParameters.class);
                    AppUser createdUser = userService.createUser(userCreateParams.toAppUser());
                    return MCPResponse.success(createdUser);
                    
                case "updateUser":
                    UserUpdateParameters userUpdateParams = convertParameters(parameters, UserUpdateParameters.class);
                    AppUser updatedUser = userService.updateUser(userUpdateParams.getId(), userUpdateParams.toAppUser());
                    return MCPResponse.success(updatedUser);
                    
                case "deleteUser":
                    UserDeleteParameters userDeleteParams = convertParameters(parameters, UserDeleteParameters.class);
                    userService.deleteUser(userDeleteParams.getId());
                    return MCPResponse.success("User deleted successfully");
                    
                default:
                    return MCPResponse.error("Unknown tool: " + toolName);
            }
        } catch (Exception e) {
            return MCPResponse.error("Error: " + e.getMessage());
        }
    }
    
    private <T> T convertParameters(Object parameters, Class<T> clazz) {
        return objectMapper.convertValue(parameters, clazz);
    }
    
    private MCPTool createTool(String name, String description, Class<?> parameterClass) {
        Map<String, Object> properties = new HashMap<>();
        
        // You would extract properties from the class and add them here
        // For simplicity, we'll just note the required parameter types
        
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        schema.put("properties", properties);
        
        // Add properties based on the parameter class
        if (parameterClass == PropertyGetParameters.class || 
            parameterClass == PropertyDeleteParameters.class ||
            parameterClass == UserGetParameters.class ||
            parameterClass == UserDeleteParameters.class) {
            properties.put("id", new HashMap<String, Object>() {{
                put("type", "integer");
                put("description", "The ID of the resource");
            }});
        } else if (parameterClass == PropertySearchParameters.class) {
            properties.put("location", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "Location to search for");
            }});
        } else if (parameterClass == PropertyCreateParameters.class || 
                   parameterClass == PropertyUpdateParameters.class) {
            if (parameterClass == PropertyUpdateParameters.class) {
                properties.put("id", new HashMap<String, Object>() {{
                    put("type", "integer");
                    put("description", "The ID of the property to update");
                }});
            }
            properties.put("title", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "Property title");
            }});
            properties.put("description", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "Property description");
            }});
            properties.put("location", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "Property location");
            }});
            properties.put("price", new HashMap<String, Object>() {{
                put("type", "number");
                put("description", "Property price");
            }});
            properties.put("ownerId", new HashMap<String, Object>() {{
                put("type", "integer");
                put("description", "ID of the property owner");
            }});
            properties.put("imageUrls", new HashMap<String, Object>() {{
                put("type", "array");
                put("items", new HashMap<String, Object>() {{
                    put("type", "string");
                }});
                put("description", "List of property image URLs");
            }});
        } else if (parameterClass == UserCreateParameters.class || 
                   parameterClass == UserUpdateParameters.class) {
            if (parameterClass == UserUpdateParameters.class) {
                properties.put("id", new HashMap<String, Object>() {{
                    put("type", "integer");
                    put("description", "The ID of the user to update");
                }});
            }
            properties.put("name", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "User's name");
            }});
            properties.put("email", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "User's email");
            }});
            properties.put("role", new HashMap<String, Object>() {{
                put("type", "string");
                put("description", "User's role (e.g., 'LANDLORD', 'TENANT', 'ADMIN')");
            }});
        }
        
        // For list parameters, there are no required fields
        
        return MCPTool.builder()
                .name(name)
                .description(description)
                .parameters(schema)
                .build();
    }
}