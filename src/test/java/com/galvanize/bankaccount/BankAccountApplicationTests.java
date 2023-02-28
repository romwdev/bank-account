package com.galvanize.bankaccount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class BankAccountApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	BankAccountRepository accountRepository;

	List<BankAccount> testAccounts;
	@BeforeEach
	void setUp() {
		this.testAccounts = new ArrayList<>();
		BankAccount account;

		for (int i = 0; i < 50; i++) {
			account = new BankAccount(ThreadLocalRandom.current().nextLong(999999),
					"Someone", "Some Company", ThreadLocalRandom.current().nextInt(1994, 2024));
			this.testAccounts.add(account);
		}
		accountRepository.saveAll(this.testAccounts);
	}

	@AfterEach
	void tearDown() {
		accountRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getAccountsReturnsList() {
		ResponseEntity<AccountList> response = restTemplate.getForEntity("/api/bank-account", AccountList.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().isEmpty()).isFalse();
	}

	@Test
	void getAccountsParamsReturnsList() {
		int seq = new Random().nextInt(50);
		String company = testAccounts.get(seq).getCompany();
		int year = testAccounts.get(seq).getYear();

		ResponseEntity<AccountList> response = restTemplate.getForEntity(
				String.format("/api/bank-account?company=%s&year=%s", company, year), AccountList.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().isEmpty()).isFalse();
		assertThat(response.getBody().getAccounts().size()).isGreaterThanOrEqualTo(1);
	}

	@Test
	void addAccountReturnsAccount() {
		BankAccount account = new BankAccount(12345L, "Robert", "Discover", 2011);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);

		ResponseEntity<BankAccount> response = restTemplate.postForEntity("/api/bank-account",
				request, BankAccount.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getAccountNumber()).isEqualTo(account.getAccountNumber());
	}

	@Test
	void getAccountByAccountNumberReturnsAccount() {
		BankAccount account = testAccounts.get(new Random().nextInt(50));

		ResponseEntity<BankAccount> response = restTemplate.getForEntity(
				String.format("/api/bank-account/%s", account.getAccountNumber()), BankAccount.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getAccountNumber()).isEqualTo(account.getAccountNumber());
	}

}
