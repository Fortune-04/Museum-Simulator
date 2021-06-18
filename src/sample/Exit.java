package sample;

import javafx.application.Platform;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Exit extends Museum implements Runnable{

    public String name;
    Controller controller;

    public Exit(String name, Controller controller) {
        this.name = name;
        this.controller = controller;
    }

    public void letVisitorExit() {
        lock.lock();
        try {
            if(isMuseumOpen() || !isMuseumOpen()) {

                //iterate through hashmap for each visitpr in currently in museum
                Iterator<ConcurrentHashMap.Entry<String, LocalTime>> itr = getMuseumVisitor().entrySet().iterator();
                while (itr.hasNext()) {
                    ConcurrentHashMap.Entry<String, LocalTime> entry = itr.next();
                    if (entry.getValue().equals(getClock())) {
                        //remove visitor if current time equal exit time
                        removeHashMap(entry.getKey(), entry.getValue());
                        System.out.println(getClock() + " Ticket " + entry.getKey() + " exited through exit " + name +
                                " turnstile " + (rand.nextInt(4) + 1));
                        if(name == "West Exit"){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    controller.exitWestGate(entry.getKey() + " exited through turnstile " + (rand.nextInt(4) + 1));
                                }
                            });

                        }
                        else{
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    controller.exitEastGate(entry.getKey() + " exited through turnstile " + (rand.nextInt(4) + 1));
                                }
                            });

                        }
                        museumFull.signalAll();
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {

        while(true) {
            // let visitor exit the museum
                letVisitorExit();

                //break the system when all visitor is out after close time
                if(getClock().isAfter(closetime) && !getMuseumVisitor().isEmpty()) {
                    System.exit(1);
                }

        }

    }
}
