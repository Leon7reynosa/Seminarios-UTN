package server;

import controllers.UsuarioController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
    	
    	//handleBars nos ayuda a generar ese dinamismo que queremos en nuestras vistas.
        Router.engine = HandlebarsTemplateEngineBuilder  
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine(); //inicializa el engine
        Spark.staticFileLocation("/public"); //esto nos lleva a la carpeta resources y public
        Router.configure();//genera la configuracion para que tire lo que queremos, los saludos
    }

    private static void configure(){
    	//Spark.get("/saludo" , (req, res) -> "Hola"); 
    	//luego de esto, ejecutar solo el server (run as) y luego poner en el chrome "localhost:9000/saludo"
    	
//    	
//    	Spark.get("/saludo/:nombre" , (req , res) -> "Hola " + req.params("nombre") );
//    	//luego de esto, ejecutar solo el server (run as) y luego poner en el chrome "localhost:9000/saludo/leon"
//    	//esto lo que hace es pasar por parametro , el : (dos puntos) es interno, no se pone en el chrome
//    	
//    	Spark.get("/saludo/*/:nombre" , (req, res) -> "Hola " + req.splat()[0] + " " +req.params("nombre"));
//    	//Splat lo que hace es conseguir todos los parametros, el asterisco en el req es para cuando no se el paramtro
//    	//en el browser ponemos "localhost:9000/saludo/leon/reynosa"
//    	
//    	Spark.get("/saludo" , (req , res) -> "Hola " + req.queryParams("nombre") + " " + req.queryParams("apellido")) ;
//    	//compilamos y en el browser ponemos http://localhost:9000/saludo?nombre=giuli&apellido=vetere (si tira hola, entro en la primera -> comentarla)
//    	
//    	//AHORA QUEREMOS HACER QUE ESTO SE DELEGUE A ALGUIEN MAS, SE LO DELEGAMOS A USUARIO CONTROLLER (ir a package controller)
    	
    	UsuarioController usuarioController = new UsuarioController();
    	
    	Spark.get("/saludo/:nombre" , usuarioController::saludo );
    	//esto es practicamente lo mismo que hicimos antes, el el browser ponemos: http://localhost:9000/saludo/leon
    	
    	Spark.get("/usuarios", usuarioController::mostrarTodos, Router.engine);
    	
    	Spark.get("/usuario/:id", usuarioController::mostrar, Router.engine);;
    	
    	Spark.post("/usuario/:id", usuarioController::modificar);
    }
}
