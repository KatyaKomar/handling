package by.task.komar.entity.impl;

import by.task.komar.entity.Component;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PunctuationLeaf implements Component {
    private char symbol;

    public PunctuationLeaf(String symbol) {
        this.symbol = symbol.charAt(0);
    }

    public PunctuationLeaf(Character symbol) {
        this.symbol = symbol;
    }

    private static Logger logger = LogManager.getLogger();

    @Override
    public void add(Component component) {
        logger.log(Level.WARN, "Can't add " + component);
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        logger.log(Level.WARN, "Can't remove " + component);
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public List<Component> getList() {
        logger.log(Level.WARN, "Hasn't got list");
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getChild(int index) {
        logger.log(Level.WARN, "Hasn't got child by " + index);
        throw new UnsupportedOperationException();
    }

    @Override
    public CompositeType getType() {
        return CompositeType.PUNCTUATION;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
