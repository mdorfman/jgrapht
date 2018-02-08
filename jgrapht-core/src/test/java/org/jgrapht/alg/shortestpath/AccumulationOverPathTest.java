/*
 * Copyright © 2018 AT&T, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * @author Masha Dorfman
 */
package org.jgrapht.alg.shortestpath;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.*;
import org.junit.Test;

public class AccumulationOverPathTest {

    private static DefaultDirectedWeightedGraph<String, GraphEdge> wGraph;

    @Test
    public void test1() {
        KShortestPaths<String, GraphEdge> algo = buildGraph();

        algo.setPathAccumulator((new PathAccumulatorA()).init(0D, 15D));
        algo.setPathAccumulator((new PathAccumulatorB()).init(0, 30));

        List<GraphPath<String, GraphEdge>> allwPaths = algo.getPaths("V1", "V5");

        assertEquals(3, allwPaths.size());
        assertEquals("[[(V1 : V2), (V2 : V4), (V4 : V5)]," + " [(V1 : V3), (V3 : V5)], "
                + "[(V1 : V3), (V3 : V4), (V4 : V5)]]", allwPaths.toString());
    }

    @Test
    public void test2() {
        KShortestPaths<String, GraphEdge> algo = buildGraph();

        algo.setPathAccumulator((new PathAccumulatorA()).init(0D, 8D));
        algo.setPathAccumulator((new PathAccumulatorB()).init(0, 30));

        List<GraphPath<String, GraphEdge>> allwPaths = algo.getPaths("V1", "V5");

        assertEquals(2, allwPaths.size());
        assertEquals("[[(V1 : V2), (V2 : V4), (V4 : V5)],"+" [(V1 : V3), (V3 : V4), (V4 : V5)]]",
                allwPaths.toString());
    }

    @Test
    public void test3() {
        KShortestPaths<String, GraphEdge> algo = buildGraph();

        algo.setPathAccumulator((new PathAccumulatorA()).init(0D, 8D));
        algo.setPathAccumulator((new PathAccumulatorB()).init(0, 10));

        List<GraphPath<String, GraphEdge>> allwPaths = algo.getPaths("V1", "V5");

        assertEquals(1, allwPaths.size());
        assertEquals("[[(V1 : V3), (V3 : V4), (V4 : V5)]]", allwPaths.toString());
    }

    class PathAccumulatorA extends AbstractPathAccumulator<GraphEdge, Double> {

        public PathAccumulator<GraphEdge> update(GraphEdge edge) {
            value = value + edge.getA();
            return this;
        }

        @Override
        public AbstractPathAccumulator<GraphEdge, Double> create() {
            return new PathAccumulatorA();
        }

        public boolean isValid(GraphEdge edge) {
            return (value + edge.getA()) < limitValue;
        }
    }

    class PathAccumulatorB extends AbstractPathAccumulator<GraphEdge, Integer> {

        public PathAccumulator<GraphEdge> update(GraphEdge edge) {
            value = value + edge.getB();
            return this;
        }

        @Override
        public AbstractPathAccumulator<GraphEdge, Integer> create() {
            return new PathAccumulatorB();
        }

        public boolean isValid(GraphEdge edge) {
            return (value + edge.getB()) < limitValue;
        }
    }

    class GraphEdge extends DefaultWeightedEdge {

        double propertyA;
        int propertyB;

        private static final long serialVersionUID = 1L;

        public GraphEdge(String src, String dst, double a, int b) {
            super();
            propertyA = a;
            propertyB = b;
        }

        public double getA() {
            return propertyA;
        }

        public int getB() {
            return propertyB;
        }
    }

    private KShortestPaths<String, GraphEdge> buildGraph() {
        // build graph
        wGraph = new DefaultDirectedWeightedGraph<String, GraphEdge>(GraphEdge.class);
        addVertices();
        addEdges();

        // KShortestPaths on wGraph
        return new KShortestPaths<String, GraphEdge>(wGraph, 3, 5);
    }

    private void addVertices() {
        wGraph.addVertex("V1");
        wGraph.addVertex("V2");
        wGraph.addVertex("V3");
        wGraph.addVertex("V4");
        wGraph.addVertex("V5");
    }

    private void addEdges() {

        GraphEdge ed = new GraphEdge("V1", "V2", 1, 10);
        wGraph.addEdge("V1", "V2", ed);
        wGraph.setEdgeWeight(ed, 1.0);

        ed = new GraphEdge("V2", "V4", 1, 10);
        wGraph.addEdge("V2", "V4", ed);
        wGraph.setEdgeWeight(ed, 1.0);

        ed = new GraphEdge("V4", "V5", 1, 1);
        wGraph.addEdge("V4", "V5", ed);
        wGraph.setEdgeWeight(ed, 1.0);

        ed = new GraphEdge("V1", "V3", 5, 1);
        wGraph.addEdge("V1", "V3", ed);
        wGraph.setEdgeWeight(ed, 2.0);

        ed = new GraphEdge("V3", "V4", 1, 1);
        wGraph.addEdge("V3", "V4", ed);
        wGraph.setEdgeWeight(ed, 3.0);

        ed = new GraphEdge("V3", "V5", 5, 10);
        wGraph.addEdge("V3", "V5", ed);
        wGraph.setEdgeWeight(ed, 2.0);
    }

}
