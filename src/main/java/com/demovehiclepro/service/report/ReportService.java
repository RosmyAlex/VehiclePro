package com.demovehiclepro.service.report;

import com.demovehiclepro.dtos.GenReportDTO;
import org.json.JSONException;
import org.json.JSONObject;

public interface ReportService {
    JSONObject genReport(GenReportDTO genReportDTO) throws JSONException;
}
