# QandAapp_microservice

1)A basic QandA application created using concept of microservice. Initially I created a monolithic application and then converted it to a microservice based application.

2)A user will make a request for example , a request to generate a quiz. The request will go to the api gateway which with of eureka discovery server will search for quiz microservice registered as a eureka client .

3)The Quiz service will request question microservice to give quiz with particular category and title.

4)We can have multiple instance of question service and the FeignClient that we have used at quiz microservice will handle the load balancing between different instances.

5)Here Api Gateway act as a layer between making a request to quiz service and then sending request to different question service instance. As different microservice are running at there own port and host , to avoid specifying for each instance , we use this api gateway that handles these request.
