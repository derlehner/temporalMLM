package edu.uoc.som.temf.testmodel.tests;

import java.util.Collections;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import edu.uoc.som.temf.core.impl.TResourceFactoryImpl;
import edu.uoc.som.temf.core.impl.TResourceImpl;
import edu.uoc.som.temf.testmodel.Node;
import edu.uoc.som.temf.testmodel.TestmodelFactory;
import edu.uoc.som.temf.testmodel.TestmodelPackage;
import edu.uoc.som.temf.testmodel.TestmodelPlugin;
import junit.framework.Assert;

public class TestModelTest {

	private static final String TEST_MODEL_PLUGIN_ID = TestmodelPlugin.getPlugin().getBundle().getSymbolicName();
	private static final String TEST_MODEL_ECORE_PATH = "/model/testmodel.ecore";

	@Test
	public void testGeneratedModelIsUpToDate() throws Exception {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("library", new TResourceFactoryImpl());
		TestmodelPackage.eINSTANCE.eClass();
		Resource resource = new TResourceImpl(null);
		resource.load(Collections.emptyMap());
		Node root = TestmodelFactory.eINSTANCE.createNode();
		root.setName("test");
		Assert.assertTrue(resource.getContents().size() > 0);
		
	}
}
