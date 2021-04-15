//**********************************************************************
// REST API created to give support for transactions retrieving.
// **********************************************************************
package br.challenge.transaction.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.challenge.transaction.dto.TransactionEntityDto;
import br.challenge.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible to give support for REST API to retrieve
 * transactions.
 */
@Controller
@Slf4j
@RequestMapping(path = "/v1")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;

    /**
     * Returns a list of transactions that exists in the month and year specified
     * for a user.
     *
     * @param userId The user Id owner of the transactions, it cannot be null
     * @param year   The year for the transactions, it cannot be null
     * @param month  The month for the transactions, it cannot be null
     * @return The ResponseEntity with a list of transactions
     */
    @GetMapping(path =
    { "{userId}/transacoes/{year}/{month}" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<TransactionEntityDto>> getTransactions(@PathVariable(required = true) int userId,
	    @PathVariable(required = true) int year, @PathVariable(required = true) int month)
    {
	log.debug("Get transactions: userId {}, month {} and year {}", userId, month, year);

	Iterable<TransactionEntityDto> transactions = transactionService.getTransactions(userId, month, year);

	return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
