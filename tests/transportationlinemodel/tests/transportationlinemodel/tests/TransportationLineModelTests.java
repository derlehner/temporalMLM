package transportationlinemodel.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.TURI;
import edu.uoc.som.temf.core.TResource;
import edu.uoc.som.temf.core.impl.H2TResourceImpl;
import edu.uoc.som.temf.core.impl.TimescaleTResourceImpl;
import transportationlinemodel.Area;
import transportationlinemodel.Component;
import transportationlinemodel.Item;
import transportationlinemodel.ItemGenerator;
import transportationlinemodel.TransportationlinemodelFactory;

class TransportationLineModelTests {	
	private static TResource res;
	private static TransportationlinemodelFactory modelFactory;
	
	@BeforeAll
	static void beforeAll() throws IOException {
		File resourceFile = File.createTempFile("temf-", null);
		resourceFile.delete();
		resourceFile.deleteOnExit();
		res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile));
		
		modelFactory = TransportationlinemodelFactory.eINSTANCE;
		
	}

	@Test
	void test() throws IOException {
		// Arrange
		transportationlinemodel.System system = modelFactory.createSystem();
		res.getContents().add(system);
		Area a1 = modelFactory.createArea();
		system.getArea().add(a1);
		ItemGenerator itemGenerator1 = modelFactory.createItemGenerator();
		a1.setItemgenerator(itemGenerator1);
		Component m1 = modelFactory.createComponent();
		a1.getComponent().add(m1);
		Item i1 = modelFactory.createItem();
		a1.getItem().add(i1);
		
		// Act
		
		i1.setIsProcessed(false);
		i1.setIsProcessed(true);
		
		// Assert
		System.out.println(res.getContents().toString());
	}

}
