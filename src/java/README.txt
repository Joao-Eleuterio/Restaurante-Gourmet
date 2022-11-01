This is a simple example of using java RMI.
The files included are:

SampleClient.java
     client program - looks up "//localhost/Sample" service
     you can replace localhost with the name of another
     server or create this string on the fly if needed.
     Sample is the name of the service on localhost.

Sample.java
     this implements the remote service. It contains
     an empty constructor and a function invert() that takes
     a string as input and returns that string with its characters
     reversed.

SampleInterface.java
    defines the remote interface provided by the service
    Here, it's a single function that accepts a string and
    returns a string.

SampleServer.java
    the server - offers the remote service
    installs a security manager and contacts rmiregistry
    with an instance of the service under the name "Sample"

policy
   a default security policy file. This is required since
   JDK 1.2

===============================
compiling and running the code:

0. Make sure your CLASSPATH environment variable contains
   your current working directory (.) or the full pathname of 
   the directory where these files live. Make sure that CLASSPATH
   is SET IN EACH OF THE THREE WINDOWS that you'll be using and
   that it is exported.
   For example:

	CLASSPATH=$CLASSPATH:.
	export CLASSPATH
	
   The shell environment commands assume that you are using a shell
   such as sh, bash, or ksh. If you are using csh or tcsh, you'll have
   to use the setenv command - check the documentation for those shells.

1. Compile the code
   javac Sample.java  SampleClient.java  SampleInterface.java  SampleServer.java

2. In one window (or in the background), start the registry:
       rmiregistry
  By default rmiregistry listens on port 1099.  This port is likely
  to be in use if you are on a shared machine.
  To run it on another port specify the port number on the command
  line. For example:
       rmiregistry 23123

3. start the server in another window (or in the background)
       java -Djava.security.policy=policy SampleServer 23123
   where the last argument is the port number of the rmi registry

4. run the client in yet another window:
       java SampleClient 23123 ABCDEF "this is just a test"
   where the second argument is the port number of the rmi registry
   You should see the following output (the strings you gave reversed):

FEDCBA
tset a tsuj si siht

===============================
troubleshooting

1. starting rmiregistry

rmiregistry binds to port 1099 by default. If this port is in use,
perhaps by another rmiregistry, you can start the rmiregistry
on another port. For example:
   rmiregistry 2022

2. The client gives you something like: 
Exception in thread "main" java.lang.NoClassDefFoundError: SampleClient

The likely reason for this is that you did not compile the client
or that your CLASSPATH environment is not set correctly. Check that
you have a file called SampleClient.class.

Check that your CLASSPATH is set and exported for the rmiregistry,
the server, and the client.

3. You get something like this when you start the server:
Exception in thread "main" java.lang.NoClassDefFoundError: Sample

Check that Sample.java is compiled (you have a Sample.class file) and
that the CLASSPATH includes the directory in which that file is contained.

4.  If you get a message such as:
Sample server failed:access denied (java.net.SocketPermission 127.0.0.1:23123 connect,resolve)
you might not have specified a policy file with the -D flag when you started the server:
	java -Djava.security.policy=policy_file_name class_name arguments ...
Java doesn't complain if policy_file_name is missing.

