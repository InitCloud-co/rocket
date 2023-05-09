package scanner.user.service;

import scanner.user.dto.UserAuthDto;
import scanner.user.dto.UserBaseDto;
import scanner.security.dto.Token;
import scanner.user.entity.User;

public interface UserService {

	Token signup(UserAuthDto.Signup dto);

	Token signin(UserAuthDto.Authentication dto);

	User getCurrentUser();

	void updateLastLogin(UserBaseDto user);
}
