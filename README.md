# rms-admin-login-service
1. The login service requires the config service to be running in order for it to function

2. Data.sql contains a dummy login with test@revature.com for username and password for password for testing with h2 database.

3. All admins stored in the login database will be received from the registration service once a new user is confirmed.

4. The endpoints used in the service are createAdmin to save a new admin to the database when received from the registration service and loginPost which to login and return an admin to the front-end if they are authenticated.
