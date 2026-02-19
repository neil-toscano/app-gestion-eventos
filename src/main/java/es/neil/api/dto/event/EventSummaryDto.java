package es.neil.api.dto.event;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventSummaryDto {
    private Long id;
    private String name;
    private LocalDate date;
    private String location;

}
