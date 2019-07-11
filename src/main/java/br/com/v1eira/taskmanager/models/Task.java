package br.com.v1eira.taskmanager.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tas_tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tas_id")
	private Long id;

	@Column(name = "tas_title", length = 50, nullable = false)
	@NotNull(message = "Title is mandatory")
	@Length(max = 50, min = 3, message = "Title length must be between 3 and 50 characters")
	private String title;

	@Column(name = "tas_description", length = 100, nullable = true)
	@Length(max = 100, message = "Description must have up to 100 characters")
	private String description;

	@Column(name = "tas_expiration_date", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expirationDate;

	@Column(name = "tas_finished", nullable = false)
	private Boolean finished = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usr_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
