package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupJpaRepository extends JpaRepository<GroupJpaEntity, String> {

    Optional<GroupJpaEntity> findByLightIdsContaining(String lightId);
}
