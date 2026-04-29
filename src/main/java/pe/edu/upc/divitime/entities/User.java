package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuario;
    @Column(name = "nameUser",length =50 ,nullable = false)
    private String nameUser;
    @Column(name = "LastName1",length =50 ,nullable = false)
    private String lastname1;
    @Column(name = "LastName2",length =50 ,nullable = false)
    private String lastname2;
    @Column(name = "Email",length =50 ,nullable = false)
    private String email;
    @Column(name = "Password",length =50 ,nullable = false)
    private String password;
    @Column(name = "Birthdate" ,nullable = false)
    private LocalDate birthdate;
    @Column(name = "CreationDate" ,nullable = false)
    private LocalDate creationDate;

    public User() {
    }

    public User(int idusuario, String nameUser, String lastname1, String lastname2, String email, String password, LocalDate birthdate, LocalDate creationDate) {
        this.idusuario = idusuario;
        this.nameUser = nameUser;
        this.lastname1 = lastname1;
        this.lastname2 = lastname2;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.creationDate = creationDate;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastname1() {
        return lastname1;
    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public String getLastname2() {
        return lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
