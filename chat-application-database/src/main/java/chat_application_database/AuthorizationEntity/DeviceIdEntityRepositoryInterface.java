package chat_application_database.AuthorizationEntity;


import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import chat_application_commonPart.Logger.Log4j2;
import chat_application_database.AuthorizationEntity.DeviceIdEntity.CompositePrimaryKey;



// device id will be generated on user by autoIncreament
public interface DeviceIdEntityRepositoryInterface  extends JpaRepository<DeviceIdEntity,CompositePrimaryKey> { 	
	
	private  String x="";
	/**Metod verify if id exist in database, otherwise return new generated ID */
	default DeviceIdEntity DeviceIdGeneration(UserEntity user,Integer deviceID) {
		if(user==null) {
			Log4j2.log.fatal(Log4j2.LogMarker.Database.getMarker(),"UserEntity cannot be null");
			throw new NullPointerException();
		}
		if(deviceID!=null) {
		Optional<DeviceIdEntity> id=this.findById(new CompositePrimaryKey(user,deviceID));
		if(id.isPresent()) {
			return id.get();
		}
		}
		EntityManager entityManager;

		entityManager.createNativeQuery(String.format("Select %s from (Insert into %s (%s) values(:%s));",
				DeviceIdEntity.DeviceIdEntityName,DeviceIdEntity.deviceIdEntityTable
				,DeviceIdEntity.userIdName,DeviceIdEntity.userIdName
				))
		.setParameter(DeviceIdEntity.userIdName, user.getUserId())
		.executeUpdate();
		
		
		entityManager.createQuery("")
		.getre;
		entityManager.createNativeQuery(String.format("Insert into %s"
				+ "(%s) values(%s);",DeviceIdEntity.userIdName,
			/* values*/user.getUserId()
				))
		;
		
		
		x.create
		DeviceIdEntity idd=new DeviceIdEntity();
		idd.setUser(user);
		this.saveAndFlush(idd);
		return idd;
	}
	
	default  DeviceIdEntity generateDeviceid(UserEntity user) {
		
		
		return null;
	}
	
	
}

