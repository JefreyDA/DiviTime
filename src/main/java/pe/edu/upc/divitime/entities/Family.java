package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Family")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFamily;

    public Family(int idFamily) {
        this.idFamily = idFamily;
    }

    public Family() {

    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }
}
