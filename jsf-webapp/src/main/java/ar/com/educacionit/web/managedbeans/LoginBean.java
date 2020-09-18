package ar.com.educacionit.web.managedbeans;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ar.com.educacionit.app.domain.User;
import ar.com.educacionit.service.UserService;
import ar.com.educacionit.service.impl.UserServiceImpl;
import ar.com.educacionit.services.exceptions.ServiceException;
import ar.com.educacionit.web.managedbeans.usuario.UsuarioBean;

@Named
@RequestScoped
public class LoginBean {

	private String error;
	
	private String password;
	
	private String user;
	
	private UserService userService = new UserServiceImpl();
	
	@Inject
	private UsuarioBean usuarioBean;
	
	public String login() {
		
		User user;
		try {
			user = userService.getUserByUserName("ADMIN");
			
			if(user !=null && user.getPassword().equals(password)) {
				
				this.usuarioBean.setUsuario(user);
				
				return "login-success";
			    
			}else {
				return "login-fail";
			}
		} catch (ServiceException e) {
			error = e.getMessage();
			return "login";
		}
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}