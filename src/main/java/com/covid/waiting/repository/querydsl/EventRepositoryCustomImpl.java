package com.covid.waiting.repository.querydsl;

import com.covid.waiting.constant.ErrorCode;
import com.covid.waiting.constant.EventStatus;
import com.covid.waiting.domain.Event;
import com.covid.waiting.domain.QEvent;
import com.covid.waiting.dto.EventViewResponse;
import com.covid.waiting.exception.GeneralException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventRepositoryCustomImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {

    public EventRepositoryCustomImpl(Class<?> domainClass) {
        super(Event.class);
    }

    @Override
    public Page<EventViewResponse> findEventViewPageBySearchParams(
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {
        QEvent event = QEvent.event;

        JPQLQuery<EventViewResponse> query = from(event)
                .select(Projections.constructor(
                        EventViewResponse.class,
                        event.id,
                        event.place.placeName,
                        event.eventName,
                        event.eventStatus,
                        event.eventStartDatetime,
                        event.eventEndDatetime,
                        event.currentNumberOfPeople,
                        event.capacity,
                        event.memo
                ));

        if (placeName != null && !placeName.isBlank()) {
            query.where(event.place.placeName.contains(placeName));
        }
        if (eventName != null && !eventName.isBlank()) {
            query.where(event.eventName.contains(eventName));
        }
        if (eventStatus != null) {
            query.where(event.eventStatus.eq(eventStatus));
        }
        if (eventStartDatetime != null) {
            query.where(event.eventStartDatetime.goe(eventStartDatetime));
        }
        if (eventEndDatetime != null) {
            query.where(event.eventEndDatetime.loe(eventEndDatetime));
        }

        List<EventViewResponse> events = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(ErrorCode.DATA_ACCESS_ERROR, "Spring Data JPA 로부터 Querydsl 인스턴스를 가져올 수 없다."))
                .applyPagination(pageable, query).fetch();

        return new PageImpl<>(events, pageable, query.fetchCount());
    }
}
