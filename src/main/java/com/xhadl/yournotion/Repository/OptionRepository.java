package com.xhadl.yournotion.Repository;

import com.xhadl.yournotion.Entity.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity,Long> {
    public OptionEntity save(OptionEntity optionEntity);
}
