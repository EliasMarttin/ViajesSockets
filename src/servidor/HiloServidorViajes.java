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
	String operacion;
	String origen;
	String codViaje;
	String codCli;
	String destino;
	String fecha ;
	long precio;
	long numplazas;

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


		boolean done = false;
		try {
			while (!done) {
				// Recibe una petición del cliente
				// Extrae la operación y sus parámetros
				try {
					objeto = (JSONObject) parser.parse(myDataSocket.receiveMessage());

					System.out.println(objeto.toString());//Esta línea es opcional, pero la uso para ver que se está enviando los datos.
					operacion = (String) objeto.get("Operacion");

				} catch (ParseException | IOException e) {
					throw new RuntimeException(e);
				}


				switch (operacion) {
				case "0":
					gestor.guardaDatos();
					myDataSocket.sendMessage("Cerrando sesión...");
					myDataSocket.close();
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
				case "3": {// Elimina Reserva
					codViaje = (String) objeto.get("codViaje");
					codCli = (String) objeto.get("codCli");
					JSONObject  resElminar = gestor.anulaReserva(codViaje, codCli);
					myDataSocket.sendMessage(resElminar.toJSONString());
					break;
				}
				case "4": { // Oferta un viaje
					// ...
					codCli = (String) objeto.get("codCli");
					origen = (String) objeto.get("codOrigen");
					destino = (String) objeto.get("destino");
					fecha = (String) objeto.get("fecha");
					precio =  Long.parseLong( objeto.get("precio").toString());
					numplazas =  Long.parseLong(objeto.get("numplazas").toString());
					JSONObject resOferta = gestor.ofertaViaje(codCli,origen,destino,fecha,precio,numplazas);
					myDataSocket.sendMessage(resOferta.toJSONString());
					break;
				}
				case "5": { // Borra un viaje
					codViaje = (String) objeto.get("codViaje");
					codCli = (String) objeto.get("codCliente");
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
