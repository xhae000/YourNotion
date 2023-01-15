package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.SurveyAvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyAvailableRepository extends JpaRepository<SurveyAvailableEntity, Long> {
    public SurveyAvailableEntity findByUsername(String username);
}
