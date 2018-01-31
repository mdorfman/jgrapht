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

import java.util.*;

import org.jgrapht.*;

/**
 * Helper class for {@link KShortestPaths}.
 *
 * @since July 5, 2007
 */
final class RankingPathElement<V, E>
    extends AbstractPathElement<V, E> implements GraphPath<V, E>
{
    /**
     * Weight of the path.
     */
    private double weight;
    
    /**
     * Accumulated properties
     */
    private List<PathAccumulator<E>> pathAccumulatorList = new ArrayList<PathAccumulator<E>>();

    private Graph<V, E> graph;

    /**
     * Creates a path element by concatenation of an edge to a path element.
     *
     * @param pathElement
     * @param edge edge reaching the end vertex of the path element created.
     * @param weight total cost of the created path element.
     */
    RankingPathElement(
        Graph<V, E> graph, RankingPathElement<V, E> pathElement, E edge, double weight)
    {
        super(graph, pathElement, edge);
        this.weight = weight;
        this.graph = graph;

        // clone accumulators from previous path and add value of the new edge to each accumulator
        for ( PathAccumulator<E> paElement : pathElement.pathAccumulatorList) {
           	this.pathAccumulatorList.add(paElement.copy().update(edge));
        }
    }

    /**
     * Creates an empty path element.
     *
     * @param vertex end vertex of the path element.
     */
    RankingPathElement(V vertex, List<PathAccumulator<E>> paList)
    {
        super(vertex);
        this.weight = 0;

        // create accumulators 
        for ( PathAccumulator<E> paElement : paList) {
           	this.pathAccumulatorList.add(paElement.copy());
        }
    }

    /**
     * Returns the weight of the path.
     *
     * @return .
     */
    public double getWeight()
    {
        return this.weight;
    }

    /**
     * Returns the previous path element.
     *
     * @return <code>null</code> is the path is empty.
     */
    @Override
    public RankingPathElement<V, E> getPrevPathElement()
    {
        return (RankingPathElement<V, E>) super.getPrevPathElement();
    }
    
    @Override
    public Graph<V, E> getGraph()
    {
        return this.graph;
    }

    @Override
    public V getStartVertex()
    {
        if (getPrevPathElement() == null) {
            return super.getVertex();
        }
        return getPrevPathElement().getStartVertex();
    }

    @Override
    public V getEndVertex()
    {
        return super.getVertex();
    }

    @Override
    public List<E> getEdgeList()
    {
        return super.createEdgeListPath();
    }
    
    public List<PathAccumulator<E>> getPathAccumulatorList()
    {
        return pathAccumulatorList;
    }

    
}

// End RankingPathElement.java
