package scanner.history.dto.history;

import java.time.LocalDateTime;

import lombok.*;
import scanner.history.entity.ScanHistory;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryDto {

	private Long id;
	private String fileName;
	private String fileHash;
	private String provider;
	private LocalDateTime scanDateTime;
	private Double score;

	public HistoryDto(ScanHistory entity) {
		this.id = entity.getId();
		this.fileName = entity.getFileName();
		this.fileHash = entity.getFileHash();
		this.scanDateTime = entity.getCreatedAt();
		this.provider = entity.getCsp();
		this.score = entity.getScore();
	}
}
