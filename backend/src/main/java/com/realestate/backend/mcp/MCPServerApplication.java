package com.realestate.backend.mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for running the MCP server standalone if needed.
 * This allows the MCP server to be run independently from the main application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.realestate.backend.mcp", "com.realestate.backend.service"})
public class MCPServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MCPServerApplication.class, args);
    }
}
