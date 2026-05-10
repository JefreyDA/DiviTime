package pe.edu.upc.divitime.dtos;

public class ChatInteractionCountWeeklyDTO {
    private int idChat;
    private String nameUser;
    private int totalInteractions;

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getTotalInteractions() {
        return totalInteractions;
    }

    public void setTotalInteractions(int totalInteractions) {
        this.totalInteractions = totalInteractions;
    }
}
