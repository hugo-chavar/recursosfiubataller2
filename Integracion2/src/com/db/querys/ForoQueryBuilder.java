package com.db.querys;

import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ForoQueryBuilder extends QueryBuilder{

	private static final String CLASS_NAME = "Foros";

	@Override
	public String getAllById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(CLASS_NAME);
		criteria.add(Restrictions.idEq(id));
		return QueryBuilder.getSerializedCriteria(criteria);
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
