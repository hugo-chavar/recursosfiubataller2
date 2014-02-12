package connection;

import model.Link;
import model.Recurso;
import connection.cache.Cache;
import connection.exceptions.GetException;
import connection.exceptions.ParseException;
import connection.responses.OperationResponse;

public class LinkRequester extends HandlerRequester {

	private LinkParser parser;
	private Cache<Link> cache;
	
	public LinkRequester() {
		super();
		parser = new LinkParser();
		cache = new Cache<Link>();
		generateTestData();
	}

	private void generateTestData() {
		Link link = new Link(11002,-1,"un link a google copado");
		link.setNombre("www.google.com.ar");
		current = link;
		updateCache();
	}

	public OperationResponse save(Link link) {
		current = link;
		String link_str = parser.serializeLink(link);
		System.out.println(link_str);

		return save(link_str);

	}
	
	protected Recurso retrieveCached(int recursoId) {
		return cache.get(new Link(recursoId, 0, ""));
	}
	
	public boolean cacheContains(int recursoId) {
		return cache.contains(new Link(recursoId, 0, ""));
	}

	public OperationResponse get(Recurso recurso) {

		current = recurso;
		String xml = this.parser.serializeQueryByType(recurso.getRecursoId(), LinkParser.LINK_TAG);
		try {
			return get(xml);
		} catch (GetException e) {
			return OperationResponse.createFailed(e.getMessage());
		} catch (ParseException e) {
			return OperationResponse.createFailed(e.getMessage());
		}
		
	}

	public void deleteFromCache(int recursoId) {
		cache.remove(new Link(recursoId, 0, ""));
	}

	@Override
	protected void deserialize(String xml_resp_e) throws ParseException {
		current = parser.deserialize(xml_resp_e);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Cache getCache() {
		return cache;
	}

	@Override
	protected Parser getParser() {
		return parser;
	}

}
