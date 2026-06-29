package pe.edu.upc.divitime.dtos;

public class ChatMessageRequestDTO {
    private int idChat;
    private String message;

    public ChatMessageRequestDTO() {
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
