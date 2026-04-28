package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idChat;

    @Column(name = "startDateChat", nullable = false)
    private LocalDate startDateChat;

    @Column(name = "frequencyChat", nullable = false)
    private int frequencyChat;
    //Insertar la foregin key de Usuario


    public Chat(int idChat, LocalDate startDateChat, int frequencyChat) {
        this.idChat = idChat;
        this.startDateChat = startDateChat;
        this.frequencyChat = frequencyChat;
    }

    public Chat() {
    }

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
}
