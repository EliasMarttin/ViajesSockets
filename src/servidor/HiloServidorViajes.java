package servidor;

import java.io.IOException;
import java.net.SocketException;

import gestor.GestorViajes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import comun.MyStreamSocket;
import org.json.simple.parser.ParseException;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del servicio de viajes.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

class HiloServidorViajes implements Runnable {


	private MyStreamSocket myDataSocket;
	private GestorViajes gestor;
	private JSONParser parser ;
	JSONObject objeto;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 * @param	unGestor		gestor de viajes
	 */
	HiloServidorViajes(MyStreamSocket myDataSocket, GestorViajes unGestor) {

		// TODO
		this.myDataSocket = myDataSocket;
		this.gestor = unGestor;
		parser = new JSONParser();
	}

	/**
	 * Gestiona una sesion con un cliente
	 */
	public void run( ) {
		//TODO
		String operacion = "";
		String origen = "";
		String codViaje = "";
		String codCli = "" ;
		String destino = "" ;;
		String fecha = "" ;
		long precio = 0;
		long numplazas = 0 ;

		boolean done = false;
		try {
			objeto = (JSONObject) parser.parse(myDataSocket.receiveMessage());
			System.out.println(objeto.toString());

			operacion = (String) objeto.get("Operacion");
			origen = (String) objeto.get("codOrigen");



		}catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		//Aqui es donde se tiene que hacer la lógica de ver que tipo de mensaje es y como tratarlo
		try {

			while (!done) {
				// Recibe una petición del cliente
				// Extrae la operación y sus parámetros

				switch (operacion) {
				case "0":
					// ...
					break;

				case "1": { // Consulta los viajes con un origen dado
					origen = (String) objeto.get("codOrigen");
					JSONArray resOrigen = gestor.consultaViajes(origen);
					myDataSocket.sendMessage(resOrigen.toJSONString());
					break;
				}
				case "2": { // Reserva una plaza en un viaje
					codViaje = (String) objeto.get("codViaje");
					codCli = (String) objeto.get("codCli");
					JSONObject resReserva = gestor.reservaViaje(codViaje,codCli);
					myDataSocket.sendMessage(resReserva.toJSONString());
					break;
				}
				case "3": { // Oferta un viaje
					// ...
					codCli = (String) objeto.get("codCli");
					origen = (String) objeto.get("codOrigen");
					destino = (String) objeto.get("destino");
					fecha = (String) objeto.get("fecha");
					precio = (long) objeto.get("precio");
					numplazas = (long) objeto.get("numplazas");
					JSONObject resOferta = gestor.ofertaViaje(codCli,origen,destino,fecha,precio,numplazas);
					myDataSocket.sendMessage(resOferta.toJSONString());
					break;
				}
				case "4": { // Borra un viaje
					codViaje = (String) objeto.get("codViaje");
					codCli = (String) objeto.get("codCli");
					JSONObject resBorraViaje = gestor.borraViaje(codViaje,codCli);
					myDataSocket.sendMessage(resBorraViaje.toJSONString());
					break;
				}

				} // fin switch
			} // fin while
		} // fin try
		catch (SocketException ex) {
			System.out.println("Capturada SocketException");
		}
		catch (IOException ex) {
			System.out.println("Capturada IOException");
		}
		catch (Exception ex) {
			System.out.println("Exception caught in thread: " + ex);
		} // fin catch*/
	} //fin run

} //fin class
