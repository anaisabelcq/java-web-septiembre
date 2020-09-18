package ar.com.educacionit.web.managedbeans.producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.app.domain.TipoProducto;
import ar.com.educacionit.service.ProductoService;
import ar.com.educacionit.service.impl.ProductoServiceImpl;
import ar.com.educacionit.services.exceptions.ServiceException;

@Named
@RequestScoped
public class ProductoBean {
	
	//Capa de Servicio
	private ProductoService productoService = new ProductoServiceImpl();
	
	private Producto producto = new Producto();
	private TipoProducto tipoProducto = new TipoProducto();
	
	private String mensajeError;
	
	//metodos de crud
	//FIND
	public List<Producto> findProductos() {
		List<Producto> productos;
		try {
			productos = this.productoService.findProductos();
		} catch (ServiceException e) {
			productos = new ArrayList<>();
		}
		return productos;
	}
	//CREATE
	public String crearNuevoProducto() {
		String salida;
		try {
			this.producto.setTipoProducto(this.tipoProducto);
			this.productoService.createProducto(producto);
			salida = "listado-productos";
		}catch (Exception e) {
			this.mensajeError = e.getMessage();
			salida = "nuevo-producto";
		}
		return salida;
	}
	
	//EDIT
	public String editarProducto(String codigo) {
		String salida;
		try {
			this.producto = this.productoService.getProducto(codigo);
			this.tipoProducto = producto.getTipoProducto();
			salida = "editar-producto";
		}catch (Exception e) {
			System.err.println(e);
			salida = "nuevo-producto";
		}
		return salida;
	}
	
	//ELIMINAR
	public String eliminar(String codigo) {
		String salida = "listado-productos?faces-redirect=true";
		try {
			this.productoService.eliminarProducto(codigo);
		}catch (Exception e) {
			salida = "nuevo-producto";
		}
		return salida;
	}
	
	//UPDATE
	public String updateProducto() {
		String salida = "listado-productos";
		try {
			this.producto.setTipoProducto(this.tipoProducto);
			this.productoService.updateProducto(producto);
			this.mensajeError = "Se ha actualizado el producto";
		}catch (Exception e) {
			System.err.println(e);
			this.mensajeError = e.getMessage();
			salida = "editar-producto";
		}
		return salida;
	}
	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public TipoProducto[] getTipoProductos() {
		TipoProducto[] tiposProductos;
		try {
			tiposProductos = this.productoService.findTipoProductos()
					.stream()
					.collect(Collectors.toSet())
					.toArray(new TipoProducto[] {});
		} catch (ServiceException e) {
			e.printStackTrace();
			tiposProductos = new TipoProducto[] {};
		}
		return tiposProductos;
	}
	
}