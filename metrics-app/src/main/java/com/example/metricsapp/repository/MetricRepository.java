package com.example.metricsapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.metricsapp.model.Metric;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {

	@Query("SELECT m FROM Metric m WHERE (:system IS NULL OR m.system = :system) " +
            "AND (:name IS NULL OR m.name = :name) " +
            "AND (:from IS NULL OR m.date >= :from) " +
            "AND (:to IS NULL OR m.date <= :to)")
    List<Metric> findAllByFilters(
            @Param("system") String system,
            @Param("name") String name,
            @Param("from") Long from,
            @Param("to") Long to
    );
	
	@Query("SELECT SUM(m.value) FROM Metric m WHERE (:system IS NULL OR m.system = :system) " +
            "AND (:name IS NULL OR m.name = :name) " +
            "AND (:from IS NULL OR m.date >= :from) " +
            "AND (:to IS NULL OR m.date <= :to)")
    int getTotalValueByFilters(
            @Param("system") String system,
            @Param("name") String name,
            @Param("from") Long from,
            @Param("to") Long to
    );
}
