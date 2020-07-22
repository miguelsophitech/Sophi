package com.sophi.app.models.entity;

import java.util.List;

public class AprobacionHorasDto {
	private List<AprobacionHoras> aprobacionhoras;
	
	public AprobacionHorasDto(List<AprobacionHoras> aprobacionhoras2) {
		
	}
	
	public AprobacionHorasDto() {
		
	}
<<<<<<< HEAD
	
=======

>>>>>>> 2ec6870fed18ccb3233a5161495163c341ea2091
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
