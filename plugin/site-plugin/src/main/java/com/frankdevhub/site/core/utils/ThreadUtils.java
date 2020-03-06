package com.frankdevhub.site.core.utils;

public class ThreadUtils {
    public synchronized static Thread check(String thread) {
        Thread alive = null;
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThread = currentGroup.activeCount();

        Thread[] lstThreads = new Thread[noThread];
        currentGroup.enumerate(lstThreads);

        for (int i = 0; i < noThread; i++) {
            String currentThreadName = lstThreads[i].getName();
            System.out.println(currentThreadName);
            if (currentThreadName.equals(thread)) {
                alive = lstThreads[i];
                break;
            }
        }
        return alive;
    }
}
