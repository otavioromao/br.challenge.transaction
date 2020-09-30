//**********************************************************************
// REST API created to give support for transactions retrieving.
// *********************************************************************
package br.challenge.transaction.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * This class is responsible to start the application by Sprint Boot.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication
{
    public static void main(String[] args)
    {
	SpringApplication.run(DiscoveryApplication.class, args);
    }
}
