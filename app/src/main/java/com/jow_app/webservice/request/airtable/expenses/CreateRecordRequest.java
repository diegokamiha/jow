package com.jow_app.webservice.request.airtable.expenses;

/**
 * Created by diego on 7/4/17.
 */

public class CreateRecordRequest {

    private CreateRecordFieldsRequest fields;

    public CreateRecordRequest(CreateRecordFieldsRequest fields) {
        this.fields = fields;
    }

    public CreateRecordFieldsRequest getFields() {
        return fields;
    }

    public void setFields(CreateRecordFieldsRequest fields) {
        this.fields = fields;
    }
}
