package com.covid.waiting.service;

import com.covid.waiting.constant.ErrorCode;
import com.covid.waiting.dto.PlaceDTO;
import com.covid.waiting.exception.GeneralException;
import com.covid.waiting.repository.PlaceRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<PlaceDTO> getPlaces(Predicate predicate) {
        try {
            return StreamSupport.stream(placeRepository.findAll(predicate).spliterator(), false)
                    .map(PlaceDTO::of)
                    .toList();
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<PlaceDTO> getPlace(Long placeId) {
        try {
            return placeRepository.findById(placeId).map(PlaceDTO::of);
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createPlace(PlaceDTO placeDTO) {
        try {
            if (placeDTO == null) {
                return false;
            }
            placeRepository.save(placeDTO.toEntity());
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyPlace(Long placeId, PlaceDTO dto) {
        try {
            if (placeId == null || dto == null) {
                return false;
            }

            placeRepository.findById(placeId)
                    .ifPresent(place -> placeRepository.save(dto.updateEntity(place)));

            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removePlace(Long placeId) {
        try {
            if (placeId == null) {
                return false;
            }

            placeRepository.deleteById(placeId);
            return true;
        } catch (Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }
}
