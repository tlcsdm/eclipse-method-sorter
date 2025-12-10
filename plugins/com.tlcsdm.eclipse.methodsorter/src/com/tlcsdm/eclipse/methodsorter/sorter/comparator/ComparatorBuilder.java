/**
 * Copyright (c) 2011 Mateusz Parzonka
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package com.tlcsdm.eclipse.methodsorter.sorter.comparator;

import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_ACCESS_LEVEL;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_CONSTRUCTOR;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_FAN_OUT;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_INITIALIZER_INVOCATION;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_INVOCATION_ORDER;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_LEXICALITY;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_ROOTS;
import static com.tlcsdm.eclipse.methodsorter.preferences.PreferenceConstants.PRIORITY_SOURCE_POSITION;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getAccessLevelComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getConstructorComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getFanOutComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getInitializerInvocationComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getInvocationComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getLexicalComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getRootSeparationComparator;
import static com.tlcsdm.eclipse.methodsorter.sorter.comparator.ComparatorFactory.getSourcePositionComparator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlcsdm.eclipse.methodsorter.preferences.IPreferences;
import com.tlcsdm.eclipse.methodsorter.sorter.callgraph.CallGraphNode;
import com.tlcsdm.eclipse.methodsorter.sorter.invocation.NodeOrdering;
import com.tlcsdm.eclipse.methodsorter.sorter.invocation.NodeOrderingInterleaved;
import com.tlcsdm.eclipse.methodsorter.sorter.invocation.NodeOrderingSimple;

/**
 * Encapsulates the mapping of {@link IPreferences} to the comparators produced
 * by the {@link ComparatorFactory}.
 *
 * @author Mateusz Parzonka
 *
 */
public class ComparatorBuilder {

	final private static Logger logger = LoggerFactory.getLogger(ComparatorBuilder.class);

	private final List<? extends CallGraphNode> callGraph;
	private final ASTNode ast;
	private final Set<Signature> knownSignatures;
	private final IPreferences preferences;

	public ComparatorBuilder(List<? extends CallGraphNode> callGraph, ASTNode ast, IPreferences preferences) {
		super();
		this.callGraph = callGraph;
		this.ast = ast;
		this.preferences = preferences;
		this.knownSignatures = new HashSet<Signature>();
		for (final CallGraphNode node : callGraph)
			this.knownSignatures.add(node.getSignature());
		logger.trace("Created {}", this.getClass().getName());
	}

	public Comparator<Signature> getMethodOrderingComparator() {
		final StackableSignatureComparator comparator = new StackableSignatureComparator(this.knownSignatures);

		logger.debug("Start-points: {}", logCallGraph(this.callGraph));

		for (final String property : this.preferences.getMethodOrderingPreferences()) {
			logger.debug("Adding comparator for [{}] to stackable comparator", property);
			comparator.add(getComparator(property));
		}
		return comparator;
	}

	private static String logCallGraph(List<? extends CallGraphNode> aCallGraph) {
		final String LF = System.getProperty("line.separator");
		final StringBuilder sb = new StringBuilder();
		for (final CallGraphNode callGraphNode : aCallGraph) {
			sb.append(callGraphNode.toString()).append(LF);
		}
		return sb.toString();
	}

	private Comparator<Signature> getComparator(String property) {

		if (property.equals(PRIORITY_INVOCATION_ORDER)) {

			final NodeOrdering nodeOrdering = getNodeOrdering(this.preferences.isBeforeAfterRelation());
			final boolean traversalStrategy = this.preferences.isInvocationStrategyDepthFirst();
			return getInvocationComparator(nodeOrdering, traversalStrategy, this.callGraph);
		}

		else if (property.equals(PRIORITY_ACCESS_LEVEL))
			return getAccessLevelComparator(this.ast);

		else if (property.equals(PRIORITY_CONSTRUCTOR))
			return getConstructorComparator(this.ast);

		else if (property.equals(PRIORITY_FAN_OUT))
			return getFanOutComparator(this.callGraph);

		else if (property.equals(PRIORITY_INITIALIZER_INVOCATION))
			return getInitializerInvocationComparator(this.ast);

		else if (property.equals(PRIORITY_LEXICALITY))
			return getLexicalComparator();

		else if (property.equals(PRIORITY_ROOTS))
			return getRootSeparationComparator(this.callGraph);

		else if (property.equals(PRIORITY_SOURCE_POSITION))
			return getSourcePositionComparator(this.ast);

		else
			throw new IllegalArgumentException(property);
	}

	private NodeOrdering getNodeOrdering(boolean beforeAfterRelation) {
		if (beforeAfterRelation)
			return new NodeOrderingInterleaved();
		return new NodeOrderingSimple();
	}

}