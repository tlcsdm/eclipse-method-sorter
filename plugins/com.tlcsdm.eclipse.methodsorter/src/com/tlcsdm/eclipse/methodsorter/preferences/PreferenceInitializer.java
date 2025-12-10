/**
 * Copyright (c) 2011 Mateusz Parzonka
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package com.tlcsdm.eclipse.methodsorter.preferences;

import java.util.Collection;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tlcsdm.eclipse.methodsorter.Activator;
import com.tlcsdm.eclipse.methodsorter.Utils;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.INVOCATION_ORDERING_STRATEGY,
				PreferenceConstants.INVOCATION_ORDERING_STRATEGY_DEPTH_FIRST);
		store.setDefault(PreferenceConstants.INVOCATION_STARTPOINT_STRATEGY,
				PreferenceConstants.INVOCATION_STARTPOINT_STRATEGY_HEURISTIC);
		store.setDefault(PreferenceConstants.CLUSTER_GETTER_SETTER, false);
		store.setDefault(PreferenceConstants.RESPECT_BEFORE_AFTER, true);
		store.setDefault(PreferenceConstants.CLUSTER_OVERLOADED_METHODS, false);
		store.setDefault(PreferenceConstants.METHOD_ORDERING_PRIORITIES,
				Utils.join(getDefaultMethodOrderingPriorities(), "#"));
	}

	public static Collection<String> getDefaultMethodOrderingPriorities() {
		return Utils.list(PreferenceConstants.PRIORITY_INVOCATION_ORDER, PreferenceConstants.PRIORITY_ACCESS_LEVEL,
				PreferenceConstants.PRIORITY_SOURCE_POSITION, PreferenceConstants.PRIORITY_LEXICALITY);
	}

}
