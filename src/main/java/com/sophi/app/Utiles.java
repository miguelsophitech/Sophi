package com.sophi.app;

import java.util.HashMap;
import java.util.Map;

public class Utiles {
	
	public Map<String, String> recursosEtapaList(){
		Map<String, String> etapaList = new HashMap<String, String>();
		etapaList.put("Trunca", "Trunca");
		etapaList.put("Terminada", "Terminada");
		return etapaList;
	}
	
	
	public Map<String, String> skillTipoList(){
		Map<String, String> tipoSkillList = new HashMap<String, String>();
		tipoSkillList.put("Skill", "Skill");
		tipoSkillList.put("Soft skill", "Soft skill");
		return tipoSkillList;
	}
	
	public Map<String, String> skillDominioList(){
		Map<String, String> dominioSkillList = new HashMap<String, String>();
		dominioSkillList.put("No aplica", "No aplica");
		dominioSkillList.put("Alto", "Alto");
		dominioSkillList.put("Medio", "Medio");
		dominioSkillList.put("Bajo", "Bajo");
		return dominioSkillList;
	}
	


}
