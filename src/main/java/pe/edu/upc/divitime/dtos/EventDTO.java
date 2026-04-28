package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class EventDTO {
    private String titleEvent;
    private LocalDate startDateEvent;
    private LocalDate endDateEvent;
    private String detailsEvent;
    private LocalDate creationDateEvent;
    private String locationEvent;
    private int idFamily;
    private int idUser;

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

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
