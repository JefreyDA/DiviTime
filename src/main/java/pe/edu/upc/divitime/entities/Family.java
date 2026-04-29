package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "family")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFamily;
    @Column(name = "name_family", length = 50, nullable = false)
    private String nameFamily;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "link_inv_fam", length = 50)
    private String linkInvitationFamily;

    @ManyToOne
    @JoinColumn(name = "id_creator_family", nullable = false)
    private User creatorFamily;

    public Family() {
    }

    public Family(int idFamily, User creatorFamily, String linkInvitationFamily, LocalDateTime creationDate, String nameFamily) {
        this.idFamily = idFamily;
        this.creatorFamily = creatorFamily;
        this.linkInvitationFamily = linkInvitationFamily;
        this.creationDate = creationDate;
        this.nameFamily = nameFamily;
    }

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

    public User getCreatorFamily() {
        return creatorFamily;
    }

    public void setCreatorFamily(User creatorFamily) {
        this.creatorFamily = creatorFamily;
    }
}