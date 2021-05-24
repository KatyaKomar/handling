package by.task.komar.entity;

import by.task.komar.entity.impl.CompositeType;

import java.util.List;

public interface Component {
    void add(Component component);

    void remove(Component component);

    int size();

    List<Component> getList();

    Object getChild(int index);

    CompositeType getType();

    String toString();
}
