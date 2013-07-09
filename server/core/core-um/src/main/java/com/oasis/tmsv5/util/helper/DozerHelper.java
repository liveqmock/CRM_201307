package com.oasis.tmsv5.util.helper;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DozerHelper {

	@Autowired
	private Mapper mapper;

	@SuppressWarnings("unchecked")
	public <P> P clone(P base) {
		if (base == null) {
			return null;
		} else {
			return (P) mapper.map(base, base.getClass());
		}
	}

	public <P> List<P> cloneList(List<P> baseList) {
		if (baseList == null) {
			return null;
		} else {
			List<P> targetList = new ArrayList<P>();
			for (P p : baseList) {
				targetList.add((P) clone(p));
			}
			return targetList;
		}
	}

	public <V, P> P convert(V base, Class<P> target) {
		if (base == null) {
			return null;
		} else {
			return (P) mapper.map(base, target);
		}
	}

	public <V, P> P convert(V base, P target) {
		if (base != null) {
			mapper.map(base, target);
			return target;
		}
		return target;
	}

	public <V, P> List<P> convertList(List<V> baseList, Class<P> target) {
		if (baseList == null) {
			return null;
		} else {
			List<P> targetList = new ArrayList<P>();
			for (V vo : baseList) {
				targetList.add((P) convert(vo, target));
			}
			return targetList;
		}
	}
}
