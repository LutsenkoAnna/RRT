package rrt;

import json.pojo.Obstacle;
import json.pojo.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.geom.Line2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anna on 28.11.2017.
 */

public class RRT {
    static public int sizeX = 50;
    static public int sizeY = 30;

    public Point getRandomPoint() {
        Random randomGenerator = new Random();
        Point pt = new Point(randomGenerator.nextDouble() * sizeX, randomGenerator.nextDouble() * sizeY);
        return pt;
    }

    private boolean checkLines(Point p1, Point p2, Point p3, Point p4) {
        Line2D line1 = new Line2D.Double(p1.getX(), p1.getY(),
                p2.getX(), p2.getY()
        );
        Line2D line2 = new Line2D.Double(p3.getX(), p3.getY(),
                p4.getX(), p4.getY()
        );
        return (line2.intersectsLine(line1));
    }

    public boolean checkObstacle(Node nearest, Point pt, List<Obstacle> obstacles) {
        boolean intersect = false;
        double maxY, minY, maxX, minX;
        if (nearest.getData().getY() > pt.getY()) {
            maxY = nearest.getData().getY();
            minY = pt.getY();
        } else {
            minY = nearest.getData().getY();
            maxY = pt.getY();
        }
        if (nearest.getData().getX() > pt.getX()) {
            maxX = nearest.getData().getX();
            minX = pt.getX();
        } else {
            minX = nearest.getData().getX();
            maxX = pt.getX();
        }
        for (Obstacle o: obstacles) {
            if (o.getMaxY() > minY || o.getMinY() < maxY || o.getMaxX() > minX || o.getMinX() < maxX) {
                List<Point> vertices = o.getVertices();
                for (int i = 0; i < vertices.size(); ++i) {
                    if (vertices.get(i).getY() > minY || vertices.get(i).getY() < maxY ||
                            vertices.get(i).getX() > minX || vertices.get(i).getX() < maxX) {
                        if (i == vertices.size() - 1)
                            intersect = checkLines(nearest.getData(), pt, vertices.get(i), vertices.get(0));
                        else
                            intersect = checkLines(nearest.getData(), pt, vertices.get(i), vertices.get(i + 1));
                        if (intersect) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void generateJSON(List<Node> path) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < path.size() - 1; ++i) {
            JSONObject tmp1 = new JSONObject();
            tmp1.put("x1", path.get(i).getData().getX());
            tmp1.put("y1", path.get(i).getData().getY());
            tmp1.put("x2", path.get(i + 1).getData().getX());
            tmp1.put("y2", path.get(i + 1).getData().getY());
            array.add(tmp1);

            JSONObject tmp = new JSONObject();
            tmp.put("cx", path.get(i).getData().getX());
            tmp.put("cy", path.get(i).getData().getY());
            array.add(tmp);
        }
        JSONObject tmp = new JSONObject();
        tmp.put("cx", path.get(path.size() - 1).getData().getX());
        tmp.put("cy", path.get(path.size() - 1).getData().getY());
        array.add(tmp);
        System.out.println(array.toJSONString());
    }

    public List<Node> method(Point start, Point finish, List<Obstacle> polygons) {
        double delta = 5.0;
        int maxIterations = 10000;
        int iterations = 5000;
        Node explore = new Node(start, null);
        int i, j = 0;
        Point pt;
        Node nearestVertex;
        List<Node> path = new ArrayList<Node>();
        for (i = 0; i < iterations; ++i) {
            do {
                pt = getRandomPoint();
                nearestVertex = explore.closestVertex(pt);
                ++j;
                if (j > maxIterations) {
                    break;
                }
            }while (//nearestVertex.distance(pt) > delta ||
                    checkObstacle(nearestVertex, pt, polygons) ||
                            (nearestVertex.getData().getX() == pt.getX() && nearestVertex.getData().getY() == pt.getY())
                    );
            if (j > maxIterations){
                break;
            }
            explore.addChildAt(nearestVertex, pt);
            if (nearestVertex.isReached(finish)) {
                break;
            }
        }
        Node q = explore.closestVertex(finish);
        while (q != null) {
            path.add(q);
            q = q.getParent();
        }
        System.out.println("Iterations: " + i);
        System.out.println("MaxIterations: " + j);
        return path;
    }
}
