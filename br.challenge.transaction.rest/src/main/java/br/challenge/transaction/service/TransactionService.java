//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.challenge.transaction.core.TransactionReader;
import br.challenge.transaction.core.model.TransactionEntity;
import br.challenge.transaction.dto.TransactionEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible to give support for services to retrieve
 * {@link TransactionEntityDto Transactions}.
 */
@Service
@Slf4j
public class TransactionService
{
    @Autowired
    private TransactionReader transactionReader;

    private ModelMapper modelMapper = new ModelMapper();

    public List<TransactionEntityDto> getTransactions(int userId, int month, int year)
    {
	log.debug("Get transactions", "");

	return mapTransactionEntityList(transactionReader.getTransactions(userId, month, year));
    }

    private List<TransactionEntityDto> mapTransactionEntityList(Iterable<TransactionEntity> transactionEntityList)
    {
	List<TransactionEntityDto> transactionEntityDtoList = new ArrayList<>();

	transactionEntityList.forEach(transactionEntity ->
	{
	    TransactionEntityDto transactionEntityDto = modelMapper.map(transactionEntity, TransactionEntityDto.class);

	    transactionEntityDtoList.add(transactionEntityDto);
	});

	return transactionEntityDtoList;
    }
}
