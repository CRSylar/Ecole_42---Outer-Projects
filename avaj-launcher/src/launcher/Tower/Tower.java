package launcher.Tower;

import launcher.Aircraft.Flyable;
import launcher.FileHandler.Writer;

import java.util.ArrayList;
import java.util.List;

public class Tower {

    private final List<Flyable> observers = new ArrayList<Flyable>();

    public void register(Flyable p_flyable) {
        if (observers.contains(p_flyable))
            return;
        observers.add(p_flyable);
        String flyableName = p_flyable.getClass().getSimpleName();
        Writer.getInstance().write("Tower: "+flyableName+"#"+p_flyable.getName()+"("+p_flyable.getId()+") registered to Weather Tower ");

    }
    public void unregister(Flyable p_flyable) {
        observers.remove(p_flyable);

        String flyableName = p_flyable.getClass().getSimpleName();
        Writer.getInstance().write("Tower: "+flyableName+"#"+p_flyable.getName()+"("+p_flyable.getId()+") UnRegistered from Weather Tower ");
    }
    protected void conditionChanged() {
        for (int i=0; i < observers.size(); i++)
            observers.get(i).updateConditions();

    }
}
