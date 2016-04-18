# AppDirect Integration Project
This repository contains the code for the AppDirect integration project, part of the code challenge.

## Running the App
Java 8 is required to run the application.  The application will attempt to bind to port 8080.

### Method 1

If you require additional JVM args, use the second method.

To start the application, you can run the following command:

`gradlew runApp -Pkey="<KEY>" -Psecret="<SECRET>"`

where...

`<KEY>` is the consumer key to verify incoming AppDirect notifications, and sign outgoing requests to AppDirect

`<SECRET>` is the consumer key to verify incoming AppDirect notifications, and sign outgoing requests to AppDirect

### Method 2
If you require additional JVM args.

```
gradlew build
java -jar ./build/libs/AppDirectIntegrationProrotype.jar --appdirect.key=<KEY> --appdirect.secret=<SECRET>
```

where...

`<KEY>` is the consumer key to verify incoming AppDirect notifications, and sign outgoing requests to AppDirect

`<SECRET>` is the consumer key to verify incoming AppDirect notifications, and sign outgoing requests to AppDirect
