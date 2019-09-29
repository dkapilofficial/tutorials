package com.baeldung.encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static java.nio.file.Files.newInputStream;

public class CharacterEncodingExamplesUnitTest {

    @Test
    public void givenTextFile_whenCalledWithEncodingASCII_thenProduceIncorrectResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.readFile(
            "src/test/resources/encoding.txt", "US-ASCII"), 
            "The fa��ade pattern is a software-design pattern commonly used with object-oriented programming.");
    }

    @Test
    public void givenTextFile_whenCalledWithEncodingUTF8_thenProduceCorrectResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.readFile(
            "src/test/resources/encoding.txt", "UTF-8"), 
            "The façade pattern is a software-design pattern commonly used with object-oriented programming.");
    }

    @Test
    public void givenCharacterA_whenConvertedtoBinaryWithEncodingASCII_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("A", "US-ASCII"), 
          "1000001 ");
    }

    @Test
    public void givenCharacterA_whenConvertedtoBinaryWithEncodingUTF8_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("A", "UTF-8"), 
          "1000001 ");
    }

    @Test
    public void givenCharacterCh_whenConvertedtoBinaryWithEncodingBig5_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("語", "Big5"), 
          "10111011 1111001 ");
    }

    @Test
    public void givenCharacterCh_whenConvertedtoBinaryWithEncodingUTF8_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("語", "UTF-8"), 
          "11101000 10101010 10011110 ");
    }

    @Test
    public void givenCharacterCh_whenConvertedtoBinaryWithEncodingUTF32_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("語", "UTF-32"), 
          "0 0 10001010 10011110 ");
    }

    @Test
    public void givenUTF8String_decodeByUS_ASCII_ReplaceMalformedInputSequence() throws IOException {
        String input = "The façade pattern is a software design pattern.";
        Assertions.assertEquals(CharacterEncodingExamples.decodeText(input, StandardCharsets.US_ASCII, CodingErrorAction.REPLACE),
                "The fa��ade pattern is a software design pattern.");
    }

    @Test
    public void givenUTF8String_decodeByUS_ASCII_IgnoreMalformedInputSequence() throws IOException {
        String input = "The façade pattern is a software design pattern.";
        Assertions.assertEquals(
                CharacterEncodingExamples.decodeText(input, StandardCharsets.US_ASCII, CodingErrorAction.IGNORE),
                "The faade pattern is a software design pattern.");
    }

    @Test
    public void givenUTF8String_decodeByUS_ASCII_ReportMalformedInputSequence() {
        String input = "The façade pattern is a software design pattern.";
        Assertions.assertThrows(MalformedInputException.class,
                () -> CharacterEncodingExamples.decodeText(input, StandardCharsets.US_ASCII, CodingErrorAction.REPORT));
    }

    @Test
    public void givenTextFile_FindSuitableCandidateEncodings() {
        Path path = Paths.get("src/test/resources/encoding.txt");
        List<Charset> allCandidateCharSets = Arrays.asList(StandardCharsets.US_ASCII, StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1);
        List<Charset> suitableCharsets = new ArrayList<>();
        allCandidateCharSets.forEach(charset -> {
            try {
                CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
                Reader reader = new InputStreamReader(newInputStream(path), charsetDecoder);
                BufferedReader bufferedReader = new BufferedReader(reader);
                while (bufferedReader.readLine() != null) {
                }
                suitableCharsets.add(charset);
            } catch (MalformedInputException ignored) {
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Assertions.assertEquals(suitableCharsets, Arrays.asList(StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1));
    }

}
