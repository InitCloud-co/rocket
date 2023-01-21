package scanner.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.dto.history.report.ScanHistoryDetailDto;
import scanner.dto.history.report.ScanSummaryDto;
import scanner.model.ScanHistory;
import scanner.model.ScanHistoryDetail;
import scanner.repository.ScanHistoryDetailsRepository;
import scanner.repository.ScanHistoryRepository;
import scanner.response.ReportResponse;

@Service
@RequiredArgsConstructor
public class ScanHistoryService {

    private final ScanHistoryRepository scanHistoryRepository;
    private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;

    public List<ScanHistory> retrieveHistoryList(){
        return scanHistoryRepository.findTop10ByOrderByHistorySeqDesc();
    }

    @Transactional
    public ReportResponse retrieveReport(Long reportId){

        ScanHistory history = scanHistoryRepository.findByHistorySeq(reportId);

        List<ScanHistoryDetail> details = scanHistoryDetailsRepository.findByHistorySeq(reportId);

        ScanSummaryDto summaryDto = ScanSummaryDto.toDto(history);

        List<ScanHistoryDetailDto> detailsDto = details.stream()
                                        .map(ScanHistoryDetailDto::toDto)
                                        .collect(Collectors.toList());

        return new ReportResponse(summaryDto, detailsDto);
    }
}
