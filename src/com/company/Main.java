package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Account.bank_account = 10000;
        new Change(9000).start();
        new Change(-10000).start();
    }
}

class Account {

    public static int bank_account;
    public static Object key = new Object();

    public static void change(int sum) throws InterruptedException {
        synchronized (key) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (sum < 0) {
                if (bank_account + sum >= 0) {
                    bank_account += sum;
                    System.out.print("Вы сняли с счёта " + -sum + ".");
                }
                else {
                    System.out.print("Недостаточно средств для вывода!");
                }
            }
            else {
                bank_account += sum;
                System.out.print("Вы положили на счёт " + sum + ".");
            }
            System.out.println(" На счёте: " + bank_account);
            key.notify();
            key.wait();
        }
    }
}

class Change extends Thread {

    private final int sum;

    public Change(int sum) {
        this.sum = sum;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Account.change(sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
