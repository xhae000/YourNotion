package com.xhadl.yournotion.Redis;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash(value="test", timeToLive = 30)
public class TestEntity {

    public TestEntity (String name){
        this.name = name;
    }
    @Id
    String id;
    String name;
}
