package com.elitecore.log4j;
// Here is a code example to configure the JDBCAppender without a configuration-file

public class LogObject
{
	private String entity ;
    private String entityName;
    private String action ;
    private String oldValue;
    private String newValue; 
    private String actionTime ;
    private String actionBy ;
	public LogObject(){
	}
	public LogObject(String a_entity,String a_entityName,String a_action,String a_oldValue,String a_newValue,String a_actionBy){
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
	