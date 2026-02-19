package es.neil.api.service;

import es.neil.api.domain.Speaker;
import es.neil.api.dto.speaker.SpeakerDto;
import es.neil.api.mapper.SpeakerMapper;
import es.neil.api.repository.ISpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeakerServiceImpl implements ISpeakerService {
    private final ISpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;

    @Override
    public Speaker findById(Long id) {
        return speakerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Speaker no encontrado con ID: " + id));
    }

    @Override
    public Speaker save(Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    @Override
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Override
    public Speaker update(Long id, SpeakerDto speakerDto) {
        Speaker speakerEntity = findById(id);

        speakerMapper.updateSpeakerFromDto(speakerDto, speakerEntity);

        return speakerRepository.save(speakerEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (!speakerRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Speaker no encontrado con ID: " + id);
        }
        speakerRepository.deleteById(id);

    }
}
