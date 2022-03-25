package transportationlinemodel.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.core.impl.TimescaleTResourceImpl;
import transportationlinemodel.Area;
import transportationlinemodel.ItemGenerator;
import transportationlinemodel.TransportationlinemodelFactory;

class TransportationLineModelTests {

	@Test
	void test() {
		//Resource resource = new XMIResourceImpl(URI.createPlatformPluginURI(TEST_MODEL_PLUGIN_ID + TEST_MODEL_ECORE_PATH, true));
				//resource.load(Collections.emptyMap());
			Resource resource = new TimescaleTResourceImpl(URI.createPlatformPluginURI("", true));
			TransportationlinemodelFactory modelFactory = TransportationlinemodelFactory.eINSTANCE;
			
			transportationlinemodel.System system = modelFactory.createSystem();
			Area a1 = modelFactory.createArea();
			ItemGenerator itemGenerator1 = modelFactory.createItemGenerator();
			a1.setItemgenerator(itemGenerator1);
			
			System.out.println(resource.getContents().toString());
	}

}
