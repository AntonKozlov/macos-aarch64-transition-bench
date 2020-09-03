
OS ?= $(shell uname)
JDK_MD_INCLUDE_Linux  := linux
JDK_MD_INCLUDE_Darwin := darwin
LIBEXT_Linux  := .so
LIBEXT_Darwin := .dylib

CFLAGS := -I$(JAVA_HOME)/include/ -I$(JAVA_HOME)/include/$(JDK_MD_INCLUDE_$(OS))

libMyBenchmark$(LIBEXT_$(OS)) : libMyBenchmark.o
	$(CC) -shared -fPIC -L$(JAVA_HOME)/lib/server/ -o $@ $^ -ljvm

clean :
	rm -f libMyBenchmark$(LIBEXT_$(OS)) libMyBenchmark.o
