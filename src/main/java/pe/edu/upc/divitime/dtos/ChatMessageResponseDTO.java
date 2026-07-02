package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class ChatMessageResponseDTO {
    private String botResponse;
    private LocalDateTime timestamp;

    public ChatMessageResponseDTO() {
    }

    public ChatMessageResponseDTO(String botResponse, LocalDateTime timestamp) {
        this.botResponse = botResponse;
        this.timestamp = timestamp;
    }

    public String getBotResponse() {
        return botResponse;
    }

    public void setBotResponse(String botResponse) {
        this.botResponse = botResponse;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
