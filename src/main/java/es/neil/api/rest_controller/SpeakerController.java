package es.neil.api.rest_controller;

import es.neil.api.domain.Speaker;
import es.neil.api.dto.speaker.SpeakerDto;
import es.neil.api.mapper.SpeakerMapper;
import es.neil.api.service.ISpeakerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
@RequiredArgsConstructor
public class SpeakerController {
    private final ISpeakerService speakerService;
    private final SpeakerMapper speakerMapper;

    @GetMapping
    public ResponseEntity<List<SpeakerDto>> getAll() {
        List<Speaker> speakers = speakerService.findAll();
        return ResponseEntity.ok(speakerMapper.toSpeakerDtoList(speakers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeakerDto> getById(@PathVariable Long id) {
        Speaker speaker = speakerService.findById(id);
        return ResponseEntity.ok(speakerMapper.toDto(speaker));
    }

    @PostMapping
    public ResponseEntity<SpeakerDto> create(@Valid @RequestBody SpeakerDto speakerDto) {
        Speaker speakerEntity = speakerMapper.toEntity(speakerDto);
        Speaker savedSpeaker = speakerService.save(speakerEntity);

        return new ResponseEntity<>(speakerMapper.toDto(savedSpeaker), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeakerDto> update(@PathVariable Long id, @Valid @RequestBody SpeakerDto speakerDto) {
        Speaker updatedSpeaker = speakerService.update(id, speakerDto);
        return ResponseEntity.ok(speakerMapper.toDto(updatedSpeaker));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        speakerService.deleteById(id);
        return ResponseEntity.noContent().build(); // Status 204
    }

}
