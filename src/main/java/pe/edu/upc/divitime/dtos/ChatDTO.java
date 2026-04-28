package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class ChatDTO {
    private LocalDate startDateChat;
    private int frequencyChat;

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
