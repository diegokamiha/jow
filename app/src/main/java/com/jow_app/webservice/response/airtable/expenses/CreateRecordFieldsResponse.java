package com.jow_app.webservice.response.airtable.expenses;

import java.util.Date;

/**
 * Created by diego on 7/4/17.
 */

public class CreateRecordFieldsResponse {
    private String description;
    private double value;
    private Date data;
    private String account;

    public CreateRecordFieldsResponse(String description, double value, Date data, String account) {
        this.description = description;
        this.value = value;
        this.data = data;
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }}
