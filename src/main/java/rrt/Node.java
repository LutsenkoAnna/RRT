package rrt;

import json.pojo.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Anna on 28.11.2017.
 */

public class Node {
    private Point data;
    private Node parent;
    private ArrayList<Node> children;

    public Node() {
        data = new Point(0,0);
        children = new ArrayList<Node>();
        parent = null;
    }

    public Node(Point a, Node p) {
        data = a;
        children = new ArrayList<Node>();
        parent = p;
    }

    public Point getData() {
        return data;
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    public Node getParent() { return parent; }

    private void findDistance(Point p, HashMap<Node, Double> distanceToPoint) {
        distanceToPoint.put(this, distance(p));
        for(Node c: children)
            c.findDistance(p, distanceToPoint);
    }

    public Node closestVertex(Point p) {
        HashMap<Node, Double> distanceToPoint = new HashMap<Node, Double>();
        findDistance(p, distanceToPoint);
        Node minNode = new Node();
        double minDistance = 9999.0;
        for (Map.Entry<Node, Double> entry : distanceToPoint.entrySet()) {
            if (entry.getValue() < minDistance) {
                minDistance = entry.getValue();
                minNode = entry.getKey();
            }
        }
        return minNode;
    }

    public double  distance(Point b) {
        return Math.sqrt(Math.pow(b.getX() - data.getX(),2) + Math.pow(b.getY() - data.getY(),2));
    }

    public void addChild(Point a) {
        Node tmp = new Node(a, this);
        this.children.add(tmp);
    }

    public void addChildAt(Node vertex, Point a) {
        if(this.getData() == vertex.getData()) {
            this.addChild(a);
        }
        else {
            for (int i = 0; i < children.size(); ++i) {
                this.getChild(i).addChildAt(vertex, a);
            }
        }
    }

    public boolean isReached(Point end) {
        if (distance(end) <= 1.0)
            return true;
        return false;
    }
}
