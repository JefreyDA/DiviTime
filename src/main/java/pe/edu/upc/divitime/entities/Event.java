package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Table
@Entity(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvent;

    @Column(name = "titleEvent", length = 50, nullable = false)
    private String titleEvent;

    @Column(name = "startDateEvent", nullable = false)
    private LocalDate startDateEvent;

    @Column(name = "endDateEvent", nullable = false)
    private LocalDate endDateEvent;

    @Column(name = "detailsEvent", length = 100, nullable = false)
    private String detailsEvent;

    @Column(name = "creationDateEvent", nullable = false)
    private LocalDate creationDateEvent;

    @Column(name = "locationEvent", length = 50, nullable = false)
    private String locationEvent;

    @ManyToOne
    @JoinColumn(name = "idFamily")
    private Family family;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    public Event(int idEvent, String titleEvent, LocalDate startDateEvent, LocalDate endDateEvent, String detailsEvent, LocalDate creationDateEvent, String locationEvent, Family family, User user) {
        this.idEvent = idEvent;
        this.titleEvent = titleEvent;
        this.startDateEvent = startDateEvent;
        this.endDateEvent = endDateEvent;
        this.detailsEvent = detailsEvent;
        this.creationDateEvent = creationDateEvent;
        this.locationEvent = locationEvent;
        this.family = family;
        this.user = user;
    }

    public Event() {

    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
    }

    public LocalDate getStartDateEvent() {
        return startDateEvent;
    }

    public void setStartDateEvent(LocalDate startDateEvent) {
        this.startDateEvent = startDateEvent;
    }

    public LocalDate getEndDateEvent() {
        return endDateEvent;
    }

    public void setEndDateEvent(LocalDate endDateEvent) {
        this.endDateEvent = endDateEvent;
    }

    public String getDetailsEvent() {
        return detailsEvent;
    }

    public void setDetailsEvent(String detailsEvent) {
        this.detailsEvent = detailsEvent;
    }

    public LocalDate getCreationDateEvent() {
        return creationDateEvent;
    }

    public void setCreationDateEvent(LocalDate creationDateEvent) {
        this.creationDateEvent = creationDateEvent;
    }

    public String getLocationEvent() {
        return locationEvent;
    }

    public void setLocationEvent(String locationEvent) {
        this.locationEvent = locationEvent;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
