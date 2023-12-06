package chat_application_common_Part.EndPoint;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import AuthorizationDTO.TokenDTO;
import User.UserProfileDTO.UserProfileRegistrationDTO;
import chat_application_DTO.UserDTO.UserAuthorizationDTO;

public interface AuthorizationEndPoint {

	public static interface AuthorizationEndPointAuthorization{
		public ResponseEntity<TokenDTO> register(@RequestBody @Valid UserAuthorizationDTO 
				userData);

		public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserAuthorizationDTO 
				userData);
	}
	public static interface AuthorizationEndPointFinishAuthorization{

		public ResponseEntity<TokenDTO>finishRegistration(@RequestBody @Valid UserProfileRegistrationDTO user);
	}
}
