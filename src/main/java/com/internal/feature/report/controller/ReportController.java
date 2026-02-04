package com.internal.feature.report.controller;

import com.internal.feature.report.dto.DashboardStatsDto;
import com.internal.feature.report.dto.LowStockAlertDto;
import com.internal.feature.report.dto.StockStatusDistributionDto;
import com.internal.feature.report.dto.WeeklyStockMovementDto;
import com.internal.feature.report.service.ReportService;
import com.internal.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Report Management", description = "Endpoints for Dashboard Statistics and Reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Get Dashboard Statistics", description = "Returns aggregated counts for total products, stock, low stock, etc.")
    @GetMapping("/dashboard-stats")
    public ResponseEntity<ResponseWrapper<DashboardStatsDto>> getDashboardStats() {
        return ResponseEntity.ok(ResponseWrapper.success(reportService.getDashboardStats(), "Dashboard stats retrieved successfully"));
    }

    @Operation(summary = "Get Stock Status Distribution", description = "Returns percentage distribution for stock statuses (for Pie Chart)")
    @GetMapping("/stock-distribution")
    public ResponseEntity<ResponseWrapper<StockStatusDistributionDto>> getStockStatusDistribution() {
        return ResponseEntity.ok(ResponseWrapper.success(reportService.getStockStatusDistribution(), "Stock status distribution retrieved successfully"));
    }

    @Operation(summary = "Get Weekly Stock Movements", description = "Returns daily stock in/out summations for the last 7 days (for Bar Chart)")
    @GetMapping("/weekly-movements")
    public ResponseEntity<ResponseWrapper<WeeklyStockMovementDto>> getWeeklyStockMovements() {
        return ResponseEntity.ok(ResponseWrapper.success(reportService.getWeeklyStockMovements(), "Weekly stock movements retrieved successfully"));
    }

    @Operation(summary = "Get Low Stock Alerts", description = "Returns list of products with low stock or out of stock status")
    @GetMapping("/alerts")
    public ResponseEntity<ResponseWrapper<List<LowStockAlertDto>>> getLowStockAlerts() {
        return ResponseEntity.ok(ResponseWrapper.success(reportService.getLowStockAlerts(), "Low stock alerts retrieved successfully"));
    }
}
