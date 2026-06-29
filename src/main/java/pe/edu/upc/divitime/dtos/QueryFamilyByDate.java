package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class QueryFamilyByDate {
    private String nameFamily;
    private LocalDate creationDate;
    private String linkInvitationFamily;

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

    public String getLinkInvitationFamily() {
        return linkInvitationFamily;
    }

    public void setLinkInvitationFamily(String linkInvitationFamily) {
        this.linkInvitationFamily = linkInvitationFamily;
    }
}
