//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class defines the transaction contract.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransactionEntityDto
{
    /**
     * Specifies the description for the transaction.
     */
    @NotNull(message = "The field description is mandatory.")
    private String description;

    /**
     * Specifies the date for the transaction.
     */
    @NotNull(message = "The field date is mandatory.")
    private String date;

    /**
     * Specifies the value for the transaction.
     */
    @NotNull(message = "The field value is mandatory.")
    private String value;

    /**
     * Specifies if the transaction is duplicated.
     */
    private String duplicated;
}
