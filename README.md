# jalcohol-api

## Before running jalcohol-api, you need to have the following installed.
1. Java 1.8
2. Maven
3. Git

## How to run it?
1. Execute git clone https://github.com/sugarforever/jalcohol-api.git
2. Execute mvn spring-boot:run

The jalcohol-api will be running and listening on port 9527.

In your web browser, open http://localhost:9527 to view homepage.

Click '通过咕咚登录授权' to authorize your codoon sports data access to jalcohol-api.

Visit ```http://localhost:9527/api/codoon/call?method=<get|post>&api=<codoon_api_method_name>``` to call codoon API and get JSON response.

For Example:
```http://localhost:9527/api/codoon/call?method=get&api=verify_credentials```
