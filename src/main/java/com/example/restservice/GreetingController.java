package com.example.restservice;

import java.io.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/sum")
	public Sum getSum(@RequestParam(value = "number", defaultValue = "1000") Long number) {
		long startTime = System.currentTimeMillis();

		Sum sum = new Sum();
		sum.setSum(0L);
		for (Long i = 1L; i <= number; i++) {
			sum.setSum(sum.getSum() + i);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");

		return sum;
	}

	@PostMapping("/file")
	public void writeFile(@RequestParam(value = "lines", defaultValue = "1000") Long lines) throws IOException {
		long startTime = System.currentTimeMillis();
		FileWriter myWriter = new FileWriter("src\\main\\resources\\data.txt", false);
		for (long i = 0l; i < lines; i++) {
			myWriter.write("This is a new line\n");
		}

		myWriter.close();

		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds for writing the file");
	}

	@GetMapping(path = "/file")
	public void readFile() throws FileNotFoundException, IOException {
		long startTime = System.currentTimeMillis();

		File file = new File("src\\main\\resources\\data.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));

		// Declaring a string variable
		String st;
		// Condition holds true till
		// there is character in a string
		while ((st = br.readLine()) != null) {

			// Print the string
			System.out.println(st);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds for reading the file");

	}

	@GetMapping(path="/streams")
	public void getRandomNumbers(@RequestParam(value = "number", defaultValue = "1000") Integer number){
		long startTime = System.currentTimeMillis();

		Stream<Integer> randomNumbers = Stream.generate(() -> (new Random()).nextInt(number));
		randomNumbers.limit(number).forEach(System.out::println);

		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " for printing the values through streams");
	}
}
