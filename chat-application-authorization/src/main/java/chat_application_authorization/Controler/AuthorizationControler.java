package chat_application_authorization.Controler;

import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.logging.log4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AuthorizationDTO.AutorizationRequestDTO;
import AuthorizationDTO.ChangeUserDetailsDTO;
import AuthorizationDTO.TokenDTO;
import chat_application_authorization.jwt.JwtTokenInterface;
import chat_application_commonPart.Authorization.CustomUserDetails;
import chat_application_commonPart.Authorization.HttpServletRequestInetAdress;
import chat_application_commonPart.Logger.Log4j2;
import chat_application_commonPart.PathProperties.AuthorizationPath;
import chat_application_commonPart.Properties.AuthorizationProperties;
import chat_application_commonPart.Validation.AutorizationRequestDTOValidator;
import chat_application_commonPart.Validation.ChangeUserDetailsRequestValidator;
import chat_application_database.AuthorizationEntity.DeviceIdEntity;
import chat_application_database.AuthorizationEntity.DeviceIdEntityRepositoryInterface;
import chat_application_database.AuthorizationEntity.Htpp_Request_Login_Entity;
import chat_application_database.AuthorizationEntity.LoginActivityEntityInterface;
import chat_application_database.AuthorizationEntity.UserEntity;
import chat_application_database.AuthorizationEntity.UserRepositoryInterface;
import chat_application_database.AuthorizationEntity.UserRepositoryInterface.UserFinishAuthorization;

@RestController
@Validated
@RequestMapping(AuthorizationPath.authorizationPreflix)
public class AuthorizationControler {


	@Autowired
	private BCryptPasswordEncoder BCryptEncoder;

	@Autowired
	private AuthorizationProperties autProperties;
	//@Autowired
	//private DeviceIdEntityRepositoryInterface device;
	@Autowired
	private LoginActivityEntityInterface LoginActivity;
	@Autowired
	private HttpServletRequestInetAdress inetAdress;
	 @Autowired
	 private UserRepositoryInterface userRepo;
	 @Autowired
	 private JwtTokenInterface JWTtoken;

	 private Marker marker=Log4j2.LogMarker.Security.getMarker();	
	 
	 public AuthorizationControler() {
	 }
	 
	
	 //RequestAttribute add by interceptor, to use primary key
	@PostMapping(AuthorizationPath.registerPath)
	public ResponseEntity<TokenDTO> register(
		 @RequestBody @Valid @AutorizationRequestDTOValidator AutorizationRequestDTO value,
			HttpServletRequest request,
			@RequestAttribute Htpp_Request_Login_Entity HttpRequestAttribute
			){
		
		
		if(this.userRepo.existsByEmailOrPhoneAndCountryPreflix(value.getEmail(), value.getPhone(), value.getCountryPreflix())) {
			Log4j2.log.info(this.marker,String.format("Registration was not successful, email or phone has been already registred."
					+ System.lineSeparator()+" email %s phone_preflix %s phone %s ", value.getEmail(),value.getCountryPreflix(),value.getPhone()));
	
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}	
		
		
		//email or password is not registred
		UserEntity newEntity=new UserEntity();
		newEntity.setEmail(value.getEmail());
		newEntity.setPhone(value.getPhone());
		newEntity.setCountryPreflix(value.getCountryPreflix());
		newEntity.setUserActive(false);
		
		String HashPassword=this.BCryptEncoder.encode(value.getPassword());
		newEntity.setPassword(HashPassword);
		try {
		this.userRepo.saveAndFlush(newEntity);
		}
		 catch(DataIntegrityViolationException ee) {
				Log4j2.log.warn(this.marker,String.format("Registration was not successful, email or phone has been already registred"
						+ System.lineSeparator()+" email %s phone_preflix %s phone %s ", value.getEmail(),value.getCountryPreflix(),value.getPhone()));
			 return ResponseEntity.status(HttpStatus.CONFLICT).build();		
		 }
		catch(Exception e) {
			Log4j2.log.error(e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
		
		
		Log4j2.log.info(this.marker,String.format("Registration was successful"
				+ System.lineSeparator()+" email %s phone_preflix %s phone %s ", value.getEmail(),value.getCountryPreflix(),value.getPhone()));
		
		TokenDTO token=this.JWTtoken.generateToken(newEntity.getUserId(), newEntity.getVersion(), value.getDeviceID(), false, this.autProperties.TokenValidUntil(), HttpRequestAttribute.getPrimaryKey());
		return ResponseEntity.status(HttpStatus.CREATED).body(token);
		}
	
	@PostMapping(AuthorizationPath.loginPath)
	public ResponseEntity<TokenDTO> login(
			@RequestBody @AutorizationRequestDTOValidator AutorizationRequestDTO value,
			HttpServletRequest request
			){		
		Optional<UserEntity> opPassword=this.userRepo.findByEmailOrPhoneAndCountryPreflix(value.getEmail(), value.getCountryPreflix(), value.getPhone());
		if(opPassword.isEmpty()) {
			Log4j2.log.info(this.marker,String.format("Loggin was not successful, email/phone was not found"
					+ System.lineSeparator()+" email %s phone_preflix %s phone %s ", value.getEmail(),value.getCountryPreflix(),value.getPhone()));
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		
		UserEntity user=opPassword.get();
		
		if(!this.BCryptEncoder.matches(value.getPassword(), user.getPassword())) {
			Log4j2.log.info(this.marker,String.format("Loggin was not successful password was not same"
					+ System.lineSeparator()+" email %s phone_preflix %s phone %s password %s ", value.getEmail(),value.getCountryPreflix(),value.getPhone(),value.getPassword()));
			
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		
		logAuthorization log=this.logDevice(user, value.getDeviceID(), request);
		
		TokenDTO token=this.JWTtoken.generateToken(user.getUserId(), user.getVersion(), value.getDeviceID(), user.isUserActive(), this.autProperties.TokenValidUntil()
				,log.getCurrentLogId());
		
		Log4j2.log.info(this.marker,String.format("Loggin was sucesfull"
				+ System.lineSeparator()+" email %s phone_preflix %s phone %s", value.getEmail(),value.getCountryPreflix(),value.getPhone()));								
		
		return ResponseEntity.ok(
			token);
	}
	
	
	@PatchMapping(AuthorizationPath.finishRegistrationPath)
	public ResponseEntity<TokenDTO> finishRegistration(
			@ChangeUserDetailsRequestValidator ChangeUserDetailsDTO value,
			@AuthenticationPrincipal CustomUserDetails userDetails
			){
		Optional<UserFinishAuthorization>userFinish=this.userRepo.findById(userDetails.getDeviceId());
		
		if(userFinish.isEmpty()) {
			//user by id was not find
			Log4j2.log.error(this.marker,String.format("Finish operation was not sucessfull"+System.lineSeparator()
			+"Id %s was not found in database"),userDetails.getUsername());
			return ResponseEntity.badRequest().build();
		}
		UserFinishAuthorization user=userFinish.get();
		
		UserEntity userEnt=new UserEntity();
		userEnt.setUserId(user.getUserId());
		userEnt.setVersion(user.getVersion());
		userEnt.setBirthDay(value.getBirthDay());
		userEnt.setSerName(value.getSerName());
		userEnt.setLastName(value.getLastName());
		HttpStatus httpStatus;
		try {
			this.userRepo.save(userEnt);
			httpStatus=HttpStatus.OK;
		}
		catch(OptimisticLockException e) {
			//registration was finish from other device before
			//just send notification conflict, but send authorization token as well
			httpStatus=HttpStatus.CONFLICT;
		}
		
		TokenDTO token=this.JWTtoken.generateToken(userEnt.getUserId(), userEnt.getVersion(), userDetails.getDeviceId(), true, this.autProperties.TokenValidUntil(), userDetails.getLogId());
		return ResponseEntity.status(httpStatus).body(token);
	}


}
