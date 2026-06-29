package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class ChatGeneralDTO {
    private int idChat;
    private LocalDate startDateChat;
    private int frequencyChat;
    private int idUser;

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public LocalDate getStartDateChat() {
        return startDateChat;
    }

    public void setStartDateChat(LocalDate startDateChat) {
        this.startDateChat = startDateChat;
    }

    public int getFrequencyChat() {
        return frequencyChat;
    }

    public void setFrequencyChat(int frequencyChat) {
        this.frequencyChat = frequencyChat;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
