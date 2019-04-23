package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

public class View extends JFrame {

	String randomString = "";
	ClientBLL clientBLL = new ClientBLL();
	ProductBLL productBLL = new ProductBLL();
	OrderBLL orderBLL = new OrderBLL();
	Client client = new Client();
	Product produs = new Product();
	Order order = new Order();

	Writer writer = null;
	int billNumber = 1;
	JButton backButton1 = new JButton("Back");
	JButton backButton2 = new JButton("Back");
	JButton backButton3 = new JButton("Back");
	JButton clientButton;
	JPanel clientButtonPanel;
	JButton productButton;
	JPanel productButtonPanel;
	JButton orderButton;
	JPanel orderButtonPanel;
	JButton insertButton1;
	JButton insertButton2;
	JButton updateButton1;
	JButton updateButton2;
	JButton deleteButton1;
	JButton deleteButton2;
	JButton deleteButton3;
	JButton addToOrder1;
	JButton addToOrder2;
	JButton finishOrder;

	JLabel welcomeLabel;
	JPanel welcomeLabelPanel;
	JLabel pickTableLabel;
	JPanel pickTablePanel;
	JLabel[] fieldsLabel;
	JTextField[] fieldsTextField;

	JTable tableClient;
	DefaultTableModel myModelClient;

	public void setMyModelClient(DefaultTableModel model) {
		myModelClient = model;
	}

	DefaultTableModel myModelProduct;

	public void setMyModelProduct(DefaultTableModel model) {
		myModelProduct = model;
	}

	DefaultTableModel myModelOrder;

	public void setMyModelOrder(DefaultTableModel model) {
		myModelOrder = model;
	}

	JTable tableProduct;
	JTable tableOrder;

	JPanel mainPanel;
	JPanel clientPanel;
	JPanel produsPanel;
	JPanel orderPanel;

	JPanel clientMainPanel;
	JPanel clientX1Panel;
	JPanel clientYPanel;
	JPanel clientX2Panel;

	JFrame clientFrame;
	JLabel clientId = new JLabel("Introduceti id-ul clientului: ");
	JLabel clientName = new JLabel("Introducet numele clientului: ");
	JLabel clientAddress = new JLabel("Introduceti adresa clientului: ");
	JLabel clientEmail = new JLabel("Introduceti email-ul clientului: ");
	JLabel clientMoney = new JLabel("Introduceti suma de bani a clientului: ");
	JTextField clientIdField = new JTextField(10);
	JTextField clientNameField = new JTextField(10);
	JTextField clientAddressField = new JTextField(10);
	JTextField clientEmailField = new JTextField(10);
	JTextField clientMoneyField = new JTextField(10);

	JPanel clientIdF = new JPanel();
	JPanel clientNameF = new JPanel();
	JPanel clientAddressF = new JPanel();
	JPanel clientEmailF = new JPanel();
	JPanel clientMoneyF = new JPanel();

	JPanel productMainPanel;
	JPanel productX1Panel;
	JPanel productYPanel;
	JPanel productX2Panel;

	JFrame productFrame;
	JLabel productId = new JLabel("Introduceti id-ul produsului: ");
	JLabel productName = new JLabel("Introducet numele produsului: ");
	JLabel productQuantity = new JLabel("Introduceti cantitatea produsului: ");
	JLabel productPrice = new JLabel("Introduceti pretul produsului: ");

	JTextField productIdField = new JTextField(10);
	JTextField productNameField = new JTextField(10);
	JTextField productQuantityField = new JTextField(10);
	JTextField productPriceField = new JTextField(10);

	JPanel productIdF = new JPanel();
	JPanel productNameF = new JPanel();
	JPanel productQuantityF = new JPanel();
	JPanel productPriceF = new JPanel();

	// __________________________

	JPanel orderMainPanel;
	JPanel orderX1Panel;
	JPanel orderX2Panel;

	JFrame orderFrame;

	/**
	 * Seteaza frame-ul principal ca fiind invizibil.
	 */
	public void setMainFrameInvisible() {
		this.setVisible(false);
	}

	/**
	 * Seteaza frame-ul principal ca fiind vizibil.
	 */
	public void setMainFrameVisible() {
		this.setVisible(true);
	}

	/**
	 * se sterge client-ul din tabela Client
	 * 
	 * @param idToDelete reprezinta un id de client
	 */
	public void deleteSelectedClient(int idToDelete) {
		Client tempClient = clientBLL.findById(idToDelete);
		clientBLL.deleteClient(tempClient);
	}

	/**
	 * face update unui Client din tabela, cu aceste campuri primite ca parametrii
	 * 
	 * @param idClient
	 * @param name
	 * @param address
	 * @param email
	 * @param money
	 */
	public void updateClientTable(int idClient, String name, String address, String email, float money) {
		Client clientTemp = new Client(idClient, name, address, email, money);
		clientBLL.updateClient(clientTemp, idClient);
	}

	/**
	 * insereaza un nou client in tabela Client, avand ca variabile instanta
	 * campurile primite ca parametrii
	 * 
	 * @param idClient
	 * @param name
	 * @param address
	 * @param email
	 * @param money
	 */
	public void insertClientTable(int idClient, String name, String address, String email, float money) {
		Client clientTemp = new Client(idClient, name, address, email, money);
		try {
			clientBLL.insertClient(clientTemp);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can't introduce two clients with the same id!", "Insert error",
					JOptionPane.ERROR);
		}
	}

	/**
	 * insereaza un nou produs in tabela Product, avand ca variabile instanta
	 * campurile primite ca parametrii
	 * 
	 * @param idProduct
	 * @param name
	 * @param quantity
	 * @param priceFloat
	 */
	public void insertProductTable(int idProduct, String name, int quantity, float priceFloat) {
		Product productTemp = new Product(idProduct, name, quantity, priceFloat);
		try {
			productBLL.insertProduct(productTemp);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can't introduce two clients with the same id!", "Insert error",
					JOptionPane.ERROR);
		}
	}

	/**
	 * face update unui produs din tabela, cu aceste campuri primite ca parametrii
	 * 
	 * @param idProduct
	 * @param name
	 * @param quantity
	 * @param priceFloat
	 */
	public void updateProductTable(int idProduct, String name, int quantity, float priceFloat) {
		Product productTemp = new Product(idProduct, name, quantity, priceFloat);
		productBLL.updateProduct(productTemp, idProduct);
	}

	/**
	 * listener pentru butonul "Add to order" din tabela Client; se adauga un client
	 * la o comanda
	 */
	public void addClientOrderListener() {
		addToOrder1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String idForOrder = null;
				int idForOrderInt = 0;
				int rowNmb = tableClient.getSelectedRow();
				String numeClient = "";
				try {
					idForOrder = (String) tableClient.getValueAt(rowNmb, 0);
					numeClient = (String) tableClient.getValueAt(rowNmb, 1);
					idForOrderInt = Integer.parseInt(idForOrder);
					client.setId_client(idForOrderInt);
					order.setId_client_pk(idForOrderInt);

					client.setName(numeClient);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "No item selected, table may be empty!", "Add to order error",
							JOptionPane.ERROR_MESSAGE);
				}
				tableClient.setModel(myModelClient);
			}

		});
	}

	/**
	 * listener pentru butonul "Delete" din tabela Client; se sterge un client
	 * selectat din tabela Client
	 */
	public void addClientDeleteListener() {
		deleteButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String idToDelete = null;
				int idToDeleteInt = 0;
				int rowNmb = tableClient.getSelectedRow();
				try {
					idToDelete = (String) tableClient.getValueAt(rowNmb, 0);
					idToDeleteInt = Integer.parseInt(idToDelete);
					if (orderBLL.verifyClientProcessingId(idToDeleteInt) == 1)
						throw new Exception("");
					myModelClient.removeRow(rowNmb);
					myModelClient.fireTableDataChanged();
					deleteSelectedClient(idToDeleteInt);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"No item selected, table may be empty or the client order is processing!", "Delete error",
							JOptionPane.ERROR_MESSAGE);
				}
				tableClient.setModel(myModelClient);
			}

		});
	}

	/**
	 * listener pentru butonul "Update" din tabela Client; se face update la un
	 * client din tabela Client
	 */
	public void addClientUpdateListener() {
		updateButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (myModelClient.getRowCount() != 0) {
					String idClientString = clientIdField.getText();
					int idClient = 0;
					String name = clientNameField.getText();
					String address = clientAddressField.getText();
					String email = clientEmailField.getText();
					String moneyString = clientMoneyField.getText();
					float money = 0.0f;

					try {
						idClient = Integer.parseInt(idClientString);
						if (name.equals("") || address.equals("") || email.equals("") || moneyString.equals(""))
							throw new Exception("");
						money = Float.parseFloat(moneyString);
						updateClientTable(idClient, name, address, email, money);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid data, please try again with valid ones!",
								"Invalid data error", JOptionPane.ERROR_MESSAGE);
					}

					myModelClient = prepareTable(clientBLL.findAllClients(), myModelClient);
					myModelClient.fireTableDataChanged();
					tableClient.setModel(myModelClient);
				} else
					JOptionPane.showMessageDialog(null, "Empty table, can't update anything!", "Update error",
							JOptionPane.ERROR_MESSAGE);

			}

		});
	}

	/**
	 * listener pentru butonul "Insert" din tabela Client; se adauga un nou client
	 * in tabela Client
	 */
	public void addClientInsertListener() {
		insertButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				// String idClientString = clientIdField.getText();
				int idClient = clientBLL.findClientId();
				String name = clientNameField.getText();
				String address = clientAddressField.getText();
				String email = clientEmailField.getText();
				String moneyString = clientMoneyField.getText();
				float money = 0.0f;

				try {
					// idClient = Integer.parseInt(idClientString);
					if (idClient == 10)
						throw new Exception("");
					if (name.equals("") || address.equals("") || email.equals("") || moneyString.equals(""))
						throw new Exception("");
					money = Float.parseFloat(moneyString);
					insertClientTable(idClient + 1, name, address, email, money);
					myModelClient = prepareTable(clientBLL.findAllClients(), myModelClient);
					myModelClient.fireTableDataChanged();
					tableClient.setModel(myModelClient);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Invalid data, please try again with valid ones! (Table may be also full)",
							"Invalid data error", JOptionPane.ERROR_MESSAGE);
				}

			}

		});
	}

	/**
	 * se va sterge produsul din tabela Product cu acel id primit ca parametru
	 * 
	 * @param idToDelete reprezinta un id de produs
	 */
	public void deleteSelectedProduct(int idToDelete) {
		Product tempProduct = productBLL.findById(idToDelete);
		productBLL.deleteProduct(tempProduct);
	}

	/**
	 * se va sterge comanda din tabela Product cu acel id primit ca parametru
	 * 
	 * @param idToDelete reprezinta un id de comanda
	 */
	public void deleteSelectedOrder(int idToDelete) {
		Order tempOrder = orderBLL.findById(idToDelete);
		orderBLL.deleteOrder(tempOrder);
	}

	/**
	 * listener pentru butonul "Add to order" din clasa Product; se va adauga la
	 * comanda produsul selectat din tabela Product
	 */
	public void addProductOrderListener() {
		addToOrder2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String idForOrder = null;
				String quantityForOrder = null;
				String pricePerUnit = null;
				int idForOrderInt = 0;
				int quantityForOrderInt = 0;
				float pricePerUnitFloat = 0.0f;
				float totalPrice = 0.0f;

				int rowNmb = tableProduct.getSelectedRow();
				try {
					if (rowNmb == -1)
						throw new Exception("Please select a row before trying to add the product to cart!");
					idForOrder = (String) tableProduct.getValueAt(rowNmb, 0);
					idForOrderInt = Integer.parseInt(idForOrder);
					quantityForOrder = (String) tableProduct.getValueAt(rowNmb, 2);
					quantityForOrderInt = Integer.parseInt(quantityForOrder);
					if (quantityForOrderInt == 0)
						throw new Exception("Not enough quantity amount in the store for the selected product!");
					pricePerUnit = (String) tableProduct.getValueAt(rowNmb, 3);
					pricePerUnitFloat = Float.parseFloat(pricePerUnit);
					String desiredQuantity = JOptionPane.showInputDialog(null,
							"Please insert the quantity amount you need from the selected product.", "Quantity amount",
							JOptionPane.INFORMATION_MESSAGE);
					int desiredQuantityInt = Integer.parseInt(desiredQuantity);
					if (desiredQuantityInt <= quantityForOrderInt && desiredQuantityInt > 0) {
						totalPrice = pricePerUnitFloat * desiredQuantityInt;

						produs.setId_product(idForOrderInt);
					} else
						throw new Exception("Invalid quantity amount introduced, please try again!");

					if (client.getId_client() == -1)
						throw new Exception("Please select a client before selecting a product!");
					Client cumparator = clientBLL.findById(order.getId_client_pk());
					if (cumparator.getMoney() >= totalPrice) {
						int nextIdOrder = orderBLL.findOrderId();
						order.setId_order(nextIdOrder + 1);
						order.setId_product_pk(idForOrderInt);

						produs.setQuantity(quantityForOrderInt - desiredQuantityInt);
						client.setMoney(cumparator.getMoney() - totalPrice);

						produs.setPrice(totalPrice);
						;
						produs.setName((String) tableProduct.getValueAt(rowNmb, 1));
					} else {
						client.setId_client(-1);
						produs.setId_product(-1);
						throw new Exception("Not enough money to add to cart the selected product!");
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null, "Please select a client before selecting a product!",
							"Add to order error", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "The quantity amount inserted is invalid!",
							"Invalid quantity amount", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Add to order error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});
	}

	/**
	 * listener pentru butonul delete din clasa Order; se va sterge comanda
	 * selectata din tabela Order
	 */
	public void addOrderDeleteListener() {
		deleteButton3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String idToDelete = null;
				int idToDeleteInt = 0;
				int rowNmb = tableOrder.getSelectedRow();
				int totalRows = tableOrder.getRowCount();
				if (totalRows != 0)
					try {
						idToDelete = (String) tableOrder.getValueAt(rowNmb, 0);
						idToDeleteInt = Integer.parseInt(idToDelete);
						myModelOrder.removeRow(rowNmb);
						myModelOrder.fireTableDataChanged();
						deleteSelectedOrder(idToDeleteInt);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "No item selected, table may be empty!", "Delete error",
								JOptionPane.ERROR_MESSAGE);
					}
				else
					JOptionPane.showMessageDialog(null, "Empty table, can't delete.", "Delete error",
							JOptionPane.ERROR_MESSAGE);

				tableOrder.setModel(myModelOrder);
			}

		});
	}

	/**
	 * listener pentru butonul "Delete" din clasa Product; se va sterge un produs
	 * selectat din tabela Product
	 */
	public void addProductDeleteListener() {
		deleteButton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String idToDelete = null;
				int idToDeleteInt = 0;
				int rowNmb = tableProduct.getSelectedRow();
				try {
					idToDelete = (String) tableProduct.getValueAt(rowNmb, 0);
					idToDeleteInt = Integer.parseInt(idToDelete);
					if (orderBLL.verifyProductProcessingId(idToDeleteInt) == 1)
						throw new Exception("");
					myModelProduct.removeRow(rowNmb);
					myModelProduct.fireTableDataChanged();
					deleteSelectedProduct(idToDeleteInt);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"No item selected, table may be empty or the product is in order list!", "Delete error",
							JOptionPane.ERROR_MESSAGE);
				}

				tableProduct.setModel(myModelProduct);
			}

		});
	}

	/**
	 * listener pentru butonul "Update" din tabela Product; se va face update unui
	 * produs din tabela Product
	 */
	public void addProductUpdateListener() {
		updateButton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (myModelProduct.getRowCount() != 0) {
					String idProductString = productIdField.getText();
					int idProduct = 0;
					String name = productNameField.getText();
					String quantity = productQuantityField.getText();
					String price = productPriceField.getText();
					float priceFloat = 0.0f;
					int quantityInt = 0;

					try {
						idProduct = Integer.parseInt(idProductString);
						if (name.equals("") || price.equals(""))
							throw new Exception("");
						priceFloat = Float.parseFloat(price);
						quantityInt = Integer.parseInt(quantity);
						updateProductTable(idProduct, name, quantityInt, priceFloat);
						myModelProduct = prepareTable(productBLL.findAllProducts(), myModelProduct);
						myModelProduct.fireTableDataChanged();
						tableProduct.setModel(myModelProduct);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid data, please try again with valid ones!",
								"Invalid data error", JOptionPane.ERROR_MESSAGE);
					}

				} else
					JOptionPane.showMessageDialog(null, "Empty table, can't update anything!", "Update error",
							JOptionPane.ERROR_MESSAGE);

			}

		});
	}

	/**
	 * listener pentru butonul "Insert" din clasa Product; se va adauga un produs in
	 * tabela Product
	 */
	public void addProductInsertListener() {
		insertButton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// String idProductString = productIdField.getText();
				int idProduct = productBLL.findProductId();
				String name = productNameField.getText();
				String quantity = productQuantityField.getText();
				String price = productPriceField.getText();
				float priceFloat = 0.0f;
				int quantityInt = 0;

				try {
					// idProduct = Integer.parseInt(idProductString);
					if (idProduct == 10)
						throw new Exception("");
					if (name.equals("") || price.equals(""))
						throw new Exception("");
					priceFloat = Float.parseFloat(price);
					quantityInt = Integer.parseInt(quantity);
					insertProductTable(idProduct + 1, name, quantityInt, priceFloat);
					myModelProduct = prepareTable(productBLL.findAllProducts(), myModelProduct);
					myModelProduct.fireTableDataChanged();
					tableProduct.setModel(myModelProduct);
					//comentariu
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Invalid data, please try again with valid ones! (Table may also be full)",
							"Invalid data error", JOptionPane.ERROR_MESSAGE);
				}

			}

		});
	}

	/**
	 * listener pentru butonul "Client" din frame-ul principal; schimba frame-ul
	 * curent la frame-ul pentru tabela Client
	 */
	public void addChangeFrameToClientListener() {
		clientButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				clientFrame.setTitle("Client table");
				clientFrame.setSize(1000, 600);
				clientFrame.setLocation(450, 200);
				clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				clientFrame.setContentPane(clientMainPanel);
				clientFrame.setVisible(true);
				setMainFrameInvisible();
			}

		});
	}

	/**
	 * listener pentru butonul "Order" din frame-ul principal; schimba frame-ul
	 * curent la frame-ul pentru tabela Client
	 */
	public void addChangeFrameToOrderListener() {
		orderButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				orderFrame.setTitle("Order table");
				orderFrame.setSize(1000, 600);
				orderFrame.setLocation(450, 200);
				orderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				orderFrame.setContentPane(orderMainPanel);
				orderFrame.setVisible(true);
				setMainFrameInvisible();
			}

		});
	}

	/**
	 * listener pentru butonul de "Back" din tabela Client; se revine la frame-ul
	 * principal
	 */
	public void addBackButtonToClientListener() {
		backButton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setMainFrameVisible();
				clientFrame.setVisible(false);
				productFrame.setVisible(false);
				orderFrame.setVisible(false);
			}
		});
	}

	/**
	 * listener pentru butonul "Back" din tabela Product; se revine la frame-ul
	 * principal
	 */
	public void addBackButtonToProductListener() {
		backButton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setMainFrameVisible();
				clientFrame.setVisible(false);
				productFrame.setVisible(false);
				orderFrame.setVisible(false);
			}

		});
	}

	/**
	 * listener pentru butonul "Product" din frame-ul principal; schimba frame-ul
	 * curent la frame-ul pentru tabela Product
	 */
	public void addChangeFrameToProductListener() {
		productButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				productFrame.setTitle("Product table");
				productFrame.setSize(1000, 600);
				productFrame.setLocation(450, 200);
				productFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				productFrame.setContentPane(productMainPanel);
				productFrame.setVisible(true);
				setMainFrameInvisible();
			}

		});
	}

	/**
	 * listener pentru butonul "Back" din tabela Order; se revina la frame-ul
	 * principal din tabela Order
	 */
	public void addBackButtonToOrderListener() {
		backButton3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				setMainFrameVisible();
				clientFrame.setVisible(false);
				productFrame.setVisible(false);
				orderFrame.setVisible(false);
			}

		});
	}

	/**
	 * listener pentru butonul "Finish order" din tabela Order; se finalizeaza o
	 * comanda, care se insereaza in tabelul Order si se scrie intr-un fisier text
	 * ca o "chitanta"
	 */
	public void addFinishOrderListener() {
		finishOrder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (client.getId_client() != -1 && produs.getId_product() != -1) {
					orderBLL.insertOrder(order);
					myModelOrder = prepareTable(orderBLL.findAllOrders(), myModelOrder);
					myModelOrder.fireTableDataChanged();
					tableOrder.setModel(myModelOrder);

					Product tempProduct = productBLL.findById(order.getId_product_pk());
					tempProduct.setQuantity(produs.getQuantity());
					updateProductTable(tempProduct.getId_product(), tempProduct.getName(), tempProduct.getQuantity(),
							tempProduct.getPrice());
					myModelProduct = prepareTable(productBLL.findAllProducts(), myModelProduct);
					myModelProduct.fireTableDataChanged();
					tableProduct.setModel(myModelProduct);

					Client tempClient = clientBLL.findById(order.getId_client_pk());
					tempClient.setMoney(client.getMoney());
					updateClientTable(tempClient.getId_client(), tempClient.getName(), tempClient.getAddress(),
							tempClient.getEmail(), tempClient.getMoney());
					myModelClient = prepareTable(clientBLL.findAllClients(), myModelClient);
					myModelClient.fireTableDataChanged();
					tableClient.setModel(myModelClient);

					try {
						try {
							randomString = new String("Bill" + billNumber + ".txt");
							billNumber++;
							writer = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(randomString), "utf-8"));
						} catch (Exception e) {
						}

						writer.append("Clientul " + client.getName() + " a comandat produsul " + produs.getName()
								+ ", achitand " + produs.getPrice() + " lei.\n");
						writer.close();
					} catch (IOException e) {
					}
					client.setId_client(-1);
					produs.setId_product(-1);
				} else
					JOptionPane.showMessageDialog(null, "Please select a client and a product before trying to order!",
							"Finish order error", JOptionPane.ERROR_MESSAGE);

			}

		});
	}

	public View() {

		myModelClient = new DefaultTableModel();
		myModelProduct = new DefaultTableModel();
		myModelOrder = new DefaultTableModel();
		tableClient = new JTable();
		tableProduct = new JTable();
		tableOrder = new JTable();

		welcomeLabel = new JLabel("              Welcome to online shopping !", JLabel.CENTER);
		welcomeLabelPanel = new JPanel();
		welcomeLabelPanel.setLayout(new FlowLayout());
		welcomeLabelPanel.add(welcomeLabel);
		pickTableLabel = new JLabel("Please choose the wanted table and start modeling it however you want.");
		pickTablePanel = new JPanel();
		pickTablePanel.setLayout(new FlowLayout());
		pickTablePanel.add(pickTableLabel);
		clientButton = new JButton("Client");
		clientButtonPanel = new JPanel();
		clientButtonPanel.setLayout(new FlowLayout());
		clientButtonPanel.add(clientButton);
		productButton = new JButton("Produs");
		orderButton = new JButton("Order");
		clientButtonPanel.add(productButton);
		clientButtonPanel.add(orderButton);
		insertButton1 = new JButton("Insert");
		insertButton2 = new JButton("Insert");
		updateButton1 = new JButton("Update");
		updateButton2 = new JButton("Update");
		deleteButton1 = new JButton("Delete");
		deleteButton2 = new JButton("Delete");
		deleteButton3 = new JButton("Delete");
		addToOrder1 = new JButton("Add to order");
		addToOrder2 = new JButton("Add to order");
		finishOrder = new JButton("Finish order");

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		clientPanel = new JPanel();
		produsPanel = new JPanel();
		orderPanel = new JPanel();

		clientBLL = new ClientBLL();
		productBLL = new ProductBLL();
		orderBLL = new OrderBLL();

		client.setId_client(-1);
		produs.setId_product(-1);

		myModelClient = prepareTable(clientBLL.findAllClients(), myModelClient);
		tableClient.setModel(myModelClient);
		JPanel tempPanel1 = new JPanel();
		tempPanel1.setLayout(new FlowLayout());
		tempPanel1.add(clientPanel);
		// tableClient.setBounds(20, 30, 100, 100);
		// tableClient.setRowHeight(1, 15);
		JScrollPane sp = new JScrollPane(tableClient);
		clientPanel.add(sp);

		myModelProduct = prepareTable(productBLL.findAllProducts(), myModelProduct);
		tableProduct.setModel(myModelProduct);
		JPanel tempPanel2 = new JPanel();
		tempPanel2.setLayout(new FlowLayout());
		tempPanel2.add(produsPanel);
		// tableProduct.setBounds(20, 30, 100, 100);
		// tableProduct.setRowHeight(1, 15);
		JScrollPane sp2 = new JScrollPane(tableProduct);
		produsPanel.add(sp2);

		myModelOrder = prepareTable(orderBLL.findAllOrders(), myModelOrder);
		tableOrder.setModel(myModelOrder);
		JPanel tempPanel3 = new JPanel();
		tempPanel3.setLayout(new FlowLayout());
		tempPanel3.add(orderPanel);
		// tableProduct.setBounds(20, 30, 100, 100);
		// tableProduct.setRowHeight(1, 15);
		JScrollPane sp3 = new JScrollPane(tableOrder);
		orderPanel.add(sp3);

		clientFrame = new JFrame();
		clientIdF.setLayout(new FlowLayout());
		clientIdF.add(clientId);
		clientIdF.add(clientIdField);
		clientNameF.setLayout(new FlowLayout());
		clientNameF.add(clientName);
		clientNameF.add(clientNameField);
		clientAddressF.setLayout(new FlowLayout());
		clientAddressF.add(clientAddress);
		clientAddressF.add(clientAddressField);
		clientEmailF.setLayout(new FlowLayout());
		clientEmailF.add(clientEmail);
		clientEmailF.add(clientEmailField);
		clientMoneyF.setLayout(new FlowLayout());
		clientMoneyF.add(clientMoney);
		clientMoneyF.add(clientMoneyField);
		clientMainPanel = new JPanel();
		clientMainPanel.setLayout(new BoxLayout(clientMainPanel, BoxLayout.Y_AXIS));
		clientX1Panel = new JPanel();
		clientX1Panel.setLayout(new BoxLayout(clientX1Panel, BoxLayout.X_AXIS));
		clientYPanel = new JPanel();
		clientYPanel.setLayout(new BoxLayout(clientYPanel, BoxLayout.Y_AXIS));
		clientX2Panel = new JPanel();
		clientX2Panel.setLayout(new FlowLayout());

		clientX2Panel.add(insertButton1);
		clientX2Panel.add(updateButton1);
		clientX2Panel.add(deleteButton1);
		clientX2Panel.add(addToOrder1);
		clientX2Panel.add(backButton1);
		clientYPanel.add(clientIdF);
		clientYPanel.add(clientNameF);
		clientYPanel.add(clientAddressF);
		clientYPanel.add(clientEmailF);
		clientYPanel.add(clientMoneyF);
		clientX1Panel.add(clientPanel);
		clientX1Panel.add(Box.createRigidArea(new Dimension(0, 5)));
		clientX1Panel.add(clientYPanel);
		clientMainPanel.add(clientX1Panel);
		clientMainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		clientMainPanel.add(clientX2Panel);
		clientFrame.add(clientMainPanel);

		// _______________

		productFrame = new JFrame();
		productIdF.setLayout(new FlowLayout());
		productIdF.add(productId);
		productIdF.add(productIdField);
		productNameF.setLayout(new FlowLayout());
		productNameF.add(productName);
		productNameF.add(productNameField);
		productQuantityF.setLayout(new FlowLayout());
		productQuantityF.add(productQuantity);
		productQuantityF.add(productQuantityField);
		productPriceF.setLayout(new FlowLayout());
		productPriceF.add(productPrice);
		productPriceF.add(productPriceField);
		productMainPanel = new JPanel();
		productMainPanel.setLayout(new BoxLayout(productMainPanel, BoxLayout.Y_AXIS));
		productX1Panel = new JPanel();
		productX1Panel.setLayout(new BoxLayout(productX1Panel, BoxLayout.X_AXIS));
		productYPanel = new JPanel();
		productYPanel.setLayout(new BoxLayout(productYPanel, BoxLayout.Y_AXIS));
		productX2Panel = new JPanel();
		productX2Panel.setLayout(new FlowLayout());

		productX2Panel.add(insertButton2);
		productX2Panel.add(updateButton2);
		productX2Panel.add(deleteButton2);
		productX2Panel.add(backButton2);
		productX2Panel.add(addToOrder2);
		productYPanel.add(productIdF);
		productYPanel.add(productNameF);
		productYPanel.add(productQuantityF);
		productYPanel.add(productPriceF);
		productX1Panel.add(produsPanel);
		productX1Panel.add(Box.createRigidArea(new Dimension(0, 5)));
		productX1Panel.add(productYPanel);
		productMainPanel.add(productX1Panel);
		productMainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		productMainPanel.add(productX2Panel);
		productFrame.add(productMainPanel);

		orderFrame = new JFrame();
		orderMainPanel = new JPanel();
		orderMainPanel.setLayout(new BoxLayout(orderMainPanel, BoxLayout.Y_AXIS));
		orderX1Panel = new JPanel();
		orderX1Panel.setLayout(new BoxLayout(orderX1Panel, BoxLayout.X_AXIS));
		orderX2Panel = new JPanel();
		orderX2Panel.setLayout(new FlowLayout());
		orderX2Panel.add(finishOrder);
		orderX2Panel.add(deleteButton3);
		orderX2Panel.add(backButton3);
		orderX1Panel.add(orderPanel);
		orderMainPanel.add(orderX1Panel);
		orderMainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		orderMainPanel.add(orderX2Panel);
		orderFrame.add(orderMainPanel);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(clientButtonPanel);
		// mainPanel.add(productButton);
		// mainPanel.add(orderButton);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(welcomeLabel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(pickTablePanel);
		this.addClientDeleteListener();
		this.addClientUpdateListener();
		this.addClientInsertListener();
		this.addClientOrderListener();
		this.addProductDeleteListener();
		this.addProductUpdateListener();
		this.addProductInsertListener();
		this.addProductOrderListener();
		this.addOrderDeleteListener();
		this.addFinishOrderListener();
		this.addChangeFrameToClientListener();
		this.addChangeFrameToProductListener();
		this.addChangeFrameToOrderListener();
		this.addBackButtonToClientListener();
		this.addBackButtonToProductListener();
		this.addBackButtonToOrderListener();

		this.setTitle("Warehouse database");
		this.setSize(500, 380);
		this.setLocation(700, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}

	/**
	 * 
	 * @param myList  o lista de obiecte
	 * @param myModel un model de tabel
	 * @return modelul de tabel, construit cu toate elementele din myList (Client /
	 *         Product / Order), folosind reflexia
	 */
	public DefaultTableModel prepareTable(Object myList, DefaultTableModel myModel) {

		Class cls = myList.getClass();
		Method m = null;
		Iterator<Object> myIt = null;
		try {
			m = cls.getMethod("iterator", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		try {
			myIt = (Iterator<Object>) m.invoke(myList, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		int totalRows = 0;
		Field[] fields = null;
		String[] fieldsName = null;
		Object[] valuesForFields = null;

		String[][] rows = new String[10][20];
		String[] row = new String[10];
		String[] columns = null;
		int currentRow = 0;
		while (myIt.hasNext()) {

			totalRows++;
			Object myObj = myIt.next();
			fields = myObj.getClass().getDeclaredFields();
			columns = new String[fields.length];
			fieldsName = new String[fields.length];
			valuesForFields = new Object[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldsName[i] = fields[i].getName();
			}

			int j = 0;
			for (Field field : fields) {
				field.setAccessible(true);

				Object value = null;

				try {
					value = field.get(myObj);
					valuesForFields[j++] = value;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}

			for (int i = 0; i < fields.length; i++) {
				columns[i] = fieldsName[i];
			}

			for (int k = 0; k < fields.length; k++) {
				rows[currentRow][k] = valuesForFields[k].toString();

			}
			currentRow++;
		}

		if (totalRows != 0) {
			int indice = 0;
			String[][] finalRows = new String[totalRows][fields.length];
			for (int i = 0; i < totalRows; i++)
				if (rows[i] != null) {
					finalRows[indice] = rows[i];
					indice++;
				}

			myModel = new DefaultTableModel(finalRows, columns);
		} else {
			myModel = new DefaultTableModel();
		}
		myModel.fireTableDataChanged();
		return myModel;
	}

}
