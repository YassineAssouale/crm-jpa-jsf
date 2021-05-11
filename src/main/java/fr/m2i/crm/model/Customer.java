package fr.m2i.crm.model;

public class Customer {

	private Integer id;
	private String lastname;
	private String firstname;
	private String company;
	private String mail;
	private String phone;
	private String mobile;
	private String notes;
	private Boolean active;

	public Customer() {
		super();
	}

	public Customer(Integer id, String lastname, String firstname, String company, String mail, String phone,
			String mobile, String notes, Boolean active) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.company = company;
		this.mail = mail;
		this.phone = phone;
		this.mobile = mobile;
		this.notes = notes;
		this.active = active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", company=" + company
				+ ", mail=" + mail + ", phone=" + phone + ", mobile=" + mobile + ", notes=" + notes + ", active="
				+ active + "]";
	}

}
