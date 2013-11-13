package service;

import javax.jws.WebService;

import model.Encuesta;

@WebService(endpointInterface = "service.Materials")
public class MaterialsImpl implements Materials {

	@Override
	public String sayHello(String name) {
		return "Hello, Welcom to jax-ws " + name;
	}
//
//	@Override
//	public Node getTree() {
//		Node n = new Node();
//		n.fill();
//		return n;
//	}

	@Override
	public Encuesta getEncuesta() {
		Encuesta e = new Encuesta();
		e.rellenar();
		return e;
	}

}
