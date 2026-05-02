package pe.edu.upc.divitime.dtos;

public class ChatUserQuantityDTO {
    private int idChat;
    private String nameUser;
    private int frequencyChat;

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

    public int getFrequencyChat() {
        return frequencyChat;
    }

    public void setFrequencyChat(int frequencyChat) {
        this.frequencyChat = frequencyChat;
    }
}
