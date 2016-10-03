# fabric8-hello

Two microservices using Spring Boot and WildFly Swarm with Camel running in kubernetes using fabric8 tooling

There are three Maven projects:

* client - Spring Boot application with Camel that triggers every 2nd second to call the hello service and log the response.
* client-hystrix - A client that uses Hystrix as circuit breaker EIP when call the hello service. This version uses currently a SNAPSHOT version of Apache Camel as it Camel 2.18.0 has not yet been released.
* helloswarm - WildFly Swarm application hostin a hello service which returns a reply message.

The diagram below illustrates this:

![Overview](diagram.png?raw=true "Overview")


### Running Kubernetes locally

The applications can be deployed in a kubernetes cluster. You can run a kubernetes cluster locally using fabric8 which you can find more details here: [fabric8 get started](https://fabric8.io/guide/getStarted/index.html)

#### Installing from Maven

The fabric8 team has made it very easy to download, install and start a Kubernetes/OpenShift cluster on your computer very easy. All you need to do is from a command line run this Maven goal:

    mvn io.fabric8:fabric8-maven-plugin:3.1.63:cluster-start

And if you want to use OpenShift instead of Kubernetes:

    mvn io.fabric8:fabric8-maven-plugin:3.1.63:cluster-start -Dfabric8.cluster.kind=openshift

Then fabric8 will download the binaries into `<HOME>/.fabric8/bin` directory. It can be a good idea to add this directory to the `$PATH`, so you can easily run the clients to interact with the cluster.

However before we had to install fabric8 using a few more manual steps as explained below.

#### Manually steps

I am using MiniShift to run my local kubernetes cluster. I run the minimal version which do not include the CI/CD pipeline and therefore I run with low memory usage.

    minishift start --memory=2000

... and follow the instructions from minishift.

And then I have installed fabric8 using gofabric8:

    gofabric8 deploy -y --console

... and follow the instructions from fabric8.

If all this is sucesfull you can open the fabric8 web console using:

    minishift service fabric8

### Slides

This source code is used for a talk at the jdkio conference, and you can find the slides for the talk here [slides.pdf](slides.pdf).

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

