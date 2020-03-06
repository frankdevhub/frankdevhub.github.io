package com.frankdevhub.site.core.generators.snowflake;


import com.frankdevhub.site.core.generators.KeyGenerator;
import org.springframework.util.Assert;

public class SnowflakeGenerator implements KeyGenerator<Long> {

    private SnowflakeIdWorker snowflakeIdWorker;

    public SnowflakeGenerator() {
        this.snowflakeIdWorker = new SnowflakeIdWorker();
    }

    public SnowflakeGenerator(long ipId, long dataCenterId) {
        this.snowflakeIdWorker = new SnowflakeIdWorker(ipId, dataCenterId);
    }

    @Override
    public Long generateKey() {
        Assert.notNull(this.snowflakeIdWorker, "snowflakeIdWorker should not be null");
        long nextId = this.snowflakeIdWorker.nextId();
        return nextId;
    }

    public SnowflakeIdWorker getSnowflakeIdWorker() {
        return this.snowflakeIdWorker;
    }

    public void setSnowflakeIdWorker(SnowflakeIdWorker snowflakeIdWorker) {
        this.snowflakeIdWorker = snowflakeIdWorker;
    }
}
