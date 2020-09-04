package ar.com.educacionit.dao;

import java.util.List;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.app.domain.TipoProducto;
import ar.com.educacionit.dao.exceptions.DuplicatedException;
import ar.com.educacionit.dao.exceptions.GenericException;


public interface ProductoRepository {

	public Producto getProducto(String codigo) throws GenericException;

	public List<Producto> findProductos() throws GenericException;

	public Producto createProducto(Producto producto) throws DuplicatedException, GenericException;
	
	public Producto updateProducto(Producto producto) throws GenericException;

	public Producto deleteProducto(String codigoProducto) throws GenericException;
	
	public List<TipoProducto> findTipoProductos() throws GenericException;

	public List<Producto> findByDescripcion(String desripcion) throws GenericException;
}