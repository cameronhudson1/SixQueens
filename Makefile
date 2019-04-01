JC 		= javac
SQ		= SixQueens.java
SQS 	= SixQueensServer.java
SQV 	= SixQueensView.java
SQJPL	= SixQueensJPanelListener.java
SQJP 	= SixQueensJPanel.java
ML		= ModelListener.java
VL		= ViewListener.java 
VP		= ViewProxy.java 
MP		= ModelProxy.java

HOST	= localhost
PORT	= 5678

.SILENT:

all:
	$(JC) -Xlint *.java

startServer:
	java SixQueensServer $(HOST) $(PORT)

startClient1:
	java SixQueens $(HOST) $(PORT) Cam

startClient2:
	java SixQueens $(HOST) $(PORT) Ryan

clean:
	rm -f *.class
