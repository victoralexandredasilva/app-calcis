package com.victoralexandre.appcalcis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import jakarta.persistence.*;

@Entity
@Table(name="tb_clients")
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String phoneNumber;
	private String email;
	private String adress;

	private boolean active = true;

	@OneToMany(mappedBy = "client")
	private List<Sale> sales;
	
	public Client() {
		super();
	}

	public Client(long id, String name, String phoneNumber, String email, String adress) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.adress = adress;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Client [Id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + ", adress="
				+ adress + "]";
	}
	
	
	
}
