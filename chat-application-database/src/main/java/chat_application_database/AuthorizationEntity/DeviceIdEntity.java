package chat_application_database.AuthorizationEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity(name = DeviceIdEntity.deviceIdEntityTable)
public class DeviceIdEntity {
	public static final String deviceIdEntityTable="user_device";
	
    public static final String userIdName = "user";
    public static final String DeviceIdEntityName = "device_id";
    public static final String activityName = "userActivity";

    //generate on database, unique 
    @EmbeddedId
    private CompositePrimaryKey primaryKey;


    @Embeddable
    public static class CompositePrimaryKey implements Serializable{
        @Column(name = DeviceIdEntityName)
        private int deviceID;

        @ManyToOne
        @Column(name=userIdName)
        @JoinColumn(name = UserEntity.userIdName)
        private UserEntity user;

        
    	protected CompositePrimaryKey( UserEntity user,int deviceID) {
			this.deviceID = deviceID;
			this.user = user;
		}

		public int getDeviceID() {
    		return deviceID;
    	}

    	public void setDeviceID(int deviceID) {
    		this.deviceID = deviceID;
    	}

    	public UserEntity getUser() {
    		return user;
    	}

    	public void setUser(UserEntity user) {
    		this.user = user;
    	}


    }
    @Column(name=DeviceIdEntity.activityName)
    @OneToMany(mappedBy = LoginActivityEntity.deviceIdName )
    private List<LoginActivityEntity> activity = new ArrayList<>();
	public List<LoginActivityEntity> getActivity() {
		return activity;
	}

	public void setActivity(List<LoginActivityEntity> activity) {
		this.activity = activity;
	}

	public CompositePrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(CompositePrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}


	
    // ... getters and setters ...
    
    
}

