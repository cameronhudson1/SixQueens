JC 		= javac

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

testErrorsServer:
	java SixQueensServer
	echo ""
	java SixQueensServer $(HOST) notaport
	echo ""
	java SixQueensServer $(HOST) 700000
	echo ""
	java SixQueensServer notanaddress $(PORT)
	echo ""

testErrorsClient:
	java SixQueens
	echo ""
	java SixQueens $(HOST) notaport Cam
	echo ""
	java SixQueens $(HOST) 700000 Cam
	echo ""
	java SixQueens notanaddress $(PORT) Cam
	echo ""

clean:
	rm -f *.class
