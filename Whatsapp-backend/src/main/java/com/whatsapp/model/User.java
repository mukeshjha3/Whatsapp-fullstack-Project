package com.whatsapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
public class User {

	@Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
	private Integer id;
	private String full_name;
	private String email;
	private  String profile_picture;
	private String password; 
	
//	
//	@OneToMany
//	private List<Notification> notifications = new ArrayList<>();
}
