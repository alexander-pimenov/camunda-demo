package ru.pim.camunda_demo.service;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.jupiter.api.Test;
import ru.pim.camunda_demo.time.DateTimeProvider;
import org.camunda.bpm.engine.variable.VariableMap;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DmnServiceImplTest {

    //TODO - разобраться с эти тестом, не дописан
    @Test
    void shouldReturnTrueForValidBirthYear() {
        // given
        DmnEngine engine = mock(DmnEngine.class);
        DmnDecision decision = mock(DmnDecision.class);
        DateTimeProvider provider = mock(DateTimeProvider.class) ;
        // DateTimeProvider provider = () -> LocalDate.of(2020, 1, 1);

        DmnServiceImpl service = new DmnServiceImpl(provider,  decision, engine);

        DmnDecisionTableResult result = mock(DmnDecisionTableResult.class);
//        when(result.getSingleResult()).thenReturn(result);
////        when(result.getSingleResult()).thenReturn(Variables.createVariables().putValue("efterlon", true));
//        when(engine.evaluateDecisionTable((DmnDecision) any(), (Map<String, Object>) any())).thenReturn(result);
//
//        // when
//        boolean valid = service.validateEfterlonFleksydelseAge(LocalDate.of(1955, 1, 1));
//
//        // then
//        assertTrue(valid);
    }

}