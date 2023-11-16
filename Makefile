JCFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JCFLAGS) $<

# Use a wildcard to find all Java source files in the current directory
SRCS = gatorLibrary.java BookNode.java BookPriorityQueue.java LibraryActionConstant.java RedBlackMethod.java RedBlackNode.java BookWaitList.java GatorLibServices.java NilNode.java

# Convert Java source files to class files
CLASSES = $(SRCS:.java=.class)

default: clean classes

classes: $(CLASSES)

clean:
	$(RM) *.class

