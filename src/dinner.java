import java.util.*;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
/*Program for diniing philosopher problem */
public class dinner {
    //number of philosophers
    static int philosophersNumber = 5;
    //create array of pholosopher
    static Philosopher philosophers[] = new Philosopher[philosophersNumber];
    //create array of forks
    static Fork forks[] = new Fork[philosophersNumber];
    //inner class fork
    static class Fork {
        //take one semaphore
        public Semaphore mutex = new Semaphore(1);
        //method to grab the fork
        void grab() {
            //try block
            try {
                mutex.acquire();
            }
            //catch exception
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        //method to release the fork
        void release() {
            mutex.release();
        }
        boolean isFree() {
            return mutex.availablePermits() > 0;
        }
    }
    //Inner philosopher class
    static class Philosopher extends Thread {
        public int number;
        public Fork leftFork;
        public Fork rightFork;
        Philosopher(int num, Fork left, Fork right){
            number = num;
            leftFork = left;
            rightFork = right;
        }
        //run method is implemented here
        public void run(){
            System.out.println("Hi! I'm philosopher #" +number);
            //itereate untill true
            while (true) {
                leftFork.grab();
                System.out.println("Philosopher #" +number + " grabs left fork.");
                rightFork.grab();
                System.out.println("Philosopher #" +number + " grabs right fork.");
                eat();
                leftFork.release();
                System.out.println("Philosopher #" +number + " releases left fork.");
                rightFork.release();
                System.out.println("Philosopher #" +number + " releases right fork.");
            }
        }
        void eat() {
            try {
                int sleepTime =ThreadLocalRandom.current().nextInt(0, 1000);
                System.out.println("Philosopher #" + "eats for " + sleepTime);
                Thread.sleep(sleepTime);
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
    public static void main(String argv[]) {
        System.out.println("Dining philosophersproblem.");
        for (int i = 0; i < philosophersNumber; i++){
            forks[i] = new Fork();
        }
        for (int i = 0; i < philosophersNumber; i++){
            philosophers[i] = new Philosopher(i, forks[i],forks[(i + 1) % philosophersNumber]);
            philosophers[i].start();
        }
        while (true) {
            try {
                // sleep 1 sec
                Thread.sleep(1000);
                // check for deadlock
                boolean deadlock = true;
                for (Fork f : forks) {
                    if (f.isFree()) {
                        deadlock = false;
                        break;
                    }
                }
                if (deadlock) {
                    Thread.sleep(1000);
                    System.out.println("Hurray!There is a deadlock!");
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
        System.out.println("Bye!");
        System.exit(0);
    }
}

