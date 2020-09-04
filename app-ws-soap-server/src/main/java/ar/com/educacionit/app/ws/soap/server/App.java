package ar.com.educacionit.app.ws.soap.server;

import java.util.List;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.service.impl.ProductoServiceImpl;
import ar.com.educacionit.services.ProductoService;
import ar.com.educacionit.services.exceptions.ServiceException;

public class App 
{
	
    public static void main( String[] args ) throws ServiceException
    {
    	ProductoService ps = new ProductoServiceImpl();
    	
    	List<Producto> productos = ps.findProductos();
    	
    	System.out.println(productos);
    }
}