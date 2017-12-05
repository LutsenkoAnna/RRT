import com.fasterxml.jackson.databind.ObjectMapper;
import json.pojo.FieldMap;
import rrt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Anna on 05.12.2017.
 */
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        RRT tree = new RRT();
        try{
            ObjectMapper mapper = new ObjectMapper();
            FieldMap field = mapper.readValue(new File(FieldMap.class.getClassLoader().getResource("Map.json").toURI()), FieldMap.class);
            List<Node> path = tree.method(field.getStart(), field.getFinish(), field.getObstacles());
            System.out.println("Time in ms: " + (System.currentTimeMillis() - start));
            tree.generateJSON(path);
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
