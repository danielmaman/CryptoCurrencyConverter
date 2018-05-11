# CryptoCurrencyConverter
# Description
Android application that gets real-time data(exchange rates) from api, converts crypto amounts to non-crypto currencies

# Entity
Attributes:
symbol: Crypto currency symbol
rate_float: Rate in float format
code: the symbol of the currency
rate: Rate in string format
description: Crypto currency description

```
@RealmClass
open class CurrencyUnitRaw(open var symbol: String = "",
                           open var rate_float: Float = 0.0000F,
                           open var code: String = "",
                           open var rate: String = "",
                           open var description: String = ""): RealmModel

```
# REST API
https://api.coindesk.com/v1/bpi/currentprice.json
Returns json with real-time exchange rates.

GET/pi - get list of coin prices.

PUT/pi{id} - updates the currency price of coin.

POST/pi - add new coin and price.

DELETE/pi{id} - deletes currency by id.


# UI
UI Final
![screenshot_20180508-171838_currencyconverter](https://user-images.githubusercontent.com/33633126/39762730-1d6aca00-52e4-11e8-861f-a76bef5ee029.jpg)
