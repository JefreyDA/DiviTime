package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.EventDTO;
import pe.edu.upc.divitime.dtos.EventFamilyDTO;
import pe.edu.upc.divitime.dtos.EventGeneralDTO;
import pe.edu.upc.divitime.entities.Event;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IEventService;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private IEventService eS;
    @Autowired
    private IFamilyService fS;
    @Autowired
    private IUserService uS;

    @GetMapping("/list-events")
    public ResponseEntity<?> list() {
        if(eS.listEvents().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay eventos registrados");
        } else {
            ModelMapper m = new ModelMapper();
            List<EventDTO> listEvents = eS.listEvents().stream()
                    .map(y -> m.map(y, EventDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(listEvents);
        }
    }

    @PostMapping("/register-events")
    public ResponseEntity<?> insert(@RequestBody EventGeneralDTO dto) {

        Optional<User> user = uS.listId(dto.getIdUser());
        Optional<Family> family = fS.listId(dto.getIdFamily());

        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if(family.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrado");
        }

        if (dto.getEndDateEvent().isBefore(dto.getStartDateEvent())) {
            return ResponseEntity.badRequest()
                    .body("La fecha fin debe ser mayor que la fecha inicio");
        }

        if(dto.getTitleEvent() == null) {
            return ResponseEntity.badRequest()
                    .body("El titulo del evento no puede ser nulo");
        }

        ModelMapper m = new ModelMapper();
        Event e = m.map(dto, Event.class);
        Event event = eS.insertEvents(e);
        EventGeneralDTO responseDTO = m.map(event, EventGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Event> event = eS.listEventById(id);
        if (event.isPresent()) {
            EventGeneralDTO dto = m.map(event.get(), EventGeneralDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Evento no encontrado");
        }
    }

    @PutMapping("/update-event")
    public ResponseEntity<String> update(@RequestBody EventGeneralDTO dto) {

        Optional<Event> event = eS.listEventById(dto.getIdEvent());
        Optional<User> user = uS.listId(dto.getIdUser());
        Optional<Family> family = fS.listId(dto.getIdFamily());

        if (event.isEmpty()) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Evento no encontrado");
        }

        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if(family.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrada");
        }

        if (dto.getStartDateEvent() == null || dto.getEndDateEvent() == null || dto.getCreationDateEvent() == null) {
            return ResponseEntity.badRequest()
                    .body("Las fechas no pueden ser nulas");
        }

        if (dto.getEndDateEvent().isBefore(dto.getStartDateEvent())) {
            return ResponseEntity.badRequest()
                    .body("La fecha fin debe ser mayor que la fecha inicio");
        }

        if(dto.getTitleEvent() == null) {
            return ResponseEntity.badRequest()
                    .body("El titulo del evento no puede ser nulo");
        }

        Event e = event.get();
        e.setTitleEvent(dto.getTitleEvent());
        e.setStartDateEvent(dto.getStartDateEvent());
        e.setEndDateEvent(dto.getEndDateEvent());
        e.setDetailsEvent(dto.getDetailsEvent());
        e.setCreationDateEvent(dto.getCreationDateEvent());
        e.setLocationEvent(dto.getLocationEvent());
        e.setFamily(family.get());
        e.setUser(user.get());

        eS.updateEvent(e);
        return ResponseEntity.ok("Evento actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Event> event = eS.listEventById(id);
        if (event.isPresent()) {
            eS.deleteEvent(id);
            return ResponseEntity.ok("Evento eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Evento no encontrado");
        }
    }

    @GetMapping("/list-events-by-family/{idFamily}")
    public ResponseEntity<?> listEventsByFamily(@PathVariable("idFamily") int idFamily){

        List<Event> events  = eS.listEventsByFamily(idFamily);

        if(events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay eventos creados para esta familia");
        }

        ModelMapper m = new ModelMapper();
        List<EventDTO> list = events.stream().map(y->m.map(y, EventDTO.class))
                .collect(Collectors.toList());
        return  ResponseEntity.ok(list);
    }

    @GetMapping("/list-events-by-user/{idUser}")
    public ResponseEntity<?> listEventsByUser(@PathVariable("idUser") int idUser){

        List<Event> events  = eS.listEventsByUser(idUser);

        if(events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay eventos creados por ese usuario");
        }

        ModelMapper m = new ModelMapper();
        List<EventDTO> list = events.stream().map(y->m.map(y, EventDTO.class))
                .collect(Collectors.toList());
        return  ResponseEntity.ok(list);
    }

    @GetMapping("/proximo-eventos-familia/{idFamily}")
    public ResponseEntity<?> ProximoEventos(@PathVariable int idFamily) {

        List<Event> events = eS.listUpcomingByFamily(idFamily);

        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay eventos próximos para esta familia");
        }

        ModelMapper m = new ModelMapper();

        List<EventFamilyDTO> result = events.stream()
                .map(e -> m.map(e, EventFamilyDTO.class))
                .toList();

        return ResponseEntity.ok(result);
    }

}
