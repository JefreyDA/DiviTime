package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "UserFamily")
public class UserFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUserFamily;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idFamily")
    private Family family;

    @Column(name = "familyPosition", nullable = false)
    private Character familyPosition;

    public UserFamily(int idUserFamily, User user, Family family, Character familyPosition) {
        this.idUserFamily = idUserFamily;
        this.user = user;
        this.family = family;
        this.familyPosition = familyPosition;
    }

    public UserFamily() {

    }

    public int getIdUserFamily() {
        return idUserFamily;
    }

    public void setIdUserFamily(int idUserFamily) {
        this.idUserFamily = idUserFamily;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Character getFamilyPosition() {
        return familyPosition;
    }

    public void setFamilyPosition(Character familyPosition) {
        this.familyPosition = familyPosition;
    }
}
