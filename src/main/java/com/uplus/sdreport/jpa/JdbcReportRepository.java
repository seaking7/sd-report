package com.uplus.sdreport.jpa;

import com.uplus.sdreport.dto.ReportDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JdbcReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReportRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<ReportDto> findByContentId(String contentId) {
        List<ReportDto> result
                = jdbcTemplate.query("select content_id, content_name, count(*) cnt from executeLog where content_id = ?",
                reportDtoRowMapper(),
                contentId);
        return result.stream().findAny();
    }



    public Iterable<ReportDto> findReportAll(){
        List<ReportDto> result
                = jdbcTemplate.query("select content_id, content_name, count(*) cnt from executeLog group by content_id order by content_id limit 10",
                reportDtoRowMapper());
        return result;
    }


    public void deleteByContentId(String contentId){
        jdbcTemplate.update("delete from executeLog where content_id = ?", contentId);
    }

    private RowMapper<ReportDto> reportDtoRowMapper() {
        return (rs, rowNum) -> {
            ReportDto reportDto = new ReportDto();
            reportDto.setContentId(rs.getString("content_id"));
            reportDto.setContentName(rs.getString("content_name"));
            reportDto.setCnt(rs.getString("cnt"));

            return reportDto;
        };
    }

}
