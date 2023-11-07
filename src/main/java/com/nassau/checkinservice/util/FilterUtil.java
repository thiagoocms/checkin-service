package com.nassau.checkinservice.util;

import com.nassau.checkinservice.search.Restriction;
import com.nassau.checkinservice.search.SearchCriteria;
import org.apache.commons.beanutils.PropertyUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilterUtil {
	
	public static boolean hasNoRestriction(List<Restriction> restrictions, String key) {
		
		return !restrictions.contains(new Restriction( key ));
	}
	
	public static Predicate andTogether(List<Predicate> predicates, CriteriaBuilder builder) {
		
		return builder.and(predicates.toArray(new Predicate[0]));
	}
	
	public static List<SearchCriteria> buildCriteria(Object filter, Class classz, Restriction... restrictions){
		List<SearchCriteria> criterias = new ArrayList<>();

		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(classz);

		for (PropertyDescriptor propertyDescriptor : pds) {
			String property = propertyDescriptor.getName();
			String propertyType = propertyDescriptor.getPropertyType().getSimpleName();
			try {

				if ( isAllowedType(propertyType)) {
					Object value = PropertyUtils.getNestedProperty(filter, property);

					if ( isNullOrEmpty(value))
						continue;

					criterias.add(new SearchCriteria(
							property,"and",value));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return criterias;
	}
	
	private static boolean isAllowedType(String type) {

		switch (type) {
			case "String":
			case "Long":
			case "Integer":
			case "Double":
			case "SharedRequestStatus":
			case "List":
			case "Boolean":
			case "FinancialCashBanckType":
				return true;
		}
		return false;
	}

	private static boolean isNullOrEmpty(Object value) {

		if ( Objects.isNull(value) ) {
			return true;
		}
		if ( (value instanceof String) && ((String)value).isEmpty() )
			return true;

		if ( (value instanceof List) && ((List<?>)value).isEmpty() )
			return true;

		return false;
	}

}
