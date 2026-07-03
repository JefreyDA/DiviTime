package pe.edu.upc.divitime.dtos;

public class UserEventDTO {

    private String usuario;
    private int totalEventos;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getTotalEventos() {
        return totalEventos;
    }

    public void setTotalEventos(int totalEventos) {
        this.totalEventos = totalEventos;
    }
}
