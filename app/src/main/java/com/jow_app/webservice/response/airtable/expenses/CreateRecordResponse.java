package com.jow_app.webservice.response.airtable.expenses;

import java.util.Date;

/**
 * Created by diego on 7/4/17.
 */

public class CreateRecordResponse {
    private String id;
    private CreateRecordFieldsResponse fields;
    private Date createdTime;

    public CreateRecordResponse(String id, CreateRecordFieldsResponse fields, Date createdTime) {
        this.id = id;
        this.fields = fields;
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CreateRecordFieldsResponse getFields() {
        return fields;
    }

    public void setFields(CreateRecordFieldsResponse fields) {
        this.fields = fields;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
