package by.task.komar._main;

import by.task.komar.entity.impl.TextComposite;
import by.task.komar.exception.CompositeException;
import by.task.komar.exception.TextException;
import by.task.komar.reader.TextReader;
import by.task.komar.service.impl.TextServiceImpl;
import by.task.komar.parser.LexemeParser;
import by.task.komar.parser.SymbolParser;
import by.task.komar.parser.WordParser;

public class Main {
    public static void main(String[] args) {
        try {
            TextReader textReader = new TextReader();
            String text = textReader.readFromFile("src\\main\\resources\\file\\text.txt");
            LexemeParser lexemeParser = new LexemeParser();
            WordParser wordParser = new WordParser();
            SymbolParser symbolParser = new SymbolParser();
            lexemeParser.setNextParser(wordParser);
            wordParser.setNextParser(symbolParser);
            TextComposite composite = lexemeParser.parse(text);
            System.out.println(composite);
            TextServiceImpl textService = new TextServiceImpl();
            System.out.println(textService.countConsonants(composite));
        } catch (TextException e) {
            e.printStackTrace();
        } catch (CompositeException e) {
            e.printStackTrace();
        }
    }
}