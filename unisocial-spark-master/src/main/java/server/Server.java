package server;

import spark.Spark;
import spark.debug.DebugScreen;

//levanta un servidor en el puerto 9000
public class Server {
	public static void main(String[] args) {
		Spark.port(9000);//esto nos inicia un servidor con el puerto 9000
		Router.init();
		DebugScreen.enableDebugScreen();//nos muestra los errores en la pantalla  (luego deberia comentarse, es de practica)
	}
}
