package authorization.Security;


import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.interfaces.DecodedJWT;

import AuthorizationDTO.TokenDTO;
import database.User.UserEntity;

public interface jwtToken {
	public static final String authoriClainName="Authority";
	public static interface jwtTokenGeneratorInterface {

		public TokenDTO generateAuthorizationToken(
				String deviceID,
				UserEntity userEntity);
		
		/**Metod generate sign device token.
		 * @return sign token, with preflix
		 *  */
		public String generateDeviceToken(
				String deviceID);
			}
	public static interface jwtTokenValidationInterface{
		
		public DecodedJWT jwtTokenDeviceIDTokenValidator(HttpServletRequest request);
		
		public DecodedJWT jwtTokenAuthorizationUserTokenValidator(HttpServletRequest request);
		}
	
}


