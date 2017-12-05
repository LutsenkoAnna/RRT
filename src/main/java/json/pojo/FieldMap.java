package json.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FieldMap {
    private Point start;
    private Point finish;

    @JsonProperty("polygons")
    List<Obstacle> obstacles;

    public FieldMap(@JsonProperty("start") Point start, @JsonProperty("finish") Point finish,
                    @JsonProperty("polygons") List<Obstacle> obstacles)
    {
       this.start = start;
       this.finish = finish;
       this.obstacles = obstacles;
    }

    public Point getStart() {
        return start;
    }

    public Point getFinish() {
        return finish;
    }

    public List<Obstacle> getObstacles() { return obstacles; }

}
