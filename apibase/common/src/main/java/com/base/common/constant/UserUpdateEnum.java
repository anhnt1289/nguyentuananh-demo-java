package com.base.common.constant;
/**
 * @author : AnhNT
 * @since : 14/11/2022, Mon
 */
public enum UserUpdateEnum {

    SUCCESS(DefaultValue.ZERO_INT),
    EMAIL_EXIST(DefaultValue.ONE_INT),
    ROLE_PERMISSION(DefaultValue.TWO_INT);

    private int value;

    UserUpdateEnum(int value){
        this.value = value;
    }

    public int intValue(){
        return value;
    }
}
