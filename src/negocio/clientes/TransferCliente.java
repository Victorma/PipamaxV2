package negocio.clientes;

public class TransferCliente {
	private int id;
	private String name;
	private String lastName;
	private String adress;
	private int postalCode;
	private String city;
	private String email;
	private Long telephoneNumber;
	private int DNI;
	private String tipo;
	private boolean activo;
	private float descuento = 0;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getDNI() {
		return DNI;
	}

	public void setDNI(int dNI) {
		DNI = dNI;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String na) {
		name = na;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String ln) {
		lastName = ln;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String ad) {
		adress = ad;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int pc) {
		postalCode = pc;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String c) {
		city = c;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String e) {
		email = e;
	}

	public Long getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(Long tn) {
		telephoneNumber = tn;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public float getDescuento() {
		return descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
}
