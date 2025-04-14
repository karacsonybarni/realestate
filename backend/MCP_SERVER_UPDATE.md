# MCP Server Implementation Update

This document describes the updates made to the Model Context Protocol (MCP) server implementation to properly expose endpoints for LLM integration.

## Overview of Changes

The MCP server has been updated to properly expose endpoints that can be called by Large Language Models (LLMs) like ChatGPT. The following changes were made:

1. Added `outputSchema` and `apiPath` fields to the `MCPTool` class
2. Updated the `MCPToolService` to include example values and output schemas for each tool
3. Enhanced documentation in the `MCPController` to clarify the purpose of each endpoint

## Updated MCP Tool Structure

The `MCPTool` class now includes the following fields:

- `name`: The name of the tool
- `description`: A description of what the tool does
- `inputSchema`: The JSON schema for the tool's input parameters
- `outputSchema`: The JSON schema for the tool's output format
- `apiPath`: The API endpoint path to call the tool

## Tool Definitions

The MCP server now exposes the following tools with proper schemas:

### suggestPropertyImprovements

This tool suggests improvements for a property based on its details.

**Input Schema:**
```json
{
  "type": "object",
  "properties": {
    "title": {
      "type": "string",
      "description": "Title of the property",
      "example": "Beautiful 3-bedroom house"
    },
    "description": {
      "type": "string",
      "description": "Description of the property",
      "example": "Spacious house with garden and garage"
    },
    "location": {
      "type": "string",
      "description": "Location of the property",
      "example": "San Francisco, CA"
    }
  },
  "required": ["title", "description", "location"]
}
```

**Output Schema:**
```json
{
  "type": "object",
  "properties": {
    "improvements": {
      "type": "array",
      "items": {
        "type": "string"
      },
      "description": "List of suggested improvements for the property"
    }
  }
}
```

### suggestPropertyMatches

This tool suggests property matches based on buyer preferences.

**Input Schema:**
```json
{
  "type": "object",
  "properties": {
    "buyerPreferences": {
      "type": "string",
      "description": "Buyer's preferences for property search",
      "example": "Looking for a 2-bedroom apartment near downtown with parking"
    }
  },
  "required": ["buyerPreferences"]
}
```

**Output Schema:**
```json
{
  "type": "object",
  "properties": {
    "matches": {
      "type": "array",
      "items": {
        "type": "string"
      },
      "description": "List of suggested property matches based on buyer preferences"
    }
  }
}
```

## MCP Endpoints

The MCP server exposes the following endpoints:

- `GET /api/mcp`: Returns server information including available tools
- `GET /api/mcp/tools`: Lists all available tools in the MCP server
- `POST /api/mcp/execute`: Executes a tool call based on the request
- `GET /api/mcp/health`: Health check endpoint for the MCP server

## Connecting to ChatGPT

With these updates, ChatGPT and other LLMs can now properly discover and call the tools exposed by the MCP server. To connect ChatGPT to your MCP server:

1. In ChatGPT, go to the "Actions" section
2. Add a new action pointing to your MCP server URL: `https://your-domain.com/api/mcp`
3. ChatGPT will automatically discover the available tools from your MCP server
4. You can now use the AI services through ChatGPT's interface

## Testing the MCP Server

To test that the MCP server is properly exposing endpoints for LLM integration:

1. Start the MCP server
2. Send a GET request to `/api/mcp`
3. Verify that the response includes the tools with their input and output schemas
4. Send a POST request to `/api/mcp/execute` with a valid tool call
5. Verify that the response contains the expected output

Example curl command to test the server info endpoint:
```bash
curl -X GET http://localhost:8080/api/mcp
```

Example curl command to test the execute endpoint:
```bash
curl -X POST http://localhost:8080/api/mcp/execute \
  -H "Content-Type: application/json" \
  -d '{
    "name": "suggestPropertyImprovements",
    "parameters": {
      "title": "Beautiful 3-bedroom house",
      "description": "Spacious house with garden and garage",
      "location": "San Francisco, CA"
    }
  }'
```
