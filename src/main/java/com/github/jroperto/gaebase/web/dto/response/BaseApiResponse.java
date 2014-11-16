package com.github.jroperto.gaebase.web.dto.response;

import java.util.Date;

public class BaseApiResponse {

    private Date respondedAt = new Date();


    public Date getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(Date respondedAt) {
        this.respondedAt = respondedAt;
    }
}
