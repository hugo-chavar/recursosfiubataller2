
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import service.Encuesta;
import service.Materials;
import service.MaterialsImplService;
import service.Pregunta;

public class Client {

	public static void main(String[] args) {
		MaterialsImplService service = new MaterialsImplService();
		Materials port = service.getMaterialsImplPort();
		System.out.println("------->>  Probando el servicio");
		System.out.println(port.sayHello("Programador"));
		System.out.println("------->>  ESTA TODO OK!");

		System.out.println("Ingrese algo para continuar y probar las encuestas : ");

		try {
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			bufferRead.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
		Encuesta enc = port.getEncuesta(0, 0);

		System.out.println("TITULO ENCUESTA: " + enc.getDescripcion());

		List<Pregunta> preguntas = enc.getPreguntas();
		for (Pregunta p : preguntas) {
			System.out.println("\t" + p.getEnunciado());
		}
		} catch (Exception e) {
			System.out.println("Encuesta inexistente!");
		}

	}

}
