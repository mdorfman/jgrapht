/*
 * Copyright © 2018 AT&T, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.jgrapht.alg.shortestpath;

/**
 * May be used to provide ability to accumulate information in path object.   
 * User can create multiple accumulators for different data types. 
 * RankingPathElement will accumulate each data type separately 
 * Optionally validation can be defined. Then the accumulated data will be checked for each new edge
 * As result of the validation the path might be dropped       
 * 
 * @author Masha Dorfman
 * @since January, 23, 2018
 *
 * @param <E> the graph edge type
 */
public interface PathAccumulator<E> {
	
	/**
	 * Method creates a copy of the Accumulator implementation
	 * 
	 * @return new accumulator
	 */
	public PathAccumulator<E> copy();
	
	/**
	 * Method retrieves value from edge and updates the accumulated value 
	 * 
	 * @param edge
	 * @return *this* object with new accumulated value
	 */
	public PathAccumulator<E> update(E edge);
	
	/** 
	 * Method is applied *before* next edge (@param) is concatenated to the path
	 * Method should validate the concatenation without actually update the accumulated value   
	 * 
	 */
	public boolean isValid(E edge);
}
