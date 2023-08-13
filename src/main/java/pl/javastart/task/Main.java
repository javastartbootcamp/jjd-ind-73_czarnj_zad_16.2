package pl.javastart.task;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final List<DateTimeFormatter> DATE_TIME_PATTERNS = List.of(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("d.MM.yyyy HH:mm:ss"));
    private static final List<DateTimeFormatter> DATE_PATTERNS = List.of(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private static final DateTimeFormatter DISPLAYED_DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Map<String, ZoneId> zones;

    public Main() {
        this.zones = createZonesMap();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run(new Scanner(System.in));
    }

    public void run(Scanner scanner) {
        LocalDateTime dateTime = loadDate(scanner);
        if (dateTime == null) {
            System.out.println("Niepoprawna data");
        } else {
            showTime(dateTime);
        }
    }

    private LocalDateTime loadDate(Scanner scanner) {
        System.out.println("Podaj datę:");
        String date = scanner.nextLine();
        return adjustDate(date);
    }

    private void showTime(LocalDateTime dateTime) {
        ZonedDateTime local = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
        for (String zone : zones.keySet()) {
            String zonedDate = getZonedFormattedDate(local, zones.get(zone));
            System.out.printf("%s: %s%n", zone, zonedDate);
        }
    }

    private LocalDateTime adjustDate(String date) {
        DateTimeFormatter pattern = matchPattern(date);
        if (pattern == null) {
            return null;
        } else if (DATE_PATTERNS.contains(pattern)) {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            builder.append(pattern).append(DateTimeFormatter.ofPattern(" HH:mm:ss"));
            return LocalDateTime.parse(date + " 00:00:00", builder.toFormatter());
        }
        return LocalDateTime.parse(date, pattern);
    }

    private String getZonedFormattedDate(ZonedDateTime date, ZoneId zone) {
        return date.withZoneSameInstant(zone).format(DISPLAYED_DATE_PATTERN);
    }

    private DateTimeFormatter matchPattern(String date) {
        DateTimeFormatter pattern = getPattern(date, DATE_TIME_PATTERNS);
        if (pattern == null) {
            pattern = getPattern(date, DATE_PATTERNS);
        }
        return pattern;
    }

    private DateTimeFormatter getPattern(String date, List<DateTimeFormatter> patterns) {
        for (DateTimeFormatter pattern : patterns) {
            if (isDateValid(date, pattern)) {
                return pattern;
            }
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

    private Map<String, ZoneId> createZonesMap() {
        Map<String, ZoneId> zones = new LinkedHashMap<>();
        zones.put("Czas lokalny", ZoneId.systemDefault());
        zones.put("UTC", ZoneId.of("UTC"));
        zones.put("Londyn", ZoneId.of("Europe/London"));
        zones.put("Los Angeles", ZoneId.of("America/Los_Angeles"));
        zones.put("Sydney", ZoneId.of("Australia/Sydney"));
        return zones;
    }
}
