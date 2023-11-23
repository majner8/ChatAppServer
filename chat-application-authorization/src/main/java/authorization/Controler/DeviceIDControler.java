package authorization.Controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import authorization.RequestScope_UserEntity;
import authorization.Security.jwtToken;
import chat_application_commonPart.httpEndPointPath.AuthorizationPath;
import chat_application_common_Part.Security.DeviceIDRequestScope;
import database.Authorization.deviceIdGenerationRepository;
public class DeviceIDControler {

	@Autowired
	private jwtToken.jwtTokenGeneratorInterface jwtToken;
	@Autowired
	private deviceIdGenerationRepository IdGeneration;
	@Autowired
	private AuthorizationService autorizationService;
	
	@Autowired
	private DeviceIDRequestScope requestScopeValue;
	@Autowired
	private RequestScope_UserEntity userEntity;
	
	
	
	/**Metod reuturn device ID token, have to be send with every request */
	@GetMapping(AuthorizationPath.deviceIdPath)
	public ResponseEntity<String> getDeviceIDToken(HttpServletRequest request){
		String id=this.IdGeneration.deviceIdGeneration();
		
		String token=this.jwtToken.generateDeviceToken(id);
						
		return ResponseEntity.ok(token);
		
	}
}
