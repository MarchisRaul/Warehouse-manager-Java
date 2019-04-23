package bll;

import java.util.Iterator;
import java.util.List;

import dao.ProductDAO;
import model.Product;

public class ProductBLL {
	private ProductDAO productDAO;

	public ProductBLL() {
		productDAO = new ProductDAO();
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	/**
	 * 
	 * @return o lista cu toate produsele din tabela Product
	 */
	public List<Product> findAllProducts() {
		List<Product> myList = productDAO.findAll();

		return myList;
	}

	/**
	 * metoda va sterge produsul din tabela Product
	 * 
	 * @param myProduct reprezinta un produs
	 */
	public void deleteProduct(Product myProduct) {
		productDAO.delete(myProduct);
	}

	/**
	 * metoda va insera produsul in tabela Product
	 * 
	 * @param myProduct reprezinta un produs
	 */
	public void insertProduct(Product myProduct) {
		productDAO.insert(myProduct);
	}

	/**
	 * 
	 * @param id reprezinta un id
	 * @return produsul cu id_product egal cu id-ul primit ca parametru
	 */
	public Product findById(int id) {
		return productDAO.findById(id);
	}

	/**
	 * metoda va face update produsului din tabela Product ce are id_product egal cu
	 * id-ul primit ca parametru
	 * 
	 * @param myProduct un produs
	 * @param id        un id
	 */
	public void updateProduct(Product myProduct, int id) {
		productDAO.update(myProduct, id);
	}

	/**
	 * 
	 * @return id_product-ul cel mai mare din tabela Product
	 */
	public int findProductId() {
		int maxId = 0;
		List<Product> myList = productDAO.findAll();
		Iterator<Product> myIt = myList.iterator();
		while (myIt.hasNext()) {
			Product productTemp = myIt.next();
			if (productTemp.getId_product() > maxId)
				maxId = productTemp.getId_product();
		}

		return maxId;
	}
}
