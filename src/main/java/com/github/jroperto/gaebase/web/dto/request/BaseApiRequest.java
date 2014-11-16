package com.github.jroperto.gaebase.web.dto.request;

import java.util.Date;

public class BaseApiRequest {

    private Date requestedAt;


    public BaseApiRequest() {
        this.requestedAt = new Date();
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }
}
