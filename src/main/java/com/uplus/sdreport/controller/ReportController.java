package com.uplus.sdreport.controller;

import com.uplus.sdreport.dto.ReportDto;
import com.uplus.sdreport.service.ReportService;
import com.uplus.sdreport.vo.RequestDeleteContent;
import com.uplus.sdreport.vo.ResponseReport;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/report-service")
public class ReportController {

    ReportService reportService;
    private Environment env;


    @Autowired
    public ReportController(ReportService reportService, Environment env) {
        this.reportService = reportService;
        this.env = env;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "report-welcome";
    }

    @GetMapping("/")
    public String reportHome(){
        return "home";
    }



    @GetMapping("/report")
    public String viewReport(Model model){

        Iterable<ReportDto> reportDto = reportService.getReportAll();

        List<ResponseReport> result = new ArrayList<>();
        reportDto.forEach( v -> {
            result.add(new ModelMapper().map(v, ResponseReport.class));
        });
        model.addAttribute("reports", result);
        setEnvModel(model);

        return "content/viewReport";
    }


    @GetMapping("/{contentId}/report")
    public ModelAndView reportContent(@PathVariable String contentId){
        ModelAndView mav= new ModelAndView("content/reportContent");

        ReportDto reportDto = reportService.getReportByContentId(contentId);


        mav.addObject("contentName", reportDto.getContentName());
        mav.addObject("cnt", reportDto.getCnt());

        mav.addObject("server_address", env.getProperty("eureka.instance.hostname"));
        mav.addObject("server_port", env.getProperty("local.server.port"));
        mav.addObject("server_service", env.getProperty("spring.application.name"));

        return mav;
    }

    @PostMapping("/report/deleteContent")
    public ResponseEntity<ResponseReport> deleteReport(@RequestBody RequestDeleteContent requestDeleteContent){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        reportService.deleteByContentId(requestDeleteContent.getContentId());

        ResponseReport responseReport = mapper.map(requestDeleteContent, ResponseReport.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseReport);

    }

    public void setEnvModel(Model model){
        model.addAttribute("server_address", env.getProperty("eureka.instance.hostname"));
        model.addAttribute("server_port", env.getProperty("local.server.port"));
        model.addAttribute("server_service", env.getProperty("spring.application.name"));
    }

}
