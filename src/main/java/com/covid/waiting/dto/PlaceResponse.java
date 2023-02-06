package com.covid.waiting.dto;

import com.covid.waiting.constant.PlaceType;

public record PlaceResponse(
        Long id,
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {

    public static PlaceResponse of(
            Long id,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceResponse(id, placeType, placeName, address, phoneNumber, capacity, memo);
    }

    public static PlaceResponse from(PlaceDTO placeDto) {
        if (placeDto == null) { return null; }
        return PlaceResponse.of(
                placeDto.id(),
                placeDto.placeType(),
                placeDto.placeName(),
                placeDto.address(),
                placeDto.phoneNumber(),
                placeDto.capacity(),
                placeDto.memo()
        );
    }
}
