package com.dorayd.sports.features.address.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
public class Address {
    @EqualsAndHashCode.Exclude
    private long id;

    private String addressLineOne;
    private String addressLineTwo;
    private String municipality;
    private String province;
}
