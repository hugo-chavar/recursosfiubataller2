package connection;

import model.Link;
import model.Recurso;
import connection.cache.Cache;
import connection.parsers.LinkParser;
import connection.parsers.Parser;
import connection.responses.OperationResponse;

public class LinkRequester extends HandlerRequester {

	private Parser parser;
	private Cache<Link> cache;
	
	public LinkRequester() {
		super();
		parser = new LinkParser();
		cache = new Cache<Link>();
		generateTestData();
	}

	private void generateTestData() {
		Link link = new Link(11002,-1, "un link a google copado");
		link.setNombre("www.google.com.ar");
		current = link;
		updateCache();
	}

//	public OperationResponse save(Link link) {
//		current = link;
//		String link_str = parser.serialize(link);
//		return save(link_str);
//	}

	public OperationResponse get(Serializable serializable) {
		getParser().setGetMode();
		current = serializable;
		String xml = parser.serialize(new Link(((Recurso) serializable).getRecursoId()));
//		System.out.println("Ahora va a querer:"+xml);
		return get(xml);
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
