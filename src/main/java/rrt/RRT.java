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
        Point pt = new Point(randomGenerator.nextInt(sizeX), randomGenerator.nextInt(sizeY));
        return pt;
    }

    public boolean checkObstacle(Node nearest, Point pt, List<Obstacle> obstacles) {
        Line2D line1 = new Line2D.Double(nearest.getData().getX(),
                nearest.getData().getY(),
                pt.getX(),
                pt.getY()
        );
        Line2D line2;
        for (Obstacle o: obstacles) {
            List<Point> vertices = o.getVertices();
            for (int i = 0; i < vertices.size(); ++i) {
                if (i == vertices.size() - 1)
                    line2 = new Line2D.Double(vertices.get(i).getX(),
                            vertices.get(i).getY(),
                            vertices.get(0).getX(),
                            vertices.get(0).getY()
                    );
                else
                    line2 = new Line2D.Double(vertices.get(i).getX(),
                            vertices.get(i).getY(),
                            vertices.get(i + 1).getX(),
                            vertices.get(i + 1).getY()
                    );
                if (line2.intersectsLine(line1)) {
                    return true;
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
        int iterations = 1000;
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
            }while (nearestVertex.distance(pt) > delta ||
                    checkObstacle(nearestVertex, pt, polygons) ||
                            (nearestVertex.getData().getX() == pt.getX() && nearestVertex.getData().getY() == pt.getY())
                    );
            if (j > 20000)
                break;
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
        return path;
    }
}
