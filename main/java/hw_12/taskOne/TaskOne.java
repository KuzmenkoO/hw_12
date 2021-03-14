package hw_12.taskOne;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class TaskOne {
    public static Phaser phaser = new Phaser(3);
    public int countMolecules;

    public void waterMolecules(int countMolecules) {
        this.countMolecules = countMolecules;
        ExecutorService service = Executors.newFixedThreadPool(countMolecules * 3);
        for (int i = 0; i < countMolecules; i++) {
            service.submit(new OxygenThread(phaser, i));
            service.submit(new HydrogenThread(phaser, i));
            service.submit(new HydrogenThread(phaser, i));
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
        service.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class OxygenThread implements Runnable {
    private final Phaser phaser;
    private final int count;

    public OxygenThread(Phaser phaser, int count) {
        this.phaser = phaser;
        this.count = count;
        phaser.register();
    }

    @Override
    public void run() {
        releaseOxygen();
    }

    private void releaseOxygen() {
        if (count < phaser.getPhase()) {
            phaser.arriveAndAwaitAdvance();
        }
        System.out.print("O");
        phaser.arriveAndDeregister();
    }
}

class HydrogenThread implements Runnable {
    private final Phaser phaser;
    private final int count;

    public HydrogenThread(Phaser phaser, int count) {
        this.phaser = phaser;
        this.count = count;
        phaser.register();
    }

    @Override
    public void run() {
        releaseHydrogen();
    }

    private void releaseHydrogen() {
        if (count < phaser.getPhase()) {
            phaser.arriveAndAwaitAdvance();
        }
        System.out.print("H");
        phaser.arriveAndDeregister();
    }
}