# Brief description of functionality
Application simulates currency transactions in user accounts. User enters sell currency amount,  chooses currency pair and initiates transaction. At that moment application sends HTTP request to the Cloud and receives answer in JSON format. If balance of currency sold is sufficient, it is reduced by the sold amount and balance of bought currency is increased.

Currency transactions are subject to a fee. The first five and every tenth transaction is free of charge, others are taxed by 0.7% fee, which is deducted from the balance of currency being sold. 

Application supports three currencies - EUR, USD, JPY. Initial balance is 1000.00 EUR, 0.00 USD, 0 JPY. Balance can’t be negative.

## Application screenshots
![Alt text](iTradeFX.png?raw=true )

## HTTP request syntax
`http://api.evp.lt/currency/commercial/exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest`

## Answer example
`{"amount": "210.54", "currency": "USD"}`
