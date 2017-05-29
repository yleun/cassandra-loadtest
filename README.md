# run unit tests and integration tests
sbt test it:test

# run
sbt run

# to execute load 
1. download gatling
2. set GATLING_HOME as environment variable add GATLING_HOME/bin to PATH
3. cd loadtest
4. gatling.sh -sf .
5. wait and review results in GATLING_HOME/results/index.html
6. run VisualVM to see leaks and run netstat to see all open TCP/IP connections.
