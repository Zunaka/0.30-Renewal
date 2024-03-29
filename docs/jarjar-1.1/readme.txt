java -jar jarjar.jar [help]
Prints this help message.

java -jar jarjar.jar strings <cp>
Dumps all string literals in classpath <cp>. Line numbers will be included if the classes have debug information.

java -jar jarjar.jar find <level> <cp1> [<cp2>]
Prints dependencies on classpath <cp2> in classpath <cp1>. If <cp2> is omitted, <cp1> is used for both arguments.

The level argument must be class or jar. The former prints dependencies between individual classes, while the latter only prints jar->jar dependencies. A "jar" in this context is actually any classpath component, which can be a jar file, a zip file, or a parent directory (see below).

java -jar jarjar.jar process <rulesFile> <inJar> <outJar>
Transform the <inJar> jar file, writing a new jar file to <outJar>. Any existing file named by <outJar> will be deleted.

The transformation is defined by a set of rules in the file specified by the rules argument (see below).

Classpath format
The classpath argument is a colon or semi-colon delimited set (depending on platform) of directories, jar files, or zip files. See the following page for more details: http://java.sun.com/j2se/1.5.0/docs/tooldocs/solaris/classpath.html

Mustang-style wildcards are also supported: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6268383

Rules file format
The rules file is a text file, one rule per line. Leading and trailing whitespace is ignored. There are three types of rules:

rule <pattern> <result>
zap <pattern>
keep <pattern>
The standard rule (rule) is used to rename classes. All references to the renamed classes will also be updated. If a class name is matched by more than one rule, only the first one will apply.

<pattern> is a class name with optional wildcards. ** will match against any valid class name substring. To match a single package component (by excluding . from the match), a single * may be used instead.

<result> is a class name which can optionally reference the substrings matched by the wildcards. A numbered reference is available for every * or ** in the <pattern>, starting from left to right: @1, @2, etc. A special @0 reference contains the entire matched class name.

The zap rule causes any matched class to be removed from the resulting jar file. All zap rules are processed before renaming rules.

The keep rule marks all matched classes as "roots". If any keep rules are defined all classes which are not reachable from the roots via dependency analysis are discarded when writing the output jar. This is the last step in the process, after renaming and zapping.