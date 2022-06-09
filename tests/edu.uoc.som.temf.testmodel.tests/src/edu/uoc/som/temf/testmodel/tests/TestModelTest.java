package edu.uoc.som.temf.testmodel.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.TURI;
import edu.uoc.som.temf.core.TResource;
import edu.uoc.som.temf.testmodel.Node;
import edu.uoc.som.temf.testmodel.TestmodelFactory;
import edu.uoc.som.temf.testmodel.TestmodelPlugin;
import edu.uoc.som.temf.testmodel.impl.NodeImpl;

public class TestModelTest {

	private static final String TEST_MODEL_PLUGIN_ID = TestmodelPlugin.getPlugin().getBundle().getSymbolicName();
	private static final String TEST_MODEL_ECORE_PATH = "/model/testmodel.ecore";
	
	private static TResource res;
	private static TestmodelFactory modelFactory;
	
	@BeforeAll
	static void beforeAll() throws IOException {
		File resourceFile = File.createTempFile("temf-", null);
		resourceFile.delete();
		resourceFile.deleteOnExit();
		res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile));
		
		modelFactory = TestmodelFactory.eINSTANCE;
		
	}

	@Test
	void testModelTest_testGetContents_containsCurrentElement() throws Exception {
		
		System.out.println(res.getContents().toString());
		Node root = modelFactory.createNode();
		root.setName("Root Node");
		res.getContents().add(root);
		Node leftChild = modelFactory.createNode();
		leftChild.setName("Left Child");
		Node rightChild = modelFactory.createNode();
		rightChild.setName("Right Child");
		System.out.println(root.isSetChildren());
		//root.getChildren().set(0, leftChild);
		root.getChildren().add(leftChild);	// getChildren uses eGet from EMF-Class BasicEObject
		root.getChildren().add(rightChild);
		res.save(null);
		/*if(resource.getContents().contains(expected)) {
			resource.getContents().remove(expected);
		}*/
		
		// ACT 
		//resource.getAllContents().forEachRemaining(System.out.println("Test")));//.add(expected);
		//resource.load(Collections.emptyMap());
		//System.out.println(resource.getContents().size());
		//Node actual = (Node) resource.getContents().get(0);
		System.out.println(res.getContents().toString());
		Node actualRoot = (NodeImpl)res.getContents().get(0);
		System.out.println(actualRoot.getChildren());
		
		
		// ASSERT
		//assertEquals(expected.getName(), actual.getName());
		//EPackage ePackage = (EPackage) resource.getContents().get(0);
		//resource.getAllContents().forEachRemaining(action);
		//ePackage.
	}
}
