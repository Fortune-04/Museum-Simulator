package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Controller {


    @FXML
    public Button startButton;
    public Text curTime;
    public Text tcRe;
    public TextArea southGate;
    public TextArea westGate;
    public Text curVis;
    public TextArea eastGate;
    public TextArea northGate;
    public TextArea ticketCounter;
    public Text waitList;
    public Button exitButton;
    public Text totalVisMus;



    public void startMuseum() {

        ExecutorService service = Executors.newFixedThreadPool(6);
        Museum museum = new Museum();
        Museum counterTicket = new CounterTicket(this);
        Museum theTime = new TheTime(this);
        Museum se = new Entrance("South Entrance", this);
        Museum ne = new Entrance("North Entrance", this);
        Museum ee = new Exit("East Exit",this);
        Museum we = new Exit("West Exit", this);

        service.execute((Runnable) counterTicket);
        service.execute((Runnable) theTime);
        service.execute((Runnable) se);
        service.execute((Runnable) ee);
        service.execute((Runnable) ne);
        service.execute((Runnable) we);

        service.shutdown();

    }
    public void setTimerTxt(String time){
        curTime.setText(time);
    }

    public void notOpen(String not_open){
        ticketCounter.appendText(not_open+"\n");
    }

    public void ticketSold(String ticket_sold){
        ticketCounter.appendText(ticket_sold);
    }

    public void openClose(String open_close){
        ticketCounter.appendText(open_close+"\n");
    }

    public void exitWestGate(String exit_west_gate){
        westGate.appendText(exit_west_gate+"\n");
    }

    public void exitEastGate(String exit_east_gate){
        eastGate.appendText(exit_east_gate+"\n");
    }

    public void entranceNorthGate(String entrance_north_gate){
        northGate.appendText(entrance_north_gate+"\n");
    }

    public void entranceSouthGate(String entrance_south_gate){
        southGate.appendText(entrance_south_gate+"\n");
    }

    public void time(String time){
        curTime.setText(time);
    }

    public void curVisitor(String cur_visitor){
        curVis.setText(cur_visitor);
    }

    public void totalTicket(String total_ticket){
        tcRe.setText(total_ticket);
    }

    public void waitList(String wait_list){
        waitList.setText(wait_list);
    }

    public void totalVisitorMuseum(String total_vis_museum){
        totalVisMus.setText(total_vis_museum);
    }

    public void exitMuseum(){
        System.exit(1);
    }



}

