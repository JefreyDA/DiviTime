package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    public User(int idUser) {
        this.idUser = idUser;
    }

    public User() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
