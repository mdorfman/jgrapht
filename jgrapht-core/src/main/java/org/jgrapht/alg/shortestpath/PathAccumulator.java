/*
 * (C) Copyright 2007-2018, by France Telecom and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.alg.shortestpath;

/**
 * Interface for Path Accumulator instance
 * 
 * May be used to accumulate information in path object. User can create multiple accumulators for
 * different data types. RankingPathElement will accumulate each data type separately In validation
 * the accumulated data will be checked for each new edge As result of the validation the path might
 * be dropped
 * 
 * @author Masha Dorfman
 * @since January, 23, 2018
 *
 * @param <E> the graph edge type
 */
public interface PathAccumulator<E>
{

    /**
     * Method creates a copy of the Accumulator implementation.
     * 
     * @return new accumulator
     */
    public PathAccumulator<E> copy();

    /**
     * Method is applied *after* next edge (@param) is concatenated to the path Method retrieves
     * value from edge and updates the accumulated value
     * 
     * @param edge new edge where next value is taken from
     * 
     * @return *this* object with new accumulated value
     */
    public PathAccumulator<E> update(E edge);

    /**
     * Method is applied *before* next edge (@param) is concatenated to the path Method should
     * validate the concatenation without actually update the accumulated value
     * 
     * @param edge new edge where next value is taken from
     * 
     * @return true if path should be continues, false - if path should be dropped
     */
    public boolean isValid(E edge);
}
