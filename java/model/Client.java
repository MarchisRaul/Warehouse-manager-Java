package model;



public class Client {
	private int id_client;
	private String name;
	private String address;
	private String email;
	private float money;

	public Client(int id_client, String name, String address, String email, float money) {
		super();
		this.id_client = id_client;
		this.name = name;
		this.address = address;
		this.email = email;
		this.money = money;
	}

	public Client() {
		
	}
	
	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

}

