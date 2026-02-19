# Elasticsearch MCP Server

A Spring Boot application that exposes Elasticsearch operations as **MCP (Model Context Protocol)** tools, allowing AI assistants to directly interact with your product data.

## 🚀 Features

*   **MCP Integration**: Native tools for AI assistants (Claude, etc.).
*   **Search**: Full-text search, category/brand filtering, price ranges.
*   **Management**: CRUD operations for the product catalog.
*   **Flexible Auth**: Supports API Key, Basic Auth, or No Auth.

## 📋 Prerequisites

*   Java 21+
*   Maven 3.6+
*   Elasticsearch 8.x/9.x (Docker recommended)

## 🖥️ Running the server

Start the MCP server (ensure Elasticsearch is running and configured in `application.yml`):

```bash
./mvnw spring-boot:run
```

The server listens on **port 8080** and exposes the MCP endpoint at **`/mcp`** (streamable HTTP).

## 🔌 Using this MCP in Cursor over HTTP

This server uses **streamable HTTP** transport, so Cursor connects to it via a URL instead of starting a process.

1. **Start the server** (see above). Cursor will not start it for you when using HTTP.
2. **Configure Cursor** to use the HTTP endpoint.

   **Option A – Project config (recommended)**  
   This repo includes `.cursor/mcp.json`:

   ```json
   {
     "mcpServers": {
       "elasticsearch-mcp": {
         "url": "http://localhost:8080/mcp",
         "type": "streamableHttp"
       }
     }
   }
   ```

   **Option B – Global config**  
   Add the same `elasticsearch-mcp` entry to `~/.cursor/mcp.json` under `mcpServers`.

3. **Reload Cursor** (or restart) so it picks up the MCP config.
4. **Check MCP status** in **Cursor Settings → MCP**. The server must be running at `http://localhost:8080` for the connection to succeed.

If the MCP shows an error, ensure the Spring Boot app is running and that nothing else is using port 8080.
