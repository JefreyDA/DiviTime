package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class QueryFamilyByDate {
    private String nameFamily;
    private LocalDateTime creationDate;
    private String linkInvitationFamily;

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

    public String getLinkInvitationFamily() {
        return linkInvitationFamily;
    }

    public void setLinkInvitationFamily(String linkInvitationFamily) {
        this.linkInvitationFamily = linkInvitationFamily;
    }
}
