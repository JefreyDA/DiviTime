package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

// CLASE AGREGADA PARA LA NUEVA VERSIÓN

public class UserGeneralListDTO {
    private int idUser;
    private String nameUser;
    private String paternalSurNameUser;
    private String maternalSurNameUser;
    private LocalDate birthDateUser;
    private String emailUser;
    private String passwordUser;
    private Boolean statusUser;
    private LocalDate accountCreatedDateUser;
    private int idRole;
    private Integer idFamily;

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

    public int getIdRole() { return idRole; }

    public void setIdRole(int idRole) { this.idRole = idRole; }

    public Integer getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(Integer idFamily) {
        this.idFamily = idFamily;
    }

    public Boolean getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(Boolean statusUser) {
        this.statusUser = statusUser;
    }

    public LocalDate getAccountCreatedDateUser() {
        return accountCreatedDateUser;
    }

    public void setAccountCreatedDateUser(LocalDate accountCreatedDateUser) {
        this.accountCreatedDateUser = accountCreatedDateUser;
    }
}
