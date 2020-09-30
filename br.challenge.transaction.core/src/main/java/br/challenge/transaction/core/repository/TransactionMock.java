//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.core.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.challenge.transaction.core.TransactionReader;
import br.challenge.transaction.core.model.TransactionEntity;

/**
 * This class is responsible to mock {@link TransactionEntity Transaction}.
 */
public class TransactionMock implements TransactionReader
{
    private static final String CONSONANT_PATTERN = "[bcdfghjklmnpqrstvwxz]+";
    private static final String ERROR_MESSAGE = "The range for user Id, month and year are invalid";
    private static final String KEY_FORMAT_STRING = "%s-%s";
    private static final String SPACE_CHAR = " ";
    private static final String VOWEL_PATTERN = "[aeiou]+";

    private static final int DECIMAL_CHAR_A = 97;
    private static final int DECIMAL_CHAR_Z = 122;
    private static final int MAX_RANDOM_NUMBER = 9999999;
    private static final int MAX_USER_ID = 100000000;
    private static final int MIN_RANDOM_NUMBER = -9999999;
    private static final int MIN_USER_ID = 1000;
    private static final int NUMBER_FIFTY_NINE = 59;
    private static final int NUMBER_FIVE = 5;
    private static final int NUMBER_OF_MONTHS = 12;
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_SIXTY = 60;
    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_THIRTY_ONE = 31;
    private static final int NUMBER_TWENTY_THREE = 23;
    private static final int NUMBER_TWO = 2;
    private static final int NUMBER_ZERO = 0;
    private static final int YEAR_LIMIT = 1970;

    private Map<Integer, Map<String, List<TransactionEntity>>> transactionUserMap = new HashMap<>();

    @Override
    public List<TransactionEntity> getTransactions(int userId, int month, int year)
    {
	validateArguments(userId, month, year);

	Map<String, List<TransactionEntity>> transactionsMap = transactionUserMap.get(userId);

	if (transactionsMap == null)
	{
	    transactionsMap = new HashMap<>();

	    transactionUserMap.put(userId, transactionsMap);
	}

	String keyYearMonth = String.format(KEY_FORMAT_STRING, String.valueOf(year), String.valueOf(month));

	if (!transactionsMap.containsKey(keyYearMonth))
	{
	    generateTransactionsByYear(userId, year, transactionsMap);
	}

	return transactionsMap.get(keyYearMonth);
    }

    private void validateArguments(int userId, int month, int year)
    {
	if (userId < MIN_USER_ID || userId > MAX_USER_ID || month <= NUMBER_ZERO || month > NUMBER_OF_MONTHS
		|| year <= YEAR_LIMIT || year >= MAX_RANDOM_NUMBER)
	{
	    throw new IllegalArgumentException(ERROR_MESSAGE);
	}
    }

    private void generateTransactionsByYear(int userId, int year, Map<String, List<TransactionEntity>> transactionsMap)
    {
	for (int currentMonth = 1; currentMonth <= NUMBER_OF_MONTHS; currentMonth++)
	{
	    List<TransactionEntity> createdTransactions = generateTransactionsByMonth(userId, currentMonth, year);

	    String key = String.format(KEY_FORMAT_STRING, String.valueOf(year), String.valueOf(currentMonth));

	    transactionsMap.put(key, createdTransactions);
	}
    }

    private List<TransactionEntity> generateTransactionsByMonth(int userId, int month, int year)
    {
	List<TransactionEntity> createdTransactions = new ArrayList<>();

	int numberOfTransactions = generateRandomNumber(NUMBER_ONE, NUMBER_THIRTY_ONE);

	boolean hasDuplicatedTransaction = false;

	for (int i = 0; i < numberOfTransactions; i++)
	{
	    TransactionEntity transactionEntity = createTransaction(userId, month, year);

	    createdTransactions.add(transactionEntity);

	    if (!hasDuplicatedTransaction)
	    {
		createdTransactions.add(copyTransaction(transactionEntity));

		hasDuplicatedTransaction = true;
	    }
	}

	return createdTransactions;
    }

    private TransactionEntity createTransaction(int userId, int month, int year)
    {
	return TransactionEntity.builder().userId(userId).date(generateTransactionDate(month, year))
		.description(generateDescription()).value(generateValue()).build();
    }

    private TransactionEntity copyTransaction(TransactionEntity transactionEntity)
    {
	return TransactionEntity.builder().userId(transactionEntity.getUserId()).date(transactionEntity.getDate())
		.description(transactionEntity.getDescription()).value(transactionEntity.getValue()).duplicated(true)
		.build();
    }

    private String generateDescription()
    {
	StringBuilder descriptionBuilder = new StringBuilder();

	int phraseSize = generateRandomNumber(NUMBER_TWO, NUMBER_TEN);

	int wordCount = 1;

	while (descriptionBuilder.length() <= NUMBER_TEN || ++wordCount < phraseSize)
	{
	    if (descriptionBuilder.length() > 0)
	    {
		descriptionBuilder.append(SPACE_CHAR);
	    }

	    int syllableNumber = generateRandomNumber(NUMBER_TWO, NUMBER_FIVE);

	    for (int k = 0; k < syllableNumber; k++)
	    {
		descriptionBuilder.append(generateWord());
	    }
	}

	String description = descriptionBuilder.toString();

	return description.length() > NUMBER_SIXTY ? description.substring(NUMBER_ZERO, NUMBER_SIXTY) : description;
    }

    private String generateWord()
    {
	int character = generateRandomNumber(DECIMAL_CHAR_A, DECIMAL_CHAR_Z);

	while (!String.valueOf((char) character).matches(CONSONANT_PATTERN))
	{
	    character = generateRandomNumber(DECIMAL_CHAR_A, DECIMAL_CHAR_Z);
	}

	String word = String.valueOf((char) character);

	character = generateRandomNumber(DECIMAL_CHAR_A, DECIMAL_CHAR_Z);

	while (!String.valueOf((char) character).matches(VOWEL_PATTERN))
	{
	    character = generateRandomNumber(DECIMAL_CHAR_A, DECIMAL_CHAR_Z);
	}

	return word + String.valueOf((char) character);
    }

    private Integer generateValue()
    {
	return generateRandomNumber(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER);
    }

    private Long generateTransactionDate(int month, int year)
    {
	int hour = generateRandomNumber(NUMBER_ZERO, NUMBER_TWENTY_THREE);
	int minute = generateRandomNumber(NUMBER_ZERO, NUMBER_FIFTY_NINE);
	int second = generateRandomNumber(NUMBER_ZERO, NUMBER_FIFTY_NINE);

	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.HOUR_OF_DAY, hour);
	calendar.set(Calendar.MINUTE, minute);
	calendar.set(Calendar.SECOND, second);
	calendar.set(Calendar.YEAR, year);
	calendar.set(Calendar.MONTH, month - 1);

	int day = generateRandomNumber(NUMBER_ONE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

	calendar.set(Calendar.DAY_OF_MONTH, day);

	return calendar.getTimeInMillis();
    }

    private Integer generateRandomNumber(int minimalRandomNumber, int maximunRandomNumber)
    {
	return new Random().nextInt((maximunRandomNumber - minimalRandomNumber) + NUMBER_ONE) + minimalRandomNumber;
    }
}
