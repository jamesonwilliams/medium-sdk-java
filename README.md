# medium-sdk-java
A Java Client for the Medium.com API

## Work in Progress

This is actively under development and should not yet be considered
stable. Gating issues are:

 - There are no tests. Seriously? Ya. ```¯\_(ツ)_/¯``` (#YOLO?)
 - Story around eror handling and logging needs improvement
 - Needs a bit of work to be usable on Android
 - Token refresh and upload image are not implemented.
 - `MediumClient.Builder` should exist, to allow customizations of SDK
   features without rebuilding the project.

## Demo

### config
Create the file `./medium-config.json` in your project root:

    {
        "credentials": {
            "clientId": "<YOUR_CLIENT_ID>",
            "clientSecret": "<YOUR_CLIENT_SECRET>"
        },
        "redirectUri": "<CALLBACK_URL_YOU_TOLD_MEDIUM_ABOUT>"
    }

Alternately, if you already have an authorization token (you can get one
on [the Medium settings page][settings]), you can use:

    {
        "accessToken": "<YOUR_ACCESS_TOKEN>",
        "credentials": {
            "clientId": "<YOUR_CLIENT_ID>",
            "clientSecret": "<YOUR_CLIENT_SECRET>"
        },
        "redirectUri": "<CALLBACK_URL_YOU_TOLD_MEDIUM_ABOUT>"
    }

### build:

    mvn install

### run:

    mvn exec:java

## Example Use

### With an existing token
The `SimpleTokenExample` assumes you already have a valid token, which
simplifies setup considerably, as opposed to obtaining one via
credentials.

To get the details of your Medium user account, just do:

    ConfigFile config = new ConfigFileReader("./medium-config.json").read();

    Medium medium = new MediumClient(config.getAccessToken());

    User user = medium.getUser();
    System.out.println("Hello, " + user.getName());

To publish a post:

    // Submit a post for publication.
    Submission submission = new Submission.Builder()
        .withTitle("A Great Day for a Java SDK!")
        .withContentFormat(ContentFormat.MARKDOWN)
        .withContent("```Medium medium = new MediumClient(TOKEN);```")
        .withTags(Arrays.asList("sdk"))
        .withPublishStatus(PublishStatus.UNLISTED)
        .withLicense(License.CC_40_BY)
        .withNotifyFollowers(false) // Just a test!
        .build();

    Post post = medium.createPost(submission, user.getId());

    System.out.println(String.format(
        "Published \"%s\" to \"%s\" at %s\n",
        post.getTitle(), post.getUrl(), post.getPublishedAt()
    ));

### From Just Credentials

The full example is provided in `AuthorizationNegotationExample`. The
key points are:

    // Register a callback to know when we are granted an access token
    listenForAccessToken(new AccessProvider.Observer() {
        @Override
        public void onAccessGranted(final AccessToken token) {
            System.out.println("Sweet, we got a token: " + token.getAccessToken());
            useApiWithToken(token);
        }

        @Override
        public void onAccessError() {
            System.err.println("Oh no. What ever could be wrong?");
        }
    });

That method will start a local HTTP server to listen for requests at the
callback URL. So, in order for this to work, you redirect URI would need
to be something local like `http://127.0.0.1:3333/callback`

The listen method is implemented as:

    private static void listenForAccessToken(final AccessProvider.Observer observer)
            throws IOException {

        ConfigFile config = new ConfigFileReader(CONFIG_FILE_PATH).read();

        // Create a client that just handles authorization
        Medium authClient = new MediumClient(config.getCredentials());

        // Initialize the Local HTTP Access Provider
        AccessProvider accessProvider = new LocalHttpAccessProvider(
            authClient,
            config.getRedirectUri(),
            observer
        );

        // Generate a state cookie
        final String clientState = UUID.randomUUID().toString();

        // Visit the authorization url in your browser
        openUrlInBrowser(authClient.getAuthorizationUrl(
            clientState, config.getRedirectUri(), REQUESTED_ACCESS_SCOPES
        ));

        // Wait around for an authorization code to come in
        accessProvider.listenForAuthorizationCodes();
    }

## Dependencies

### Unirest 1.4.9+

The project uses the [Unirest HTTP library][unirest]. If you want to use
something else, you can use your own implementation of the generic
`HttpClient` interface. The one currently used is `UnirestClient`.

### Jackson 2+

You could use another serializer by implementing the
`JsonModelConverter` interface. However, the model POJOs are currently
wearing Jackson annotations.

Note: there are ways to avoid all of the Jackson annotations, such as:

 - using reflection features of Java8
 - not using immutable objects.

Since it would be a little bold at this point to take on Java 8 as a
dependency, and since immutability is worth the cost of a little extra
syntactic fluff, the current decision seems to be the best.

[unirest]: https://github.com/Mashape/unirest-java
[jackson]: https://github.com/FasterXML/jackson
[settings]: https://medium.com/me/settings

