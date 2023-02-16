package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import scanner.dto.enums.ResponseCode;

@AllArgsConstructor
@Getter
public class ExceptionDto {
	private final int code;
	private final String message;

	@Builder
	public ExceptionDto(ResponseCode res) {
		this.code = res.getCode();
		this.message = res.getMessage();
	}
}
