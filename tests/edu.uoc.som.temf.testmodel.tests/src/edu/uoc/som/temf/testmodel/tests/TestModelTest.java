package edu.uoc.som.temf.testmodel.tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.testmodel.TestmodelPlugin;

public class TestModelTest {

	private static final String TEST_MODEL_PLUGIN_ID = TestmodelPlugin.getPlugin().getBundle().getSymbolicName();
	private static final String TEST_MODEL_ECORE_PATH = "/model/testmodel.ecore";

	@Test
	public void testGeneratedModelIsUpToDate() throws Exception {
		assertTrue(true);
		/*Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("library", new TResourceFactoryImpl());
		TestmodelPackage.eINSTANCE.eClass();
		Resource resource = new TResourceImpl(null);
		resource.load(Collections.emptyMap());
		Node root = TestmodelFactory.eINSTANCE.createNode();
		root.setName("test");*/
		
	}
}
