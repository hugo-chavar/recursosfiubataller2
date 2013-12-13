package connection.cache;

import model.Encuesta;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

	@Test
	public void encuestaAddedToCacheIsIn() {
		Cache<Encuesta> cache = new Cache<Encuesta>();
		cache.add(new Encuesta(1, 1, "", false));

		Assert.assertTrue(cache.contains(new Encuesta(1, 1, "", false)));
	}

	@Test
	public void encuestasAddedToCacheAreIn() {
		Cache<Encuesta> cache = new Cache<Encuesta>();
		for (int i = 1; i <= Cache.MAX_ITEMS_LIST; i++) {
			cache.add(new Encuesta(i, 1, "", false));

		}
		for (int i = 1; i <= Cache.MAX_ITEMS_LIST; i++) {
			Assert.assertTrue(cache.contains(new Encuesta(i, 1, "", false)));
		}
	}
	
	@Test
	public void encuestaNotAddedToCacheInstIn() {
		Cache<Encuesta> cache = new Cache<Encuesta>();
		for (int i = 1; i <= Cache.MAX_ITEMS_LIST; i++) {
			cache.add(new Encuesta(i, 1, "", false));

		}
		Assert.assertFalse(cache.contains(new Encuesta(Cache.MAX_ITEMS_LIST + 1, 1, "", false)));
	}
	
	@Test
	public void encuestaFirstAddedIsOutOfCacheWhenIsFullAndAnotherIsAdded() {
		Cache<Encuesta> cache = new Cache<Encuesta>();
		for (int i = 1; i <= Cache.MAX_ITEMS_LIST + 1; i++) {
			cache.add(new Encuesta(i, 1, "", false));

		}
		Assert.assertFalse(cache.contains(new Encuesta(1, 1, "", false)));
	}
	
	@Test
	public void encuestaFirstAddedIsntOutOfCacheWhenIsFullIsUsedAndAnotherIsAdded() {
		Cache<Encuesta> cache = new Cache<Encuesta>();
		for (int i = 1; i <= Cache.MAX_ITEMS_LIST; i++) {
			cache.add(new Encuesta(i, 1, "", false));

		}
		cache.get(new Encuesta(1, 1, "", false));
		cache.add(new Encuesta(Cache.MAX_ITEMS_LIST + 1, 1, "", false));
		Assert.assertTrue(cache.contains(new Encuesta(1, 1, "", false)));
		Assert.assertFalse(cache.contains(new Encuesta(2, 1, "", false)));
	}

}
