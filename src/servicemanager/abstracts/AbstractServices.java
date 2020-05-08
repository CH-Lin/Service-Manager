package servicemanager.abstracts;

public abstract class AbstractServices {

	protected String serviceName;

	public AbstractServices(String name) {
		serviceName = name;
	}

	public String getName() {
		return serviceName;
	}

	public abstract boolean start();

	public abstract boolean stop();

	public abstract boolean open();
}
