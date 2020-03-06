package com.frankdevhub.site.core.generators.snowflake;

public class SnowflakeIdWorker {

    private final static long twepoch = 1483200000000l;

    private final static long ipIdBits = 8L;
    private final static long ipIdMax = ~(-1L << ipIdBits);
    private final static long dataCenterIdBits = 2L;
    private final static long dataCenterIdMax = ~(-1L << dataCenterIdBits);
    private final static long seqBits = 12L;
    private final static long seqMax = ~(-1L << seqBits);

    private final static long dataCenterIdLeftShift = seqBits;
    private final static long ipIdLeftShift = seqBits + dataCenterIdBits;
    private final static long timeLeftShift = seqBits + dataCenterIdBits + ipIdLeftShift;

    private long ipId;
    private long dataCenterId;
    private long seq = 0L;
    private long lastTime = -1L;

    public SnowflakeIdWorker() {
    }

    public SnowflakeIdWorker(long ipId, long dataCenterId) {
        if (ipId < 0 || ipId > ipIdMax) {
            System.out.println(" ---------- invalid ipId(0~" + ipIdMax + ") " + ipId);
            System.exit(0);
        }

        if (dataCenterId < 0 || dataCenterId > dataCenterIdMax) {
            System.out.println(" ---------- invalid dataCenterId(0~" + dataCenterIdMax + ") " + dataCenterId);
            System.exit(0);
        }

        this.ipId = ipId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() {
        long nowTime = System.currentTimeMillis();

        if (nowTime < lastTime) {
            System.out.println(" ---------- invalid current timestamp: " + nowTime);
            System.exit(0);
        }

        if (nowTime == lastTime) {
            seq = (seq + 1) & seqMax;
            if (seq == 0) {
                nowTime = getNextTimeStamp();
            }
        } else {
            seq = 0L;
        }

        lastTime = nowTime;

        return ((nowTime - twepoch) << timeLeftShift)
                | (ipId << ipIdLeftShift)
                | (dataCenterId << dataCenterIdLeftShift)
                | seq;
    }

    private long getNextTimeStamp() {
        long nowTime;
        do {
            nowTime = System.currentTimeMillis();
        } while (nowTime <= lastTime);
        return nowTime;
    }
}
