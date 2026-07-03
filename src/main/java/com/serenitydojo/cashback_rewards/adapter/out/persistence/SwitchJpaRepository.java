package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwitchJpaRepository extends JpaRepository<SwitchJpaEntity, String> {
    List<SwitchJpaEntity> findByGroupId(String groupId);
}
