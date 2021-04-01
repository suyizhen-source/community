package com.syz.community.service;

import com.github.pagehelper.PageInfo;
import com.syz.community.dto.PaginationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationService {

    public PaginationDTO setPaginationDTO(PageInfo pageInfo, List data, int pageNo) {
        int totalPage = (int) pageInfo.getPages();
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setData(data);
        paginationDTO.setShowPrevious(pageInfo.isHasPreviousPage());
        paginationDTO.setShowFirstPage(!(pageInfo.isIsFirstPage()));
        paginationDTO.setShowNext(pageInfo.isHasNextPage());
        paginationDTO.setShowEndPage(!(pageInfo.isIsLastPage()));
        paginationDTO.setPagination(totalPage, pageNo);
        return paginationDTO;
    }

}
