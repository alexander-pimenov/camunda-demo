package ru.pim.camunda_demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DmnConfiguration {

    private final ResourceLoader resourceLoader;

    @Bean(name = "dmnEngine")
    public DmnEngine createDmnEngine() {
        // create default DMN engine configuration
        DmnEngineConfiguration configuration = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration();

        // build a new DMN engine
        DmnEngine dmnEngine = configuration.buildEngine();
        return dmnEngine;
    }

    @Bean(name = "efterlon")
    public DmnDecision createDecisionEfterlon(DmnEngine dmnEngine) {
        Resource resource = resourceLoader.getResource("classpath:dmn/efterlon.dmn");
        try (InputStream is = resource.getInputStream()) {
            return dmnEngine.parseDecision("decision_efterlon", is);
        } catch (Exception e) {
            log.error("Failed to load DMN decision: efterlon.dmn with exception: {}", e);
            throw new IllegalStateException("Failed to load DMN decision: efterlon.dmn", e);
        }
    }
}
