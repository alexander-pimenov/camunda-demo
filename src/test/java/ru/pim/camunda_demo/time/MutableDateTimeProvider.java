package ru.pim.camunda_demo.time;

import java.time.Instant;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Изменяемый DateTimeProvider, который предоставляет функции, связанные с датой и временем.
 * Возможность устанавливать время дает этому классу гибкость для "остановки часов" во время тестов.
 */
@Component
@Primary
public class MutableDateTimeProvider implements DateTimeProvider {

    private Instant timeSource;

    public MutableDateTimeProvider() {
        timeSource = Instant.now();
    }

    public void set(Instant newTimeSource) {
        timeSource = newTimeSource;
    }

    @Override
    public Instant nowAsInstant() {
        return timeSource;
    }
}
