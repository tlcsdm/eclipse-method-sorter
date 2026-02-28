/**
 * Copyright (c) 2011 Mateusz Parzonka
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package com.tlcsdm.eclipse.methodsorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Static helpers.
 *
 * @author Mateusz Parzonka
 *
 */
public class Utils {

	private Utils() {
		// not meant to be instantiated
	}

	public static String join(Collection<String> strings, String delimiter) {
		if (strings.isEmpty())
			return "";
		final Iterator<String> iter = strings.iterator();
		final StringBuilder buffer = new StringBuilder(iter.next());
		while (iter.hasNext())
			buffer.append(delimiter).append(iter.next());
		return buffer.toString();
	}

	@SafeVarargs
	public static <T> List<T> list(T... objects) {
		final List<T> result = new ArrayList<T>();
		for (final T t : objects)
			result.add(t);
		return result;
	}

}
