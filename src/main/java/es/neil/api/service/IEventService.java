package es.neil.api.service;

import es.neil.api.domain.Event;
import es.neil.api.dto.event.EventRequestDto;
import es.neil.api.dto.event.EventResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEventService {
    Page<EventResponseDto> findAll(String name, Pageable pageable);
    Event save(EventRequestDto eventRequestDto);
    Event findById(Long id);
    Event update(Long id, EventRequestDto eventRequestDto);
    void deleteById(Long id);
}
