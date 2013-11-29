package com.db.querys;

import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.ws.tags.UsuarioTags;

public class UsuarioQueryBuilder extends QueryBuilder{

	private static final String CLASS_NAME = "ar.fiuba.redsocedu.datalayer.dtos.Usuario";

	@Override
	public String getAllById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(CLASS_NAME);
		criteria.add(Restrictions.idEq(id));
		return QueryBuilder.getSerializedCriteria(criteria);
	}

	@Override
	public String getAllByAttributes(Map<String, String> attributes) {		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(CLASS_NAME);
		if(attributes.containsKey(UsuarioTags.ID_TAG)) {
			Long id = Long.parseLong(attributes.get(UsuarioTags.ID_TAG));
			criteria.add(Restrictions.idEq(id));
			attributes.remove(UsuarioTags.ID_TAG);
		}
		criteria.add(Restrictions.allEq(attributes));   //todos los atributos pasados por parámtero deben matchear
		return QueryBuilder.getSerializedCriteria(criteria);
	}
	
	@Override
	public String removeById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(CLASS_NAME);
		criteria.add(Restrictions.idEq(id));
		String xml = QueryBuilder.getSerializedCriteria(criteria);
		return xml;
	}
}
