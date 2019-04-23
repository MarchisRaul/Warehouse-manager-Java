package model;

public class Order {
	private int id_order;
	private int id_client_pk;
	private int id_product_pk;

	public Order(int id_order, int id_client_pk, int id_product_pk) {
		super();
		this.id_order = id_order;
		this.id_client_pk = id_client_pk;
		this.id_product_pk = id_product_pk;
	}

	public Order() {

	}

	public int getId_order() {
		return id_order;
	}

	public void setId_order(int id_order) {
		this.id_order = id_order;
	}

	public int getId_client_pk() {
		return id_client_pk;
	}

	public void setId_client_pk(int id_client_pk) {
		this.id_client_pk = id_client_pk;
	}

	public int getId_product_pk() {
		return id_product_pk;
	}

	public void setId_product_pk(int id_product_pk) {
		this.id_product_pk = id_product_pk;
	}

}
