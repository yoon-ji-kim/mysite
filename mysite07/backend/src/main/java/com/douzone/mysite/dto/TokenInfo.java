package com.douzone.mysite.dto;

import java.util.ArrayList;

import lombok.Builder;
import lombok.ToString;

@ToString
public class TokenInfo {
	private final int status;
    private final String message;
    private final Integer size;
    private final ArrayList<?> items;

    @Builder
    public TokenInfo(int status, String message, Integer size, ArrayList<?> items) {
        this.status = status;
        this.message = message;
        this.size = size;
        this.items = items;
    }
}
