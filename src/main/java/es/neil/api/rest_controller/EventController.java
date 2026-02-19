package es.neil.api.rest_controller;

import es.neil.api.domain.Event;
import es.neil.api.dto.event.EventRequestDto;
import es.neil.api.dto.event.EventResponseDto;
import es.neil.api.mapper.IEventMapper;
import es.neil.api.service.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Validated
@Tag(name = "Eventos", description = "Operaciones para gestionar los eventos de Neil")
public class EventController {
    private final IEventService eventService;
    private final IEventMapper eventMapper;

    @Operation(summary = "Obtener todos los eventos", description = "Devuelve una lista de eventos con sus categorías")
    @GetMapping("")
    public ResponseEntity<Page<EventResponseDto>> getAll(
            @RequestParam(required = false) String name,
            @PageableDefault(page = 0, size = 10, sort = "name")Pageable pageable
            ) {

        Page<EventResponseDto> events = eventService.findAll(name, pageable);

        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> createEvent(@Valid  @RequestBody EventRequestDto requestDto) {

        Event eventSaved = eventService.save(requestDto);
        EventResponseDto response = eventMapper.toResponseDto(eventSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getById(@PathVariable @Positive Long id) {
        Event event = eventService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(eventMapper.toResponseDto(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> update(
            @PathVariable @Positive Long id,
            @Valid @RequestBody EventRequestDto requestDto) {

        Event updatedEvent = eventService.update(id, requestDto);
        EventResponseDto response = eventMapper.toResponseDto(updatedEvent);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        eventService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
