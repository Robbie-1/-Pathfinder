package uk.ac.reigate.rm13030.utils;

/**
 * @author Robbie <http://reigate.ac.uk/>
 */

public class SimpleLogger {

	public SimpleLogger() {}

	public enum MessageType {

		DEBUG(),
		INFO(),
		WARNING(),
		ERROR();
		
	}

	/**
	 * Typical usage;
	 * log(this.getClass(), MessageType.ERROR, "JAR non-existent in given directory", "Exiting program...");
	 * 
	 * --> [Splash] [ERROR]: JAR non-existent in given directory <+> Exiting program...
	 */

	@SuppressWarnings("rawtypes")
	public static void log(Class clazz, MessageType mT, String message, String... detail) {
		System.out.println("["+Utils.getCurrentTime()+"] "+(detail.length >= 1 ? "[" + clazz.getSimpleName() + "] [" + mT + "]: " + message + " <+>" + buildString(detail) : "[" + clazz.getSimpleName() + "] [" + mT + "]: " + message));
	}
	
	private static String buildString(String... str) {
		String build = "";
        for(String arg : str) {
        	build += (" " + arg);
        }
        return build;
	}
	
}