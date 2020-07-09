[![CircleCI](https://circleci.com/gh/KarolPulawski/betting-site.svg?style=svg&circle-token=56f92698891a553abc0d6b42808f0d977ccc3930)](https://circleci.com/gh/KarolPulawski/betting-site)

Betting site imitate service for gambling

#################################### run BS
To run locally only that app: run docker-compose file from /src/main/scripts/bs/

Then betting site app is running on: http://localhost:8087 or http://172.23.0.3:8085

BS credentials: karol@gmail.com/karol

#################################### run SEA & BS

To run locally fully integrated sports event api (SEA) and betting site (BS) apps: run docker-compose file from /src/main/scripts/seabs/

Then sports event api app is running on: http://177.23.0.5:8080

Betting site app is running on: http://177.23.0.3:8085 

SEA available endpoints:
/api/events/{competitionId}}/games
/api/events/{competitionId}}/results
/api/events/results

BS credentials: karol@gmail.com/karol

#################################### overview
When user has own or test account(karol@gmail.com/karol), then he is able to gambling. Once events are ready in fixtures section, user can place bet slip.
If user has enough balance to place a bet, the post request is sent through ajax to server side. There is a validation both on Front End or Back End site.
User can place some special generated betslips, check table for competition, monitor history of balance and verify if his bets are still inplay or have been resulted. 
Service allows user to imitate depositing and withdrawing cash.