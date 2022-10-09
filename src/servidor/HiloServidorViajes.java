package servidor;

import java.io.IOException;
import java.net.SocketException;

import gestor.GestorViajes;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import comun.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del servicio de viajes.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

class HiloServidorViajes implements Runnable {


	private MyStreamSocket myDataSocket;
	private GestorViajes gestor;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 * @param	unGestor		gestor de viajes
	 */
	HiloServidorViajes(MyStreamSocket myDataSocket, GestorViajes unGestor) {
		// TODO
		this.myDataSocket = myDataSocket;
		this.gestor = unGestor;
	}

	/**
	 * Gestiona una sesion con un cliente
	 */
	public void run( ) {
		//TODO
		String operacion = "1";
		String origen = "Castellón";
		String codViaje = null;
		String codCli = null ;
		String destino = null ;;
		String fecha = null ;
		long precio = 0;
		long numplazas = 0 ;

		boolean done = false;

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
					JSONArray resOrigen = gestor.consultaViajes(origen);
					myDataSocket.sendMessage(resOrigen.toJSONString());
					myDataSocket.close();
					break;
				}
				case "2": { // Reserva una plaza en un viaje

					JSONObject resReserva = gestor.reservaViaje(codViaje,codCli);
					myDataSocket.sendMessage(resReserva.toJSONString());
					myDataSocket.close();
					break;
				}
				case "3": { // Oferta un viaje
					// ...
					JSONObject resOferta = gestor.ofertaViaje(codCli,origen,destino,fecha,precio,numplazas);
					myDataSocket.sendMessage(resOferta.toJSONString());
					myDataSocket.close();
					break;
				}
				case "4": { // Borra un viaje
					// ...
					JSONObject resBorraViaje = gestor.borraViaje(codViaje,codCli);
					myDataSocket.sendMessage(resBorraViaje.toJSONString());
					myDataSocket.close();

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
