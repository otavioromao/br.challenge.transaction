//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.challenge.transaction.dto.TransactionEntityDto;
import br.challenge.transaction.service.TransactionService;

/**
 * This class is responsible to test {@link TransactionController}.
 */
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest
{
    private static final String DESCRIPTION_VALUE = "Description for the transaction";

    private static final Integer MONTH_VALUE = 2;
    private static final Integer TRANSACTION_VALUE_VALUE = 1;
    private static final Integer USER_ID_VALUE = 1000;
    private static final Integer YEAR_VALUE = 2020;

    private static final Long DATE_VALUE = Calendar.getInstance().getTimeInMillis();

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController = new TransactionController();

    @Test
    void getTransactionModel_withTwoTransactions_shoudReturnTwoTransactions()
    {
	TransactionEntityDto transactionOneExpected = createTransactionEntityDto(DESCRIPTION_VALUE,
		String.valueOf(DATE_VALUE), String.valueOf(TRANSACTION_VALUE_VALUE), Boolean.FALSE.toString());
	TransactionEntityDto transactionTwoExpected = createTransactionEntityDto(DESCRIPTION_VALUE,
		String.valueOf(DATE_VALUE), String.valueOf(TRANSACTION_VALUE_VALUE), Boolean.TRUE.toString());

	List<TransactionEntityDto> transactionsExpected = new ArrayList<>();
	transactionsExpected.add(transactionOneExpected);
	transactionsExpected.add(transactionTwoExpected);

	when(transactionService.getTransactions(USER_ID_VALUE, MONTH_VALUE, YEAR_VALUE))
		.thenReturn(transactionsExpected);

	ResponseEntity<Iterable<TransactionEntityDto>> response = transactionController.getTransactions(USER_ID_VALUE,
		YEAR_VALUE, MONTH_VALUE);

	assertEquals(HttpStatus.OK, response.getStatusCode());
	assertNotNull(response.getBody());
	assertIterableEquals(response.getBody(), transactionsExpected);
    }

    private TransactionEntityDto createTransactionEntityDto(String description, String date, String value,
	    String duplicated)
    {
	return TransactionEntityDto.builder().description(description).date(date).value(value).duplicated(duplicated)
		.build();
    }
}
