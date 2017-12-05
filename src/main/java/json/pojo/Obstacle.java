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

    private List<Point> vertices;

    public Obstacle(@JsonProperty("x") int width, @JsonProperty("y") int height, @JsonProperty("vertices") List<Point> vertices){
        this.width = width;
        this.height = height;
        this.vertices = vertices;
    }

    public List<Point> getVertices() {
        return vertices;
    }
}
