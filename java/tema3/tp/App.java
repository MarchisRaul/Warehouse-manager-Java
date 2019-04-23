package tema3.tp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import connection.ConnectionFactory;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;
import presentation.View;



public class App {
	public static void main(String[] args) {
		//ConnectionFactory myConn = new ConnectionFactory();
		System.out.println("Hello World!");
		ClientDAO myClient = new ClientDAO();
		ProductDAO myProduct = new ProductDAO();
		OrderDAO myOrder = new OrderDAO();
		String[] fields = { "name", "address", "grades" };

		Client c1 = new Client(1, "Raul", "Satu Mare", "dan_mar_chis@yahoo.com", 130f);
		myClient.insert(c1);
		c1 = new Client(2, "Gabriela", "Zalau", "gabriela@yahoo.com", 500f);
		myClient.insert(c1);
		c1 = new Client(3, "Ronaldo", "Satu Mare", "ronny@yahoo.com", 280f);
		myClient.insert(c1);

		Product p1 = new Product(1, "Laptop", 10, 150f);
		myProduct.insert(p1);
		p1 = new Product(2, "Tricou", 20, 30f);
		myProduct.insert(p1);
		p1 = new Product(3, "Telefon", 5, 250f);
		myProduct.insert(p1);

		

		View myView = new View();

		

	}
}

