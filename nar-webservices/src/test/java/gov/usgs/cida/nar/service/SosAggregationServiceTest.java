package gov.usgs.cida.nar.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import gov.usgs.webservices.framework.basic.MimeType;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.socket.PortFactory;

import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.regex;

public class SosAggregationServiceTest {
	private static ClientAndServer mockServer;
	private static String localGeoserverURL;
	private static String localSosURL;
	private static int serverPort;
	
	private static String siteFeaturesResponse;
	
	private static String l95AvailabilityResponse;
	private static String u95AvailabilityResponse;
	private static String massAvailabilityResponse;
	private static String yieldAvailabilityResponse;
	private static String concAvailabilityResponse;

	private static String l95Response;
	private static String u95Response;
	private static String massResponse;
	private static String yieldResponse;
	private static String concResponse;
	
	private static String expectedCsv;
	
	@BeforeClass
	public static void setUpClass() throws FileNotFoundException, IOException, URISyntaxException {
		serverPort = PortFactory.findFreePort();
		mockServer = startClientAndServer(serverPort);
		localGeoserverURL = "http://localhost:" + serverPort + "/geoserver/";
		localSosURL = "http://localhost:" + serverPort + "/sos/";
		
		siteFeaturesResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/wfsResponses/siteFeatures.xml").toURI())), Charset.defaultCharset());
		
		l95AvailabilityResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/availability.lower95.2013.xml").toURI())), Charset.defaultCharset());
		u95AvailabilityResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/availability.upper95.2013.xml").toURI())), Charset.defaultCharset());
		massAvailabilityResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/availability.annualMass.2013.xml").toURI())), Charset.defaultCharset());
		yieldAvailabilityResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/availability.annualYield.2013.xml").toURI())), Charset.defaultCharset());
		concAvailabilityResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/availability.flowWeighted.2013.xml").toURI())), Charset.defaultCharset());
		
		l95Response = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/obs.lower95.2013.xml").toURI())), Charset.defaultCharset());
		u95Response = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/obs.upper95.2013.xml").toURI())), Charset.defaultCharset());
		massResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/obs.annualMass.2013.xml").toURI())), Charset.defaultCharset());
		yieldResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/obs.annualYield.2013.xml").toURI())), Charset.defaultCharset());
		concResponse = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/sosResponses/obs.flowWeighted.2013.xml").toURI())), Charset.defaultCharset());
		
		expectedCsv = IOUtils.toString(new FileInputStream(new File(SosAggregationServiceTest.class.getResource("/expectedOutput/2013-annualLoad-ILWI-TN-SS.csv").toURI())), Charset.defaultCharset());
	}

	@AfterClass
	public static void tearDownClass() {
		mockServer.stop();
	}

	@Before
	public void setUp() {
		mockServer.reset();
		
		//WFS response
		mockServer.
				when(request().withPath("/geoserver/")).
				respond(response().
						withHeaders(new Header("Content-Type", "application/xml")).
						withBody(siteFeaturesResponse));
		
		//GetDataAvailability responses
		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<gda:GetDataAvailability.*annual_mass_upper_95/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(u95AvailabilityResponse));

		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<gda:GetDataAvailability.*annual_mass_lower_95/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(l95AvailabilityResponse));
		
		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<gda:GetDataAvailability.*annual_mass/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(massAvailabilityResponse));
		
		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<gda:GetDataAvailability.*annual_yield/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(yieldAvailabilityResponse));
		
		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<gda:GetDataAvailability.*annual_concentration_flow_weighted/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(concAvailabilityResponse));

		//GetObservation responses
		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<sos:GetObservation.*annual_concentration_flow_weighted/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(concResponse));

		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<sos:GetObservation.*annual_mass/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(massResponse));

		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<sos:GetObservation.*annual_mass_upper_95/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(u95Response));

		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<sos:GetObservation.*annual_yield/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(yieldResponse));

		mockServer.
		when(request().
				withPath("/sos/").
				withBody(regex(".*<sos:GetObservation.*annual_mass_lower_95/COMP.*"))).
		respond(response().
				withHeaders(new Header("Content-Type", "application/xml")).
				withBody(l95Response));
	}
	
	@Test
	public void loopTests() throws IOException {
		for(int i = 0; i < Integer.MAX_VALUE; i++) {
			testAnnualLoadDownload();
		}
	}


	@Test
	public void testAnnualLoadDownload() throws IOException {
		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		
		new SosAggregationService(
				DownloadType.annualLoad, 
				localSosURL,
				"http://cida.usgs.gov/def/NAR/property/",
				new SiteInformationService(localGeoserverURL).getStationFeatures(new ArrayList<String>(), new ArrayList<String>(), 
						Arrays.asList(new String[] { "IL", "WI"}))
				).streamData(resultStream, 
					MimeType.CSV,
					Arrays.asList(new String[] { "TN", "SSC"}),
					"10/01/2012",
					"09/30/2013",
					"");
		
		String resultCsv = new String(resultStream.toByteArray());
		resultStream.close();
		assertEquals("The combined SOS responses were transformed into the expected CSV", expectedCsv, resultCsv);
	}
}
