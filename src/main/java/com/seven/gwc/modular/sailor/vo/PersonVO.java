package com.seven.gwc.modular.sailor.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class PersonVO implements Serializable {

    private String id;

    private String personName;

    private String lawCode;

}
