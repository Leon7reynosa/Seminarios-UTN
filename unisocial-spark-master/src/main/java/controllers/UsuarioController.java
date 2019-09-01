package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Usuario;
import repositories.RepositorioUsuario;
import repositories.factories.FactoryRepositorioUsuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class UsuarioController {
	private RepositorioUsuario repo;

	public UsuarioController() {
		
		this.repo = FactoryRepositorioUsuario.get();
		//este repo es el que tiene la lista de usuarios.
	}
	
	public String saludo(Request request , Response response) {
		
		String nombre = request.params("nombre");
		
		String saludo  = "Hola " + nombre;
		
		return saludo;
		 
	}
	
	public ModelAndView mostrarTodos(Request request, Response response) {
		//queremos tener nuestras entities de Usuario
		//para esto necesitamos usar nuestro repositorio de usuario
		
		List<Usuario> usuarios = this.repo.buscarTodos();
		
		//ya tenemos los usuarios del repo, ahora hay que mandar a que se cree la vista en el handle bar
		//estos handlebar estan en Resources -> Templates ; son simplemente unos html
		
		Map<String, Object> paramtros = new HashMap<String, Object>();
		paramtros.put("usuarios" ,usuarios);
		
		ModelAndView vista = new ModelAndView(paramtros, "usuarios.hbs");
		
		return vista;
		
	}
	
	public ModelAndView mostrar (Request request , Response response) {
		
		int id = new Integer(request.params("id"));
		
		Usuario usuarioBuscado = this.repo.buscar(id);
		
		Map<String , Object> parametros = new HashMap<String, Object>();
		parametros.put("usuario" , usuarioBuscado);
		
		ModelAndView vista = new ModelAndView(parametros, "usuario.hbs");
		
		return vista;	
	}
	
	public Response modificar(Request request, Response response) {
		
		int id = new Integer(request.params("id"));
		
		Usuario usuarioBuscado = this.repo.buscar(id);
		
		String nombre = request.queryParams("nombre");
		usuarioBuscado.setNombre(nombre);
		
		String apellido = request.queryParams("apellido");
		usuarioBuscado.setApellido(apellido);
		
		String telefono = request.queryParams("telefono");
		if(!telefono.isEmpty()) {
			int telefonoEntero = new Integer(telefono);
			usuarioBuscado.setTelefono(telefonoEntero);
		}
		
		this.repo.modificar(usuarioBuscado);
		
		response.redirect("/usuarios"); //esto para que cuadno termine de modificar, vuelve a la pagina principal
		
		return response;
	}
	
	
}
