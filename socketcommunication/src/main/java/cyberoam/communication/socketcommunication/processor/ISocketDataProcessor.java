package cyberoam.communication.socketcommunication.processor;

/**
 * The Interface ISocketProcessor.
 * 
 * @author Bhavik Aniruddh Ambani
 * 
 * @since X.3.7.3.0
 */
public interface ISocketDataProcessor {

	/**
	 * Process socket data.
	 *
	 * @param o
	 *            the Socket Data Object
	 */
	public boolean processSocketData(Object o);

}
