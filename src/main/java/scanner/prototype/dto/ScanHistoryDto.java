package scanner.prototype.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.ScanHistory;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanHistoryDto {

    private Long id;

    public static ScanHistoryDto toDto(final ScanHistory entity) {
        return ScanHistoryDto.builder()
                .id(entity.getId())
                .build();
    }

    public static ScanHistory toEntity(final ScanHistoryDto dto){
        return ScanHistory.builder()
                .id(dto.getId())
                .build();
    }
}