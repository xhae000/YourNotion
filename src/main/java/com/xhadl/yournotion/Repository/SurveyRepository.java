package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<SurveyEntity, Integer> {
    public SurveyEntity findById(int id);
    public Page<SurveyEntity> findAllByOrderByIdDesc(Pageable pageable);
    public SurveyEntity save(SurveyEntity surveyEntity);
    public int countBy();
    @Query("select s from SurveyEntity s where  replace(s.title, ' ','')  like %:keyword% order by s.id desc")
    public Page<SurveyEntity> getSurveysByKeyword(Pageable pageable, @Param("keyword") String keyword);
}
