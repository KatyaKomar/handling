package by.test.komar.service;

import by.task.komar.entity.Component;
import by.task.komar.entity.impl.TextComposite;
import by.task.komar.exception.CompositeException;
import by.task.komar.exception.TextException;
import by.task.komar.parser.*;
import by.task.komar.reader.TextReader;
import by.task.komar.service.impl.TextServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TextServiceImplTest {

    String readText(String path) throws TextException {
        TextReader textReader = new TextReader();
        String text = textReader.readFromFile(path);
        return text;
    }

    TextComposite parseText(String data) {
        ParagraphParser paragraphParser = new ParagraphParser();
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();
        paragraphParser.setNextParser(sentenceParser);
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);
        wordParser.setNextParser(symbolParser);
        TextComposite composite = paragraphParser.parse(data);
        return composite;
    }

    @Test
    public void testSortParagraphs() throws TextException, CompositeException {
        String text = readText("src\\test\\java\\resources\\file\\text1.txt");
        String expectedText = readText("src\\test\\java\\resources\\file\\text2.txt");
        TextComposite composite = parseText(text);
        TextServiceImpl textService = new TextServiceImpl();
        List<Component> actualSortedParagraphs = textService.sortParagraphs(composite);
        List<Component> expectedSortedParagraphs = parseText(expectedText).getList();
        Assert.assertEquals(expectedSortedParagraphs.toString(), actualSortedParagraphs.toString());
    }

    @Test
    public void testFindSentencesWithLongWord() throws TextException, CompositeException {
        String text = readText("src\\test\\java\\resources\\file\\text1.txt");
        TextComposite composite = parseText(text);
        TextServiceImpl textService = new TextServiceImpl();
        List<Component> actualSentences = textService.findSentencesWithLongWord(composite);
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);
        wordParser.setNextParser(symbolParser);
        TextComposite expectedComposite = sentenceParser.parse("Advertising companies say advertising is necessary and important. Advertising hoardings in the street make our environment colourful. Sometimes they are mini-dramas and we wait for the next programme in the mini-drama. Advertising can educate, too. And adverts in magazines give us ideas for how to look prettier, be fashionable and be successful. Without advertising life is boring and colourless. But some consumers argue that advertising is a bad thing. They say that advertising is bad for children.");
        List<Component> expectedSentences = expectedComposite.getList();
        Assert.assertEquals(expectedSentences.toString(), actualSentences.toString());
    }

    @Test
    public void testDeleteSentencesWithShortWord() throws TextException, CompositeException {
        String text = readText("src\\test\\java\\resources\\file\\text1.txt");
        TextComposite composite = parseText(text);
        TextServiceImpl textService = new TextServiceImpl();
        List<Component> actualSentences = textService.deleteSentencesWithShortWord(composite, 3);
        SentenceParser sentenceParser = new SentenceParser();
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();
        sentenceParser.setNextParser(lexemeParser);
        lexemeParser.setNextParser(wordParser);
        wordParser.setNextParser(symbolParser);
        TextComposite expectedComposite = sentenceParser.parse("Advertising can educate, too.");
        List<Component> expectedSentences = expectedComposite.getList();
        Assert.assertEquals(expectedSentences.toString(), actualSentences.toString());
    }

    @Test
    public void testCountRepeatWords() throws CompositeException, TextException {
        String text = readText("src\\test\\java\\resources\\file\\text1.txt");
        TextComposite composite = parseText(text);
        TextServiceImpl textService = new TextServiceImpl();
        List<String> actualWords = textService.countRepeatWords(composite);
        List<String> expectedWords = new ArrayList<>();
        expectedWords.add("bad");
        expectedWords.add("about");
        expectedWords.add("advertising");
        expectedWords.add("adverts");
        expectedWords.add("that");
        expectedWords.add("they");
        expectedWords.add("us");
        expectedWords.add("new");
        expectedWords.add("in");
        expectedWords.add("is");
        expectedWords.add("be");
        expectedWords.add("for");
        expectedWords.add("products");
        expectedWords.add("are");
        expectedWords.add("and");
        expectedWords.add("say");
        expectedWords.add("the");
        Assert.assertEquals(expectedWords, actualWords);
    }

    @Test
    public void testCountVowels() throws CompositeException {
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();
        lexemeParser.setNextParser(wordParser);
        wordParser.setNextParser(symbolParser);
        TextComposite composite = lexemeParser.parse("Advertising can educate, too.");
        TextServiceImpl textService = new TextServiceImpl();
        int actualCount = textService.countVowels(composite);
        int expectedCount = 11;
        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountConsonants() throws CompositeException {
        LexemeParser lexemeParser = new LexemeParser();
        WordParser wordParser = new WordParser();
        SymbolParser symbolParser = new SymbolParser();
        lexemeParser.setNextParser(wordParser);
        wordParser.setNextParser(symbolParser);
        TextComposite composite = lexemeParser.parse("Advertising can educate, too.");
        TextServiceImpl textService = new TextServiceImpl();
        int actualCount = textService.countConsonants(composite);
        int expectedCount = 13;
        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test(expectedExceptions = CompositeException.class)
    public void testFundException() throws CompositeException {
        TextServiceImpl textService = new TextServiceImpl();
        List<Component> actualSentences = textService.findSentencesWithLongWord(null);
    }
}