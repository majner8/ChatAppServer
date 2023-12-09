package authorization.Controler;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import AuthorizationDTO.TokenDTO;
import User.UserProfileDTO.UserProfileRegistrationDTO;
import authorization.Authorization_RequestScope_UserEntity;
import authorization.Security.jwtToken;
import chat_application_DTO.UserDTO.UserAuthorizationDTO;
import chat_application_commonPart.Logger.Log4j2;
import chat_application_commonPart.httpEndPointPath.AuthorizationPath;
import chat_application_common_Part.EndPoint.AuthorizationEndPoint;
import chat_application_common_Part.Security.CustomSecurityContextHolder;
import database.Exception.UserWasNotFoundInDatabaseException;

@RequestMapping(AuthorizationPath.authorizationPreflix)
public class AuthorizationControler {
	
	public static class AuthorizationControlerAuthorization implements AuthorizationEndPoint.AuthorizationEndPointAuthorization{
	
		@Autowired
		private AuthorizationService.AuthorizationServiceAuthorizationProcess autorizationService;
		@Autowired
		private Authorization_RequestScope_UserEntity userEntity;
		@Autowired
		private jwtToken.jwtTokenGeneratorInterface jwtToken;
		
		@PostMapping(AuthorizationPath.registerPath)
		public ResponseEntity<TokenDTO> register(UserAuthorizationDTO 
				userData){
			String deviceID=CustomSecurityContextHolder.getCustomSecurityContext().getDeviceID();
			if(this.autorizationService.doesUserExist(userData.getProfile(),false)) {
				//userExist
				Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"Email or Phone has been registred yet");
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
						
			}
		
			try {
				this.autorizationService.register(userData.getProfile(),userData.getPassword());
			}
			catch(DataIntegrityViolationException e) {
				//email or password was created
				Log4j2.log.warn(Log4j2.MarkerLog.Authorization.getMarker(),"Email or Phone has been registred yet");
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
			Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"User was registred");
			TokenDTO token=this.jwtToken.generateAuthorizationToken(deviceID, this.userEntity.getUserEntity());
			
			return ResponseEntity.ok(token);
			
		}
		
		public ResponseEntity<TokenDTO> login(UserAuthorizationDTO 
				userData){
			String deviceID=CustomSecurityContextHolder.getCustomSecurityContext().getDeviceID();
			if(!this.autorizationService.doesUserExist(userData.getProfile(), true)) {
				//email/phone has been registred
				Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"Login was not sucessfull, email/phone were incorecct, user was not found");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			if(!this.autorizationService.login(userData.getPassword())) {
				//incorect password
				Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"Login was not sucessfull, email/phone or password were incorecct");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			TokenDTO token=this.jwtToken.generateAuthorizationToken(deviceID, this.userEntity.getUserEntity());
			Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),
					"Login was sucesfull");
			return ResponseEntity.ok(token);
			
		}
	}
	
	public static class AuthorizationControlerFinishAuthorization implements AuthorizationEndPoint.AuthorizationEndPointFinishAuthorization{
		@Autowired
		private AuthorizationService.AuthorizationServiceFinishAuthorization autorizationService;
		@Autowired
		private jwtToken.jwtTokenGeneratorInterface jwtToken;
		@Autowired
		private Authorization_RequestScope_UserEntity RequestScopeUserEntity;
		
		public ResponseEntity<TokenDTO>finishRegistration(UserProfileRegistrationDTO user){
			HttpStatus status;
			String deviceID=CustomSecurityContextHolder.getCustomSecurityContext().getDeviceID();
			long userID=CustomSecurityContextHolder.getCustomSecurityContext().getUserID();
			try {
				this.autorizationService.FinishRegistration(user,userID);
				status=HttpStatus.OK;
				Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"User finish registration");

			}
			catch(OptimisticLockException e) {
				//conflict, user finish registration from diferent device
				//just inform user
				status=HttpStatus.CONFLICT;
				Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"FinishRegistratrion task was not sucesfull, registration was finished before");
			} catch (UserWasNotFoundInDatabaseException e) {
				Log4j2.log.warn(Log4j2.MarkerLog.Database.getMarker(),"User not found with ID: during finish registeration process");
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: "+userID+System.lineSeparator()
	            +"Try make register again, if problem persist contact administrator");
			}
			
			Log4j2.log.debug(Log4j2.MarkerLog.Authorization.getMarker(),"I am generating fully authorizated token");
			TokenDTO token=
					this.jwtToken.generateAuthorizationToken(deviceID, this.RequestScopeUserEntity.getUserEntity());
			return ResponseEntity.status(status)
					.body(token);
			
			}
	}
	
	

}

