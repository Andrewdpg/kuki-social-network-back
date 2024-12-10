package com.kuki.social_networking.response;

import lombok.Data;

@Data
public class NotificationResponse {
        private String id;
        private String content;
        private boolean isRead;
        private String creationDate;
        private String url;

        public boolean getIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }
}
