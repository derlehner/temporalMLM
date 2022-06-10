package multilevelmodel.tests;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.uoc.som.temf.TURI;
import edu.uoc.som.temf.core.TResource;
import multilevelmodel.MultilevelmodelFactory;

public class TemporalEMFTests {
	private static TResource res;
	private static MultilevelmodelFactory modelFactory;
	
	@BeforeAll
	static void beforeAll() throws IOException {
		// Prepare TemporalEMF
		File resourceFile = File.createTempFile("temf-", null);
		resourceFile.delete();
		resourceFile.deleteOnExit();
		res = (TResource) new ResourceSetImpl().createResource(TURI.createTMapURI(resourceFile));
		
		// Prepare model factory
		modelFactory = MultilevelmodelFactory.eINSTANCE;
	}
	
	@Test
	void test() {
		
	}

}
