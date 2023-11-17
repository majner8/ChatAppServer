package authorization.Controler;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import AuthorizationDTO.TokenDTO;
import User.UserProfileDTO.UserProfileRegistrationDTO;
import chat_application_DTO.UserDTO.UserAuthorizationDTO;
import chat_application_authorization.jwt.JwtTokenInterface;
import chat_application_commonPart.Authorization.HttpServletRequestInetAdress;
import chat_application_commonPart.Config.Durations;
import chat_application_commonPart.Logger.Log4j2;
import chat_application_commonPart.httpEndPointPath.AuthorizationPath;
import database.Authorization.deviceIdGenerationRepository;
import database.User.UserAuthEntityRepository;
import database.User.UserEntity;
import database.User.UserEntityRepository;

@RequestMapping(AuthorizationPath.authorizationPreflix)
public class AuthorizationControler {
	@Autowired
	private JwtTokenInterface jwtToken;
	@Autowired
	private deviceIdGenerationRepository IdGeneration;
	@Autowired
	private HttpServletRequestInetAdress inetAdress;
	@Autowired
	private AuthorizationService autorizationService;
	@Autowired
	private Durations duration;
	
	/**Metod reuturn device ID token, have to be send with every request */
	@GetMapping(AuthorizationPath.deviceIdPath)
	public ResponseEntity<String> getDeviceIDToken(HttpServletRequest request){
		String id=this.IdGeneration.deviceIdGeneration();
		String token=this.jwtToken.generateDeviceToken(this.duration.getDeviceToken(),id);
		
		return ResponseEntity.ok(token);
		
	}
	
	public ResponseEntity<TokenDTO> register(@RequestBody @Valid UserAuthorizationDTO 
			userData,
			@RequestAttribute String deviceID){
		
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
		UserEntity ent=this.autorizationService.getSavedThreadLocalUserEntity();
		
		TokenDTO token=this.jwtToken.generateAuthorizationToken(this.duration.getUserAuthorizationToken(), ent.getUserId(), deviceID, false, ent, ent.getVersion());
		
		
		return ResponseEntity.ok(token);
		
	}
	
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserAuthorizationDTO 
			userData,
			@RequestAttribute String deviceID){
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
		UserEntity ent=this.autorizationService.getSavedThreadLocalUserEntity();
		TokenDTO token=
				this.jwtToken.generateAuthorizationToken(this.duration.getUserAuthorizationToken(), ent.getUserId(), deviceID, ent.isUserActive(), ent, ent.get)
				this.jwtToken.generateAuthorizationToken(this.duration.getd, 0, deviceID, false, ent, 0)
				0, deviceID, false, null, 0, 0)
				
		//generate token
		return null;	
	}
	public ResponseEntity<TokenDTO>finishRegistration(@RequestBody @Valid UserProfileRegistrationDTO user,
			@RequestAttribute UserEntity userEnt	){
		HttpStatus status;
		try {
			this.autorizationService.FinishRegistration(user, userEnt);
			status=HttpStatus.OK;
			Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"User finish registration");

		}
		catch(OptimisticLockException e) {
			//conflict, user finish registration from diferent device
			//just inform user
			status=HttpStatus.CONFLICT;
			Log4j2.log.info(Log4j2.MarkerLog.Authorization.getMarker(),"FinishRegistratrion task was not sucesfull, registration was finished before");
		}
		Log4j2.log.debug(Log4j2.MarkerLog.Authorization.getMarker(),"I am generating fully authorizated token");

		// generate Token
		return null;
	}
}

