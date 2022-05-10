package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    static final int countThreads = 5;
    static final String strInvokeAll = "Запускаем потоки. Получаем результат от каждого...";
    static final String strInvokeAny = "Запускаем потоки. Получаем результат от первого успешно завершенного...";

    public static void main(String[] args) {
        final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<Integer>> tasks = new ArrayList<>();
        try {
            for (int i = 0; i < countThreads; i++) {
                tasks.add(new MyCallable());
            }
            System.out.println(strInvokeAll);
            List<Future<Integer>> invokeAll = threadPool.invokeAll(tasks);
            int iterThreads = 0;
            for (Future<Integer> future : invokeAll) {
                iterThreads++;
                try {
                    System.out.println(iterThreads + "-й поток сообщил о себе: " + future.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    threadPool.shutdown();
                }
            }
//            System.out.println(strInvokeAny);
//            Integer fastestCount = threadPool.invokeAny(tasks);
//            System.out.println("Первый успешно завершившийся поток сообщил о себе: " + fastestCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        // столкнулся с проблемой повторного использования threadPool, поэтому пришлось создавать новый threadPoolAny
        // повторный вызов threadPool.invokeAny(tasks), помещенный в предыдущий try, падал с ошибкой
        final ExecutorService threadPoolAny = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            System.out.println(strInvokeAny);
            // задачи уже созданы ранее
            Integer fastestCount = threadPoolAny.invokeAny(tasks);
            System.out.println("Первый успешно завершившийся поток сообщил о себе: " + fastestCount);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPoolAny.shutdown();
        }
    }
}
