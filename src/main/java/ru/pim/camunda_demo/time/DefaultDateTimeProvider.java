package ru.pim.camunda_demo.time;

import java.time.Instant;

import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 *
 * An immutable DateTimeProvider that supplies date-time related functions.
 * Неизменяемый DateTimeProvider, который предоставляет функции, связанные с датой и временем.
 */
@Component
public class DefaultDateTimeProvider implements DateTimeProvider {

    @Override
    public Instant nowAsInstant() {
        return Instant.now();
    }
}
