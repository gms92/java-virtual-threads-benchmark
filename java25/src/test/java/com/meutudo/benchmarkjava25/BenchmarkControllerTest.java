package com.meutudo.benchmarkjava25;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BenchmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void benchmarkEndpointReturnsExpectedFields() throws Exception {
        mockMvc.perform(get("/benchmark/concurrent?tasks=2&delay=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.javaVersion").value("26"))
                .andExpect(jsonPath("$.threadType").value("virtual"))
                .andExpect(jsonPath("$.tasks").value(2))
                .andExpect(jsonPath("$.delayMs").value(10))
                .andExpect(jsonPath("$.totalTimeMs").isNumber())
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void benchmarkEndpointUsesDefaults() throws Exception {
        mockMvc.perform(get("/benchmark/concurrent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tasks").value(50))
                .andExpect(jsonPath("$.delayMs").value(100));
    }
}
