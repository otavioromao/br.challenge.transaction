//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.core;

import java.util.List;

import br.challenge.transaction.core.model.TransactionEntity;

/**
 * This interface is responsible to specify the API service.
 */
public interface TransactionReader
{
    List<TransactionEntity> getTransactions(int userId, int month, int year);
}
