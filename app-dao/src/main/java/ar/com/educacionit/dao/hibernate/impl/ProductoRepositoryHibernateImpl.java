package ar.com.educacionit.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.app.domain.TipoProducto;
import ar.com.educacionit.dao.ProductoRepository;
import ar.com.educacionit.dao.exceptions.DuplicatedException;
import ar.com.educacionit.dao.exceptions.GenericException;
import ar.com.educacionit.dao.hibernate.HibernateUtils;

public class ProductoRepositoryHibernateImpl implements ProductoRepository {

	private SessionFactory factory;
	
	public ProductoRepositoryHibernateImpl() {
		
		factory = HibernateUtils.getSessionFactory();
	}
	
	@Override
	public Producto getProducto(String codigo) throws GenericException {
		
		Session session = factory.getCurrentSession();

		Producto producto = null;
		
		try {

			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();

			// Create an HQL statement, query the object.
			String sql = "Select e from " + Producto.class.getName() + " e where e.codigo=:codigo ";

			// Create Query object.
			Query<Producto> query = session.createQuery(sql);

			query.setParameter("codigo", codigo);

			// Execute query.
			Optional<Producto> employees = query.uniqueResultOptional();

			if(employees.isPresent()) {
				producto = employees.get();
			}
			
			// Commit data.
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
			throw new GenericException(e.getMessage(), e);
		}
		return producto;
	}

	@Override
	public List<Producto> findProductos() {
		Session session = factory.getCurrentSession();

		List<Producto> products = new ArrayList<Producto>();
		
		try {

			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();

			// Create an HQL statement, query the object.
			String sql = "Select e from " + Producto.class.getName() + " e ";

			// Create Query object.
			Query<Producto> query = session.createQuery(sql);

			// Execute query.
			products = query.getResultList();

			// Commit data.
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
		}
		return products;
	}

	@Override
	public Producto createProducto(Producto producto) throws GenericException, DuplicatedException {
		Session session = factory.getCurrentSession();

		try {

			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();

			session.saveOrUpdate(producto);
			
			// Commit data.
			session.getTransaction().commit();
		}catch (ConstraintViolationException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new DuplicatedException(e.getCause().getMessage(),e);
		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
			throw new GenericException(e.getMessage(),e);
		}finally {
			session.close();
		}
		return producto;
	}

	@Override
	public Producto updateProducto(Producto producto) throws GenericException {
		try {
			// return createProducto(producto);
			Session session = factory.getCurrentSession();

			try {
				// All the action with DB via Hibernate
				// must be located in one transaction.
				// Start Transaction.
				session.getTransaction().begin();

				// Create an HQL statement, query the object.
				String sql = "Select e from " + Producto.class.getName() + " e where e.codigo=:codigo ";

				// Create Query object.
				Query<Producto> query = session.createQuery(sql);
				
				query.setParameter("codigo", producto.getCodigo());

				// Execute query.
				Optional<Producto> productoOptional = query.uniqueResultOptional();

				Producto productoBean = null;
				if(productoOptional.isPresent()) {
					productoBean = productoOptional.get();
					productoBean.setTitulo(producto.getTitulo());
					productoBean.setPrecio(producto.getPrecio());
					productoBean.setTipoProducto(producto.getTipoProducto());
				}

				session.saveOrUpdate(productoBean);
				
				// Commit data.
				session.getTransaction().commit();
			}catch (ConstraintViolationException e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				throw new DuplicatedException(e.getCause().getMessage(),e);
			} catch (Exception e) {
				e.printStackTrace();
				// Rollback in case of an error occurred.
				session.getTransaction().rollback();
				throw new GenericException(e.getMessage(),e);
			}finally {
				session.close();
			}
			return producto;
		} catch (DuplicatedException e) {
			throw new GenericException(e.getMessage(), e);
		}
	}

	@Override
	public Producto deleteProducto(String codigoProducto) throws GenericException {
		Producto producto = getProducto(codigoProducto);

		Session session = factory.getCurrentSession();

		try {

			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();
			
			session.remove(producto);
			
			// Commit data.
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
			throw new GenericException(e.getMessage(),e);
		}finally {
			session.close();
		}
		return producto;
	}

	@Override
	public List<TipoProducto> findTipoProductos() throws GenericException {
		Session session = factory.getCurrentSession();

		List<TipoProducto> tipoProductos = new ArrayList<TipoProducto>();
		
		try {

			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();

			// Create an HQL statement, query the object.
			String sql = "Select e from " + TipoProducto.class.getName() + " e ";

			// Create Query object.
			Query<TipoProducto> query = session.createQuery(sql);

			// Execute query.
			tipoProductos = query.getResultList();

			// Commit data.
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
		}
		return tipoProductos;
	}
	
	@Override
	public List<Producto> findByDescripcion(String desripcion) throws GenericException {
		Session session = factory.getCurrentSession();

		List<Producto> productos = new ArrayList<>();
		
		try {

			// All the action with DB via Hibernate
			// must be located in one transaction.
			// Start Transaction.
			session.getTransaction().begin();

			// Create an HQL statement, query the object.
			String sql = "Select e from " + Producto.class.getName() + " e where UPPER(e.descripcion) like :descripcion";

			// Create Query object.
			Query<Producto> query = session.createQuery(sql);

			query.setParameter("descripcion", "%"+desripcion.toUpperCase()+"%");
			
			// Execute query.
			productos = query.getResultList();

			// Commit data.
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			// Rollback in case of an error occurred.
			session.getTransaction().rollback();
		}
		return productos;
	}
}