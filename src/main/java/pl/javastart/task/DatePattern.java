package pl.javastart.task;

import java.time.format.DateTimeFormatter;

public enum DatePattern {
    PATTERN_1(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    PATTERN_2(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    PATTERN_3(DateTimeFormatter.ofPattern("d.MM.yyyy HH:mm:ss"));

    private DateTimeFormatter formatter;

    DatePattern(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
