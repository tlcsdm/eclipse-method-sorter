/**
 * Copyright (c) 2011 Mateusz Parzonka
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package com.tlcsdm.eclipse.methodsorter.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tlcsdm.eclipse.methodsorter.Activator;
import com.tlcsdm.eclipse.methodsorter.Utils;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		addField(new SimpleListEditor(PreferenceConstants.METHOD_ORDERING_PRIORITIES,
				PreferenceConstants.METHOD_ORDERING_PRIORITIES, getFieldEditorParent()));

		addRadioGroupField(PreferenceConstants.INVOCATION_STARTPOINT_STRATEGY,
				PreferenceConstants.INVOCATION_STARTPOINT_STRATEGY_USER,
				PreferenceConstants.INVOCATION_STARTPOINT_STRATEGY_HEURISTIC);

		addRadioGroupField(PreferenceConstants.INVOCATION_ORDERING_STRATEGY,
				PreferenceConstants.INVOCATION_ORDERING_STRATEGY_BREADTH_FIRST,
				PreferenceConstants.INVOCATION_ORDERING_STRATEGY_DEPTH_FIRST);

		addBooleanField(PreferenceConstants.CLUSTER_OVERLOADED_METHODS);

		addBooleanField(PreferenceConstants.CLUSTER_GETTER_SETTER);

		addBooleanField(PreferenceConstants.RESPECT_BEFORE_AFTER);

	}

	private void addRadioGroupField(String fieldName, String option1, String option2) {
		addField(new RadioGroupFieldEditor(fieldName, fieldName, 1,
				new String[][] { { option1, option1 }, { option2, option2 } }, getFieldEditorParent()));
	}

	private void addBooleanField(String field) {
		addField(new BooleanFieldEditor(field, field, getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	public class SimpleListEditor extends ListEditor {

		public SimpleListEditor(String name, String labelText, Composite parent) {
			super(name, labelText, parent);
			getAddButton().setVisible(false);
			getRemoveButton().setVisible(false);
			getDownButton().moveAbove(getRemoveButton());
			getUpButton().moveAbove(getDownButton());
		}

		@Override
		protected String createList(String[] items) {
			return Utils.join(Utils.list(items), PreferenceConstants.DELIMITER);
		}

		@Override
		protected String getNewInputObject() {
			return null;
		}

		@Override
		protected String[] parseString(String stringList) {
			return stringList.split(PreferenceConstants.DELIMITER);
		}

	}

}