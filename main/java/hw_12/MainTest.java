package hw_12;

import hw_12.taskOne.TaskOne;
import hw_12.taskTwo.CustomThreadPoolExecutor;

public class MainTest {
    public static void main(String[] args) {
        //Завдання №1
        System.out.println("Завдання №1");
        TaskOne taskOne = new TaskOne();
        taskOne.waterMolecules(10);
        System.out.println();


        //Завдання №2
        System.out.println("Завдання №2");
        CustomThreadPoolExecutor customThreadPoolExecutor =
                new CustomThreadPoolExecutor(10);
        customThreadPoolExecutor.execute(new CustomThreadPoolExecutor.MyRunnable());
        customThreadPoolExecutor.shutdown();
    }
}