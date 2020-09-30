package ar.com.educacionit.ws.rest.configuracion;

import org.glassfish.jersey.server.ResourceConfig;

import ar.com.educacionit.ws.rest.filters.AuthenticationFilter;

public class AppWSRestApplication extends ResourceConfig {

	public AppWSRestApplication() {
		register(AuthenticationFilter.class);
		register(CORSFilter.class);
	}
}
