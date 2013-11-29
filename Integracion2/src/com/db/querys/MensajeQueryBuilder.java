package com.db.querys;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class MensajeQueryBuilder extends QueryBuilder {
	
	public static String getMensajesByMiembro(String idmiembro){ // Mover a getAllByAttributes
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("Mensajes","mensajes");
		criteria.createAlias("mensajes.miembro","miembro");
		criteria.add(Restrictions.eq("miembro.nombre",idmiembro));
		return QueryBuilder.getSerializedCriteria(criteria);
		
	}

	@Override
	public String getAllById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllByAttributes(Map<String, String> attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
