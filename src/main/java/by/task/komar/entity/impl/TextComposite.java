package by.task.komar.entity.impl;

import by.task.komar.entity.Component;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TextComposite implements Component {
    private static Logger logger = LogManager.getLogger();
    private CompositeType compositeType;
    private List<Component> components = new ArrayList<>();

    public TextComposite(CompositeType compositeType) {
        this.compositeType = compositeType;
    }

    @Override
    public void add(Component component) {
        components.add(component);
        logger.log(Level.INFO, "Add new component " + component);
    }

    @Override
    public void remove(Component component) {
        components.remove(component);
        logger.log(Level.INFO, "Delete " + component);
    }

    @Override
    public int size() {
        return components.size();
    }

    @Override
    public List<Component> getList() {
        return components;
    }

    @Override
    public Object getChild(int index) {
        return components.get(index);
    }

    @Override
    public CompositeType getType() {
        return compositeType;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(compositeType.getDelimiter(), compositeType.getPrefix(), "");
        for (Component сomponent : components) {
            stringJoiner.add(сomponent.toString());
        }
        return stringJoiner.toString();
    }
}
