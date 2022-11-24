# SFU Transiter

## About
The ultimate transit app for SFU Students.
Rate, comment, ride. Use this app to view top rated buses and see what other people think about the ride.

### Features
- Display live user ratings and reviews for each bus stops for 143, 144, 145
- Display ETA of the buses for a desired bus stop
- Display entire schedule for a desired bus
- Provide favorite buses or bus stops database

### How it works
Utilize TransLink’s REST API to receive current bus positions and ETA
Use network communications to post comments and/or stream other user comments and ratings
Single bus commutes, no scheduling between buses

### Team members
- Luke (In Ho) Seo
- Johnny Doan
- Kenny Mim
- Myron Yung

## Getting started to developing
Translink API key is required:
1. clone the reposiory to your work folder
2. you will need to register for a translink API key at https://www.translink.ca/about-us/doing-business-with-translink/app-developer-resources/register, sign in and it should email you your key.
3. create `keys.properties` in the `app` level directory and put `TRANSLINK_API_KEY="YOUR_API_KEY"` (replace YOUR_API_KEY with your key you registered)
    1. To create this file easily in Android Studio, right-click the app directory,
    2. New -> Resource Bundle
    3. Enter in `keys` for Resource bundle base name field
    4. click ok
    5. `keys.properties` should be created and then paste the key in as intructed from step 3.
4. you are ready to use the Translink api and develop! 😀

see https://www.translink.ca/about-us/doing-business-with-translink/app-developer-resources for more information

## Disclaimer
Some of the data used in this product or service is provided by permission of TransLink. TransLink assumes no responsibility for the accuracy or currency of the Data used in this product or service.
