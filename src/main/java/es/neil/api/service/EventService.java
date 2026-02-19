package es.neil.api.service;

import es.neil.api.domain.Category;
import es.neil.api.domain.Event;
import es.neil.api.domain.Speaker;
import es.neil.api.dto.event.EventRequestDto;
import es.neil.api.dto.event.EventResponseDto;
import es.neil.api.exception.ResourceNotFoundException;
import es.neil.api.mapper.IEventMapper;
import es.neil.api.repository.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService implements IEventService {
    private final IEventRepository eventRepository;
    private final IEventMapper eventMapper;
    private final ICategoryService categoryService;
    private final ISpeakerService speakerService;

    @Override
    public Page<EventResponseDto> findAll(String name, Pageable pageable) {

        Page<Event> eventPage;
        if(name !=null && !name.trim().isEmpty() ){
            eventPage = eventRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        else {
            eventPage = eventRepository.findAll(pageable);
        }

        List<EventResponseDto> dtos = eventPage.getContent().stream()
                .map(eventMapper::toResponseDto)
                .toList();

        return new PageImpl<>(dtos, pageable, eventPage.getTotalElements());
    }

    @Override
    public Event save(EventRequestDto eventRequestDto) {

        Event event = eventMapper.toEntity(eventRequestDto);

        Category category = categoryService.findById(eventRequestDto.getCategoryId());
        event.setCategory(category);

        if(eventRequestDto.getSpeakersIds() != null && !eventRequestDto.getSpeakersIds().isEmpty()){
            Set<Speaker> speakers = eventRequestDto.getSpeakersIds().stream()
                    .map(speakerService::findById)
                    .collect(Collectors.toSet());

            speakers.forEach(event::addSpeker);
        }

        return eventRepository.save(event);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el evento con ID: " + id));
    }

    @Override
    public Event update(Long id, EventRequestDto dto) {
        Event existingEvent = findById(id);
        eventMapper.updateEventFromDto(dto, existingEvent);

        if(!existingEvent.getCategory().getId().equals(dto.getCategoryId())) {
            Category category = categoryService.findById(dto.getCategoryId());
            existingEvent.setCategory(category);
        }

        Set<Speaker> updatedSpeakers;
        if(dto.getSpeakersIds()!= null && !dto.getSpeakersIds().isEmpty()) {
            updatedSpeakers = dto.getSpeakersIds().stream()
                    .map(speakerService::findById)
                    .collect(Collectors.toSet());


        } else {
            updatedSpeakers = new HashSet<>();
        }

        new HashSet<>(existingEvent.getSpeakers())
                .forEach(currentSpeaker -> {
                    if(!updatedSpeakers.contains(currentSpeaker)){
                        existingEvent.removeSpeaker(currentSpeaker);
                    }
                });

        updatedSpeakers.forEach(newSpeaker -> {
            if(!existingEvent.getSpeakers().contains(newSpeaker)) {
                existingEvent.addSpeker(newSpeaker);
            }
        });

        return eventRepository.save(existingEvent);
    }

    @Override
    public void deleteById(Long id) {
        Event existingEvent = findById(id);
        eventRepository.delete(existingEvent);
    }
}
