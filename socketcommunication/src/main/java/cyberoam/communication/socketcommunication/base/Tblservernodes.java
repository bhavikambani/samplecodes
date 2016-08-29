package cyberoam.communication.socketcommunication.base;

import java.io.Serializable;

/**
 * The Class Tblguiclusters.
 * 
 * @author Bhavik Aniruddh Ambani
 * @since X.3.7.3.0
 */
public class Tblservernodes implements Serializable, Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1429312236831830038L;

	/** The Constant DEFAULT_NODEPROPERTYCONFIG_ID. */
	private static final int DEFAULT_NODEPROPERTYCONFIG_ID = 1;

	/** The serveridentifier. */
	private String serveridentifier;

	/** The serverip. */
	private String serverip;

	/** The serverlocation. */
	private String serverlocation;

	/** The serverport. */
	private int serverport;

	/** The clientnodes. */
	private String clientnodes;

	/** The nodepropertyid. */
	private int nodepropertyid = DEFAULT_NODEPROPERTYCONFIG_ID;

	/**
	 * Instantiates a new tblservernodes.
	 */
	public Tblservernodes() {
	}

	/**
	 * Instantiates a new tblguiclusters.
	 *
	 * @param serveridentifier
	 *            the serveridentifier
	 * @param serverip
	 *            the serverip
	 * @param serverlocation
	 *            the serverlocation
	 * @param serverport
	 *            the serverport
	 * @param clientnodes
	 *            the clientnodes
	 * @param nodepropertyid
	 *            the nodepropertyid
	 */
	public Tblservernodes(String serveridentifier, String serverip,
			String serverlocation, int serverport, String clientnodes,
			int nodepropertyid) {
		super();
		this.serveridentifier = serveridentifier;
		this.serverip = serverip;
		this.serverlocation = serverlocation;
		this.serverport = serverport;
		this.clientnodes = clientnodes;
		this.nodepropertyid = nodepropertyid;
	}

	/**
	 * Gets the serveridentifier.
	 *
	 * @return the serveridentifier
	 */
	public String getServeridentifier() {
		return serveridentifier;
	}

	/**
	 * Sets the serveridentifier.
	 *
	 * @param serveridentifier
	 *            the new serveridentifier
	 */
	public void setServeridentifier(String serveridentifier) {
		this.serveridentifier = serveridentifier;
	}

	/**
	 * Gets the serverip.
	 *
	 * @return the serverip
	 */
	public String getServerip() {
		return serverip;
	}

	/**
	 * Sets the serverip.
	 *
	 * @param serverip
	 *            the new serverip
	 */
	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	/**
	 * Gets the serverlocation.
	 *
	 * @return the serverlocation
	 */
	public String getServerlocation() {
		return serverlocation;
	}

	/**
	 * Sets the serverlocation.
	 *
	 * @param serverlocation
	 *            the new serverlocation
	 */
	public void setServerlocation(String serverlocation) {
		this.serverlocation = serverlocation;
	}

	/**
	 * Gets the serverport.
	 *
	 * @return the serverport
	 */
	public int getServerport() {
		return serverport;
	}

	/**
	 * Sets the serverport.
	 *
	 * @param serverport
	 *            the new serverport
	 */
	public void setServerport(int serverport) {
		this.serverport = serverport;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the clientnodes.
	 *
	 * @return the clientnodes
	 */
	public String getClientnodes() {
		return clientnodes;
	}

	/**
	 * Sets the clientnodes.
	 *
	 * @param clientnodes
	 *            the new clientnodes
	 */
	public void setClientnodes(String clientnodes) {
		this.clientnodes = clientnodes;
	}

	/**
	 * Gets the nodepropertyid.
	 *
	 * @return the nodepropertyid
	 */
	public int getNodepropertyid() {
		return nodepropertyid;
	}

	/**
	 * Sets the nodepropertyid.
	 *
	 * @param nodepropertyid
	 *            the new nodepropertyid
	 */
	public void setNodepropertyid(int nodepropertyid) {
		this.nodepropertyid = nodepropertyid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tblservernodes [serveridentifier=" + serveridentifier
				+ ", serverip=" + serverip + ", serverlocation="
				+ serverlocation + ", serverport=" + serverport
				+ ", clientnodes=" + clientnodes + ", nodepropertyid="
				+ nodepropertyid + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientnodes == null) ? 0 : clientnodes.hashCode());
		result = prime * result + nodepropertyid;
		result = prime
				* result
				+ ((serveridentifier == null) ? 0 : serveridentifier.hashCode());
		result = prime * result
				+ ((serverip == null) ? 0 : serverip.hashCode());
		result = prime * result
				+ ((serverlocation == null) ? 0 : serverlocation.hashCode());
		result = prime * result + serverport;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tblservernodes other = (Tblservernodes) obj;
		if (clientnodes == null) {
			if (other.clientnodes != null)
				return false;
		} else if (!clientnodes.equals(other.clientnodes))
			return false;
		if (nodepropertyid != other.nodepropertyid)
			return false;
		if (serveridentifier == null) {
			if (other.serveridentifier != null)
				return false;
		} else if (!serveridentifier.equals(other.serveridentifier))
			return false;
		if (serverip == null) {
			if (other.serverip != null)
				return false;
		} else if (!serverip.equals(other.serverip))
			return false;
		if (serverlocation == null) {
			if (other.serverlocation != null)
				return false;
		} else if (!serverlocation.equals(other.serverlocation))
			return false;
		if (serverport != other.serverport)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Tblservernodes clone = (Tblservernodes) super.clone();
		clone.serveridentifier = this.serveridentifier;
		clone.serverip = this.serverip;
		clone.serverlocation = this.serverlocation;
		clone.serverport = this.serverport;
		clone.clientnodes = this.clientnodes;
		clone.nodepropertyid = this.nodepropertyid;
		return clone;
	}

}
