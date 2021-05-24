package by.task.komar.parser;

import by.task.komar.entity.impl.TextComposite;

public abstract class AbstractParser {
    protected AbstractParser nextParser = DefaultParser.getParser();

    public void setNextParser(AbstractParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract TextComposite parse(String data);

    private static class DefaultParser extends AbstractParser {
        private static DefaultParser parser = new DefaultParser();

        public static DefaultParser getParser() {
            return parser;
        }

        @Override
        public TextComposite parse(String data) {
            return null;
        }
    }
}
