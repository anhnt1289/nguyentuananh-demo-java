package com.base.common.constant;
/**
 * @author : AnhNT
 * @since : 14/11/2022, Mon
 */
public enum UserStatusEnum {

    DEACTIVATE(DefaultValue.ZERO_INT),
    ACTIVATE(DefaultValue.ONE_INT),
    DELETE(DefaultValue.NEGATIVE_INT);

    private int value;

    UserStatusEnum(int value){
        this.value = value;
    }

    public int intValue(){
        return value;
    }
}
