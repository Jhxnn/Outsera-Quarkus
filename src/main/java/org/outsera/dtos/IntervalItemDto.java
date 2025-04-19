package org.outsera.dtos;

public record IntervalItemDto(String producer, int interval, int previousWin, int followingWin) {
}
