package fr.m2i.crm.model;

public class Order {

	private Integer id;
	private String label;
	private Double adrEt;
	private Double numberOfDays;
	private Double tva;
	private String status;
	private String type;
	private String notes;
	private Customer customer;

	public Order() {
		super();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", label=" + label + ", adrEt=" + adrEt + ", numberOfDays=" + numberOfDays + ", tva="
				+ tva + ", status=" + status + ", type=" + type + ", notes=" + notes + ", customer=" + customer + "]";
	}

	public Order(Double adrEt) {
		super();
		this.adrEt = adrEt;
	}

	public Order(Integer id, String label, Double adrEt, Double numberOfDays, Double tva, String status, String type,
			String notes, Customer customer) {
		super();
		this.id = id;
		this.label = label;
		this.adrEt = adrEt;
		this.numberOfDays = numberOfDays;
		this.tva = tva;
		this.status = status;
		this.type = type;
		this.notes = notes;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getAdrEt() {
		return adrEt;
	}

	public void setAdrEt(Double adrEt) {
		this.adrEt = adrEt;
	}

	public Double getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Double numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
