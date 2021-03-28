package com.syz.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ページネーションDTO
 * */


@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer pageNo;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalPage, Integer pageNo) {
        this.totalPage = totalPage;
        this.pageNo = pageNo;

        pages.add(pageNo);
        for (int i = 1; i <= 3; i++) {
            if (pageNo - i > 0) {
                pages.add(0, pageNo - i);
            }

            if (pageNo + i <= totalPage) {
                pages.add(pageNo + i);
            }
        }
    }
}
