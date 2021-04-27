Overview

Lottery Service keeps all the business logic required on the funnel at the backend. It orchestrates the response with some business logic to produce the output to be shown on funnel pages. It supports many micro APIs which are either stand-alone or many small APIs merged into one.

Framework Architecture :
............................................................................
Controller ---> Service ----> Validators -----> Helpers and Utils

Controller – This is the first place where the request from clients lands. One controller will have many related API endpoints required for that functionality.

Response Builders - Response Builders are used for building the final response whether it is a success response or error response.

Services/Workflow Executors - Workflow Executors define the workflow steps that are to be performed to complete a request. For ex- most of the workflow executors work in the order of  Validators > Helpers in sequence or parallel > Response Builders (success or error).

Helpers - There are some common methods or functionalities which are called in multiple services, these common codes are taken out into helpers for reuse.

.........................................................................

API Controllers:

Below is the list of all APIs that the app currently support :

1. /lottery/purchase/{nameOfBuyer}

This API allows a person to buy a lottery ticket . Everytime a person hits this API , he is returned with complete information about the lottery ticket . A person can buy as many lottery tickets as he wants . Though , only 1 ticket per game can be used to play , I have put no restriction on buying plenty of tickets .

2. /lottery/registerForGame/{nameOfBuyer}

This API allows a person to register his ticket for a game. If the same person tries to register for a game twice , an exception will be thrown stating that only 1 registration per person is allowed . We have assumed here that no two people can have same name .
If a person tries to register for a game without actually buying the ticket , an exception will be raised here and his name will not be present on any of the ticket's owner . Therefore , exception thrown as PlayingWithoutPurchaseException .

3. /lottery/reset

This API is used ti reset the entire game . All the tickets are set to not sold having no owners . Complete information regarding the past winners are cleared and the game is set to reset .

4. /lottery/draw

This API is used to declare the winner of the game . When this API is hit , we find the winner from the pool of tickets which have a status as sold and also which have been registered to play as well . The ticket selection is random using a java library . The complete information of the winner is returned in the response . The tickets are rest after the game .

5. /lottery/winnersInPastWeek

This API returns the winners that have won the lottery in the past 7 days . This returns the winner information along the prize earned . When a new winner wins , all the winners are moved 1 place further and 7th day winner looses its place in the map and the new winner gets the 1st position .

6. /lottery/getNextDrawDetails

This API returns a String telling the timings and the prizes of the next lottery . Since the time is fixed , I have hard coded it . The prizes are dependant on the day of the week for which functionality has been added .


Have tried to make the code
1. Extensible
2. Readable
3. Testable
4. Managable

.............................................

Scope of Improvemenets :

1. All the constant values can be moved to a constant file .
2. The configurable properties should be moved to a property management system so that we do not have to send a new release just to change some config properties .
3. As the ticket are presently stored in application memeory only , it is not scalable . We can use a designated database for the same to incorporate scaling .
4. We can use a load balancer which internally uses consistent hashing to distribute load among the application servers incase we wish to scale in the future .
5. Junits should be written to test the individual features .

................................................
