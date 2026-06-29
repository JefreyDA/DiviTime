package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class EventFamilyDTO {
    private int idEvent;
    private String titleEvent;
    private LocalDate startDateEvent;
    private LocalDate endDateEvent;
    private String locationEvent;

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

    public String getLocationEvent() {
        return locationEvent;
    }

    public void setLocationEvent(String locationEvent) {
        this.locationEvent = locationEvent;
    }
}

