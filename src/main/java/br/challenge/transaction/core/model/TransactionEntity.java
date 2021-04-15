//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.core.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class defines the transaction information.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TransactionEntity
{
    /**
     * Specifies the Id for the user for the transaction.
     */
    @NotNull(message = "The field userId is mandatory.")
    @EqualsAndHashCode.Exclude
    private Integer userId;

    /**
     * Specifies the description for the transaction.
     */
    @NotNull(message = "The field description is mandatory.")
    private String description;

    /**
     * Specifies the date for the transaction.
     */
    @NotNull(message = "The field date is mandatory.")
    private Long date;

    /**
     * Specifies the value for the transaction.
     */
    @NotNull(message = "The field value is mandatory.")
    private Integer value;

    /**
     * Specifies if the transaction is duplicated.
     */
    @NotNull(message = "The field duplicated is mandatory.")
    @Builder.Default
    private Boolean duplicated = false;
}
