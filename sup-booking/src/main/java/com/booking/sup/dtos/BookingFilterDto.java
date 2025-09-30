package com.booking.sup.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingFilterDto {
    private Long start;

    private Long finish;

    private Long status;
}
