package ru.netology;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class MyCallable implements Callable<Integer> {
    static final float DELAY = 2.5F;
    static final int MAX = 5;
    static final int MIN = 1;

    @Override
    public Integer call() throws Exception {
        Integer planCountMessage = -1;
        Integer realCountMessage = -1;
        ThreadLocalRandom random = ThreadLocalRandom.current();

        planCountMessage = random.nextInt(MIN, MAX);
        try {
            for (realCountMessage = 0; realCountMessage < planCountMessage; realCountMessage++) {
                Thread.sleep((int) DELAY * 1000);
                System.out.println("Я " + Thread.currentThread().getName() + ". Всем привет!"
                        + " (Количество раз, которое я могу сообщить о себе: " + planCountMessage + ")");
            }
        } catch (InterruptedException err) {
            //err.printStackTrace();
        } finally {
            //System.out.printf("%s завершен\n", getName());
        }

        return realCountMessage;
    }

}
