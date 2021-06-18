package sample;


import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Museum {

    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLACK = "\u001B[30m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";

    public final LocalTime opentime = LocalTime.of(9,0);
    public final LocalTime closetime = LocalTime.of(18,0);
    public Random rand;
    private static boolean museumOpen;
    private static boolean counterOpen;
    // total visitor
    private static volatile AtomicInteger totalVisitor = new AtomicInteger(0);
    // buy time ticket
    private static volatile LocalTime buyTime = LocalTime.of(7, 45);
    // total ticket buy
    public static volatile AtomicInteger totalTicket = new AtomicInteger(0);

    private static volatile LocalTime clock;
    // current visitor in museum
    private static ConcurrentHashMap<String, LocalTime> museumVisitor = new ConcurrentHashMap<String, LocalTime>();
    // queue of visitor who buy this ticket
    private static ConcurrentLinkedQueue<Visitor> visitorQueue = new ConcurrentLinkedQueue<>();



    public static Lock lock;
    public static Condition museumFull;
    public static Condition turnstileFull;
    public Museum() {
        rand = new Random();
        clock = LocalTime.of(7, 45);
        lock = new ReentrantLock();
        museumFull = lock.newCondition();
        turnstileFull = lock.newCondition();
        counterOpen = false;
        museumOpen = false;
    }
    //get buy time
    public LocalTime getBuyTime() {
        return buyTime;
    }
    // set buy time
    public void setBuyTime(LocalTime buyTime) {
        this.buyTime = buyTime;
    }
    //check is museum is open
    public boolean isMuseumOpen() {
        return museumOpen;
    }

    public void setMuseumOpen(boolean museumOpen) {
        this.museumOpen = museumOpen;
    }

    public boolean isCounterOpen() {
        return counterOpen;
    }

    public void setCounterOpen(boolean counterOpen) {
        this.counterOpen = counterOpen;
    }

    public LocalTime getClock() {
        return clock;
    }

    public void setClock(LocalTime clock) {
        this.clock = clock;
    }

    public ConcurrentHashMap<String, LocalTime> getMuseumVisitor() {
        return museumVisitor;
    }

    public ConcurrentLinkedQueue<Visitor> getVisitorQueue() {
        return visitorQueue;
    }

    public AtomicInteger getTotalVisitor() {
        return totalVisitor;
    }

    public void setTotalVisitor(AtomicInteger totalVisitor) {
        this.totalVisitor = totalVisitor;
    }
    //adding into the hashmap
    public void addHashMap(String v, LocalTime t) throws InterruptedException {

        museumVisitor.put(v, t);
        System.out.println(TEXT_GREEN + "number in current museum " + getMuseumVisitor().size() + TEXT_RESET);
    }

    public void removeHashMap(String v, LocalTime t) {
        museumVisitor.remove(v, t);
    }




}
