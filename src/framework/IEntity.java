package framework;

/**
 * Sluzi za oznacavanje entiteta za perzistenciju.
 *
 */
public interface IEntity {
		
	public Object[] getValues();
	
	public Integer getId();

}
