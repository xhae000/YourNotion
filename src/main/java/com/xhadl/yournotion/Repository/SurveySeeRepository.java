package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveySeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SurveySeeRepository extends JpaRepository<SurveySeeEntity, Integer> {

    @Query("select s.seeCount from SurveySeeEntity s where s.surveyId = :surveyId")
    public Integer getSeeCount(@Param("surveyId") int surveyId);

    @Transactional
    @Modifying
    @Query("update SurveySeeEntity set seeCount = :seecount where surveyId = :surveyid")
    public Integer increaseSeeCount(@Param("surveyid") int surveyId, @Param("seecount") int seeCount);

    public SurveySeeEntity save(SurveySeeEntity surveySeeEntity);
}
