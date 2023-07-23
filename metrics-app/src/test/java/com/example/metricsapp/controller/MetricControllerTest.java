package com.example.metricsapp.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.metricsapp.model.Metric;
import com.example.metricsapp.repository.MetricRepository;

public class MetricControllerTest {
	
	@Mock
	private MetricRepository metricRepository;
	
	@InjectMocks
	private MetricController metricController;
	
	private List<Metric> testMetrics;
	
	
	@Test
    public void testGetAllMetrics() {
        // Mock the repository method to return testMetrics
        when(metricRepository.findAllByFilters(anyString(), anyString(), anyLong(), anyLong())).thenReturn(testMetrics);

        // Call the controller method
        ResponseEntity<List<Metric>> response = metricController.getAllMetrics(null, null, null, null);

        // Assert the response
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(testMetrics, response.getBody());
    }
	
	@Test
    public void testGetMetricById() {
        // Mock the repository method to return a specific metric
        Long metricId = 1L;
        Metric metric = testMetrics.get(0);
        when(metricRepository.findById(metricId)).thenReturn(Optional.of(metric));

        // Call the controller method
        ResponseEntity<Metric> response = metricController.getMetricById(metricId);

        // Assert the response
        assertSame(HttpStatus.OK, response.getStatusCode());
        assertSame(metric, response.getBody());
    }

}
