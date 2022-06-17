package multilevelmodel.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.TURI;
import edu.uoc.som.temf.core.TResource;
import multilevelmodel.Clabject;
import multilevelmodel.Model;
import multilevelmodel.MultilevelmodelFactory;
import multilevelmodel.Slot;
import multilevelmodel.impl.ModelImpl;

public class TemporalEMFTests {
	private static TResource res;
	private static MultilevelmodelFactory modelFactory;
	
	private static String expectedSystemClabjectName = "System";
	private static String expectedAreaClabjectName = "Area";
	
	private static String expectedSystemName = "System 1";
	private static String expectedAreaName = "Area 1";
	private static String expectedItemGeneratorName = "Item Generator 1";
	private static String expectedItemName = "Item 1";
	private static String expectedCopmponentName = "Component 1";
	
	@BeforeAll
	static void beforeAll() throws IOException {
		// Prepare TemporalEMF
		File resourceFile = File.createTempFile("temf-", null);
		resourceFile.delete();
		resourceFile.deleteOnExit();
		res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile, "keyvalue"));
		
		// Prepare model factory
		modelFactory = MultilevelmodelFactory.eINSTANCE;
		
		
	}
	
	@BeforeEach
	void beforeEach() {
		// Prepare metamodel
			Model transportationLineModel = modelFactory.createModel();
			res.getContents().add(transportationLineModel);
			
			Clabject system = modelFactory.createClabject();
			transportationLineModel.getClabject().add(system);
			system.setName(expectedSystemClabjectName);
			Slot systemName = modelFactory.createSlot();
			systemName.setName("name");
			system.getSlot().add(systemName);
			
			
			Clabject area = modelFactory.createClabject();
			transportationLineModel.getClabject().add(area);
			area.setName(expectedAreaClabjectName);
			Slot areaName = modelFactory.createSlot();
			areaName.setName("name");
			area.getSlot().add(areaName);
			
			
			// Prepare model
			Clabject system1 = modelFactory.createClabject();
			transportationLineModel.getClabject().add(system1);
			system1.setName("system1");
			system1.setType(system);
			Slot system1Name = modelFactory.createSlot();
			system1Name.setName("system 1");
			system1Name.setType(systemName);
			
			Clabject area1 = modelFactory.createClabject();
			transportationLineModel.getClabject().add(area1);
			area1.setName("area1");
			area1.setType(area);
			Slot area1Name = modelFactory.createSlot();
			area1Name.setName("area 1");
			area1Name.setType(areaName);
	}
	
	@Test
	void testAllClabjectsAvailable() {
		// Arrange
		
		// Act
		Model actualModel = (ModelImpl) res.getContents().get(0);
		
		// Assert
		assertEquals(actualModel.getClabject().size(), 4);
	}
	
	@Test
	void testAClabjectNamesCorrect() {
		// Arrange
		
		// Act
		Model actualModel = (ModelImpl) res.getContents().get(0);
		
		// Assert
		assertEquals(actualModel.getClabject().size(), 4);
	}

}
