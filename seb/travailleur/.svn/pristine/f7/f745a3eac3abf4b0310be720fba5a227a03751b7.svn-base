package travailleur;

import inria.smarttools.core.component.Container;
import inria.smarttools.core.component.STGenericActivator;

public class Activator extends STGenericActivator {

	public Activator() {
		resourceFileName = "/travailleur/resources/travailleur.cdml";
		bundleName = "travailleur";
	}
	

	@Override
	public Container createComponent(final String componentId) {
		final TravailleurContainer container = new TravailleurContainer(
				componentId, resourceFileName);
		registerContainer(container);		
		return container;
	}
	

}
