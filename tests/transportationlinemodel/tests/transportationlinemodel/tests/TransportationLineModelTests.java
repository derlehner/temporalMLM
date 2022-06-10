package transportationlinemodel.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.SortedMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.TURI;
import edu.uoc.som.temf.core.TGlobalClock;
import edu.uoc.som.temf.core.TResource;
import edu.uoc.som.temf.core.impl.H2TResourceImpl;
import edu.uoc.som.temf.core.impl.TimescaleTResourceImpl;
import transportationlinemodel.Area;
import transportationlinemodel.Component;
import transportationlinemodel.Item;
import transportationlinemodel.ItemGenerator;
import transportationlinemodel.TransportationlinemodelFactory;
import transportationlinemodel.impl.SystemImpl;

class TransportationLineModelTests {	
	private static TResource res;
	private static TransportationlinemodelFactory modelFactory;
	
	private static String expectedSystemName = "System 1";
	private static String expectedAreaName = "Area 1";
	private static String expectedItemGeneratorName = "Item Generator 1";
	private static String expectedItemName = "Item 1";
	private static String expectedCopmponentName = "Component 1";
	
	private static Item i1;
	
	@BeforeAll
	static void beforeAll() throws IOException {
		// Prepare TemporalEMF
		File resourceFile = File.createTempFile("temf-", null);
		resourceFile.delete();
		resourceFile.deleteOnExit();
		res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile));
		
		// Prepare model factory
		modelFactory = TransportationlinemodelFactory.eINSTANCE;
		
		// Create example model
		
		
		transportationlinemodel.System system = modelFactory.createSystem();
		system.setName(expectedSystemName);
		res.getContents().add(system);
		
		Area a1 = modelFactory.createArea();
		a1.setName(expectedAreaName);
		system.getArea().add(a1);
		ItemGenerator itemGenerator1 = modelFactory.createItemGenerator();
		itemGenerator1.setName(expectedItemGeneratorName);
		a1.setItemgenerator(itemGenerator1);
		Component m1 = modelFactory.createComponent();
		m1.setName(expectedCopmponentName);
		a1.getComponent().add(m1);
		i1 = modelFactory.createItem();
		i1.setName(expectedItemName);
		a1.getItem().add(i1);
	}

	@Test
	void testExampleModelCorrectlyStored() throws IOException {
		// Arrange
		
		// Act
		transportationlinemodel.System actualSystem = (transportationlinemodel.impl.SystemImpl) res.getContents().get(0);
		Area actualArea = actualSystem.getArea().get(0);
		Component actualComponent = actualArea.getComponent().get(0);
		Item actualtem = actualArea.getItem().get(0);
		ItemGenerator actualItemGenerator = actualArea.getItemgenerator();
		
		// Assert
		assertEquals(actualSystem.getName(), expectedSystemName);
		assertEquals(actualArea.getName(), expectedAreaName);		
		assertEquals(actualComponent.getName(), expectedCopmponentName);		
		assertEquals(actualtem.getName(), expectedItemName);		
		assertEquals(actualItemGenerator.getName(), expectedItemGeneratorName);
	}
	
	@Test
	void testIsProcessed_CorrectlyStored() throws IOException {
		// Arrange
		i1.setIsProcessed(false);
		
		// Act
		transportationlinemodel.System actualSystem = (transportationlinemodel.impl.SystemImpl) res.getContents().get(0);
		Area actualArea = actualSystem.getArea().get(0);
		Item actualtem = actualArea.getItem().get(0);
		
		// Arrange
		assertEquals(actualtem.isIsProcessed(), false);
	}
	
	@Test
	void testIsProcessed_ChangeCorrecltyStored() throws IOException {
		// Arrange
		i1.setIsProcessed(false);
		i1.setIsProcessed(true);
		
		// Act
		transportationlinemodel.System actualSystem = (transportationlinemodel.impl.SystemImpl) res.getContents().get(0);
		Area actualArea = actualSystem.getArea().get(0);
		Item actualtem = actualArea.getItem().get(0);
		
		
		// Assert
		assertEquals(actualtem.isIsProcessed(), true);
	}
	
	@Test
	void testIsProcessed_ChangeOldValueStillAvailable() throws IOException,NoSuchFieldException {
		// Arrange
		i1.setIsProcessed(false);
		i1.setIsProcessed(true);
		
		// Act
		transportationlinemodel.System actualSystem = (transportationlinemodel.impl.SystemImpl) res.getContents().get(0);
		Area actualArea = actualSystem.getArea().get(0);
		Component actualComponent = actualArea.getComponent().get(0);
		Item actualItem = actualArea.getItem().get(0);
		ItemGenerator actualItemGenerator = actualArea.getItemgenerator();
		Instant now = TGlobalClock.INSTANCE.instant();
		
		EAttribute isProcessed = actualItem.eClass().getEAllAttributes().get(1);
		SortedMap<Instant, Object> isProcessedValues = actualItem.eGetAllBetween(now, now.minusSeconds(1000), isProcessed);
		
		// Assert
		assertEquals(isProcessed, "isProcessed");
		assertTrue(isProcessedValues.size() != 2);
	}

}
