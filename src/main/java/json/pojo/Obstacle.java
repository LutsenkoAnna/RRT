package json.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Obstacle {

    @JsonProperty("x")
    private int width;

    @JsonProperty("y")
    private int height;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    private List<Point> vertices;

    public Obstacle(@JsonProperty("x") int width, @JsonProperty("y") int height, @JsonProperty("vertices") List<Point> vertices){
        this.width = width;
        this.height = height;
        this.vertices = vertices;
        findMinMax();
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public int getCenterX() {return width; }

    public int getCenterY() {return height; }

    public double getMinX() { return minX; }

    public double getMinY() { return minY; }

    public double getMaxX() { return maxX; }

    public double getMaxY() { return maxY; }

    private void findMinMax() {
        this.minX = 1000; this.minY = 1000;
        this.maxX = -1000; this.maxY = -1000;
        for (int i = 0; i < vertices.size(); ++i) {
            if (vertices.get(i).getY() < this.minY)
                this.minY = vertices.get(i).getY();
            if (vertices.get(i).getY() > this.maxY)
                this.maxY = vertices.get(i).getY();
            if (vertices.get(i).getX() < this.minX)
                this.minX = vertices.get(i).getX();
            if (vertices.get(i).getX() > this.maxX)
                this.maxX = vertices.get(i).getX();
        }
    }
}
