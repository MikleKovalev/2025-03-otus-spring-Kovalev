package com.booking.sup.controllers;

import com.booking.sup.dtos.StatisticsDto;
import com.booking.sup.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StatisticsController {
    private final BookingService bookingService;

    @GetMapping("/api/admin/statistics")
    public StatisticsDto getStatistics() {
        var dto = new StatisticsDto();
        dto.setTotalBookings(bookingService.getTotalBookingsCount());
        dto.setTotalRevenue(bookingService.getTotalRevenue());
        dto.setActiveBookings(bookingService.getActiveBookingsCount());
        dto.setAverageCheck(bookingService.getAverageCheck());
        return dto;
    }
}
