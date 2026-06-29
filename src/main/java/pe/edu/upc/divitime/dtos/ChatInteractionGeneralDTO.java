package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class ChatInteractionGeneralDTO {
    private int idInteraction;
    private String messageText;
    private String senderRole;
    private LocalDateTime interactionDate;

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
}
