//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.core;

import java.util.List;

import org.springframework.stereotype.Service;

import br.challenge.transaction.core.model.TransactionEntity;
import br.challenge.transaction.core.repository.TransactionMock;

/**
 * This class is responsible to expose the API service.
 */
@Service
public class TransactionReaderService implements TransactionReader
{
    @Override
    public List<TransactionEntity> getTransactions(int userId, int month, int year)
    {
	TransactionReader transactionReader = new TransactionMock();

	return transactionReader.getTransactions(userId, month, year);
    }
}
