package me.arthurlins.jdownload.pool.util;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueWrapper extends LinkedBlockingQueue<Runnable> {

    @Override
    public boolean offer(Runnable runnable) {
        if (size() == 0) {
            return super.offer(runnable);
        } else {
            return false;
        }
    }
}
