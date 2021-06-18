package sample;

import javafx.application.Platform;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Entrance extends Museum implements Runnable{

    private String name;
    private Visitor bob;
    private LocalTime current;
    Controller controller;

    public Entrance(String name, Controller controller) {
        this.name = name;
        current = getClock();
        this.controller = controller;
    }

    public void letVisitorEnter() {
        try {

            // if there is visiting waiting to enter
            if(getVisitorQueue().peek() != null) {
                bob = getVisitorQueue().poll();
                LocalTime timeExit = getClock().plusMinutes(bob.getTimeInside());
                // get visitor current time inside
                int bobTime = bob.getTimeInside();
                // if visitor time inside exceed museum close time set it to close time
                if(timeExit.isAfter(closetime)) {
                    bobTime = (int) getClock().until(closetime, ChronoUnit.MINUTES);
                    timeExit = closetime;
                }
                // adding visitor in museum visitor current
                addHashMap(bob.getTicket().getTicketId(), timeExit);
                getTotalVisitor().incrementAndGet();
                int turnstile = (rand.nextInt(4) + 1);
                System.out.println(getClock() + " Tickets " + bob.getTicket().getTicketId() +
                        " Entered through " + this.name + " Turnstile " + turnstile +
                        " staying for " + bobTime + " minutes. exiting at : " + timeExit);
                if(this.name == "North Entrance"){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.entranceNorthGate(bob.getTicket().getTicketId() +
                                    " Entered through Turnstile " + turnstile +
                                    " staying for " + bob.getTimeInside() + " minutes" );
                        }
                    });

                }
                else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.entranceSouthGate(bob.getTicket().getTicketId() +
                                    " Entered through Turnstile " + turnstile +
                                    " staying for " + bob.getTimeInside() + " minutes");
                        }
                    });

                }

            }

        } catch(Exception e) {

        }
    }

    @Override
    public void run() {

        try {
            while(true) {
                if( getTotalVisitor().get() == 900 ) {
                    // print the total visitor visit the museum here
                    System.out.println("the Museum total visitor is full will not entertain any more visitor");
                    System.out.println("total visitor " + getTotalVisitor());
                    setCounterOpen(false);
                    break;
                }
                if(isMuseumOpen()) {
                    // controller current visitor here

                    lock.lock();
                    if(getMuseumVisitor().size() >= 100) {
                        System.out.println(TEXT_RED+ "Museum is full, wait for other visitor to left " + TEXT_RESET);
                        museumFull.await();
                    } else {
                        letVisitorEnter();
                    }
                    lock.unlock();
                    Thread.sleep(50);
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
