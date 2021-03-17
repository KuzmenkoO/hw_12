package hw_12.taskOne;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class TaskOne {
    public static Phaser phaser = new Phaser(3);
    public int countMolecules;

    public void waterMolecules(String input) {
        this.countMolecules = input.length() / 3;
        ExecutorService service = Executors.newFixedThreadPool(countMolecules * 3);
        for (int i = 0; i < countMolecules; i++) {
            service.submit(new Oxygen(phaser, i));
            service.submit(new Hydrogen(phaser, i));
            service.submit(new Hydrogen(phaser, i));
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
        service.shutdown();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Oxygen extends threadElement {
    public Oxygen(Phaser phaser, int count) {
        super(phaser, count);
    }

    @Override
    public void run() {
        releaseOxygen();
    }
}

class Hydrogen extends threadElement {
    public Hydrogen(Phaser phaser, int count) {
        super(phaser, count);
    }

    @Override
    public void run() {
        releaseHydrogen();
    }
}

abstract class threadElement implements Runnable {
    private final Phaser phaser;
    private final int count;

    public threadElement(Phaser phaser, int count) {
        this.phaser = phaser;
        this.count = count;
        phaser.register();
    }

    @Override
    abstract public void run();

    public void releaseOxygen() {
        if (count < phaser.getPhase()) {
            phaser.arriveAndAwaitAdvance();
        }
        System.out.print("O");
        phaser.arriveAndDeregister();
    }

    public void releaseHydrogen() {
        if (count < phaser.getPhase()) {
            phaser.arriveAndAwaitAdvance();
        }
        System.out.print("H");
        phaser.arriveAndDeregister();
    }
}