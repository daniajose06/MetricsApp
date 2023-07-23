package com.example.metricsapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.metricsapp.model.Metric;
import com.example.metricsapp.model.MetricSummary;
import com.example.metricsapp.repository.MetricRepository;

@RestController
@RequestMapping("/metrics")
public class MetricController {
	
	@Autowired
	private final MetricRepository metricRepository;

    public MetricController(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }
    
    private static final Logger logger = LoggerFactory.getLogger(MetricController.class);
    
 // GET /metrics
    @GetMapping
    public ResponseEntity<List<Metric>> getAllMetrics(
            @RequestParam(required = false) String system,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to
    ) {
    	logger.info("Received request to retrieve all metrics");
        List<Metric> metrics = metricRepository.findAllByFilters(system, name, from, to);
        logger.debug("Retrieved {} metrics from the database", metrics.size());
        return ResponseEntity.ok(metrics);
    }
    
 // GET /metrics/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Metric> getMetricById(@PathVariable Long id) {
    	logger.info("Received request to retrieve metric with ID: {}", id);
        return metricRepository.findById(id)
        		.map(metric -> {
                    logger.debug("Found metric with ID: {}", id);
                    return ResponseEntity.ok(metric);
                })
                .orElseGet(() -> {
                    logger.warn("Metric with ID: {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }
    
 // POST /metrics
    @PostMapping
    public ResponseEntity<Metric> createMetric(@RequestBody Metric metric) {
    	logger.info("Received request to create a new metric");
        metric.setValue(1); // Set the default value to 1
        Metric savedMetric = metricRepository.save(metric);
        logger.debug("New metric created with ID: {}", savedMetric.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMetric);
    }
    
 // PUT /metrics/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Metric> updateMetric(@PathVariable Long id, @RequestBody Metric metricToUpdate) {
    	logger.info("Received request to update metric with ID: {}", id);
        return metricRepository.findById(id)
                .map(existingMetric -> {
                    // Only update the fields that are provided in the request body
                    if (metricToUpdate.getSystem() != null) {
                        existingMetric.setSystem(metricToUpdate.getSystem());
                    }
                    if (metricToUpdate.getName() != null) {
                        existingMetric.setName(metricToUpdate.getName());
                    }
                    if (metricToUpdate.getDate() != null) {
                        existingMetric.setDate(metricToUpdate.getDate());
                    }
                    if (metricToUpdate.getValue() != 0) {
                        existingMetric.setValue(metricToUpdate.getValue());
                    }
                    Metric updatedMetric = metricRepository.save(existingMetric);
                    logger.debug("Metric with ID {} updated successfully", id);
                    return ResponseEntity.ok(updatedMetric);
                })
                .orElseGet(() -> {
                    logger.warn("Metric with ID: {} not found for update", id);
                    return ResponseEntity.notFound().build();
                });
    }
    
 //GET/metricsummary
    @GetMapping("/metricsummary")
    public ResponseEntity<MetricSummary> getMetricSummary(
            @RequestParam(required = false) String system,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to
    ) {
    	logger.info("Received request to retrieve metric summary");
        int totalValue = metricRepository.getTotalValueByFilters(system, name, from, to);
        logger.debug("Total value for the metric summary: {}", totalValue);
        MetricSummary summary = new MetricSummary();
        summary.setSystem(system);
        summary.setName(name);
        summary.setFrom(from);
        summary.setTo(to);
        summary.setValue(totalValue);
        return ResponseEntity.ok(summary);
    }

}
