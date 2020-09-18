package ar.com.educacionit.web.managedbeans.usuario;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ar.com.educacionit.app.domain.User;

@Named
@SessionScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 6827752261604502841L;
	
	private User usuario;
	private String[] roles;
	
	public boolean logueado() {
		return this.usuario != null;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
		
		if(usuario != null) {
			//stream + lambda
			Set<String> roles = this.usuario.getRoles()
				.stream()
				.map( role -> role.getRole())
				.collect(Collectors.toSet());
			
			int i=0;
			this.roles = new String[roles.size()];
			for(String rol : roles ) {
				this.roles[i++] = rol;
			}
		}
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	public String[] getUserRoles() {
		Set<String> rolesUsuario = this.usuario.getRoles()
			.stream()
			.map(rol -> rol.getRole())
			.collect(Collectors.toSet());
	
		String[] rolesArray = rolesUsuario.toArray(new  String[] {});
		return rolesArray;
	}
	//logout
	
	//getRoles
	
}