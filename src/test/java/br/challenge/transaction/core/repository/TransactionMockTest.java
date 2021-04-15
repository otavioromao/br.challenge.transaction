//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.core.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.challenge.transaction.core.model.TransactionEntity;

/**
 * This class is responsible to test {@link TransactionMock}.
 */
@ExtendWith(MockitoExtension.class)
class TransactionMockTest
{
    private static final String ERROR_MESSAGE = "The range for user Id, month and year are invalid";

    private static final Integer MAXIMUM_VALUE = 9999999;
    private static final Integer MINIMAL_VALUE = -9999999;
    private static final Integer MONTH_VALUE = 2;
    private static final Integer NUMBER_THIRTEEN = 13;
    private static final Integer NUMBER_THIRTY_ONE = 31;
    private static final Integer NUMBER_ZERO = 0;
    private static final Integer USER_ID_INVALID = 100000001;
    private static final Integer USER_ID_VALUE = 1000;
    private static final Integer YEAR_VALUE = 2020;

    private TransactionMock transactionReader = new TransactionMock();

    @Test
    void getTransactions_withValidInputs_shouldReturnTransactions()
    {
	List<TransactionEntity> transactions = transactionReader.getTransactions(USER_ID_VALUE, MONTH_VALUE,
		YEAR_VALUE);

	assertNotNull(transactions);
	assertThat(transactions).isNotEmpty().hasSizeLessThanOrEqualTo(NUMBER_THIRTY_ONE);

	for (TransactionEntity transaction : transactions)
	{
	    assertNotNull(transaction.getDescription());

	    assertNotNull(transaction.getDate());

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(Long.valueOf(transaction.getDate()));

	    assertThat(calendar.get(Calendar.YEAR)).isEqualTo(YEAR_VALUE);

	    assertNotNull(transaction.getValue());
	    assertThat(Integer.valueOf(transaction.getValue())).isBetween(MINIMAL_VALUE, MAXIMUM_VALUE);

	    assertNotNull(transaction.getDuplicated());
	}
    }

    @Test
    void getTransactions_withAlreadyExistingTransactions_shouldNotRecreateTransactions()
    {
	List<TransactionEntity> existingTransactions = transactionReader.getTransactions(USER_ID_VALUE, MONTH_VALUE,
		YEAR_VALUE);

	List<TransactionEntity> transactionsReponse = transactionReader.getTransactions(USER_ID_VALUE, MONTH_VALUE,
		YEAR_VALUE);

	assertNotNull(existingTransactions);
	assertNotNull(transactionsReponse);

	assertThat(existingTransactions).isEqualTo(transactionsReponse);
    }

    @Test
    void getTransactions_withUserIdLessThenLimit_shouldThrowException()
    {
	assertThatThrownBy(() ->
	{
	    transactionReader.getTransactions(NUMBER_ZERO, MONTH_VALUE, YEAR_VALUE);
	}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    @Test
    void getTransactions_withUserIdGreaterThenLimit_shouldThrowException()
    {
	assertThatThrownBy(() ->
	{
	    transactionReader.getTransactions(USER_ID_INVALID, MONTH_VALUE, YEAR_VALUE);
	}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    @Test
    void getTransactions_withMonthLessThenLimit_shouldThrowException()
    {
	assertThatThrownBy(() ->
	{
	    transactionReader.getTransactions(USER_ID_VALUE, NUMBER_ZERO, YEAR_VALUE);
	}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    @Test
    void getTransactions_withMonthGreaterThenLimit_shouldThrowException()
    {
	assertThatThrownBy(() ->
	{
	    transactionReader.getTransactions(USER_ID_VALUE, NUMBER_THIRTEEN, YEAR_VALUE);
	}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    @Test
    void getTransactions_withYearLessThenLimit_shouldThrowException()
    {
	assertThatThrownBy(() ->
	{
	    transactionReader.getTransactions(USER_ID_VALUE, MONTH_VALUE, NUMBER_ZERO);
	}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }

    @Test
    void getTransactions_withYearGreaterThenLimit_shouldThrowException()
    {
	assertThatThrownBy(() ->
	{
	    transactionReader.getTransactions(USER_ID_VALUE, MONTH_VALUE, MAXIMUM_VALUE);
	}).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ERROR_MESSAGE);
    }
}
