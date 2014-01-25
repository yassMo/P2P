package maitre;

import inria.smarttools.core.component.Container;
import inria.smarttools.core.component.STGenericActivator;

public class Activator extends STGenericActivator {

	public Activator() {
		resourceFileName = "/maitre/resources/maitre.cdml";
		bundleName = "maitre";
	}

	@Override
	public Container createComponent(final String componentId) {
		final MaitreContainer container = new MaitreContainer(
				componentId, resourceFileName);
		registerContainer(container);
		return container;
	}

}
