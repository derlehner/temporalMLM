package transportationlinemodel.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.SortedMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

class PerformanceTests {
	
	private static TResource res;
	private static TransportationlinemodelFactory modelFactory;
	
	private static String expectedSystemName = "System 1";
	private static String expectedAreaName = "Area 1";
	private static String expectedItemGeneratorName = "Item Generator 1";
	private static String expectedItemName = "Item 1";
	private static String expectedCopmponentName = "Component 1";
	
	
	@BeforeAll
	public static void beforeAll() throws IOException {
		// Prepare TemporalEMF
		File resourceFile = File.createTempFile("temf-", null);
		resourceFile.delete();
		resourceFile.deleteOnExit();
		//res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile, "keyvalue"));
		PerformanceTests.res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile, "keyvalue"));
		
		// Prepare model factory
		PerformanceTests.modelFactory = TransportationlinemodelFactory.eINSTANCE;
		
		// Create example model
		
		
		
	}
	
	//@AfterAll
	static void cleanup() {
		((TimescaleTResourceImpl)PerformanceTests.res).cleanup();
	}
	
	@Test
	void testRQ1() {
		// vary iterations from 2 to 40 960
		for(int iterations = 2; iterations <= 100; iterations+=2) {
			// setup system 
			
			/* For reference, this System is composed
			by a single Area, with 1 ItemGenerator, 1 WaitingQueue, 1 StorageQueue, 3
			Conveyors, 4 TurnTables, and 1 Machine.*/
			transportationlinemodel.System system = modelFactory.createSystem();
			system.setName(expectedSystemName);
			PerformanceTests.res.getContents().add(system);
			
			Area a1 = PerformanceTests.modelFactory.createArea();
			a1.setName(expectedAreaName);
			system.getArea().add(a1);
			ItemGenerator itemGenerator1 = modelFactory.createItemGenerator();
			itemGenerator1.setName(expectedItemGeneratorName);
			a1.setItemgenerator(itemGenerator1);
			
			Component waitingQueue1 = modelFactory.createComponent();
			waitingQueue1.setName("waiting queue 1");
			a1.getComponent().add(waitingQueue1);
			
			Component storageQueue1 = modelFactory.createComponent();
			storageQueue1.setName("storage queue 1");
			a1.getComponent().add(storageQueue1);
			
			Component conveyor1 =  modelFactory.createComponent();
			conveyor1.setName("conveyor 1");
			a1.getComponent().add(conveyor1);
			Component conveyor2 =  modelFactory.createComponent();
			conveyor2.setName("conveyor 2");
			a1.getComponent().add(conveyor2);
			Component conveyor3 =  modelFactory.createComponent();
			conveyor3.setName("conveyor 3");
			a1.getComponent().add(conveyor3);
			
			Component turnTable1 = modelFactory.createComponent();
			turnTable1.setName("turntable 1");
			a1.getComponent().add(turnTable1);
			Component turnTable2 = modelFactory.createComponent();
			turnTable2.setName("turntable 2");
			a1.getComponent().add(turnTable2);
			Component turnTable3 = modelFactory.createComponent();
			turnTable3.setName("turntable 3");
			a1.getComponent().add(turnTable3);
			Component turnTable4 = modelFactory.createComponent();
			turnTable1.setName("turntable 4");
			a1.getComponent().add(turnTable4);
			
			Component machine1 = modelFactory.createComponent();
			machine1.setName("machine 1");
			a1.getComponent().add(machine1);
			cleanup();
		}
		
		assertEquals(1, 2-1);
		
		// get machine m
		
		// todo: inwiefern soll die processing time jetzt die execution time beeinflussen?
		// maybe: perform change operations to gradually change processing time
		
		// measure overall execution time for current setting
		
	}
	
	@Test
	void testRQ2() {
		// vary iterations from 2 to 40 960
		
		// Setup System
		
		// todo: how to measure memory consumption
		// - RAM during the simulation/running the test case
		// - disk space for persisting data
		
	}
	
	@Test
	void testExampleModelCorrectlyStored() throws IOException {
		// Arrange
		
		// Act
		transportationlinemodel.System actualSystem = (transportationlinemodel.impl.SystemImpl) res.getContents().get(1);
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
		
		TransportationLineModelTests.cleanup();
	}
	
	@Test
	public static void testRQ3() {
		// Setup System
		// vary iterations from 2 to 40 960
		
		// get machine m
		
		
		// perform query: get all items processed by m
		// m.getItems()
		
		// perform q2-q4
		
	}
}
