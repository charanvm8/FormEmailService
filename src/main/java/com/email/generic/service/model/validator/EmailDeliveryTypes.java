package com.email.generic.service.model.validator;

public enum EmailDeliveryTypes {

    FORM_EMAIL("form_email");

    private String type;

    EmailDeliveryTypes(String emailType){
        this.type = emailType;
    }

    public String getType(){
        return this.type;
    }

}
