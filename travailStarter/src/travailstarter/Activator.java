package travailstarter;

import inria.smarttools.core.component.AbstractContainer;
import inria.smarttools.core.component.Container;
import inria.smarttools.core.component.MessageImpl;
import inria.smarttools.core.component.PropertyMap;
import inria.smarttools.core.component.STGenericActivator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator extends STGenericActivator implements BundleActivator {

	private static BundleContext context;
	private static String DEBUT = "travail.debut";
	private static String FIN = "travail.fin";
	private static int debut = 0;
	private static int fin = 0;

	static BundleContext getContext() {
		return Activator.context;
	}

	public Activator() {
		resourceFileName = "/travailstarter/resources/Travail.cdml";
		bundleName = "travail-starter";
	}

	@Override
	public Container createComponent(final String componentID) {
		System.out.println("Create");
		final AbstractContainer c = new AbstractContainer("travailstarter",
				"/travailstarter/resources/Travail.cdml") {
			{
			}
		};
		if (Activator.debut == 1) {
			/*
			 * Cr�ation du maitre
			 */
			System.out.println("Creating maitre");
			c.sendWhenAvailable(new MessageImpl("connectTo", new PropertyMap() {
				private static final long serialVersionUID = 1L;
				{
					/*
					 * Ajout d'un composant de type travailleur
					 */
					put("id_src", "ComponentsManager");
					put("type_dest", "maitre");
					put("id_dest", "maitre");
//					put("dc", "");
//					put("tc", "");
//					put("sc", "OFF");
				}
			}, new PropertyMap()));
			Activator.fin--;
		}

		while (Activator.debut <= Activator.fin) {
			final String name = "travailleur." + Activator.debut;
			System.out.println("Creating " + name);
			c.sendWhenAvailable(new MessageImpl("connectTo", new PropertyMap() {
				private static final long serialVersionUID = 1L;
				{
					/*
					 * Ajout d'un composant de type travailleur
					 */
					put("id_src", "ComponentsManager");
					put("type_dest", "travailleur");
					put("id_dest", name);
				}
			}, new PropertyMap()));

			c.sendWhenAvailable(new MessageImpl("connectTo", new PropertyMap() {
				private static final long serialVersionUID = 1L;
				{

					/*
					 * Connexion entre le travailleur cree et le maitre
					 */
					put("id_src", name);
					put("type_dest", "maitre");
					put("id_dest", "maitre");					
					put("dc", "");
					put("tc", "");
					put("sc", "OFF");
				}

			}, new PropertyMap()));
			Activator.debut++;
		}
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {

		super.start(bundleContext);
		Activator.context = bundleContext;

		String optNb = Activator.context.getProperty(Activator.DEBUT);
		if (optNb != null) {
			Activator.debut = Integer.parseInt(optNb);
		}
		optNb = Activator.context.getProperty(Activator.FIN);
		if (optNb != null) {
			Activator.fin = Integer.parseInt(optNb);
		}

		inria.smarttools.componentsmanager.World.getInstance().setWorldFile(
				"resources:travailstarter/resources/Travail.world");
		inria.smarttools.componentsmanager.World.getInstance().loadWorld();
		final Bundle[] bundles = Activator.context.getBundles();
		for (final Bundle bundle : bundles) {
			if (bundle.getSymbolicName().equals("componentsManager")) {
				bundle.start();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
}
