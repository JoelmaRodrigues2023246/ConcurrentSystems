# Notes on Demonstration Limits

## Task Assessment

- [x] Once the total is greater than or equal to 1 million, the client will stop and print out the total, followed by `numList`
  - **Code:**
    ```java
    // Client.java
    while (server.getTotal() < 1000000) {
        int number = random.nextInt(13);
        server.addNumber(number, clientId);
        clientCount++;
        System.out.println(clientId + " : Generated number: " + number + " | Total Count: " + clientCount);
        Thread.sleep(10);
    }
    if (server.getTotal() >= 1000000) {
        int total = server.getTotal();
    
        List<Integer> numList = server.getNumList();
        System.out.println(clientId + " - Total reached: " + total);
        System.out.println(clientId + " - Number list: " + numList);
    }
    ```

  - **Explanation:** This task ensures that once the `total` reaches 1 million, the clients stop generating numbers and print the total and the list of numbers.
  
  - **Note:** In the demonstration video, the limit was set to 10,000 instead of 1 million. This was done because generating up to 1 million would take a considerable amount of time. Here’s the calculation for the estimated time:

    ### Calculation for 1 Million Total:
    - **Assumption:** Each number addition takes approximately 10ms (as specified in the task).
    - **Average random number:** 
      \[
      (0 + 12) / 2 = 6
      \]
    - **Total iterations needed to reach 1 million:**
      \[
      1,000,000 / 6 \approx 166,667 \text{ iterations}
      \]
    - **Time for each iteration:**
      \[
      10 \text{ms}
      \]
    - **Total time:**
      \[
      166,667 \times 10 \text{ms} = 1,666,670 \text{ms}
      \]
    - **Total time in seconds:**
      \[
      1,666,670 / 1000 \approx 1667 \text{ seconds} \approx 27.78 \text{ minutes}
      \]

    ### Calculation for 10,000 Total:
    - **Total iterations needed to reach 10,000:**
      \[
      10,000 / 6 \approx 1,667 \text{ iterations}
      \]
    - **Time for each iteration:**
      \[
      10 \text{ms}
      \]
    - **Total time:**
      \[
      1,667 \times 10 \text{ms} = 16,670 \text{ms}
      \]
    - **Total time in seconds:**
      \[
      16,670 / 1000 \approx 16.67 \text{ seconds}
      \]

    ### Conclusion:
    For demonstration purposes, a limit of 10,000 was chosen to make the video concise and efficient while still illustrating the functionality. Running up to 1 million would take approximately 27.78 minutes, which is impractical for a short demonstration.
