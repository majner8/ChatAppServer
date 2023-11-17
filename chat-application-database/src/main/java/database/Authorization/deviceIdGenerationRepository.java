package database.Authorization;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import chat_application_commonPart.Logger.Log4j2;

public interface deviceIdGenerationRepository extends JpaRepository<DeviceIdEntity,String> {

	/**Metod generate deviceId and persist them
	 * Metod has implement mechanism to prevent duplicate id error */
	public default String deviceIdGeneration() {
		boolean finish=false;
		DataIntegrityViolationException ex = null;
		int i=0;
		String id=null;
		do {
			id=UUID.randomUUID().toString();
			try {
				DeviceIdEntity entity=new DeviceIdEntity();
				entity.setDeviceId(id);
				entity.setLastSeen(LocalDate.now(ZoneId.systemDefault()));
				this.save(entity);
			}
			catch(DataIntegrityViolationException e) {
				ex=e;
				Log4j2.log.warn(Log4j2.LogMarker.Database.getMarker(),"DataIntegrityViolationException occurs, system try generate Id again");
				i++;
				continue;
			}
			finish=true;
		}
		while(i<3&&finish==false);
		if(finish==true) {
			throw ex;
		}
		
		return id;
	}
	
	
}
