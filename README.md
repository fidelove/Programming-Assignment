ViaBill Programming Assignment
=======

This is the implementation of the Application Assigment proposed by ViaBill.

These frameworks have been used for developing the backend service:

  1. [Spark](http://sparkjava.com/) : as Java web framework.
  2. [Hazelcast](http://hazelcast.org/) : as local storage support.
  2. [Gson](https://github.com/google/gson) : as JSON mapper.
  3. [Hibernate Validator](http://hibernate.org/validator/) : as bean validator.
  4. [Apache Commons Validator](https://commons.apache.org/proper/commons-validator/) : as email addresses validator.
  5. [Lib Phone Number](https://github.com/googlei18n/libphonenumber) : as phone format validator.
  6. [junit](http://junit.org/) and [easymock](http://easymock.org/) : as unitary tests tools.

Use
=======

Five resources are proposed by this web application:

  1. CreateCompany
-----------
A new company will be stored in the application database. The company entity has these properties:
  1. **name** : A string containing the name of the new company. This parameter is **mandatory**.
  2. **address** : a string containing the address of the new company. This parameter is **mandatory**.
  3. **city** : a string containing the city of the new company. This parameter is **mandatory**.
  4. **country** : a string containing the country of the new company. This parameter is **mandatory**.
  5. **email** : a string containing the email address of the new company. This email address must be a valid email address, and if an invalid email address is sent the company will not be created. This parameter is **optional**.
  6. **phoneNumber** : a string containing the phone number of the new company. This phone number must be a valid phone number, which can be sent in the international phone format (+45-XX-XX-XX-XX), or in the local Danish phone format (XX-XX-XX-XX). if an invalid phone number is sent the company will not be created. This parameter is **optional**.
  7. **beneficiaOwner** : A list containing all the beneficial owners. This parameter is **mandatory**.

In order to create a new company a POST HTTP request must be executed.
The body of this request must contain the company fields in JSON format. 
The resource can be invoked via this curl command:

``` sh
curl -H "Content-Type: application/json" -X POST -d '{"name":"name", "address":"address", "city":"city", "country":"country", "email" : "mail@mail.com", "phoneNumber" : "20-21-22-23", "beneficiaOwner":["owner1", "owner2"]}' http://localhost:9090/createCompany
```

The service will return the idCompany, which can be considered the unique identifier for this company, and will be a numeric value.

``` json
{ "idCompany" : "1"}
```

 2. GetCompanies
-----------
Returns a list with all the companies that have been previously initialised.

In order to get all the companies a GET HTTP request must be executed.
The resource can be invoked via this curl command:

``` sh
curl http://localhost:9090/getCompanies
```

The result of invoking this resource is:

``` json
[{
  "idCompany" : 0,
  "name" : "Awesome Company",
  "address" : "Awesome Street",
  "city" : "Awesome City",
  "country" : "Awesome country",
  "beneficiaOwner" : 
    ["Awesome Owner Chief" , "Awesome Owner Developer"]
},
{
  "idCompany" : 1,
  "name" : "Awful Company",
  "address" : "Aweful Street",
  "city" : "Aweful City",
  "country" : "Aweful Country",
  "beneficiaOwner" : 
    ["Aweful Owner Chief" , "Aweful Owner Developer"]
}]
```

  3. GetCompany
-----------
Returns the detailed information of a single company.
In order to do it a GET HTTP request must be executed.
At the end of the URL path the *idCompany* must be added, in order to be able to identify the requested company.
As mentioned before, the *idCompany* is a numeric value.
The resource can be invoked via this curl command:

``` sh
curl  http://localhost:9090/getCompany/0
```

The service will return the idCompany, which can be considered the unique identifier for this company.

``` json
{
  "idCompany" : 0,
  "name" : "Awesome Company",
  "address" : "Awesome Street",
  "city" : "Awesome City",
  "country" : "Awesome country",
  "beneficiaOwner" : 
    ["Awesome Owner Chief" , "Awesome Owner Developer"]
}
```

  4. UpdateCompany
-----------
This resource updates the details of a single company.
In order to do it a PUT HTTP request must be executed.
At the end of the URL path the *idCompany* must be added, in order to be able to identify the company to be updated.
The body of this request must contain the company fields to be updated in JSON format.
The beneficial owners are not allowed to be updated via this resource.
There is another resource *addOwner* which can process this type of request.
An optional field can be erased if sending an empty string as value.
The resource can be invoked via this curl command:

``` sh
curl -H "Content-Type: application/json" -X PUT -d '{"name":"not as awesome as promised company", "address":"moved to a not as awesome street"}' http://localhost:9090/updateCompany/0
```

The service will return the detailed company information with the requested modifications:

``` json
{
  "idCompany" : 0,
  "name" : "not as awesome as promised company",
  "address" : "moved to a not as awesome street",
  "city" : "Awesome City",
  "country" : "Awesome country",
  "beneficiaOwner" : 
    ["Awesome Owner Chief" , "Awesome Owner Developer"]
}
```

  5. AddOwner
-----------
Allows the creation of a new beneficial owner in the beneficial owners list.
In order to do it a PUT HTTP request must be executed.
At the end of the URL path the *idCompany* must be added, in order to be able to identify the company to be updated.
The body of this request must contain the new beneficial owner into a JSON list.
The resource can be invoked via this curl command:

``` sh
curl -H "Content-Type: application/json" -X PUT -d '["new fullstack developer"]' http://localhost:9090/addOwner/0
```

The service will return the detailed company information with the new beneficial owners:

``` json
{
  "idCompany" : 0,
  "name" : "not as awesome as promised company",
  "address" : "moved to a not as awesome street",
  "city" : "Awesome City",
  "country" : "Awesome country",
  "beneficiaOwner" : 
    ["Awesome Owner Chief" , "Awesome Owner Developer", "new fullstack developer"]
}
```

License
=======

The MIT License (MIT)
Copyright (c) 2016 Fidel Besada Juárez

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
