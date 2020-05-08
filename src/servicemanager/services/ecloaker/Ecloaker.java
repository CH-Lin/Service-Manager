package servicemanager.services.ecloaker;

import servicemanager.abstracts.AbstractServices;

public class Ecloaker extends AbstractServices {

	public Ecloaker(String name) {
		super(name); // serviceName = "E-mail cloaker";
	}

	public boolean open() {
		return false;
	}

	public boolean start() {
		return false;
	}

	public boolean stop() {
		return false;
	}

}
