package edu.uoc.som.temf.testmodel.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.core.impl.TimescaleTResourceImpl;
import edu.uoc.som.temf.testmodel.Node;
import edu.uoc.som.temf.testmodel.TestmodelFactory;
import edu.uoc.som.temf.testmodel.TestmodelPlugin;

public class TestModelTest {

	private static final String TEST_MODEL_PLUGIN_ID = TestmodelPlugin.getPlugin().getBundle().getSymbolicName();
	private static final String TEST_MODEL_ECORE_PATH = "/model/testmodel.ecore";

	@Test
	void testModelTest_testGetContents_containsCurrentElement() throws Exception {
		
		
		//Resource resource = new XMIResourceImpl(URI.createPlatformPluginURI(TEST_MODEL_PLUGIN_ID + TEST_MODEL_ECORE_PATH, true));
		//resource.load(Collections.emptyMap());
		Resource resource = new TimescaleTResourceImpl(URI.createPlatformPluginURI(TEST_MODEL_PLUGIN_ID + TEST_MODEL_ECORE_PATH, true));
		TestmodelFactory modelFactory = TestmodelFactory.eINSTANCE;
		
		Node expected = modelFactory.createNode();
		expected.setName("Root Node");
		System.out.println("Name: " + expected.getName());
		expected.getName();
		/*if(resource.getContents().contains(expected)) {
			resource.getContents().remove(expected);
		}*/
		
		// ACT 
		//resource.getAllContents().forEachRemaining(System.out.println("Test")));//.add(expected);
		//resource.load(Collections.emptyMap());
		resource.getContents().add(expected);
		//System.out.println(resource.getContents().size());
		//Node actual = (Node) resource.getContents().get(0);
		System.out.println(resource.getContents().toString());
		
		
		// ASSERT
		//assertEquals(expected.getName(), actual.getName());
		//EPackage ePackage = (EPackage) resource.getContents().get(0);
		//resource.getAllContents().forEachRemaining(action);
		//ePackage.
	}
}
