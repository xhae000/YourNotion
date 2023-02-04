package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Integer> {
    public AnswerEntity save(AnswerEntity answerEntity);
}
