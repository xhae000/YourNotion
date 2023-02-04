package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity,Integer> {
    public OptionEntity save(OptionEntity optionEntity);

    public List<OptionEntity> findAllByQuestionId(int questionId);
}
