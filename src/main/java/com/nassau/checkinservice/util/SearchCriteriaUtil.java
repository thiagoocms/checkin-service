package com.nassau.checkinservice.util;

import com.nassau.checkinservice.search.Restriction;
import com.nassau.checkinservice.search.SearchCriteria;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchCriteriaUtil {

	public static List<SearchCriteria> buildCriteria(Object filter, Restriction... restrictions) {

		List<SearchCriteria> searchCriteriaList = new ArrayList<>();
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(filter.getClass());

        for (PropertyDescriptor propertyDescriptor : pds) {

            String property = propertyDescriptor.getName();

            try {

				Object value = PropertyUtils.getNestedProperty(filter, property);

				if (isNullOrEmpty(value))
					continue;

				searchCriteriaList.add(new SearchCriteria(property, "and", value));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        
        addDeleted(searchCriteriaList);
		return searchCriteriaList;
	}

    private static void addDeleted(List<SearchCriteria> criteria) {
		criteria.add(new SearchCriteria("deleted","and", 0));
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
