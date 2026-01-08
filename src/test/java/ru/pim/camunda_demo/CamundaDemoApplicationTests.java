package ru.pim.camunda_demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pim.camunda_demo.service.DmnServiceImpl;
import ru.pim.camunda_demo.time.MutableDateTimeProvider;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CamundaDemoApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("test");
	}

	private static final ZoneId UTC = ZoneId.of("UTC");
	private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

	@Autowired
	private MutableDateTimeProvider mutableDateTimeProvider;

	private static final int YEAR = 2016;
	private static final int MONTH = 1;
	private static final int DAY = 1;
	private static final int HOUR = 8;
	private static final int MINUTE = 0;
	private static final int SECOND = 0;
	private static final int NANO_SECOND = 0;

	@Autowired
	private DmnServiceImpl dmnService;

	@BeforeEach
	public void setup() throws Exception {
		setDateTime(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);
	}

	private void setDateTime(int year, int month, int day, int hour, int minute, int second) {
		Instant instant = LocalDateTime.of(year, month, day, hour, minute, second)
				.atZone(ZoneId.systemDefault())
				.toInstant();
		Date dateNow = Date.from(instant);

		mutableDateTimeProvider.set(dateNow.toInstant());
	}

	@AfterEach
	public void teardown() throws Exception {
		mutableDateTimeProvider.set(Instant.now());
	}

	@Test
	public void testValidEfterlonBirth1953() {
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1953, 1, 1)));
	}

	@Test
	public void testValidEfterlonBirth1954() {
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1954, 1, 1)));
	}

	@Test
	public void testValidEfterlonBirth1955() {
		setDateTime(2020, MONTH, DAY, HOUR, MINUTE, SECOND);
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1955, 1, 1)));
	}

	@Test
	public void testInvalidEfterlonBirth1955() {
		assertFalse(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1955, 1, 1)));
	}

	@Test
	public void testValidEfterlonBirth1956() {
		setDateTime(2020, MONTH, DAY, HOUR, MINUTE, SECOND);
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1956, 1, 1)));
	}

	@Test
	public void testValidEfterlonBirth1959() {
		setDateTime(2024, MONTH, DAY, HOUR, MINUTE, SECOND);
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1959, 1, 1)));
	}

	@Test
	public void testValidEfterlonBirth1963() {
		setDateTime(2029, MONTH, DAY, HOUR, MINUTE, SECOND);
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1963, 1, 1)));
	}

	@Test
	public void testValidEfterlonBirth1967() {
		setDateTime(2033, MONTH, DAY, HOUR, MINUTE, SECOND);
		assertTrue(dmnService.validateEfterlonFleksydelseAge(LocalDate.of(1967, 1, 1)));
	}

}
