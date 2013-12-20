package model;

import org.junit.Assert;
import org.junit.Test;

public class EncuestaRespondidaTest {

	@Test
	public void sameRecursoDifferentObjectWithSameIdsAreEqual() {
		EncuestaRespondida e = new EncuestaRespondida(5, 1, 0);
		EncuestaRespondida e2 = new EncuestaRespondida(5, 1, 67);
		Assert.assertTrue(e2.equals(e));
	}

	@Test
	public void sameRecursoDifferentObjectWithDifferentIdUsuarioArentEqual() {
		EncuestaRespondida e = new EncuestaRespondida(5, 1, 0);
		EncuestaRespondida e2 = new EncuestaRespondida(6, 1, 67);
		Assert.assertFalse(e2.equals(e));
	}

	@Test
	public void sameRecursoDifferentObjectWithDifferentIdRecursoArentEqual() {
		EncuestaRespondida e = new EncuestaRespondida(5, 1, 0);
		EncuestaRespondida e2 = new EncuestaRespondida(5, 1, 0);
		Assert.assertFalse(e2.equals(e));
	}

}
