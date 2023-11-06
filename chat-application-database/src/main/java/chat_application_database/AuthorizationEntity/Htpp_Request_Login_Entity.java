package chat_application_database.AuthorizationEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name=Htpp_Request_Login_Entity.Htpp_Request_Login_Entity_tableName)
public class Htpp_Request_Login_Entity {
	public static final String Htpp_Request_Login_Entity_tableName="";
	
	public static final String Htpp_Request_Login_Entity_PrimaryKeyName="";
	
	public static final String Htpp_Request_Login_Entity_InetAdressName="";
	
	public static final String Htpp_Request_Login_Entity_EndPointPathName="";

	public static final String Htpp_Request_Login_Entity_serverResponseStatusName="";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Htpp_Request_Login_Entity_PrimaryKeyName)
    private long primaryKey;

    @Column(name=Htpp_Request_Login_Entity.Htpp_Request_Login_Entity_InetAdressName, nullable=false)
    private String inetAdress;
    @Column(name=Htpp_Request_Login_Entity.Htpp_Request_Login_Entity_EndPointPathName, nullable=false)
    private String EndPointPath;
    @Column(name=Htpp_Request_Login_Entity.Htpp_Request_Login_Entity_serverResponseStatusName, nullable=false)
    private int serverResponseStatus;
	
}
