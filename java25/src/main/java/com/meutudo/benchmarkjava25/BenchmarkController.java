package com.meutudo.benchmarkjava25;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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

        return Map.of(
                "javaVersion", "26",
                "threadType", "virtual",
                "tasks", tasks,
                "delayMs", delay,
                "totalTimeMs", totalTimeMs,
                "timestamp", Instant.now().toString()
        );
    }
}
