package ar.com.educacionit.service.impl;

import java.util.List;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.app.domain.TipoProducto;
import ar.com.educacionit.dao.ProductoRepository;
import ar.com.educacionit.dao.exceptions.DuplicateException;
import ar.com.educacionit.dao.exceptions.GenericExeption;
import ar.com.educacionit.dao.hibernate.impl.ProductoRepositoryHibernateImpl;
import ar.com.educacionit.service.ProductoService;
import ar.com.educacionit.services.exceptions.ServiceException;

public class ProductoServiceImpl implements ProductoService {

	private ProductoRepository productoRepository;
	
	public ProductoServiceImpl() {
		this.productoRepository = new ProductoRepositoryHibernateImpl();
	}

	@Override
	public Producto getProducto(String codigo) throws ServiceException {
		try {
			return this.productoRepository.getProducto(codigo);
		} catch (GenericExeption e) {
			e.printStackTrace();
			throw new ServiceException("No se ha podido obtener el producto con codigo="+ codigo, e);
		}
	}

	@Override
	public List<Producto> findProductos() throws ServiceException {
		try {
			return this.productoRepository.findProductos();
		} catch (GenericExeption e) {
			e.printStackTrace();
			throw new ServiceException("No se ha podido obtener el lisado de productos",e);
		}
	}

	@Override
	public Producto createProducto(Producto producto) throws ServiceException {
		try {
			return this.productoRepository.createProducto(producto);
		} catch (DuplicateException e) {
			throw new ServiceException("Producto Duplicado: ",e);
		} catch (GenericExeption e) {
			throw new ServiceException("No se pudo crear el producto",e);
		}
	}

	@Override
	public Producto updateProducto(Producto producto) throws ServiceException {
		try {
			return this.productoRepository.updateProducto(producto);
		} catch (GenericExeption e) {
			throw new ServiceException("No se pudo actualizar el producto",e);
		}
	}

	@Override
	public Producto eliminarProducto(String codigoProducto) throws ServiceException {
		try {
			return this.productoRepository.deleteProducto(codigoProducto);
		} catch (GenericExeption e) {
			throw new ServiceException("No se pudo eliminar el producto",e);
		}
	}

	@Override
	public List<TipoProducto> findTipoProductos() throws ServiceException {
		try {
			return this.productoRepository.findTipoProductos();
		} catch (GenericExeption e) {
			throw new ServiceException("No se pudo obtener los tipos de productos",e);
		}
	}

	@Override
	public List<Producto> findProductosByDescripcion(String desripcion) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	


}
