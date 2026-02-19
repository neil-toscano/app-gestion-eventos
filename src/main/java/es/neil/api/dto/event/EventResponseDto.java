package es.neil.api.dto.event;

import es.neil.api.domain.Category;
import es.neil.api.dto.speaker.SpeakerDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class EventResponseDto {
    private Long id;
    private String name;
    private LocalDate date;
    private String location;
//    private Category category;
    private Long categoryId;
    private String categoryName;

    private Set<SpeakerDto> speakerDtos;

}
