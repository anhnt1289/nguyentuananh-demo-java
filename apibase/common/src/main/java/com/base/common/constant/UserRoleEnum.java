package com.base.common.constant;

/**
 * @author : AnhNT
 * @since : 31/11/2022, Mon
 */
public enum UserRoleEnum {

    ADMINISTRATOR(DefaultValue.ZERO_INT, "ADMINISTRATOR"),
    ADMIN(DefaultValue.ONE_INT, "ADMIN"),
    USER(DefaultValue.TWO_INT, "USER"),
    NONE(DefaultValue.NEGATIVE_INT, "");

    private int id;
    private String name;

    UserRoleEnum(int id, String name){
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public static UserRoleEnum getUserRole(int id) {
        switch (id){
            case DefaultValue.ZERO_INT:
                return ADMINISTRATOR;
            case DefaultValue.ONE_INT:
                return ADMIN;
            case DefaultValue.TWO_INT:
                return USER;
            default:
                return NONE;
        }
    }
}
