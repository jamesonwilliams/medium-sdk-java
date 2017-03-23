# medium-sdk-java
A Java Client for the Medium.com API

## Work in Progress

This is actively under development and should not yet be considered
stable. Gating issues are:

 - There are no tests. Seriously? Ya.
 - To be considered usable, you'd have to be able to use the distributed
   binary form of this software without modifcation. At present, you'd
   have to check out the code and tinker.

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
        .withUserId(user.getId())
        .withTitle("A Great Day for a Java SDK!")
        .withContentFormat(ContentFormat.MARKDOWN)
        .withContent("```Medium medium = new MediumClient(TOKEN);```")
        .withTags(Arrays.asList("sdk"))
        .withPublishStatus(PublishStatus.UNLISTED)
        .withLicense(License.CC_40_BY)
        .withNotifyFollowers(false) // Just a test!
        .build();

    Post post = medium.publishPost(submission);

    System.out.println(String.format(
        "Published \"%s\" to \"%s\" at %s\n",
        post.getTitle(), post.getUrl(), post.getPublishedAt()
    ));

## Dependencies

### Unirest 1.4.9+

The project uses the [Unirest HTTP library][unirest]. If you want to use
something else, you can use your own implementation of the `HttpClient`
interface. The one currently used is `UnirestClient`.

### Jackson 2+

The project is at this point pretty heavily tied into
[Jackson][jackson], as the above dependency used Jackson and also most
of the immutable POJOs have been annotated with Jackson annotations.
Those things considered, you try to use some other serializer (Gson?) by
using your own implementation of the `JsonConverter` interface.

Note: there are ways to avoid all of the Jackson annotations, such as by
using reflection features of Java8, or by not using immutable objects.
Neither seemed worth it, so we just went with the annotations.

[unirest]: https://github.com/Mashape/unirest-java
[jackson]: https://github.com/FasterXML/jackson
[settings]: https://medium.com/me/settings
