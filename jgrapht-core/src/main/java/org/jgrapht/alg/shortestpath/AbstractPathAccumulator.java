/*
 * Copyright © 2018. AT&T Intellectual Property.  All rights reserved.
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
 * @param <M> the data type for user to implement
 */
public abstract class AbstractPathAccumulator <E, M> implements PathAccumulator <E> {
	protected M value;
	protected M limitValue;
	
	public PathAccumulator<E> init(M value, M limitValue) {
        this.value = value;
        this.limitValue = limitValue;
        return this;
	}

	public PathAccumulator<E> copy() {
		return create().init(value, limitValue);
	}

	protected abstract AbstractPathAccumulator<E, M> create();
}
