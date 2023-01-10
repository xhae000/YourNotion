package com.xhadl.yournotion.Redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, String> {
    public TestEntity save(TestEntity testEntity);
    public TestEntity findByName(String name);
}
