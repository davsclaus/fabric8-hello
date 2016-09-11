# fabric8-hello

Two microservices using Spring Boot and WildFly Swarm with Camel running in kubernetes using fabric8 tooling

There are two Maven projects:

* client - Spring Boot application with Camel that triggers every 2nd second to call the hello service and log the response.
* helloswarm - WildFly Swarm application hostin a hello service which returns a reply message.

The two applications can be deployed in a kubernetes cluster. You can run a kubernetes cluster locally using fabric8 which you can find more details here: [fabric8 get started](https://fabric8.io/guide/getStarted/index.html)

I am using MiniShift to run my local kubernetes cluster. I run the minimal version which do not include the CI/CD pipeline and therefore I run with low memory usage.

    minishift start --memory=2000

... and follow the instructions from minishift.

And then I have installed fabric8 using gofabric8 (yes the --app= should be empty to install the minimal):

    gofabric8 deploy -y  --domain=$(minishift ip).xip.io  --api-server=$(minishift ip) --app=

... and follow the instructions from fabric8.

If all this is sucesfull you can open the fabric8 web console using:

    minishift service fabric8


### Prepare shell

When using Maven tooling you want to setup your command shell for docker/kubernetes which can be done by

    minishift docker-env

Which tells you how to setup using eval

    eval $(minishift docker-env)


### Deploying WildFly Swarm (server)

You can deploy the WildFly Swarm application which hosts the hello service.

    cd helloswarm
    mvn install

If the build is success you can deploy to kubernetes using:

    mvn fabric:run


### Deploying Spring Boot (client)

You can deploy the Spring Boot application which is the client calling the hello service

    cd client
    mvn install

If the build is success you can deploy to kubernetes using:

    mvn fabric:run

You should then be able to show the logs of the client, by running `oc get pods` and find the name of the pod that runs the client, and then use `oc logs -f pod-name` to follow the logs.

