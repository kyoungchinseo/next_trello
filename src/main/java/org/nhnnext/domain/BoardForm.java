package org.nhnnext.domain;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BoardForm {
	@NotNull
	private String title;
}
