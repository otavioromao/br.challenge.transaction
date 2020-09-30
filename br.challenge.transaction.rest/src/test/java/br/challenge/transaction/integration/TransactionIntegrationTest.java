//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import br.challenge.transaction.ctrl.TransactionController;
import br.challenge.transaction.dto.TransactionEntityDto;
import br.challenge.transaction.service.TransactionService;

/**
 * This class is responsible to test {@link TransactionController}.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties =
{ "eureka.client.enabled:false" })
class TransactionIntegrationTest
{
    private static final String TRANSACTION_ENTRY_POINT_FOR_GET = "http://localhost:%s/v1/%s/transacoes/%s/%s";
    private static final String EXPECTED_ERROR_MESSAGE = "{\"errorCode\":\"BAD_REQUEST\",\"message\":\"The range for user Id, month and year are invalid\"}";

    private static final Integer INVALID_MONTH_VALUE = -1;
    private static final Integer MAXIMUM_VALUE = 9999999;
    private static final Integer MINIMAL_VALUE = -9999999;
    private static final Integer MONTH_VALUE = 2;
    private static final Integer USER_ID_VALUE = 1000;
    private static final Integer YEAR_VALUE = 2020;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController = new TransactionController();

    @LocalServerPort
    private int localServerPort;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    void testEndPoints_withValidInput_shouldReturnTransactions()
	    throws ClientProtocolException, IOException, JSONException
    {
	String uri = String.format(TRANSACTION_ENTRY_POINT_FOR_GET, localServerPort, USER_ID_VALUE, YEAR_VALUE,
		MONTH_VALUE);

	Object[] response = restTemplate.getForObject(uri, TransactionEntityDto[].class);

	assertNotNull(response);
	assertThat(response).isNotEmpty();

	for (Object transaction : response)
	{
	    TransactionEntityDto transactionEntityDto = (TransactionEntityDto) transaction;

	    assertNotNull(transactionEntityDto.getDescription());

	    assertNotNull(transactionEntityDto.getDate());

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(Long.valueOf(transactionEntityDto.getDate()));

	    assertThat(calendar.get(Calendar.YEAR)).isEqualTo(YEAR_VALUE);

	    assertNotNull(transactionEntityDto.getValue());
	    assertThat(Integer.valueOf(transactionEntityDto.getValue())).isBetween(MINIMAL_VALUE, MAXIMUM_VALUE);

	    assertNotNull(transactionEntityDto.getDuplicated());
	}
    }

    @Test
    void testEndPoints_withInvalidMonth_shouldReturnBadRequest()
	    throws ClientProtocolException, IOException, JSONException
    {
	String uri = String.format(TRANSACTION_ENTRY_POINT_FOR_GET, localServerPort, USER_ID_VALUE, YEAR_VALUE,
		INVALID_MONTH_VALUE);

	HttpClient client = HttpClients.createDefault();

	HttpGet getRequest = new HttpGet(uri);

	HttpResponse response = client.execute(getRequest);

	assertNotNull(response);
	assertThat(EntityUtils.toString(response.getEntity())).isEqualTo(EXPECTED_ERROR_MESSAGE);
	assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.getStatusLine().getStatusCode());
    }

    @Test
    void testEndPoints_withEmptyUserId_shouldReturnNotFound() throws ClientProtocolException, IOException, JSONException
    {
	String uri = String.format(TRANSACTION_ENTRY_POINT_FOR_GET, localServerPort, "", YEAR_VALUE,
		INVALID_MONTH_VALUE);

	HttpClient client = HttpClients.createDefault();

	HttpGet getRequest = new HttpGet(uri);

	HttpResponse response = client.execute(getRequest);

	assertNotNull(response);
	assertThat(HttpStatus.NOT_FOUND.value()).isEqualTo(response.getStatusLine().getStatusCode());
    }

    @Test
    void testEndPoints_withNullInUserId_shouldReturnBadRequest()
	    throws ClientProtocolException, IOException, JSONException
    {
	String uri = String.format(TRANSACTION_ENTRY_POINT_FOR_GET, localServerPort, null, YEAR_VALUE,
		INVALID_MONTH_VALUE);

	HttpClient client = HttpClients.createDefault();

	HttpGet getRequest = new HttpGet(uri);

	HttpResponse response = client.execute(getRequest);

	assertNotNull(response);
	assertThat(HttpStatus.BAD_REQUEST.value()).isEqualTo(response.getStatusLine().getStatusCode());
    }
}
