package bll;

import java.util.Iterator;
import java.util.List;

import dao.OrderDAO;
import model.Order;

public class OrderBLL {
	private OrderDAO orderDAO;

	public OrderBLL() {
		orderDAO = new OrderDAO();
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	/**
	 * 
	 * @return o lista cu toate comenzile din tabela Order
	 */
	public List<Order> findAllOrders() {
		List<Order> myList = orderDAO.findAll();

		return myList;
	}

	/**
	 * se va sterge comanda primita ca parametru din tabela Order
	 * 
	 * @param myOrder reprezinta o comanda
	 */
	public void deleteOrder(Order myOrder) {
		orderDAO.delete(myOrder);
	}

	/**
	 * comanda primita ca parametru va fi inserata in tabela Order
	 * 
	 * @param myOrder reprezinta o comanda
	 */
	public void insertOrder(Order myOrder) {
		orderDAO.insert(myOrder);
	}

	/**
	 * @param id
	 * @return comanda din tabela Order care are id_order egal cu id-ul primit ca
	 *         parametru
	 */
	public Order findById(int id) {
		return orderDAO.findById(id);
	}

	/**
	 * se va face update comenzii cu id_order egal cu id-ul primit ca parametru
	 * 
	 * @param myOrder reprezinta o comanda
	 * @param id      reprezinta un id
	 */
	public void updateOrder(Order myOrder, int id) {
		orderDAO.update(myOrder, id);
	}

	/**
	 * 
	 * @return id_order-ul cel mai mare existent in tabela Order
	 */
	public int findOrderId() {
		int maxId = 0;
		List<Order> myList = orderDAO.findAll();
		Iterator<Order> myIt = myList.iterator();
		while (myIt.hasNext()) {
			Order orderTemp = myIt.next();
			if (orderTemp.getId_order() > maxId)
				maxId = orderTemp.getId_order();
		}

		return maxId;
	}

	/**
	 * 
	 * @param idToDeleteInt reprezinta un id_client
	 * @return 1 daca clientul se afla intr-o comanda
	 */
	public int verifyClientProcessingId(int idToDeleteInt) {
		List<Order> myList = orderDAO.findAll();
		Iterator<Order> myIt = myList.iterator();
		while (myIt.hasNext()) {
			Order orderTemp = myIt.next();
			if (orderTemp.getId_client_pk() == idToDeleteInt)
				return 1;
		}

		return 0;
	}

	/**
	 * 
	 * @param idToDeleteInt reprezinta un id_product
	 * @return 1 daca produsul se afla intr-o comanda
	 */
	public int verifyProductProcessingId(int idToDeleteInt) {
		List<Order> myList = orderDAO.findAll();
		Iterator<Order> myIt = myList.iterator();
		while (myIt.hasNext()) {
			Order orderTemp = myIt.next();
			if (orderTemp.getId_product_pk() == idToDeleteInt)
				return 1;
		}

		return 0;
	}
}
