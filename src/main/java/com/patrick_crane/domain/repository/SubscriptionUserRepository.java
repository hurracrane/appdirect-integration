package com.patrick_crane.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patrick_crane.domain.entities.SubscriptionUser;

@Repository
public interface SubscriptionUserRepository extends JpaRepository<SubscriptionUser, Long> {

  SubscriptionUser findByUuidAndSubscription_companyUuid(String uuid, String companyUuid);

}
