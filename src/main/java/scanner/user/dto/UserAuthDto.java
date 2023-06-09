package scanner.user.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.user.enums.RoleType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthDto {

	@Getter
	@NoArgsConstructor
	public static class Authentication extends UserBaseDto {
		private String password;

		public Authentication(String username, String password) {
			super(username, LocalDateTime.now());
			this.password = password;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class Signup extends UserBaseDto {
		private String nickname;
		private String password;
		private String email;
		private String contact;
		private RoleType role;

		public Signup(String username, String nickname, String password, LocalDateTime lastLogin, String email,
			String contact, RoleType role) {
			super(username, lastLogin);
			this.nickname = nickname;
			this.email = email;
			this.contact = contact;
			this.role = role;
			this.password = password;
		}
	}
}
