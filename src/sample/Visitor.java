package sample;

public class Visitor {

    public Ticket ticket;
    public int timeInside;

    public Visitor(Ticket ticket, int timeInside) {
        this.ticket = ticket;
        this.timeInside = timeInside;
    }

    public int getTimeInside() {
        return timeInside;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return ticket.getTicketId();
    }
}
