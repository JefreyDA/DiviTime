package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class UserDTO {
    private String nameUser;
    private String paternalSurNameUser;
    private String maternalSurNameUser;
    private LocalDate birthDateUser;
    private String emailUser;

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
}
