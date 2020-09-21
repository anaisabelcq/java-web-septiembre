package ar.com.educacionit.web.managedbeans.producto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.app.domain.TipoProducto;
import ar.com.educacionit.service.ProductoService;
import ar.com.educacionit.service.impl.ProductoServiceImpl;
import ar.com.educacionit.services.exceptions.ServiceException;

@Named
@ViewScoped
public class ProductoBean implements Serializable  {

	private static final long serialVersionUID = 4457738492699245320L;

	//capa de servicio
	private ProductoService productoService = new ProductoServiceImpl();
	
	private Producto producto = new Producto();
	private Long tipoProducto;
	
	private String mensajeError;
	
	private List<Producto> productos;
	
	@PostConstruct
	public void loadProductos() {
		this.productos = findProductos();
	}
	
	//metodos de crud
	public List<Producto> findProductos() {
		List<Producto> productos;
		try {
			productos = this.productoService.findProductos();
		} catch (ServiceException e) {
			productos = new ArrayList<>();
		}
		return productos;
	}
	
	public String crearNuevoProducto() {
		String salida;
		try {
			TipoProducto nuevoTipoProducto = new TipoProducto();
			nuevoTipoProducto.setId(this.tipoProducto);
			
			this.producto.setTipoProducto(nuevoTipoProducto);
			this.productoService.createProducto(producto);
			salida = "listado-productos?faces-redirect=true";
		}catch (Exception e) {
			this.mensajeError = e.getMessage();
			salida = "nuevo-producto";
		}
		return salida;
	}
	
	public String editarProducto(String codigo) {
		String salida;
		try {
			this.producto = this.productoService.getProducto(codigo);
			this.tipoProducto = producto.getTipoProducto().getId();
			salida = "editar-producto";
		}catch (Exception e) {
			System.err.println(e);
			salida = "nuevo-producto";
		}
		return salida;
	}
	
	public String eliminar(String codigo) {
		String salida = "listado-productos?faces-redirect=true";
		try {
			this.productoService.eliminarProducto(codigo);
		}catch (Exception e) {
			salida = "nuevo-producto";
		}
		return salida;
	}
	
	public String updateProducto() {
		String salida = "listado-productos";
		try {
			TipoProducto nuevoTipoProducto = new TipoProducto();
			nuevoTipoProducto.setId(this.tipoProducto);
			
			this.producto.setTipoProducto(nuevoTipoProducto);
			this.productoService.updateProducto(producto);
			this.mensajeError = "Se ha actualizado el producto";
		}catch (Exception e) {
			System.err.println(e);
			this.mensajeError = e.getMessage();
			salida = "editar-producto";
		}
		return salida;
	}

	public ProductoService getProductoService() {
		return productoService;
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
	
	public Long getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(Long tipoProducto) {
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	//METODOS AGREGADOS PARA PRIMERFACES
	public void onRowSelect(SelectEvent<Producto> event) {
		this.producto = event.getObject();
    }
	
	public void onRowEdit(RowEditEvent<Producto> event) {
		FacesMessage msg;
		try {
			if(!event.getObject().getTipoProducto().getId().equals(this.tipoProducto)) {
				event.getObject().getTipoProducto().setId(this.tipoProducto);
			}
			this.productoService.updateProducto(event.getObject());
			msg = new FacesMessage("Producto editado ", event.getObject().getId().toString());
			this.productos = findProductos();
		} catch (ServiceException e) {
			msg = new FacesMessage(e.getMessage(), event.getObject().getId().toString());
		}
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent<Producto> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", event.getObject().getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
	public void eliminarProducto() {
		
		FacesMessage msg;
		try {
			this.productoService.eliminarProducto(producto.getCodigo());
			msg = new FacesMessage("Producto eliminado ", producto.getId().toString());
			this.productos.remove(producto);
			this.producto = null;
		} catch (Exception e) {
			msg = new FacesMessage("Error eliminando producto", e.getMessage());
		}
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}