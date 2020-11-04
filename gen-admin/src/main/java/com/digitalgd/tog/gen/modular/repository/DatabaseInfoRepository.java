package com.digitalgd.tog.gen.modular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalgd.tog.gen.modular.model.DatabaseInfo;

/**
 * Created by stephan on 20.03.16.
 */
public interface DatabaseInfoRepository extends JpaRepository<DatabaseInfo, Long> {
}
