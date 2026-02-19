package es.neil.api.data_loader;

import es.neil.api.domain.Category;
import es.neil.api.domain.Event;
import es.neil.api.domain.Role;
import es.neil.api.domain.Speaker;
import es.neil.api.repository.ICategoryRepository;
import es.neil.api.repository.IEventRepository;
import es.neil.api.repository.IRoleRepository;
import es.neil.api.repository.ISpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final IRoleRepository roleRepository;
    private final ICategoryRepository categoryRepository;
    private final ISpeakerRepository speakerRepository;
    private final IEventRepository eventRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initRoles();
        initCategories();
        initSpeakers();
        initEvents();

        System.out.println("--- ¡Carga de datos inicial completada con éxito! ---");
    }

    private void initRoles() {
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }
    }

    private void initCategories() {
        if (!categoryRepository.existsByName("TECHNOLOGY")) {
            Category cat = new Category();
            cat.setName("TECHNOLOGY");
            cat.setDescription("Related to software and hardware");
            categoryRepository.save(cat);
        }

        if (!categoryRepository.existsByName("BUSINESS")) {
            Category cat = new Category();
            cat.setName("BUSINESS");
            cat.setDescription("Entrepreneurship, marketing, and finance");
            categoryRepository.save(cat);
        }
    }

    private void initSpeakers() {
        if (speakerRepository.findByEmail("neil.toscano.f@uni.pe").isEmpty()) {
            Speaker speaker = new Speaker();
            speaker.setName("Neil Toscano");
            speaker.setEmail("neil.toscano.f@uni.pe");
            speaker.setBio("Software Developer and Spring Boot enthusiast.");
            speakerRepository.save(speaker);
        }

        if (speakerRepository.findByEmail("j.doe@example.com").isEmpty()) {
            Speaker speaker = new Speaker();
            speaker.setName("John Doe");
            speaker.setEmail("j.doe@example.com");
            speaker.setBio("Expert in Cloud Computing and Architecture.");
            speakerRepository.save(speaker);
        }
    }

    private void initEvents() {
        if (eventRepository.count() > 0) return;

        List<Category> categories = categoryRepository.findAll();
        List<Speaker> speakers = speakerRepository.findAll();

        if (categories.isEmpty() || speakers.isEmpty()) return;

        String[] eventNames = {
                "Java Clean Code", "Spring Boot Masterclass", "Microservices Architecture",
                "Docker for Beginners", "Kubernetes in Production", "React vs Vue 2026",
                "AWS Solutions Architect", "Database Optimization", "Agile Leadership",
                "Fintech Revolution", "Cybersecurity Basics", "AI with Python",
                "Next.js Advanced", "DevOps Culture", "UI/UX Trends",
                "Cloud Native Apps", "TDD Workshop", "Domain Driven Design",
                "Hexagonal Architecture", "Event Driven Systems"
        };

        for (int i = 0; i < eventNames.length; i++) {
            Event event = new Event();
            event.setName(eventNames[i]);
            event.setLocation("Remote Venue " + (i + 1));
            event.setDate(LocalDate.now().plusDays(i + 10));

            event.setCategory(categories.get(i % categories.size()));

            event.addSpeker(speakers.get(i % speakers.size()));

            eventRepository.save(event);
        }
        System.out.println("Se han creado " + eventNames.length + " eventos para pruebas.");
    }
}