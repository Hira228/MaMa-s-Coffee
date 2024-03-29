package com.order.module.repository;

import com.order.module.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItem, UUID> {
}
