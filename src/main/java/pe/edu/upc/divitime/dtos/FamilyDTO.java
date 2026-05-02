package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class FamilyDTO {
    private String nameFamily;
    private LocalDateTime creationDate;

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
