package com.codemos.ovsystem.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataDto {
	private int id;
	private String name;
	private String url;
	private String imageUrl;
	private boolean isRecommended;
	private boolean isSoldOut;
}
