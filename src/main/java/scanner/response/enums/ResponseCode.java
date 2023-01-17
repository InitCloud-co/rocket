package scanner.response.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    /* Invalid Request */
    STATUS_4001(4001, HttpStatus.UNAUTHORIZED,"Expired."),
    STATUS_4002(4002, HttpStatus.UNAUTHORIZED,"Invalid Token."),
    STATUS_4003(4003, HttpStatus.BAD_REQUEST,"Invalid Scan Provider."),
    STATUS_4004(4004, HttpStatus.BAD_REQUEST,"Invalid File Format."),
    STATUS_4005(4005, HttpStatus.BAD_REQUEST,"Data missing."),
    STATUS_4007(4007, HttpStatus.BAD_REQUEST,"Invalid Request."),
    /* Server Error. */
    STATUS_5001(5001, HttpStatus.INTERNAL_SERVER_ERROR,"Server busy."),
    STATUS_5100(5100, HttpStatus.INTERNAL_SERVER_ERROR,"Unknown error.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}