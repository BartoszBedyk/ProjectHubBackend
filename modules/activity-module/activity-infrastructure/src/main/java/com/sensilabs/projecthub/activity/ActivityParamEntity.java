package com.sensilabs.projecthub.activity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "activity_param")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ActivityParamEntity {
	@Id
	@Column(name = "id")
	private String id;
	@ManyToOne
	private ActivityEntity activity;
	@Column(name = "name")
	private String paramName;
	@Column(name = "value")
	private String paramValue;
}
