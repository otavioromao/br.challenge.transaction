//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.service;

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

import br.challenge.transaction.core.TransactionReaderService;
import br.challenge.transaction.core.model.TransactionEntity;
import br.challenge.transaction.dto.TransactionEntityDto;

/**
 * This class is responsible to test {@link TransactionService}.
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest
{
    private static final String DESCRIPTION_VALUE = "Description for transaction";

    private static final Integer MONTH_VALUE = 2;
    private static final Integer TRANSACTION_VALUE_VALUE = 1;
    private static final Integer USER_ID_VALUE = 1000;
    private static final Integer YEAR_VALUE = 2020;

    private static final Long DATE_VALUE = Calendar.getInstance().getTimeInMillis();

    @Mock
    private TransactionReaderService transactionReader;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getTransactions_withTwoTransactions_shoudReturnTwoTransactions()
    {
	TransactionEntityDto transactionOneExpected = createTransactionEntityDto(DESCRIPTION_VALUE, DATE_VALUE,
		TRANSACTION_VALUE_VALUE, Boolean.FALSE);
	TransactionEntityDto transactionTwoExpected = createTransactionEntityDto(DESCRIPTION_VALUE, DATE_VALUE,
		TRANSACTION_VALUE_VALUE, Boolean.TRUE);

	List<TransactionEntityDto> transactionsExpected = new ArrayList<>();
	transactionsExpected.add(transactionOneExpected);
	transactionsExpected.add(transactionTwoExpected);

	TransactionEntity transactionOne = createTransactionEntity(DESCRIPTION_VALUE, DATE_VALUE,
		TRANSACTION_VALUE_VALUE, Boolean.FALSE);
	TransactionEntity transactionTwo = createTransactionEntity(DESCRIPTION_VALUE, DATE_VALUE,
		TRANSACTION_VALUE_VALUE, Boolean.TRUE);

	List<TransactionEntity> transactions = new ArrayList<>();
	transactions.add(transactionOne);
	transactions.add(transactionTwo);

	when(transactionReader.getTransactions(USER_ID_VALUE, MONTH_VALUE, YEAR_VALUE)).thenReturn(transactions);

	Iterable<TransactionEntityDto> response = transactionService.getTransactions(USER_ID_VALUE, MONTH_VALUE,
		YEAR_VALUE);

	assertNotNull(response);
	assertIterableEquals(response, transactionsExpected);
    }

    private TransactionEntity createTransactionEntity(String description, Long date, Integer value, Boolean duplicated)
    {
	return TransactionEntity.builder().description(description).date(date).value(value).duplicated(duplicated)
		.build();
    }

    private TransactionEntityDto createTransactionEntityDto(String description, Long date, Integer value,
	    Boolean duplicated)
    {
	return TransactionEntityDto.builder().description(description).date(date).value(value).duplicated(duplicated)
		.build();
    }
}
