package com.main.api.model;

import lombok.Data;

/**
 * @author : AnhNT
 * @since : 06/07/2021, Tue
 */
@Data
public class BlackListRedis {
    private Long id;
    private String token;
    private String ip;
    private String userName;
}
