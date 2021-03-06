package model;

import org.junit.Assert;
import org.junit.Test;

public class RecursoTest {

	@Test
	public void differentRecursoWithSameIdsAreEqual() {
		Encuesta e = new Encuesta(1, 5, "", false);
		Link l = new Link(1, 5, "");
		Assert.assertTrue(l.equals(e));
	}

	@Test
	public void sameRecursoDifferentObjectWithSameIdsAreEqual() {
		Encuesta e = new Encuesta(1, 5, "", false);
		Encuesta e2 = new Encuesta(1, 5, "otra encuesta", true);
		Assert.assertTrue(e2.equals(e));
	}

	@Test
	public void sameRecursoDifferentObjectWithDifferentIdAmbienteAreEqual() {
		Encuesta e = new Encuesta(1, 5, "", false);
		Encuesta e2 = new Encuesta(1, 6, "otra encuesta", true);
		Assert.assertTrue(e2.equals(e));
	}
	
	@Test
	public void sameRecursoDifferentObjectWithDifferentIdRecursoArentEqual() {
		Encuesta e = new Encuesta(1, 5, "", false);
		Encuesta e2 = new Encuesta(3, 5, "", false);
		Assert.assertFalse(e2.equals(e));
	}

}
