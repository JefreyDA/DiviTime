package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

// ENTIDAD ALTERADA PARA LA NUEVA VERSIÓN

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(name = "nameUser", length = 50, nullable = false)
    private String nameUser;

    @Column(name = "paternalSurNameUser", length = 50, nullable = false)
    private String paternalSurNameUser;

    @Column(name = "maternalSurNameUser", length = 50, nullable = false)
    private String maternalSurNameUser;

    @Column(name = "birthDateUser", nullable = false)
    private LocalDate birthDateUser;

    @Column(name = "emailUser", length = 100, nullable = false)
    private String emailUser;

    @Column(name = "passwordUser", length = 150, nullable = false)
    private String passwordUser;

    @Column(name = "accountCreatedDateUser", nullable = false)
    private LocalDate accountCreatedDateUser;

    @Column(name = "statusUser", nullable = false)
    private Boolean statusUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRole", nullable = false)
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "idFamily", nullable = true)
    private Family family;

    public User() {
    }

    public User(int idUser, String nameUser, String paternalSurNameUser, String maternalSurNameUser, LocalDate birthDateUser, String emailUser, String passwordUser, LocalDate accountCreatedDateUser, Boolean statusUser, Roles roles, Family family) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.paternalSurNameUser = paternalSurNameUser;
        this.maternalSurNameUser = maternalSurNameUser;
        this.birthDateUser = birthDateUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.accountCreatedDateUser = accountCreatedDateUser;
        this.statusUser = statusUser;
        this.roles = roles;
        this.family = family;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPaternalSurNameUser() {
        return paternalSurNameUser;
    }

    public void setPaternalSurNameUser(String paternalSurNameUser) {
        this.paternalSurNameUser = paternalSurNameUser;
    }

    public String getMaternalSurNameUser() {
        return maternalSurNameUser;
    }

    public void setMaternalSurNameUser(String maternalSurNameUser) {
        this.maternalSurNameUser = maternalSurNameUser;
    }

    public LocalDate getBirthDateUser() {
        return birthDateUser;
    }

    public void setBirthDateUser(LocalDate birthDateUser) {
        this.birthDateUser = birthDateUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public LocalDate getAccountCreatedDateUser() {
        return accountCreatedDateUser;
    }

    public void setAccountCreatedDateUser(LocalDate accountCreatedDateUser) { this.accountCreatedDateUser = accountCreatedDateUser; }

    public Boolean getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(Boolean statusUser) {
        this.statusUser = statusUser;
    }

    public Roles getRoles() { return roles; }

    public void setRoles(Roles roles) { this.roles = roles; }

    public Family getFamily() { return family; }

    public void setFamily(Family family) { this.family = family; }
}
