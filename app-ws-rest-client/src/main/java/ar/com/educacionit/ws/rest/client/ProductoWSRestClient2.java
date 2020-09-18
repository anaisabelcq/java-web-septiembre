package ar.com.educacionit.ws.rest.client;

import ar.com.educacionit.app.domain.Producto;
import ar.com.educacionit.ws.rest.client.client.ProductoRestExecutor;

public class ProductoWSRestClient2 
{
    public static void main( String[] args ) throws Exception
    {
        
    	ProductoRestExecutor pre = new ProductoRestExecutor("http://localhost:8081/app-ws-rest-server/api/producto/0001");
    	
    	Producto p = pre.executeRestCall();
    	
    	System.out.println(p);
    }
}
