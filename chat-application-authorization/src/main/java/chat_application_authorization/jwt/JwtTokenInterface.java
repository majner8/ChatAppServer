package chat_application_authorization.jwt;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import AuthorizationDTO.TokenDTO;
import chat_application_commonPart.Config.SecurityConfiguration;
import database.User.UserEntity;

public interface JwtTokenInterface {


	default TokenDTO generateAuthorizationToken(
			Duration tokenDuration,
			String deviceID,
			UserEntity userEntity) {
		Calendar validUntil=Calendar.getInstance();
		validUntil.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
		validUntil.setTime(new Date());
		int minute=(int)Math.floor(tokenDuration.getSeconds()/60);

		validUntil.add(Calendar.MINUTE, minute);
		validUntil.add(Calendar.SECOND, (int)(tokenDuration.getSeconds()-minute*60));

		JWTCreator.Builder jwtBuilder= 
				JWT.create()
				.withSubject(String.valueOf(userEntity.getUserId()))
				.withIssuedAt(new Date())
				.withClaim(SecurityConfiguration.DeviceIdClaimName, deviceID)
				.withClaim(SecurityConfiguration.VersionClaimName,userEntity.getVersion())
				.withClaim(SecurityConfiguration.userIsActiveClaimName, userEntity.isUserActive())
				
				.withExpiresAt(validUntil.getTime());

		if(!userEntity.isUserActive()) {
			//add userEntity to finish registration
			//user Entity is just as map
			jwtBuilder.withClaim(SecurityConfiguration.userEntityClaimName,userEntity.getValues());
		}
		String jwtToken=jwtBuilder		
				.sign(Algorithm.HMAC512
				(SecurityConfiguration.hashTokenPassword));

		TokenDTO token=new TokenDTO();
		token.setUserActive(userEntity.isUserActive());
		token.setValidUntil(validUntil.getTime());
		token.setToken(jwtToken);
		return token;
	}

	default TokenDTO generateAuthorizationTokens(
			Duration tokenDuration,long userID,
			String deviceID,
			boolean isUserActive,
			//just for non activce user
			UserEntity userEntity,
			//not necessary, because activity is managed during WebSocket lifecycle 
			//long loginId,
			//verison using during password setting e.t.c
			//when user change password or any security dat
			//new token have to be generated
			long version) {
		Calendar validUntil=Calendar.getInstance();
		validUntil.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
		validUntil.setTime(new Date());
		int minute=(int)Math.floor(tokenDuration.getSeconds()/60);

		validUntil.add(Calendar.MINUTE, minute);
		validUntil.add(Calendar.SECOND, (int)(tokenDuration.getSeconds()-minute*60));

		JWTCreator.Builder jwtBuilder= 
				JWT.create()
				.withSubject(String.valueOf(userID))
				.withIssuedAt(new Date())
				.withClaim(SecurityConfiguration.DeviceIdClaimName, deviceID)
				.withClaim(SecurityConfiguration.VersionClaimName,version)
				.withClaim(SecurityConfiguration.userIsActiveClaimName, isUserActive)
				
				.withExpiresAt(validUntil.getTime());

		if(!isUserActive) {
			//add userEntity to finish registration
			//user Entity is just as map
			jwtBuilder.withClaim(SecurityConfiguration.userEntityClaimName,userEntity.getValues());
		}
		String jwtToken=jwtBuilder		
				.sign(Algorithm.HMAC512
				(SecurityConfiguration.hashTokenPassword));

		TokenDTO token=new TokenDTO();
		token.setUserActive(isUserActive);
		token.setValidUntil(validUntil.getTime());
		token.setToken(jwtToken);
		return token;
	}
	/**Metod generate sign device token.
	 * @return sign token, with preflix
	 *  */
	default String generateDeviceToken(Duration tokenDuration,
			String deviceID) {
		Calendar validUntil=Calendar.getInstance();
		validUntil.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
		validUntil.setTime(new Date());
		int minute=(int)Math.floor(tokenDuration.getSeconds()/60);

		validUntil.add(Calendar.MINUTE, minute);
		validUntil.add(Calendar.SECOND, (int)(tokenDuration.getSeconds()-minute*60));

		return JWT.create()
				.withSubject(deviceID)
				.withIssuedAt(new Date())
				.withExpiresAt(validUntil.getTime())
				.sign(Algorithm.HMAC512(SecurityConfiguration.hashTokenPassword));
	
	}
	
}
