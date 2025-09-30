package com.booking.sup.dtos;

import lombok.Data;

@Data
public class StatisticsDto {
    private Long totalBookings;

    private Long totalRevenue;

    private Long activeBookings;

    private Double averageCheck;
}
