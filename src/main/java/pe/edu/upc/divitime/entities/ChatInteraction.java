package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ChatInteraction")
public class ChatInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInteraction;

    @Column(name = "interactionDate", nullable = false)
    private LocalDate interactionDate;

    @ManyToOne
    @JoinColumn(name = "idChat")
    private Chat chat;

    public ChatInteraction() {
    }

    public ChatInteraction(int idInteraction, LocalDate interactionDate, Chat chat) {
        this.idInteraction = idInteraction;
        this.interactionDate = interactionDate;
        this.chat = chat;
    }

    public int getIdInteraction() {
        return idInteraction;
    }

    public void setIdInteraction(int idInteraction) {
        this.idInteraction = idInteraction;
    }

    public LocalDate getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDate interactionDate) {
        this.interactionDate = interactionDate;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
