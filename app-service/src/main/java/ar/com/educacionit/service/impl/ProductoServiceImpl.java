package ar.com.educacionit.service.impl;

import java.util.List;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.app.domain.TipoProducto;
import ar.com.educacionit.dao.ProductoRepository;
import ar.com.educacionit.dao.exceptions.GenericException;
import ar.com.educacionit.dao.hibernate.impl.ProductoRepositoryHibernateImpl;
import ar.com.educacionit.services.ProductoService;
import ar.com.educacionit.services.exceptions.ServiceException;

public class ProductoServiceImpl implements ProductoService {

	private ProductoRepository productoRepository;
	
	public ProductoServiceImpl() {
		this.productoRepository = new ProductoRepositoryHibernateImpl();
	}

	@Override
	public Producto getProducto(String codigo) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> findProductos() throws ServiceException {
		try {
			return this.productoRepository.findProductos();
		} catch (GenericException e) {
			e.printStackTrace();
			throw new ServiceException("No se ha podido obtener el lisado de productos",e);
		}
	}

	@Override
	public Producto createProducto(Producto producto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto updateProducto(Producto producto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto eliminarProducto(String codigoProducto) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoProducto> findTipoProductos() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Producto> findProductosByDescripcion(String desripcion) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	


}