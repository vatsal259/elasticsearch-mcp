package com.vatsal.esmcp.config;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Auto-registers all beans that have at least one {@link Tool}-annotated method
 * as MCP tools via a single {@link ToolCallbackProvider}.
 * <p>
 * Spring AI does not auto-register {@code @Tool} for the MCP server when only
 * the MCP starter is on the classpath: the MCP server natively auto-discovers
 * only {@code @McpTool}, and the autoconfig that builds {@link ToolCallbackProvider}
 * from {@code @Tool} (ToolCallingAutoConfiguration) runs only when {@code ChatModel}
 * is present. So we provide one {@link ToolCallbackProvider} that discovers all
 * {@code @Tool} beans so they are exposed to the MCP server without adding
 * each class by name.
 */
@Configuration
public class McpToolConfig {

    private static final Set<String> EXCLUDED_BEAN_NAMES = Set.of(
            "toolCallbackProvider",
            "syncTools",
            "asyncTools",
            "mcpSyncServer",
            "mcpAsyncServer"
    );

    @Bean
    public ToolCallbackProvider toolCallbackProvider(ApplicationContext applicationContext) {
        // Lazy: discover @Tool beans only when getToolCallbacks() is called, not during bean creation.
        // That avoids cycles (syncTools/mcpSyncServer depend on us; resolving them would pull us in).
        return () -> {
            var toolBeans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                    .filter(name -> !EXCLUDED_BEAN_NAMES.contains(name))
                    .map(name -> applicationContext.getBean(name))
                    .filter(bean -> hasAnyToolMethod(bean.getClass()))
                    .collect(Collectors.toList());
            return toolBeans.stream()
                    .map(bean -> MethodToolCallbackProvider.builder().toolObjects(bean).build())
                    .flatMap(provider -> Arrays.stream(provider.getToolCallbacks()))
                    .toArray(ToolCallback[]::new);
        };
    }

    private static boolean hasAnyToolMethod(Class<?> clazz) {
        var current = clazz;
        while (current != null && current != Object.class) {
            for (var method : current.getDeclaredMethods()) {
                if (AnnotationUtils.findAnnotation(method, Tool.class) != null) {
                    return true;
                }
            }
            current = current.getSuperclass();
        }
        return false;
    }
}
