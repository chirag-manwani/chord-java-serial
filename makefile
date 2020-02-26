run: compile
	java Main ${ARGS}

compile: Main.java
	javac *.java