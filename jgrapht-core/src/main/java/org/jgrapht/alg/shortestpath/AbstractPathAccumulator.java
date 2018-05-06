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
 * Abstract PathAccumulator class
 *
 * May be used to accumulate information in path object. User can create multiple accumulators for
 * different data types. RankingPathElement will accumulate each data type separately. In validation
 * the accumulated data will be checked for each new edge. As result of the validation the path
 * might be dropped
 * 
 * @author Masha Dorfman
 * @since January, 23, 2018
 * 
 * @param <E> the graph edge type
 * @param <M> the data type for user to implement
 */
public abstract class AbstractPathAccumulator<E, M>
    implements PathAccumulator<E>
{
    /**
     * Current value.
     */
    protected M value;

    /**
     * Value to compare the current value to. Not necessary Max/Min. E.g. M might be List of Strings.
     */
    protected M limitValue;

    /**
     * Method to assign initial values
     * 
     * @param value initial value to start accumulation
     * @param limitValue value to compare the current value to.
     * 
     * @return new accumulator
     */
    public PathAccumulator<E> init(M value, M limitValue)
    {
        this.value = value;
        this.limitValue = limitValue;
        return this;
    }

    /**
     * Method to copy the accumulator into a new instance. Is used on each new path as on each fork
     * of path.
     * 
     * @return new accumulator
     */
    public PathAccumulator<E> copy()
    {
        return create().init(value, limitValue);
    }

    /**
     * Method creates a copy of the Accumulator implementation Allows user application to use "new"
     * 
     * @return new accumulator
     */
    protected abstract AbstractPathAccumulator<E, M> create();
}
