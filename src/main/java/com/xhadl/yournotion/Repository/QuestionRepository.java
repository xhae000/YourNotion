package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository  extends JpaRepository<QuestionEntity, Long> {

    public QuestionEntity save(QuestionEntity questionEntity);
}
