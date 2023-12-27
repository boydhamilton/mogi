package m.ecs;

import java.util.List;
import java.awt.*;


public interface system {
    void init(List<entity> entities);

    void update(List<entity> entities);

    void draw(List<entity> entities, Graphics g);
}