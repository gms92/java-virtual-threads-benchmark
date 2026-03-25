package com.meutudo.benchmarkjava17;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final ConcurrentTaskService taskService;

    public BenchmarkController(ConcurrentTaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/concurrent")
    public Map<String, Object> concurrent(
            @RequestParam(defaultValue = "50") int tasks,
            @RequestParam(defaultValue = "100") int delay) {

        long totalTimeMs = taskService.runConcurrentTasks(tasks, delay);

        Map<String, Object> result = new HashMap<>();
        result.put("javaVersion", "17");
        result.put("threadType", "platform");
        result.put("tasks", tasks);
        result.put("delayMs", delay);
        result.put("totalTimeMs", totalTimeMs);
        result.put("timestamp", Instant.now().toString());
        return result;
    }
}
