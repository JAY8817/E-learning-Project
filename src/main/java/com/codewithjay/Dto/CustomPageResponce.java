package com.codewithjay.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResponce<T> {

    public int pagesize;
    public int pagenumber;
    public long totalElements;
    public long totalPages;
    public boolean isLast;

    private List<T> content;


}
