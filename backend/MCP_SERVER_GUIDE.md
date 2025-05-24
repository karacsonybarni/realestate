# MCP Server Guide for Real Estate Application

This guide explains how to use the Model Context Protocol (MCP) server that has been implemented for the real estate application. The MCP server allows ChatGPT and other AI assistants to interact with the application's API in a structured way.

## What is MCP?

Model Context Protocol (MCP) allows AI assistants like ChatGPT to use your API directly from within a conversation. MCP servers expose a standardized interface that AI assistants can discover and understand, making it possible for them to call your API endpoints without requiring plugins or specific integrations.

## MCP Server Endpoints

Our MCP server exposes two main endpoints:

1. `GET /api/mcp/info` - Provides information about the server and the available tools
2. `POST /api/mcp/invoke` - Executes a function call using a specified tool

## Available Tools

The MCP server provides the following tools:

### Property Tools

- `listProperties` - List all properties 
- `getProperty` - Get a property by ID
- `searchProperties` - Search properties by location
- `createProperty` - Create a new property
- `updateProperty` - Update an existing property
- `deleteProperty` - Delete a property

### User Tools

- `listUsers` - List all users
- `getUser` - Get a user by ID
- `createUser` - Create a new user
- `updateUser` - Update an existing user
- `deleteUser` - Delete a user

## Using the MCP Server with ChatGPT

To use the MCP server with ChatGPT, you can reference this URL:

```
https://your-backend-url/api/mcp
```

When ChatGPT is prompted to interact with your real estate application, it can use this URL to discover the available tools and make calls to your API.

### Example Prompts for ChatGPT

1. "Help me search for properties in New York using the real estate API."
2. "Create a new property listing for me using the real estate API."
3. "Show me all users registered in the real estate system."

## Testing the MCP Server

You can test the MCP server using tools like curl or Postman:

### Getting Server Info

```bash
curl -X GET https://your-backend-url/api/mcp/info
```

### Invoking a Tool

```bash
curl -X POST https://your-backend-url/api/mcp/invoke \
  -H "Content-Type: application/json" \
  -d '{
    "name": "listProperties",
    "parameters": {}
  }'
```

```bash
curl -X POST https://your-backend-url/api/mcp/invoke \
  -H "Content-Type: application/json" \
  -d '{
    "name": "searchProperties",
    "parameters": {
      "location": "New York"
    }
  }'
```

## Security Considerations

The MCP server is configured with CORS to allow requests from the following origins:
- `https://chat.openai.com`
- `https://claude.ai`

If you need to add additional origins, update the CORS configuration in `MCPServerConfig.java`.

## Troubleshooting

If you encounter issues with the MCP server:

1. Ensure the server is running and accessible
2. Check that the URL provided to ChatGPT is correct
3. Verify that the parameters sent in the requests match the expected format
4. Check the server logs for any error messages

## MCP Schema

The MCP server adheres to the standard MCP schema format, making it compatible with LLM-based assistants that support MCP.