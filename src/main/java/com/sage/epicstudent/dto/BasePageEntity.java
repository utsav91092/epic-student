package com.sage.epicstudent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasePageEntity {
    private long totalPage;
    private long pageNumber;
    private int pageSize;
    private long totalElements;
}
