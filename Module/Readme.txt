Erik Hendrickson's Database Project 1: Readme

Info:
Erik Hendrickson
U87970756
erikh1@mail.usf.edu

How to compile:

    The files contained in this tar file consist of this readme file, a P1.java file, a Command.java file, and a CommandParser.java file. To compile this program, open a command console window, type "javac *.java" without the quotes, and then type "java P1" again without the quotes. This will start the program.

Some notes about this program:

    Although coaches have a team_Id contained in a field, there is no code in the program to check this foreign key at time of add. This is by design, the examples shown had coaches being loaded first. This would not be possible if the foreign key on coaches was enforced.

    Validation is done on all data, regardless of add or loading. This means that improperly formatted data will never be accepted into the database. When loading, if a specific entry fails in the table, loading will continue on. After loading finishes, errors will show for eah object that was failed to be added.

    Uniqueness is not enforced. This was not given in the spec for the program, so it was not coded in.