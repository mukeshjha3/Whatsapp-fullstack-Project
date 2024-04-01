package com.whatsapp.model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Chat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String chat_name;
	
	private String chat_image;
	
	@Column(name = "is_group")
	private boolean isGroup;
	
	@ManyToMany
	private Set<User> admins = new HashSet<>();
	
	@JoinColumn(name = "created_by")
	@ManyToOne
	private User createdby;
	
	@ManyToMany
	private Set<User> users = new HashSet<>();
	
	@OneToMany
	private List<Message> messages= new ArrayList<>();
	
}
/*
   
   Represents a conversation or chat between users.
   Each chat has a unique ID.
   A chat can have multiple messages sent by different users.
   Users participate in chats by sending messages.
   
*/