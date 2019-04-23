package model;

public class Product {
	private int id_product;
	private String name;
	private int quantity;
	private float price;

	public Product(int id_product, String name, int quantity, float price) {
		super();
		this.id_product = id_product;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Product() {
		super();
	}

	public int getId_product() {
		return id_product;
	}

	public void setId_product(int id_product) {
		this.id_product = id_product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
