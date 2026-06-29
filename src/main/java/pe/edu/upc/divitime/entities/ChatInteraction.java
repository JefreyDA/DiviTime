package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "ChatInteraction")
public class ChatInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInteraction;

    @Column(name = "messageText", nullable = false, columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "senderRole", nullable = false, length = 20)
    private String senderRole;

    @Column(name = "interactionDate", nullable = false)
    private LocalDateTime interactionDate;

    @ManyToOne
    @JoinColumn(name = "idChat")
    private Chat chat;

    public ChatInteraction() {
    }

    public ChatInteraction(int idInteraction, String messageText, String senderRole, LocalDateTime interactionDate, Chat chat) {
        this.idInteraction = idInteraction;
        this.messageText = messageText;
        this.senderRole = senderRole;
        this.interactionDate = interactionDate;
        this.chat = chat;
    }

    public int getIdInteraction() {
        return idInteraction;
    }

    public void setIdInteraction(int idInteraction) {
        this.idInteraction = idInteraction;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public LocalDateTime getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDateTime interactionDate) {
        this.interactionDate = interactionDate;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
