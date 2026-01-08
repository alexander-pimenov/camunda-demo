package ru.pim.camunda_demo.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DefaultDateTimeProvider - Positive Unit Tests")
class DefaultDateTimeProviderTest {

    private DefaultDateTimeProvider dateTimeProvider;

    @BeforeEach
    void setUp() {
        dateTimeProvider = new DefaultDateTimeProvider();
    }

    static Stream<Instant> provideBoundaryInstants() {
        return Stream.of(
                Instant.EPOCH,                              // 1970-01-01T00:00:00Z
                Instant.parse("2024-02-29T00:00:00Z"), // високосный год
                Instant.parse("2023-12-31T23:59:59.999999999Z") // конец года
                //Instant.MAX,  //максимальное значение: эти тестировать не будем, т.к. в начале очень много числе, а не 4:  +1000000000-12-31T23:59:59.999999999Z
                //Instant.MIN   //минимальное значение: эти тестировать не будем, т.к. в начале очень много числе, а не 4: -1000000000-01-01T00:00:00Z
        );
    }

    @Test
    @DisplayName("nowAsInstant_withFixedTime_returnsExpectedInstant")
    void nowAsInstant_withFixedTime_returnsExpectedInstant() {
        Instant fixedTime = Instant.parse("2023-10-01T12:00:00Z");

        try (MockedStatic<Instant> mocked = Mockito.mockStatic(Instant.class)) {
            mocked.when(Instant::now).thenReturn(fixedTime);

            Instant result = dateTimeProvider.nowAsInstant();

            assertThat(result)
                    .isEqualTo(fixedTime)
                    .isNotNull();
            assertThat(result.getNano()).isBetween(0, 999_999_999);
        }
    }

    @ParameterizedTest(name = "nowAsInstant_with_{0}_returnsValidInstant")
    @MethodSource("provideBoundaryInstants")
    @DisplayName("nowAsInstant_withBoundaryInstants_returnsValidResult")
    void nowAsInstant_withBoundaryInstants_returnsValidResult(Instant providedTime) {
        try (MockedStatic<Instant> mocked = Mockito.mockStatic(Instant.class)) {
            mocked.when(Instant::now).thenReturn(providedTime);

            Instant result = dateTimeProvider.nowAsInstant();

            assertThat(result).isEqualTo(providedTime);
            assertThat(result.toString()).matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?Z$");
        }
    }

    @Test
    @DisplayName("nowAsInstant_returnsInstantInStandardISOFormat")
    void nowAsInstant_returnsInstantInStandardISOFormat() {
        Instant fixed = Instant.parse("2023-02-28T23:59:59.123456789Z");

        try (MockedStatic<Instant> mocked = Mockito.mockStatic(Instant.class)) {
            mocked.when(Instant::now).thenReturn(fixed);

            Instant result = dateTimeProvider.nowAsInstant();

            assertThat(result.toString())
                    .isEqualTo("2023-02-28T23:59:59.123456789Z");
            assertThat(result.toString()).matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?Z$");
        }
    }

    @Test
    @DisplayName("constructor_createsNonNullInstance")
    void constructor_createsNonNullInstance() {
        DefaultDateTimeProvider provider = new DefaultDateTimeProvider();
        assertThat(provider).isNotNull();
    }

    @Test
    @DisplayName("nowAsInstant_performsEfficientlyUnderLoad")
    void nowAsInstant_performsEfficientlyUnderLoad() {
        int iterations = 100_000;
        long start = System.nanoTime();

        try (MockedStatic<Instant> mocked = Mockito.mockStatic(Instant.class)) {
            Instant now = Instant.now();
            mocked.when(Instant::now).thenReturn(now);

            for (int i = 0; i < iterations; i++) {
                dateTimeProvider.nowAsInstant();
            }
        }

        long durationMs = (System.nanoTime() - start) / 1_000_000;
        assertThat(durationMs).isLessThan(2000); // менее 2000 мс
    }

    @Test
    @DisplayName("instance_isImmutable_noStateToChange")
    void instance_isImmutable_noStateToChange() {
        // Класс не имеет состояния — просто проверяем, что он создался
        assertThat(dateTimeProvider).isNotNull();
        // Immutability подразумевается отсутствием сеттеров и изменяемых полей

        System.out.println(Instant.MAX);
        System.out.println(Instant.MIN);
    }
}