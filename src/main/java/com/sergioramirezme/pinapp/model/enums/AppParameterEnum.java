package com.sergioramirezme.pinapp.model.enums;

import lombok.Getter;

@Getter
public enum AppParameterEnum {
    GLOBAL_LONGEVITY_NAME("LONGEVIDAD_GLOBAL");

    private String value;

    AppParameterEnum(String value) {
        this.value = value;
    }
}
