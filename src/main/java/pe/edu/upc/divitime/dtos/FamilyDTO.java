package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;
public class FamilyDTO {
    private String nameFamily;
    private LocalDate creationDate;

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
