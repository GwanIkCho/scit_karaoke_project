package com.app.karaoke.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PagenationDTO<T> {
    private List<T> content;  // 현재 페이지의 데이터
    private int page;         // 현재 페이지 번호
    private int size;         // 한 페이지에 보여줄 데이터 수
    private long totalElements;  // 전체 데이터 개수
    private int totalPages;      // 전체 페이지 수

}
