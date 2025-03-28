# Model Context Protocol (MCP) Server Implementation

This document provides comprehensive documentation for the Model Context Protocol (MCP) server implementation in the Real Estate application.

## Overview

The Model Context Protocol (MCP) is an open protocol that standardizes how applications provide context to Large Language Models (LLMs). This implementation adds MCP server capabilities to the existing Spring backend, allowing AI assistants like ChatGPT to connect to the application and use its AI services.

## Architecture

The MCP server implementation follows a standard Spring architecture with the following components:

### Model Classes

1. **MCPRequest**: Represents a request to the MCP server with a tool name and parameters.
2. **MCPResponse**: Represents a response from the MCP server with status, result, and error information.
3. **MCPTool**: Represents a tool definition in the MCP protocol with name, description, and input schema.
4. **MCPServerInfo**: Represents the MCP server information response with server details and available tools.

### Services

1. **MCPToolService**: Handles MCP tool registration and management, providing a list of available tools.
2. **MCPExecutionService**: Handles MCP tool execution, processing requests and generating responses.

### Controllers

1. **MCPController**: Exposes REST endpoints for the MCP protocol, including:
   - `GET /api/mcp`: Returns server information including available tools
   - `GET /api/mcp/tools`: Lists all available tools
   - `POST /api/mcp/execute`: Executes a tool call
   - `GET /api/mcp/health`: Health check endpoint

### Configuration

1. **MCPConfig**: Provides configuration for the MCP server, including CORS settings.

### Standalone Application

1. **MCPServerApplication**: Main application class for running the MCP server standalone if needed.

## Integration with Existing Services

The MCP server integrates with the existing `AiService` to provide AI functionality through the MCP protocol. The current implementation exposes two AI tools:

1. **suggestPropertyImprovements**: Suggests improvements for a property based on its details.
2. **suggestPropertyMatches**: Suggests property matches based on buyer preferences.

## API Documentation

### Server Information

**Endpoint**: `GET /api/mcp`

**Response**:
```json
{
  "name": "RealEstate MCP Server",
  "version": "1.0.0",
  "description": "Model Context Protocol server for real estate AI services",
  "tools": [
    {
      "name": "suggestPropertyImprovements",
      "description": "Suggests improvements for a property based on its details",
      "inputSchema": {
        "type": "object",
        "properties": {
          "title": {
            "type": "string",
            "description": "Title of the property"
          },
          "description": {
            "type": "string",
            "description": "Description of the property"
          },
          "location": {
            "type": "string",
            "description": "Location of the property"
          }
        },
        "required": ["title", "description", "location"]
      }
    },
    {
      "name": "suggestPropertyMatches",
      "description": "Suggests property matches based on buyer preferences",
      "inputSchema": {
        "type": "object",
        "properties": {
          "buyerPreferences": {
            "type": "string",
            "description": "Buyer's preferences for property search"
          }
        },
        "required": ["buyerPreferences"]
      }
    }
  ]
}
```

### List Tools

**Endpoint**: `GET /api/mcp/tools`

**Response**:
```json
[
  {
    "name": "suggestPropertyImprovements",
    "description": "Suggests improvements for a property based on its details",
    "inputSchema": {
      "type": "object",
      "properties": {
        "title": {
          "type": "string",
          "description": "Title of the property"
        },
        "description": {
          "type": "string",
          "description": "Description of the property"
        },
        "location": {
          "type": "string",
          "description": "Location of the property"
        }
      },
      "required": ["title", "description", "location"]
    }
  },
  {
    "name": "suggestPropertyMatches",
    "description": "Suggests property matches based on buyer preferences",
    "inputSchema": {
      "type": "object",
      "properties": {
        "buyerPreferences": {
          "type": "string",
          "description": "Buyer's preferences for property search"
        }
      },
      "required": ["buyerPreferences"]
    }
  }
]
```

### Execute Tool

**Endpoint**: `POST /api/mcp/execute`

**Request**:
```json
{
  "toolName": "suggestPropertyImprovements",
  "parameters": {
    "title": "Cozy Apartment in Downtown",
    "description": "A small but comfortable apartment in the heart of the city",
    "location": "Downtown"
  }
}
```

**Response**:
```json
{
  "status": "success",
  "result": "Consider adding key amenities for location: Downtown",
  "error": null
}
```

### Health Check

**Endpoint**: `GET /api/mcp/health`

**Response**:
```json
{
  "status": "healthy",
  "version": "1.0.0",
  "protocol": "MCP"
}
```

## Using with ChatGPT

To use the MCP server with ChatGPT:

1. Deploy the MCP server (see Deployment section below)
2. Configure ChatGPT to connect to the MCP server URL
3. Use the available tools through ChatGPT's interface

## Future Enhancements

Potential future enhancements for the MCP server:

1. Add authentication and authorization for secure access
2. Implement more AI tools for real estate operations
3. Add logging and monitoring for tool usage
4. Implement rate limiting to prevent abuse
5. Add support for streaming responses for long-running operations

## Deployment

The MCP server can be deployed in two ways:

1. **As part of the main application**: The MCP server components are automatically included when deploying the main Spring application.
2. **As a standalone service**: The MCP server can be run independently using the `MCPServerApplication` class.

See the Deployment Documentation for detailed instructions on deploying the MCP server.
