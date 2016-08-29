package cyberoam.communication.socketcommunication.base;

import java.io.Serializable;

/**
 * The Class Tblnodeproperties.
 * 
 * @author Bhavik Aniruddh Ambani
 * @since X.3.7.3.0
 */
public class Tblnodeproperties implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The nodepropertyid. */
	private int nodepropertyid;

	/** The workqueuesize. */
	private int workqueuesize;

	/** The corethreadsize. */
	private int corethreadsize;

	/** The maxthreadsize. */
	private int maxthreadsize;

	/** The keepalivetime. */
	private int keepalivetime;

	/** The waittimeonemptyqueue. */
	private int waittimeonemptyqueue;

	/** The clientlinkdnwaitinterval. */
	private int clientlinkdnwaitinterval;

	/** The clientreceivertimeout. */
	private int clientreceivertimeout;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clientlinkdnwaitinterval;
		result = prime * result + clientreceivertimeout;
		result = prime * result + corethreadsize;
		result = prime * result + keepalivetime;
		result = prime * result + maxthreadsize;
		result = prime * result + nodepropertyid;
		result = prime * result + waittimeonemptyqueue;
		result = prime * result + workqueuesize;
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
		Tblnodeproperties other = (Tblnodeproperties) obj;
		if (clientlinkdnwaitinterval != other.clientlinkdnwaitinterval)
			return false;
		if (clientreceivertimeout != other.clientreceivertimeout)
			return false;
		if (corethreadsize != other.corethreadsize)
			return false;
		if (keepalivetime != other.keepalivetime)
			return false;
		if (maxthreadsize != other.maxthreadsize)
			return false;
		if (nodepropertyid != other.nodepropertyid)
			return false;
		if (waittimeonemptyqueue != other.waittimeonemptyqueue)
			return false;
		if (workqueuesize != other.workqueuesize)
			return false;
		return true;
	}

	/**
	 * Instantiates a new tblnodeproperties.
	 *
	 * @param nodepropertyid
	 *            the nodepropertyid
	 * @param workqueuesize
	 *            the workqueuesize
	 * @param corethreadsize
	 *            the corethreadsize
	 * @param maxthreadsize
	 *            the maxthreadsize
	 * @param keepalivetime
	 *            the keepalivetime
	 * @param waittimeonemptyqueue
	 *            the waittimeonemptyqueue
	 * @param clientlinkdnwaitinterval
	 *            the clientlinkdnwaitinterval
	 * @param clientreceivertimeout
	 *            the clientreceivertimeout
	 */
	public Tblnodeproperties(int nodepropertyid, int workqueuesize,
			int corethreadsize, int maxthreadsize, int keepalivetime,
			int waittimeonemptyqueue, int clientlinkdnwaitinterval,
			int clientreceivertimeout) {
		super();
		this.nodepropertyid = nodepropertyid;
		this.workqueuesize = workqueuesize;
		this.corethreadsize = corethreadsize;
		this.maxthreadsize = maxthreadsize;
		this.keepalivetime = keepalivetime;
		this.waittimeonemptyqueue = waittimeonemptyqueue;
		this.clientlinkdnwaitinterval = clientlinkdnwaitinterval;
		this.clientreceivertimeout = clientreceivertimeout;
	}

	/**
	 * Instantiates a new tblnodeproperties.
	 */
	public Tblnodeproperties() {
		super();
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

	/**
	 * Gets the workqueuesize.
	 *
	 * @return the workqueuesize
	 */
	public int getWorkqueuesize() {
		return workqueuesize;
	}

	/**
	 * Sets the workqueuesize.
	 *
	 * @param workqueuesize
	 *            the new workqueuesize
	 */
	public void setWorkqueuesize(int workqueuesize) {
		this.workqueuesize = workqueuesize;
	}

	/**
	 * Gets the corethreadsize.
	 *
	 * @return the corethreadsize
	 */
	public int getCorethreadsize() {
		return corethreadsize;
	}

	/**
	 * Sets the corethreadsize.
	 *
	 * @param corethreadsize
	 *            the new corethreadsize
	 */
	public void setCorethreadsize(int corethreadsize) {
		this.corethreadsize = corethreadsize;
	}

	/**
	 * Gets the maxthreadsize.
	 *
	 * @return the maxthreadsize
	 */
	public int getMaxthreadsize() {
		return maxthreadsize;
	}

	/**
	 * Sets the maxthreadsize.
	 *
	 * @param maxthreadsize
	 *            the new maxthreadsize
	 */
	public void setMaxthreadsize(int maxthreadsize) {
		this.maxthreadsize = maxthreadsize;
	}

	/**
	 * Gets the keepalivetime.
	 *
	 * @return the keepalivetime
	 */
	public int getKeepalivetime() {
		return keepalivetime;
	}

	/**
	 * Sets the keepalivetime.
	 *
	 * @param keepalivetime
	 *            the new keepalivetime
	 */
	public void setKeepalivetime(int keepalivetime) {
		this.keepalivetime = keepalivetime;
	}

	/**
	 * Gets the waittimeonemptyqueue.
	 *
	 * @return the waittimeonemptyqueue
	 */
	public int getWaittimeonemptyqueue() {
		return waittimeonemptyqueue;
	}

	/**
	 * Sets the waittimeonemptyqueue.
	 *
	 * @param waittimeonemptyqueue
	 *            the new waittimeonemptyqueue
	 */
	public void setWaittimeonemptyqueue(int waittimeonemptyqueue) {
		this.waittimeonemptyqueue = waittimeonemptyqueue;
	}

	/**
	 * Gets the clientlinkdnwaitinterval.
	 *
	 * @return the clientlinkdnwaitinterval
	 */
	public int getClientlinkdnwaitinterval() {
		return clientlinkdnwaitinterval;
	}

	/**
	 * Sets the clientlinkdnwaitinterval.
	 *
	 * @param clientlinkdnwaitinterval
	 *            the new clientlinkdnwaitinterval
	 */
	public void setClientlinkdnwaitinterval(int clientlinkdnwaitinterval) {
		this.clientlinkdnwaitinterval = clientlinkdnwaitinterval;
	}

	/**
	 * Gets the clientreceivertimeout.
	 *
	 * @return the clientreceivertimeout
	 */
	public int getClientreceivertimeout() {
		return clientreceivertimeout;
	}

	/**
	 * Sets the clientreceivertimeout.
	 *
	 * @param clientreceivertimeout
	 *            the new clientreceivertimeout
	 */
	public void setClientreceivertimeout(int clientreceivertimeout) {
		this.clientreceivertimeout = clientreceivertimeout;
	}

}
