//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        //Part A
        System.out.println("Starting Part A - Three Threads");
        long startTimeA = System.currentTimeMillis();

        try{
            Thread primeThread = new PrimeThread();
            Thread fibonacciThread = new FibonacciThread();
            Thread factorialThread = new FactorialThread();

            primeThread.start();
            fibonacciThread.start();
            factorialThread.start();

            primeThread.join();
            fibonacciThread.join();
            factorialThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        long endTimeA = System.currentTimeMillis();
        System.out.println("Total time for Part A: " + (endTimeA - startTimeA) + "ms\n");

        //Part B
        System.out.println("Starting Part B - ThreadPool");
        long startTimeB = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        //submit task for prime
        for(int i = 1; i <= 25; i++){
            int finalI = i;
            threadPool.submit(() -> calculatePrime(finalI));
        }
        //submit task for fibonacci
        for(int i = 1; i <= 50; i++){
            int finalI = i;
            threadPool.submit(() -> calculateFibonacci(finalI));
        }
        //submit task for factorial
        for(int i = 1; i <= 100; i++){
            int finalI = i;
            threadPool.submit(() -> calculateFactorial(finalI));
        }

        threadPool.shutdown();
        try{
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        long endTimeB = System.currentTimeMillis();
        System.out.println("Total time for Part B: " + (endTimeB - startTimeB) + "ms");
    }

    //This part is for the first thread
    public static void calculatePrime(int n){
        int count = 0, num = 2;
        Random random = new Random();

        while(count < n){
            try{
                int delay = random.nextInt(401) + 100;
                Thread.sleep(delay);
                if(isPrime(num)){
                    count++;
                    if(count == n){
                        System.out.println("[" + System.currentTimeMillis() + "] Thread " + Thread.currentThread().getName()
                        + " - Prime " + n + ":" + num + " (Delay: " + delay + "ms)");
                    }
                }
                num++;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //This part is for the first thread
    public static boolean isPrime(int number){
        for(int i = 2; i <= number / 2; i++){
            if(number % i == 0){
                return false;
            }
        }
        return true;
    }

    //This part is for the second
    public static void calculateFibonacci(int n){
        Random random = new Random();
        int a = 0, b = 1;

        for(int i = 1; i <= n; i++){
            try{
                int delay = random.nextInt(401) + 100;
                Thread.sleep(delay);
                if(i == n){
                    System.out.println("[" + System.currentTimeMillis() + "] Thread " + Thread.currentThread().getName()
                            + " - Fibonacci " + n + ":" + a + " (Delay: " + delay + "ms)");
                }
                int sum = a + b;
                a = b;
                b = sum;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //This part for the third
    public static void calculateFactorial(int n){
        Random random = new Random();

        try{
            int delay = random.nextInt(401) + 100;
            Thread.sleep(delay);
            System.out.println("[" + System.currentTimeMillis() + "] Thread " + Thread.currentThread().getName()
                    + " - Factorial of " + n + ":" + factorial(n) + " (Delay: " + delay + "ms)");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static long factorial(int number){
        long result = 1;
        for(int i = 1; i <= number; i++){
            result *= i;
        }
        return result;
    }
}

class PrimeThread extends Thread{
    @Override
    public void run(){
        for(int i = 1; i <= 25; i++){
            Main.calculatePrime(i);
        }
    }
}

class FibonacciThread extends Thread{
    @Override
    public void run(){
        for(int i = 1; i <= 50; i++){
            Main.calculateFibonacci(i);
        }
    }
}

class FactorialThread extends Thread{
    @Override
    public void run(){
        for(int i = 1; i <= 100; i++){
            Main.calculateFactorial(i);
        }
    }
}