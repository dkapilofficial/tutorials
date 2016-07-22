package com.baeldung.xmlunit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.xmlunit.matchers.CompareMatcher.isIdenticalTo;
import static org.xmlunit.matchers.CompareMatcher.isSimilarTo;
import static org.xmlunit.matchers.HasXPathMatcher.hasXPath;

import java.io.File;
import java.util.Iterator;

import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ComparisonControllers;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationProblem;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;
import org.xmlunit.xpath.JAXPXPathEngine;

public class XMLUnitTest {
	@Test
	public void givenWrongXml_whenValidateFailsAgainstXsd_thenCorrect() {
		Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSource(Input.fromStream(
				new XMLUnitTest().getClass().getResourceAsStream(
						"/students.xsd")).build());
		ValidationResult r = v.validateInstance(Input.fromStream(
				new XMLUnitTest().getClass().getResourceAsStream(
						"/students_with_error.xml")).build());
		assertFalse(r.isValid());
	}

	@Test
	public void givenXmlWithErrors_whenReturnsValidationProblems_thenCorrect() {
		Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSource(Input.fromStream(
				new XMLUnitTest().getClass().getResourceAsStream(
						"/students.xsd")).build());
		ValidationResult r = v.validateInstance(Input.fromStream(
				new XMLUnitTest().getClass().getResourceAsStream(
						"/students_with_error.xml")).build());
		Iterator<ValidationProblem> probs = r.getProblems().iterator();
		int count = 0;
		while (probs.hasNext()) {
			count++;
			probs.next().toString();
		}
		assertTrue(count > 0);
	}

	@Test
	public void givenXml_whenValidatesAgainstXsd_thenCorrect() {
		Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSource(Input.fromStream(
				new XMLUnitTest().getClass().getResourceAsStream(
						"/students.xsd")).build());
		ValidationResult r = v.validateInstance(Input.fromStream(
				new XMLUnitTest().getClass().getResourceAsStream(
						"/students.xml")).build());
		Iterator<ValidationProblem> probs = r.getProblems().iterator();
		while (probs.hasNext()) {
			System.out.println(probs.next().toString());
		}
		assertTrue(r.isValid());
	}

	@Test
	public void givenXPath_whenAbleToRetrieveNodes_thenCorrect() {
		ClassLoader classLoader = getClass().getClassLoader();

		Iterable<Node> i = new JAXPXPathEngine().selectNodes(
				"//teacher",
				Input.fromFile(
						new File(classLoader.getResource("teachers.xml")
								.getFile())).build());
		assertNotNull(i);
		int count = 0;
		for (Iterator<Node> it = i.iterator(); it.hasNext();) {
			count++;
			Node node = it.next();
			assertEquals("teacher", node.getNodeName());
			NamedNodeMap map = node.getAttributes();
			assertEquals("department", map.item(0).getNodeName());
			assertEquals("id", map.item(1).getNodeName());
			assertEquals("teacher", node.getNodeName());
		}
		assertEquals(2, count);
	}

	@Test
	public void givenXmlSource_whenAbleToValidateExistingXPath_thenCorrect() {
		ClassLoader classLoader = getClass().getClassLoader();
		assertThat(Input.fromFile(new File(classLoader.getResource(
				"teachers.xml").getFile())), hasXPath("//teachers"));
		assertThat(Input.fromFile(new File(classLoader.getResource(
				"teachers.xml").getFile())), hasXPath("//teacher"));
		assertThat(Input.fromFile(new File(classLoader.getResource(
				"teachers.xml").getFile())), hasXPath("//subject"));
		assertThat(Input.fromFile(new File(classLoader.getResource(
				"teachers.xml").getFile())), hasXPath("//@department"));
	}

	@Test
	public void givenXmlSource_whenFailsToValidateInExistentXPath_thenCorrect() {
		ClassLoader classLoader = getClass().getClassLoader();

		assertThat(Input.fromFile(new File(classLoader.getResource(
				"teachers.xml").getFile())), not(hasXPath("//sujet")));
	}

	@Test
	public void given2XMLs_whenSimilarWithDiff_thenCorrect() throws Exception {
		String myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
		String myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";

		Diff myDiffSimilar = DiffBuilder
				.compare(myControlXML)
				.withTest(myTestXML)
				.withNodeMatcher(
						new DefaultNodeMatcher(ElementSelectors.byName))
				.checkForSimilar().build();
		assertFalse("XML similar " + myDiffSimilar.toString(),
				myDiffSimilar.hasDifferences());

	}

	@Test
	public void given2XMLsWithDifferences_whenTestsSimilarWithDifferenceEvaluator_thenCorrect() {
		final String control = "<a><b attr=\"abc\"></b></a>";
		final String test = "<a><b attr=\"xyz\"></b></a>";
		Diff myDiff = DiffBuilder
				.compare(control)
				.withTest(test)
				.withDifferenceEvaluator(
						new IgnoreAttributeDifferenceEvaluator("attr"))
				.checkForSimilar().build();

		assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void given2XMLsWithDifferences_whenTestsDifferentWithoutDifferenceEvaluator_thenCorrect() {
		final String control = "<a><b attr=\"abc\"></b></a>";
		final String test = "<a><b attr=\"xyz\"></b></a>";

		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.checkForSimilar().build();

		assertTrue(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void given2XMLS_whenSimilarWithCustomElementSelector_thenCorrect() {
		String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		String testXml = "<struct><boolean>false</boolean><int>3</int></struct>";
		assertThat(
				testXml,
				isSimilarTo(controlXml).withNodeMatcher(
						new DefaultNodeMatcher(ElementSelectors.byName)));

	}

	@Test
	public void givenFileSourceAsObject_whenAbleToInput_thenCorrect() {
		ClassLoader classLoader = getClass().getClassLoader();
		assertThat(Input.from(new File(classLoader.getResource("test.xml")
				.getFile())), isSimilarTo(Input.from(new File(classLoader
				.getResource("control.xml").getFile()))));

	}

	@Test
	public void givenStreamAsSource_whenAbleToInput_thenCorrect() {
		assertThat(Input.fromStream(new XMLUnitTest().getClass()
				.getResourceAsStream("/test.xml")),
				isSimilarTo(Input.fromStream(new XMLUnitTest().getClass()
						.getResourceAsStream("/control.xml"))));

	}

	@Test
	public void givenStreamAsObject_whenAbleToInput_thenCorrect() {
		assertThat(Input.from(new XMLUnitTest().getClass().getResourceAsStream(
				"/test.xml")), isSimilarTo(Input.from(new XMLUnitTest()
				.getClass().getResourceAsStream("/control.xml"))));

	}

	@Test
	public void givenStringSourceAsObject_whenAbleToInput_thenCorrect() {
		assertThat(
				Input.from("<struct><int>3</int><boolean>false</boolean></struct>"),
				isSimilarTo(Input
						.from("<struct><int>3</int><boolean>false</boolean></struct>")));

	}

	@Test
	public void givenFileSource_whenAbleToInput_thenCorrect() {
		ClassLoader classLoader = getClass().getClassLoader();
		String testPath = classLoader.getResource("test.xml").getPath();
		String controlPath = classLoader.getResource("control.xml").getPath();
		assertThat(Input.fromFile(testPath),
				isSimilarTo(Input.fromFile(controlPath)));

	}

	@Test
	public void givenStringSource_whenAbleToInput_thenCorrect() {
		String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		String testXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		assertThat(Input.fromString(testXml),
				isSimilarTo(Input.fromString(controlXml)));

	}

	@Test
	public void givenSource_whenAbleToInput_thenCorrect() {
		String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		String testXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		assertThat(Input.fromString(testXml),
				isSimilarTo(Input.fromString(controlXml)));

	}

	@Test
	public void given2XMLS_whenIdentical_thenCorrect() {
		String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		String testXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		assertThat(testXml, isIdenticalTo(controlXml));

	}

	@Test
	public void given2XMLSWithSimilarNodesButDifferentSequence_whenNotIdentical_thenCorrect() {
		String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		String testXml = "<struct><boolean>false</boolean><int>3</int></struct>";
		assertThat(testXml, not(isIdenticalTo(controlXml)));

	}

	@Test
	public void given2XMLS_whenGeneratesDifferences_thenCorrect()
			throws Exception {
		String controlXml = "<struct><int>3</int><boolean>false</boolean></struct>";
		String testXml = "<struct><boolean>false</boolean><int>3</int></struct>";
		Diff myDiff = DiffBuilder.compare(controlXml).withTest(testXml).build();
		Iterator<Difference> iter = myDiff.getDifferences().iterator();
		int size = 0;
		while (iter.hasNext()) {
			iter.next().toString();
			size++;
		}
		assertThat(size, greaterThan(1));
	}

	@Test
	public void given2XMLS_whenGeneratesOneDifference_thenCorrect()
			throws Exception {
		String myControlXML = "<struct><int>3</int><boolean>false</boolean></struct>";
		String myTestXML = "<struct><boolean>false</boolean><int>3</int></struct>";
		Diff myDiff = DiffBuilder
				.compare(myControlXML)
				.withTest(myTestXML)
				.withComparisonController(
						ComparisonControllers.StopWhenDifferent).build();
		Iterator<Difference> iter = myDiff.getDifferences().iterator();
		int size = 0;
		while (iter.hasNext()) {
			iter.next().toString();
			size++;
		}
		assertThat(size, equalTo(1));
	}

}
