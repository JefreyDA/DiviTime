package pe.edu.upc.divitime.dtos;

import java.io.Serializable;

public class JwtResponseDTO implements Serializable {

    private final String jwttoken;
    private final int idUser;
    private final int idFamily;

    public JwtResponseDTO(String jwttoken, int idUser, int idFamily) {
        this.jwttoken = jwttoken;
        this.idUser = idUser;
        this.idFamily = idFamily;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdFamily() {
        return idFamily;
    }
}