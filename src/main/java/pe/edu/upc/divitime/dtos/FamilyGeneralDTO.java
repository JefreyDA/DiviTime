package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class FamilyGeneralDTO {
    private int idFamily;
    private String nameFamily;
    private LocalDateTime creationDate;
    private String linkInvitationFamily;

    private int idCreatorFamily;

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

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

    public int getIdCreatorFamily() {
        return idCreatorFamily;
    }

    public void setIdCreatorFamily(int idCreatorFamily) {
        this.idCreatorFamily = idCreatorFamily;
    }
}