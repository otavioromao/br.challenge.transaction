//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * This class is responsible to start the application by Sprint Boot.
 */
@SpringBootApplication
@EnableEurekaClient
@EntityScan(
{ "br.challenge.transaction.core.model" })
public class TransactionApplication
{
    public static void main(String[] args)
    {
	SpringApplication.run(TransactionApplication.class, args);
    }
}
