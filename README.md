# medium-sdk-java
A Java Client for the Medium.com API

## Work in Progress
This is actively under development and should not yet be considered
stable.

## Howto

### config
Create the file `./medium-config.json` in your project root:

    {
        "credentials": {
            "clientId": "<YOUR_CLIENT_ID>",
            "clientSecret": "<YOUR_CLIENT_SECRET>"
        },
        "redirectUri": "<CALLBACK_URL_YOU_TOLD_MEDIUM_ABOUT>"
    }

Note that currently `redirectUri` is not respected and the value
`https://127.0.0.1:9000/Callback` is hardcoded. This will be fixed
shortly.

### build:

    mvn install

### run:

    mvn exec:java


