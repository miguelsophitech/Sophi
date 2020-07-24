package com.sophi.app.models.entity;

import java.util.List;

public class AprobacionHorasDto {
	private List<AprobacionHoras> aprobacionhoras;
	
	public AprobacionHorasDto(List<AprobacionHoras> aprobacionhoras2) {
		
	}
	
	public AprobacionHorasDto() {
		
	}

	public void addAprobacionHoras(AprobacionHoras aprobacionhoras) {
		this.aprobacionhoras.add(aprobacionhoras);
	}

	public List<AprobacionHoras> getAprobacionhoras() {
		return aprobacionhoras;
	}

	public void setAprobacionhoras(List<AprobacionHoras> aprobacionhoras) {
		this.aprobacionhoras = aprobacionhoras;
	}
	
	
}
