package ru.pim.camunda_demo;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.junit.jupiter.api.BeforeEach;

public class AbstractDecisionTest {
    protected DmnEngine dmnEngine;

    @BeforeEach
    public void init() {
        this.dmnEngine = DmnEngineConfiguration
                .createDefaultDmnEngineConfiguration().buildEngine();
    }
}
