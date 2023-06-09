package scanner.history.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import scanner.checklist.entity.ComplianceEng;
import scanner.checklist.entity.ComplianceKor;
import scanner.checklist.entity.Tag;
import scanner.checklist.entity.UsedRule;
import scanner.scan.dto.ScanDto;
import scanner.common.entity.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SCAN_HISTORY_DETAIL")
public class ScanHistoryDetail extends BaseEntity {

	@Id
	@Column(name = "HISTORY_DETAIL_ID", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@OneToMany(mappedBy = "rule")
	private List<Tag> tags = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "rule")
	private List<ComplianceEng> complianceEngs = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "rule")
	private List<ComplianceKor> complianceKors = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "rule")
	private List<ScanHistoryDetail> historyDetails = new ArrayList<>();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rule_id", updatable = false)
	private UsedRule rule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "history_id", updatable = false)
	private ScanHistory history;

	@Column(name = "RESOURCE")
	@NotNull
	private String resource;

	@Column(name = "RESOURCE_NAME")
	@NotNull
	private String resourceName;

	@Column(name = "SCAN_RESULT")
	@NotNull
	@Size(max = 8)
	private String scanResult;

	@Column(name = "TARGET_FILE")
	@NotNull
	private String targetFile;

	@Column(name = "LINE")
	@NotNull
	@Size(max = 8)
	private String line;

	@Column(name = "CODE")
	@NotNull
	private String code;

	@Builder
	public ScanHistoryDetail(UsedRule rule, ScanHistory history, String resource, String resourceName,
		String scanResult, String targetFile, String line, String code) {
		this.rule = rule;
		this.history = history;
		this.resource = resource;
		this.resourceName = resourceName;
		this.scanResult = scanResult;
		this.targetFile = targetFile;
		this.line = line;
		this.code = code;
	}

	public static ScanHistoryDetail toEntity(final ScanDto.Result dto, UsedRule rule, ScanHistory history) {
		return ScanHistoryDetail.builder()
			.rule(rule)
			.history(history)
			.resource(dto.getTargetResource())
			.resourceName(dto.getTargetResource())
			.scanResult(dto.getStatus())
			.targetFile(dto.getTargetFile())
			.line(dto.getLines())
			.code(dto.getDetail())
			.build();
	}
}
