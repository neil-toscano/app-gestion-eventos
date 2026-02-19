package es.neil.api.service;

import es.neil.api.domain.Speaker;
import es.neil.api.dto.speaker.SpeakerDto;

import java.util.List;

public interface ISpeakerService {
    Speaker findById(Long id);
    Speaker save(Speaker speaker);
    List<Speaker> findAll();
    Speaker update(Long id, SpeakerDto speakerDto);
    void deleteById(Long id);
}
