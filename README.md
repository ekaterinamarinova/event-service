# event-service project
*This project represents a simple OSGi app with 4 bundles that work together but aren't tightly coupled.*
## Bundles
- Bundle *api* contains all resources shared between bundles;
- Bundle *a* contains the three main services' implementations - Logging, Monitoring and Retrieving service;
- Bundle *b* uses the LoggingService to log events in a new thread with a rate of 100 events per second;
- Bundle *c* uses MonitoringService to fetch events in real-time and RetrievingService to fetch the events of the last 120 seconds and store them in a csv called Events.csv

## Services
- LoggingService has a simple *logEvent* method that saves a LoggingEvent in a list;
- RetrievingService has a *retrieve* method that when passed the correct params returns the stored events from the last 120 seconds;
- MonitoringService has an *addMonitoringListener* method that accepts a MonitoringListener object; by adding a listener the user of the MonitoringService has a guarantee that whenever an event is received in the LoggingService, the listener shall be notified and show that in the console;
- StorageService has a *storeEventsInCSV* method that when passed a list of events appends them to the Events.csv file.

## External Dependencies

- [OSGi 6.0](https://mvnrepository.com/artifact/org.osgi/org.osgi.core/6.0.0)
- [Apache Felix 1.0](https://mvnrepository.com/artifact/org.apache.felix/org.osgi.core/1.0.0)

## Plugins

- [Maven Compiler Plugin 3.8.1](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-compiler-plugin/3.8.1)
- [Maven Bundle  Plugin 3.3.0](https://felix.apache.org/documentation/subprojects/apache-felix-maven-bundle-plugin-bnd.html)