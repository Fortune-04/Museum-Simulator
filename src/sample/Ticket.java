package sample;

import java.time.LocalTime;

public class Ticket {

    public String ticketId;
    public LocalTime timeBought;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LocalTime getTimeBought() {
        return timeBought;
    }

    public void setTimeBought(LocalTime timeBought) {
        this.timeBought = timeBought;
    }

    public Ticket(String ticketId, LocalTime timeBought) {
        this.ticketId = ticketId;
        this.timeBought = timeBought;
    }
}
