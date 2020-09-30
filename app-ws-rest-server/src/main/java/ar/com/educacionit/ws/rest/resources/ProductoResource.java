package ar.com.educacionit.ws.rest.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.service.ProductoService;
import ar.com.educacionit.service.impl.ProductoServiceImpl;
import ar.com.educacionit.services.exceptions.ServiceException;

@Path("producto")
public class ProductoResource {

	private ProductoService productoService;
	
	public ProductoResource() {
		this.productoService = new ProductoServiceImpl();
	}
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllProductos() {
		try {
			List<Producto> productos = this.productoService.findProductos();
			return Response.ok(productos).build();
		} catch (ServiceException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GET
	@Path("/{codigoProducto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductobyCodigo(@PathParam("codigoProducto") String codigo) {
		
		ResponseBuilder response;
		
		try {
			
			Producto producto = this.productoService.getProducto(codigo);
			
			if(producto == null) {
				response = Response.status(Status.NOT_FOUND);
			}else {
				response = Response.ok(producto);
			}
		} catch (ServiceException e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		
		return response.build();
	}
	
	@RolesAllowed({"ADMIN","USER"})
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createProducto(Producto producto) {
		
		try {
			Producto productoCreado = this.productoService.createProducto(producto);
			return Response.status(Status.CREATED).entity(productoCreado).build();
		} catch (ServiceException e) {
			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity(producto).build();
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProducto(Producto producto) {
		
		//validaciones
		if(producto.getPrecio() == null) {
			ProductoRespuestaDTO error = new ProductoRespuestaDTO("Precio es requerido", ProductoRestWSErrorEnum.PRECIO_REQUERIDO);
			return Response.status(Status.BAD_REQUEST).entity(error).build();
		}
		//mas validaciones
		
		ResponseBuilder response; 
		try {
			//existencia
			Producto productoActualizado = this.productoService.getProducto(producto.getCodigo());
			if(productoActualizado == null ) {
				ProductoRespuestaDTO error = new ProductoRespuestaDTO("Producto codigo="+producto.getCodigo() + " inexistente", ProductoRestWSErrorEnum.PRODUCTO_INEXISTENTE);
				return Response.status(Status.BAD_REQUEST).entity(error).build();
			}
			productoActualizado = this.productoService.updateProducto(producto);
			response = Response.ok(productoActualizado);
		} catch (ServiceException e) {
			e.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR);
		}
		
		return response.build();
	}
}