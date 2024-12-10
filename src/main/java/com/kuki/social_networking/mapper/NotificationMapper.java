package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Notification;
import com.kuki.social_networking.response.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "content", target = "content"),
        @Mapping(source = "isRead", target = "isRead"),
        @Mapping(source = "creationDate", target = "creationDate"),
        @Mapping(source = "url", target = "url"),
    })
    NotificationResponse toNotificationResponse(Notification notification);

}
