package model;

import org.junit.Assert;
import org.junit.Test;

public class EncuestaRespondidaTest {

	@Test
	public void sameRecursoDifferentObjectWithSameIdsAndAreEqual() {
		EncuestaRespondida e = new EncuestaRespondida(5, 67);
		EncuestaRespondida e2 = new EncuestaRespondida(5,67);
		Assert.assertTrue(e2.equals(e));
	}

	@Test
	public void sameRecursoDifferentObjectWithDifferentIdRecursoArentEqual() {
		EncuestaRespondida e = new EncuestaRespondida(5, 0);
		EncuestaRespondida e2 = new EncuestaRespondida(6, 67);
		Assert.assertFalse(e2.equals(e));
	}


}
