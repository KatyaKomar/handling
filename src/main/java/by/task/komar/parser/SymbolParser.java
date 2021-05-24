package by.task.komar.parser;

import by.task.komar.entity.impl.CompositeType;
import by.task.komar.entity.impl.SymbolLeaf;
import by.task.komar.entity.impl.TextComposite;

public class SymbolParser extends AbstractParser {

    @Override
    public TextComposite parse(String data) {
        TextComposite symbolComposite = new TextComposite(CompositeType.LETTER);
        char[] symbols = data.toCharArray();
        for (char symbol : symbols) {
            SymbolLeaf leaf = new SymbolLeaf(Character.toString(symbol));
            symbolComposite.add(leaf);
        }
        return symbolComposite;
    }
}
