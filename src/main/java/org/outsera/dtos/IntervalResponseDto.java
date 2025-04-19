package org.outsera.dtos;

import java.util.List;

public record IntervalResponseDto(List<IntervalItemDto> min, List<IntervalItemDto> max) {
}
