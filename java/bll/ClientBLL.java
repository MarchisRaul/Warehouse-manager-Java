package bll;

import java.util.Iterator;
import java.util.List;

import dao.ClientDAO;
import model.Client;

public class ClientBLL {
	private ClientDAO clientDAO;

	public ClientBLL() {
		clientDAO = new ClientDAO();
	}

	public ClientDAO getClientDAO() {
		return clientDAO;
	}

	/**
	 * 
	 * @return o lista cu toti clientii din tabela Client
	 */
	public List<Client> findAllClients() {
		List<Client> myList = clientDAO.findAll();

		return myList;
	}

	/**
	 * se va sterge clientul primit ca parametru din tabela Client
	 * 
	 * @param myClient reprezinta un client
	 */
	public void deleteClient(Client myClient) {
		clientDAO.delete(myClient);
	}

	/**
	 * clientul primit ca parametru va fi inserat in tabela Client
	 * 
	 * @param myClient reprezinta un client
	 */
	public void insertClient(Client myClient) {
		clientDAO.insert(myClient);
	}

	/**
	 * 
	 * @param id reprezinta un id
	 * @return clientul din tabela Client care are id_client egal cu id-ul primit ca
	 *         parametru
	 */
	public Client findById(int id) {
		return clientDAO.findById(id);
	}

	/**
	 * metoda va face update pe clientul din tabel care are id_client egal cu id-ul
	 * primit ca parametru
	 * 
	 * @param myClient reprezinta un client din tabela Client
	 * @param id       reprezinta un id
	 */
	public void updateClient(Client myClient, int id) {
		clientDAO.update(myClient, id);
	}

	/**
	 * 
	 * @return id_client-ul cel mai mare existent in tabela
	 */
	public int findClientId() {
		int maxId = 0;
		List<Client> myList = clientDAO.findAll();
		Iterator<Client> myIt = myList.iterator();
		while (myIt.hasNext()) {
			Client clientTemp = myIt.next();
			if (clientTemp.getId_client() > maxId)
				maxId = clientTemp.getId_client();
		}

		return maxId;
	}

}
