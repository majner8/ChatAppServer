package authorization.Security;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import AuthorizationDTO.TokenDTO;
import chat_application_commonPart.Config.DurationService;
import chat_application_commonPart.Config.SecurityConfiguration;
import chat_application_common_Part.Security.SecurityProperties;
import database.User.UserEntity;
import io.jsonwebtoken.UnsupportedJwtException;

public interface jwtToken {	
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


