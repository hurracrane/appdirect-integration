package com.patrick_crane.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patrick_crane.domain.entities.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

}
