package com.elitecore.utilities;
// Here is a code example to configure the JDBCAppender without a configuration-file

public class AuditObject
{
	private String entity ;
    private String entityName;
    private String action ;
    private String oldValue;
    private String newValue; 
    private String actionTime ;
    private String actionBy ;
	
	/**
	 * Constants for entity, on which action is to be take place.IF any new entity
	 * created , enter constant for that.
	 */
	public final static String USER = "user" ;
	public final static String GROUP = "group" ;
	public final static String PACKAGE = "package" ;
	public final static String PACKAGEHOURACCOUNT = "package hour account" ;
	public final static String IPADDRESS = "ipaddress" ;
	public final static String POOL = "pool" ;
	public final static String ZONE = "zone" ;
	public final static String ZONEADMINREL = "zone administrator relation" ;
	public final static String SURFINGPOLICY = "surfing policy";
	public final static String SECURITYPOLICY = "security policy" ;
	public final static String SECURITYPOLICYDETAIL = "security policy detail" ;
	public final static String ACCESSPOLICY = "access policy";
	public final static String ACCESSPOLICYDETAIL = "access policy detail";
	public final static String BANDWIDTHPOLICY = "bandwidth policy";
	public final static String DATATRANSFERPOLICY = "datatransfer policy";
	public final static String DATATRANSFERRATE = "datatransfer rate";
	public final static String WEBCATEGORY = "webcategory";
	public final static String WEBCATEGORYFILETYPEREL = "webcategory file typerel";
	public final static String WEBCATEGORYKEYWORDDETAIL = "webcategory keyword detail";
	public final static String WEBCATEGORYSERVICEDETAIL = "webcategory service detail";
	public final static String CACHEWEBCATEGORY = "cache webcategory";
	public final static String FILETYPE = "filetype";
	public final static String FILETYPEDETAIL = "filetype detail";
	public final static String SCHEDULE = "schedule";
	public final static String SCHEDULEDETAIL = "schedule detail";
	public final static String FIREWALLACCESS = "firewall access";
	public final static String SERVICE = "service";
	public final static String DIALUP = "dial-up";
	public final static String ALIAS = "alias";
	public final static String ALIASDETAIL = "alias detail";
	public final static String INTRAPOPALIAS = "intrapop alias";
	public final static String INTRAPOP = "intrapop";
	public final static String INTRAPOPMAILSTATUS = "intrapop mail status";
	public final static String INTRAPOPUSER = "intrapop user";
	public final static String AUTHSERVER = "authserver";
	public final static String COMPANY = "company";
	public final static String CITY = "city";
	public final static String INVOICE = "invoice";
	public final static String INVOICEDETAIL = "invoice detail";
	public final static String USERDETAIL = "userdetail";
	public final static String LIVEUSER = "liveuser";
	public final static String USERIPREL = "user ip relation";
	public final static String USERSURFINGDETAIL = "user surfing detail";
	public final static String MESSAGE = "message";
	
	public final static String DHCP = "dhcp";
	public final static String DNS = "dns";
	public final static String MAILSERVER = "mailserver";
	public final static String SERVER = "24online server";
	public final static String DATA = "data";
	public final static String AUTOPURGECONFIG = "auto purge configuration";
	public final static String MANUALPURGE = "manual purge";
	public final static String CACHESERVER = "cache server";
	public final static String CACHE = "cache";
	public final static String SMTPREDIRECTION = "smtp redirection";
	public final static String MAILRELAYDOMAIN = "mail relay domain";
	public final static String UPLOADVERSION = "upload version";
	public final static String MANUALREGISTRATION = "manual registration";
	public final static String ONLINEREGISTRATION = "online registraion" ;
	public final static String UPGRADELINCENSE = "upgrade lincense";
	public final static String MANAGEMENTGUI = "management gui";
	public final static String REPORTGUI = "report gui";
	public final static String MYACCOUNTGUI = "my account gui";
	
	/**
	 * Action to be taken
	 */
	public final static String INSERT = "insert";
	public final static String DELETE = "delete";
	public final static String UPDATE = "update";
	public final static String ENABLE = "enable";
	public final static String DISABLE = "disable";
	public final static String START = "start";
	public final static String STOP = "stop";
	public final static String ENABLEAUTOSTART = "enable autostart";
    public final static String DISABLEAUTOSTART = "disable autostart";
	public final static String BACKUP = "backup";
	public final static String UPLOAD = "upload";
	public final static String RESTORE = "restore";
	public final static String CONFIGURATION = "configuration";
	public final static String PURGEWEBSURFINGREPORT = "purge websurfing report";
	public final static String PURGEUSERSESSIONLOG = "purge usersession log";
	public final static String REGISTER = "register";
	public final static String UPGRADE = "upgrade";
	public final static String LOGIN = "login";
	public final static String AUTOSTART = "auto start";
	public final static String MANUALSTART = "manual start";
	public final static String SHUTDOWN = "Shut Down";
	public final static String REBOOT = "reboot";
	public final static String RESTART = "restart";
	
	/**
	 * User related actions
	 */
	public final static String SAVECONFIG = "save configuration";
	public final static String CHANGEPACKAGE = "change package";
	public final static String RENEWUSER = "renew user";
	public final static String CHANGEPERSONALDET = "change personal details";
	
	
	public AuditObject(){
	}
	public AuditObject(String a_entity,String a_entityName,String a_action,String a_oldValue,String a_newValue,String a_actionBy){
		entity = a_entity;
		entityName = a_entityName;
		action = a_action;
		newValue = a_newValue ;
		oldValue = a_oldValue ;
		actionBy = a_actionBy;
	}
	public String getEntity(){
		return this.entity;
	}
	public String getEntityName(){
		return this.entityName;
	}
	public String getAction(){
		return this.action;
	}
	public String getActionTime(){
		return this.actionTime;
	}
	public String getActionBy(){
		return this.actionBy;
	}
	public String getNewValue(){
		return this.newValue;
	}
	public String getOldValue(){
		return this.oldValue;
	}
	
	public void setEntity(String value){
		entity = value;
	}
	public void setEntityName(String value){
		entityName = value;
	}
	public void setAction(String value){
		action = value;
	}
	public void setActionTime(String value){
		actionTime = value;
	}
	public void setActionBy(String value){
		actionBy = value ;
	}
	public void setNewValue(String value){
		newValue = value;
	}
	public void setOldValue(String value){
		oldValue = value;
	}
	
}
	