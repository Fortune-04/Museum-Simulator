package sample;

import javafx.application.Platform;

import java.time.LocalTime;

public class TheTime extends Museum implements Runnable{

//    private String time;
    Controller controller;


    public TheTime(Controller controller){
        this.controller=controller;
    }


    @Override
    public void run() {

        try {
            while(true) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String time = getClock().toString();
                        controller.time(time);
                    }
                });

                //set museum open when current time is same with open time and vice versa
                if(getClock().equals(opentime)) {
                    System.out.println("Museum is open");
                    setMuseumOpen(true);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.openClose("Museum is open");
                        }
                    });
                } else if(getClock().equals(closetime)) {
                    System.out.println("Museum is close");
                    setMuseumOpen(false);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.openClose("Museum is close");
                        }
                    });
                }
                //set counter open when 8:00 and close at 17:00
                if(getClock().equals(LocalTime.of(8, 0))) {
                    System.out.println("Counter Ticket is open");
                    setCounterOpen(true);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.openClose("Counter Ticket is open");
                        }
                    });
                }else if(getClock().equals(LocalTime.of(17, 0))) {
                    System.out.println("Counter Ticket is close");
                    setCounterOpen(false);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.openClose("Counter Ticket is close");
                        }
                    });
                }


                if(Thread.currentThread().isAlive()) {
                    Thread.sleep(300);
                    //time will move 1 minutes after 300milisecond
                    setClock(getClock().plusMinutes(1));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.curVisitor(Integer.toString(getMuseumVisitor().size()));
                            controller.totalVisitorMuseum(getTotalVisitor().toString());
                            controller.waitList(Integer.toString(getVisitorQueue().size()));
                        }
                    });

                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
