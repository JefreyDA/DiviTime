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
    public ResponseEntity<?> listar() {
        if(eS.listEvents().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay eventos registrados");
        } else {
            ModelMapper m = new ModelMapper();
            List<EventDTO> listaEventos = eS.listEvents().stream()
                    .map(y -> m.map(y, EventDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(listaEventos);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> insertar(@RequestBody EventGeneralDTO dto) {

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

        if (!dto.getEndDateEvent().isAfter(dto.getStartDateEvent())) {
            return ResponseEntity.badRequest()
                    .body("La fecha fin debe ser mayor que la fecha inicio");
        }

        ModelMapper m = new ModelMapper();
        Event e = m.map(dto, Event.class);
        Event event = eS.insertEvents(e);
        EventGeneralDTO responseDTO = m.map(event, EventGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
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

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody EventGeneralDTO dto) {

        Optional<Event> existe = eS.listEventById(dto.getIdEvent());
        Optional<User> user = uS.listId(dto.getIdUser());
        Optional<Family> family = fS.listId(dto.getIdFamily());

        if (existe.isEmpty()) {
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

        if (!dto.getEndDateEvent().isAfter(dto.getStartDateEvent())) {
            return ResponseEntity.badRequest()
                    .body("La fecha fin debe ser mayor que la fecha inicio");
        }

        Event e = existe.get();
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
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Event> event = eS.listEventById(id);
        if (event.isPresent()) {
            eS.deleteEvent(id);
            return ResponseEntity.ok("Evento eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Evento no encontrado");
        }
    }

    @GetMapping("/list-events-date")
    public ResponseEntity<?> listarPorFecha(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        if(eS.listEventsByDate(fechaInicio, fechaFin).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay eventos registrados en esa fecha");
        } else {
            ModelMapper m = new ModelMapper();
            List<EventDTO> listaEventosPorFecha = eS.listEventsByDate(fechaInicio, fechaFin).stream()
                    .map(y -> m.map(y, EventDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(listaEventosPorFecha);
        }
    }

    @GetMapping("/buscar-evento-titulo")
    public ResponseEntity<?> buscarEventoTitulo(@RequestParam("tituloEvento") String tituloEvento){
        if(eS.findByTitleEvent(tituloEvento).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay eventos con ese titulo");
        } else {
            ModelMapper m = new ModelMapper();
            List<EventDTO> lista = eS.findByTitleEvent(tituloEvento)
                    .stream().map(y->m.map(y, EventDTO.class))
                    .collect(Collectors.toList());
            return  ResponseEntity.ok(lista);
        }
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
