package es.neil.api.mapper;

import es.neil.api.domain.Event;
import es.neil.api.dto.event.EventRequestDto;
import es.neil.api.dto.event.EventResponseDto;
import es.neil.api.dto.event.EventSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEventMapper {
    List<EventResponseDto> toEventResponseDtoList(List<Event> events);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "speakers", ignore = true)
    @Mapping(target = "attendedUsers", ignore = true)
    Event toEntity(EventRequestDto eventRequestDto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "speakerDtos", source = "speakers")
    EventResponseDto toResponseDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "speakers", ignore = true)
    @Mapping(target = "attendedUsers", ignore = true)
    void updateEventFromDto(EventRequestDto dto, @MappingTarget() Event event);


    EventSummaryDto toSummaryDto(Event event);
    List<EventSummaryDto> toSummaryDtoList(List<Event> events);

}
