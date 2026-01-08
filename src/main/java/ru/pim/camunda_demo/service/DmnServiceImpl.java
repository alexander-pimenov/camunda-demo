package ru.pim.camunda_demo.service;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.pim.camunda_demo.time.DateTimeProvider;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Service
public class DmnServiceImpl {

    private final DateTimeProvider dateTimeProvider;
    private final DmnEngine dmnEngine;
    private final DmnDecision decision;

    public DmnServiceImpl(DateTimeProvider dateTimeProvider,
                          @Qualifier("efterlon") DmnDecision decision,
                          DmnEngine dmnEngine
    ) {
        this.dateTimeProvider = dateTimeProvider;
        this.decision = decision;
        this.dmnEngine = dmnEngine;
    }


    public boolean validateEfterlonFleksydelseAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birthDate must not be null");
        }
        int birthYear = birthDate.getYear();
        int age = Period.between(birthDate, dateTimeProvider.nowAsDate()).getYears();

        Date currentDate = Date.from(dateTimeProvider.nowAsDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        //System.err.println(currentDate.toString());
        VariableMap variables = Variables
                .putValue("currentDate", currentDate)
                .putValue("birthYear", birthYear)
                .putValue("age", age);

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
        //return Optional.ofNullable(result.getSingleResult().get("efterlon")).isPresent();
        return ((Boolean) result.getSingleResult().get("efterlon")).booleanValue();
    }

}

//в этом измененном методе что-то не так. можно позже разобраться, почему его вызов не работает
//    public boolean validateEfterlonFleksydelseAge(LocalDate birthDate) {
//        if (birthDate == null) {
//            throw new IllegalArgumentException("birthDate must not be null");
//        }
//
//        LocalDate now = dateTimeProvider.nowAsDate();
//        int age = Period.between(birthDate, now).getYears();
//
//        // Используем LocalDate напрямую без преобразования в Date
//        var variables = Variables
//                .putValue("currentDate", now)
//                .putValue("birthYear", birthDate.getYear())
//                .putValue("age", age);
//
//        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);
//
//        // Проверка результата с явным именем выходного параметра
//        Boolean eligible = (Boolean) result.getSingleResult().get("efterlon");
//        if (eligible == null) {
//            throw new IllegalStateException("Decision table did not return 'efterlon' result");
//        }
//
//        return eligible;
//    }