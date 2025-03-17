package com.contractvault.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Contact {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String pricture;
	@Column(length = 1000)
	private String description;
	private Boolean favourite = false;
	
	
	private String websiteLink;
	private String LinkedInLink;

	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	private List<SocialLink> links = new ArrayList<>();
}
