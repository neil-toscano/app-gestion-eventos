package es.neil.api.mapper;

import es.neil.api.domain.Event;
import es.neil.api.domain.Speaker;
import es.neil.api.dto.event.EventRequestDto;
import es.neil.api.dto.speaker.SpeakerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {

    SpeakerDto toDto(Speaker speaker);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    Speaker toEntity(SpeakerDto speakerDto);

    List<SpeakerDto> toSpeakerDtoList(List<Speaker> speakers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    void updateSpeakerFromDto(SpeakerDto dto, @MappingTarget() Speaker speaker);

}
