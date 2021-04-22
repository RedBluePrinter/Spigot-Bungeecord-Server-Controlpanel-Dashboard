package tk.snapz.web;

import tk.snapz.util.ThreadSafeList;

public class Testing {
    public static void main(String[] args) {
        final boolean[] doRun = {true};
        ThreadSafeList<String> threadSafeList = new ThreadSafeList<>();
        final int[] actions = {0};
        new Thread(() -> {
            try {
                Thread.sleep(180000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            doRun[0] = false;
        }).start();
        new Thread(() -> {
            while (doRun[0]) {
                threadSafeList.add(new String("Hello World!"));
                actions[0]++;
            }
        }).start();
        new Thread(() -> {
            while (doRun[0]) {
                threadSafeList.size();
                System.out.println("Actions: " + actions);
                actions[0]++;
            }
        }).start();
        new Thread(() -> {
            while (doRun[0]) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                threadSafeList.remove(0);
                actions[0]++;
            }
        }).start();
    }
}
