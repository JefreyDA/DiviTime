package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="roles", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "rol"})})
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRole;
    @Column(name = "NameRole",length = 50,nullable = false)
    private String nameRole;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Roles() {
    }

    public Roles(int idRole, String nameRole) {
        this.idRole = idRole;
        this.nameRole = nameRole;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
