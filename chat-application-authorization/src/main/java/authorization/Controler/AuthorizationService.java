package authorization.Controler;

import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import User.UserProfileDTO.UserProfileRegistrationDTO;
import authorization.Authorization_RequestScope_UserEntity;
import chat_application_DTO.UserDTO.UserAuthPasswordDTO;
import chat_application_DTO.UserDTO.UserComunicationDTO;
import chat_application_common_Part.Security.CustomSecurityContextHolder;
import database.Exception.UserWasNotFoundInDatabaseException;
import database.User.UserAuthEntity;
import database.User.UserAuthEntityRepository;
import database.User.UserEntity;
import database.User.UserEntityRepository;

public class AuthorizationService{
	@Service
	public static class AuthorizationServiceAuthorizationProcess{
		@Autowired
		private UserEntityRepository userEntityRepo;
		@Autowired
		private UserAuthEntityRepository passwordRepo;
		@Autowired
		private BCryptPasswordEncoder BCryptEncoder;
		@Autowired
		private Authorization_RequestScope_UserEntity RequestUserEntity;
		
		/**Metod verify if user exist in database and return appropriate boolen value
		 * @param processLoginRequest- if value is true metod does not call metod existsBy..
		 * Instead, it call metod findBy... and save returned value to threadLocalValue, if returned Optional would be empty
		 * metod return false */
		public boolean doesUserExist(UserComunicationDTO user,boolean processLoginRequest) {
			if(!processLoginRequest)return this.userEntityRepo.existsByEmailOrPhoneAndCountryPreflix(user.getEmail(),user.getPhone(),user.getPhonePreflix());
			Optional<UserEntity> opt=this.userEntityRepo.findByEmailOrPhoneAndCountryPreflix(user.getEmail(), user.getPhone(), user.getPhonePreflix());
			if(opt.isEmpty()) {
				return false;
			}
			this.RequestUserEntity.setUserEntity(opt.get());
			return true;

		
		}
		@Transactional
		public void register(UserComunicationDTO user,UserAuthPasswordDTO password) {
			
			UserEntity userEnt=new UserEntity();
			userEnt.setEmail(user.getEmail());
			userEnt.setPhone(user.getPhone());
			userEnt.setCountryPreflix(user.getPhonePreflix());
			this.userEntityRepo.save(userEnt);	
			this.RequestUserEntity.setUserEntity(userEnt);
			UserAuthEntity autUser=new UserAuthEntity();
			autUser.setUserId(userEnt.getUserId());
			autUser.setPassword(this.BCryptEncoder.encode(password.getPassword()));
			this.passwordRepo.save(autUser);

		}
		
		/**Metod compare sent password and saved password in database */
		public boolean login(UserAuthPasswordDTO password) {
			long userID=this.RequestUserEntity.getUserEntity().getUserId();
			Optional <UserAuthEntity> user=this.passwordRepo.findById(userID);
				if(user.isEmpty()) {
					throw new NullPointerException("user id: "+userID+" was not found in password database");
				}
			return this.BCryptEncoder.matches(password.getPassword(),user.get().getPassword());
		}
		
	}
	@Service
	public static class AuthorizationServiceFinishAuthorization{
		@Autowired
		private UserEntityRepository userEntityRepo;
		@Autowired
		private Authorization_RequestScope_UserEntity RequestUserEntity;
		

		public void FinishRegistration(UserProfileRegistrationDTO user,long userID) throws UserWasNotFoundInDatabaseException {
			UserEntity databaseUser=this.userEntityRepo.findById(userID).orElseThrow(UserWasNotFoundInDatabaseException::new);
			long previousDatabaseVersion=CustomSecurityContextHolder.getCustomSecurityContext().getCustomUserDetails().getDatabaseVersion();
			if(previousDatabaseVersion!=databaseUser.getVersion()) {
				throw new OptimisticLockException();
			}
			databaseUser.setNick(user.getNickName());
			databaseUser.setSerName(user.getSerName());
			databaseUser.setLastName(user.getLastName());
			databaseUser.setBornDate(user.getUserBorn());

			this.userEntityRepo.saveAndFlush(databaseUser);
			this.RequestUserEntity.setUserEntity(databaseUser);
		}
		

	}

	

	
}
