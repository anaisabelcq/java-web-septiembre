package ar.com.educacionit.ws.rest.resources;

import java.util.Base64;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import ar.com.educacionit.app.domain.User;
import ar.com.educacionit.service.UserService;
import ar.com.educacionit.service.impl.UserServiceImpl;

@Path("auth")
public class AuthResource {

	private UserService userService = new UserServiceImpl();
	
	@PermitAll
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(
			@QueryParam("username") String username,
			@QueryParam("password") String password
	) {
		
		ResponseBuilder response = Response.ok();
		
		try {
			User user = this.userService.getUserByUserName(username);
			
			if(user != null && user.getPassword().equals(password)) {
				
				String basic = user.getUsername()+":"+user.getPassword();
				
				//codificar
				String basicEncode = new String(Base64.getEncoder().encode(basic.getBytes()));
				
				response.header("Access-Token", basicEncode);
			}else {
				response = Response.status(Status.UNAUTHORIZED);
			}
		}catch (Exception e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		} 
			
		return response.build();
	}
	
}