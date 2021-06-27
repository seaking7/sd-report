package com.uplus.sdreport.service;


import com.uplus.sdreport.dto.ReportDto;
import com.uplus.sdreport.vo.ResponseContent;

public interface ReportService {

    ResponseContent getReportByContentId(String contentId);

    Iterable<ReportDto> getReportAll();

    void deleteByContentId(String contentId);

}
