package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DISPLAYED_DATE_PATTERN = DatePattern.PATTERN_1.getFormatter();

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        System.out.println("Podaj datę:");
        String date = scanner.nextLine();
        DatePattern pattern = matchPattern(date);
        if (pattern == null) {
            System.out.println("Niepoprawna data");
        } else {
            String adjustedDate = adjustDate(date, pattern);
            if (pattern.equals(DatePattern.PATTERN_2)) {
                pattern = DatePattern.PATTERN_1;
            }
            LocalDateTime dateTime = LocalDateTime.parse(adjustedDate, pattern.getFormatter());
            showTime(dateTime);
        }
    }

    private void showTime(LocalDateTime dateTime) {
        Instant instant = dateTime.toInstant(OffsetDateTime.now().getOffset());
        System.out.printf("Czas lokalny: %s%n", getTimeForZone(instant, ZoneId.systemDefault()));
        System.out.printf("UTC: %s%n", getTimeForZone(instant, ZoneId.of("UTC")));
        System.out.printf("Londyn: %s%n", getTimeForZone(instant, ZoneId.of("Europe/London")));
        System.out.printf("Los Angeles: %s%n", getTimeForZone(instant, ZoneId.of("America/Los_Angeles")));
        System.out.printf("Sydney: %s%n", getTimeForZone(instant, ZoneId.of("Australia/Sydney")));
    }

    private String adjustDate(String date, DatePattern pattern) {
        if (pattern.equals(DatePattern.PATTERN_2)) {
            return date + " 00:00:00";
        }
        return date;
    }

    private String getTimeForZone(Instant instant, ZoneId zone) {
        return ZonedDateTime.ofInstant(instant, zone).format(DISPLAYED_DATE_PATTERN);
    }

    private DatePattern matchPattern(String date) {
        if (isDateValid(date, DatePattern.PATTERN_1.getFormatter())) {
            return DatePattern.PATTERN_1;
        } else if (isDateValid(date, DatePattern.PATTERN_2.getFormatter())) {
            return DatePattern.PATTERN_2;
        } else if (isDateValid(date, DatePattern.PATTERN_3.getFormatter())) {
            return DatePattern.PATTERN_3;
        }
        return null;
    }

    private boolean isDateValid(String date, DateTimeFormatter pattern) {
        try {
            pattern.parse(date);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

}
